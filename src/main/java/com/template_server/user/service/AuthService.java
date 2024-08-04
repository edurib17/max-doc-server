package com.template_server.user.service;

import com.template_server.infra.security.TokenService;
import com.template_server.user.domain.User;
import com.template_server.user.dto.LoginRequestDTO;
import com.template_server.user.dto.RegisterRequestDTO;
import com.template_server.user.dto.ResponseDTO;
import com.template_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public Optional<ResponseDTO> login(@RequestBody LoginRequestDTO body){
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return Optional.of(new ResponseDTO(user.getName(), token));
        }
        return Optional.empty();
    }

    public Optional<ResponseDTO> register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.repository.findByEmail(body.email());
        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return Optional.of(new ResponseDTO(newUser.getName(), token));
        }else{
            return Optional.empty();
        }
    }
}
