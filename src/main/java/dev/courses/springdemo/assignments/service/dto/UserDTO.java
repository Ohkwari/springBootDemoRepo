package dev.courses.springdemo.assignments.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private final Long id;
    private String name;
    private Integer age;
    private Integer heightInCm;

}
