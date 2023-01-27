package dev.courses.springdemo.assignments.service;

import dev.courses.springdemo.assignments.controller.error.NotFoundException;
import dev.courses.springdemo.assignments.gateway.swapi.StarWarsGateway;
import dev.courses.springdemo.assignments.gateway.swapi.StarWarsPeople;
import dev.courses.springdemo.assignments.repository.UserRepository;
import dev.courses.springdemo.assignments.repository.model.User;
import dev.courses.springdemo.assignments.service.dto.UserDTO;
import dev.courses.springdemo.assignments.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.courses.springdemo.assignments.service.mapper.UserMapper.toEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StarWarsGateway swGateway;


    public UserDTO getById(Long id) {
        log.info("getById");
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
        return UserMapper.toDto(user);
    }
    public UserDTO getStarWarsPeopleById(Long id) {
        log.info("getStarWarsPeopleById");
        StarWarsPeople swPeople = swGateway.getPeopleById(id);
        User swUser = userRepository.save(toEntity(swPeople));
        return UserMapper.toDto(swUser);
    }

    public List<UserDTO> getAll() {
        log.info("getAll");
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public UserDTO createUser(UserDTO userDTO) {
        log.info("createUser");
        User savedUser = userRepository.save(toEntity(userDTO));
        return UserMapper.toDto(savedUser);
    }

    public UserDTO updateUserById(Long id, UserDTO userDTO) {
        log.info("updateUser");
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
        userToUpdate.setAge(userDTO.getAge());
        userToUpdate.setHeightInCm(userDTO.getHeightInCm());
        userToUpdate.setName(userDTO.getName());
        User userUpdated = userRepository.save(userToUpdate);
        return UserMapper.toDto(userUpdated);
    }

    public void deleteById(Long id) {
        log.info("deleteById");
        getById(id);
        userRepository.deleteById(id);
    }
}
