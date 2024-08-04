package com.template_server.user.controller;

import com.template_server.user.dto.UserDTO;
import com.template_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity login(@PathVariable String id){
        UserDTO responseDTO = service.getOne(id);
        if(Objects.nonNull(responseDTO)){
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.badRequest().build();
    }

}
