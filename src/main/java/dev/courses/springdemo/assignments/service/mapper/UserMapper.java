package dev.courses.springdemo.assignments.service.mapper;

import dev.courses.springdemo.assignments.gateway.swapi.StarWarsPeople;
import dev.courses.springdemo.assignments.gateway.swapi.utils.SWDateUtils;
import dev.courses.springdemo.assignments.repository.model.User;
import dev.courses.springdemo.assignments.service.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDto(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .heightInCm(entity.getHeightInCm())
                .build();
    }

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .age(userDTO.getAge())
                .heightInCm(userDTO.getHeightInCm())
                .build();
    }

    public static User toEntity(StarWarsPeople swPeople) {
        return User.builder()
                .name(swPeople.getName())
                .age(SWDateUtils.getAgeFromDateOfBirth(swPeople.getBirthYear()))
                .heightInCm(swPeople.getHeight())
                .build();
    }

}
