package com.template_server.user.repository;

import com.template_server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
}
