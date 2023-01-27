package dev.courses.springdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import dev.courses.springdemo.assignments.gateway.swapi.StarWarsGateway;
import dev.courses.springdemo.assignments.gateway.swapi.StarWarsPeople;
import dev.courses.springdemo.assignments.repository.UserRepository;
import dev.courses.springdemo.assignments.repository.model.User;
import dev.courses.springdemo.assignments.service.dto.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.UnsupportedEncodingException;

import static com.fasterxml.jackson.databind.cfg.ConstructorDetector.USE_PROPERTIES_BASED;
import static dev.courses.springdemo.assignments.gateway.swapi.utils.SWDateUtils.getAgeFromDateOfBirth;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringdemoApplication.class)
@AutoConfigureMockMvc
@EnableWebMvc
@ComponentScan("dev.courses.springdemo")
public class UserControllerIntTest {
    public static final int USER_AGE = 12;
    public static final String USER_NAME = "Bob";
    public static final int USER_HEIGHT = 67;
    public static final String USERS_PATH = "/users";
    public static final Long PEOPLE_ID = 1L;
    public static final String SW_NAME = "Luke Skywalker";
    public static final String SW_BIRTH_YEAR = "19BBY";
    public static final int SW_HEIGHT = 77;
    public static final String SW_USER_PATH = "/users/sw";
    private static final Integer SW_AGE = 69;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private StarWarsGateway starWarsGateway;

    @Test
    void createUser_withGoodData_shouldReturnSavedUser() throws Exception {
        // init test
        int usersBefore = userRepository.findAll().size();

        //do all the tests
        var request = UserDTO.builder()
                .age(USER_AGE)
                .name(USER_NAME)
                .heightInCm(USER_HEIGHT)
                .build();

        //make request
        ResultActions resultActions = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());

        // read response
        UserDTO response = deserializeResult(resultActions, UserDTO.class);

        // assertions
        assertNotNull(response.getId());
        assertEquals(USER_NAME, response.getName());
        assertEquals(USER_AGE, response.getAge());
        assertEquals(USER_HEIGHT, response.getHeightInCm());

        // database assertions
        int usersAfter = userRepository.findAll().size();
        assertEquals(usersBefore + 1, usersAfter);
        assertEquals(1, usersAfter);
        assertNotNull(userRepository.findById(response.getId()).get());
    }

    @Test
    void getUserById_withExistingUser_shouldGetUser() throws Exception {
        //do all the tests
        User savedUser = userRepository.save(User.builder()
                .age(USER_AGE)
                .name(USER_NAME)
                .heightInCm(USER_HEIGHT)
                .build());

        //make request
        ResultActions resultActions = mockMvc.perform(
                get(USERS_PATH + "/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // read response
        UserDTO response = deserializeResult(resultActions, UserDTO.class);
        assertEquals(response.getId(),response.getId());

        // assertions
        assertEquals(savedUser.getId(), response.getId());
        assertEquals(USER_NAME, response.getName());
        assertEquals(USER_AGE, response.getAge());
        assertEquals(USER_HEIGHT, response.getHeightInCm());

    }

    @Test
    void createStarWArsUser_withExistingSWCharacter_ShouldCreateSWUser() throws Exception {

        Mockito.when(starWarsGateway.getPeopleById(PEOPLE_ID))
                .thenReturn(StarWarsPeople.builder()
                        .name(SW_NAME)
                        .birthYear(SW_BIRTH_YEAR)
                        .height(SW_HEIGHT)
                        .build());

        //make request
        ResultActions resultActions = mockMvc.perform(
                post(SW_USER_PATH)
                        .param("id",String.valueOf(PEOPLE_ID))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // read response
        UserDTO response = deserializeResult(resultActions, UserDTO.class);
        assertEquals(response.getId(),response.getId());

        // assertions
        assertNotNull(response.getId());
        assertEquals(SW_NAME, response.getName());
        assertEquals(SW_AGE, getAgeFromDateOfBirth(SW_BIRTH_YEAR));
        assertEquals(SW_HEIGHT, response.getHeightInCm());

    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = JsonMapper.builder()
                    .addModule(new ParameterNamesModule())
                    .constructorDetector(USE_PROPERTIES_BASED)
                    .build();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T deserializeResult(ResultActions resultActions, Class<T> clazz)
            throws JsonProcessingException, UnsupportedEncodingException {
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        return objectMapper.readValue(contentAsString, clazz);
    }
    @AfterEach
    public void clean() {
        userRepository.deleteAll();
    }
}
