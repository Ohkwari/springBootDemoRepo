package dev.courses.springdemo.assignments.controller;

import dev.courses.springdemo.assignments.gateway.swapi.StarWarsGateway;
import dev.courses.springdemo.assignments.gateway.swapi.StarWarsPeople;
import dev.courses.springdemo.assignments.service.UserService;
import dev.courses.springdemo.assignments.service.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@RestController
@RequestMapping(value = "users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    @Operation(description = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public UserDTO getById(@PathVariable
                           @Parameter(description = "Existing user ID")
                           long id) {
        return userService.getById(id);
    }
    @PostMapping("sw")
    public UserDTO getStarWarsPeopleById(
            @Min(1)
            @Max(83)
            @RequestParam
            @Valid
            @Parameter(description = "ID of a Star Wars character. [Min = 1 - Max = 83]")
                           long id) {
        return userService.getStarWarsPeopleById(id);
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping("{id}")
    public UserDTO updateUserById(@Valid @PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.updateUserById(id, userDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}