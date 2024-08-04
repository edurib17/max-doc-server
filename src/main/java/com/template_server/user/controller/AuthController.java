package com.template_server.user.controller;
import com.template_server.user.dto.LoginRequestDTO;
import com.template_server.user.dto.RegisterRequestDTO;
import com.template_server.user.dto.ResponseDTO;
import com.template_server.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        Optional<ResponseDTO> responseDTO = service.login(body);
        if(Objects.nonNull(responseDTO)){
            return ResponseEntity.ok(responseDTO.get());
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<ResponseDTO> responseDTO = service.register(body);
        if(Objects.nonNull(responseDTO) && responseDTO.isPresent()) {
            return ResponseEntity.ok(responseDTO.get());
        }
        return ResponseEntity.badRequest().build();
    }
}
