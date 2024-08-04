package com.template_server.user.service;

import com.template_server.user.domain.User;
import com.template_server.user.dto.UserDTO;
import com.template_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    public UserDTO getOne(String id){
        Optional<User> user = repository.findById(id);
        return modelMapper.map(user, UserDTO.class);
    }
}
