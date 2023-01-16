package dev.courses.springdemo.assignments.service;

import dev.courses.springdemo.assignments.service.dto.UserDTO;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class UserService {


    public UserDTO getById(Long id) {
        System.out.println("getById");
        return null;
    }

    public List<UserDTO> getAll() {
        System.out.println("getAll");
        return null;
    }

    public UserDTO createUser(UserDTO userDTO) {
        System.out.println("createUser");
        return null;
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        System.out.println("updateUser");
        return null;
    }

    public void deleteById(Long id) {
        System.out.println("deleteById");
    }
}
