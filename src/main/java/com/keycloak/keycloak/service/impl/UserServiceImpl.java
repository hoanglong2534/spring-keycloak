package com.keycloak.keycloak.service.impl;

import com.keycloak.keycloak.entity.User;
import com.keycloak.keycloak.repository.UserRepository;
import com.keycloak.keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User updateUser(User userUpdate, String keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId);
        if (user == null) {
           throw new RuntimeException("User not found");
        }

        user.setAddress(userUpdate.getAddress());
        user.setAvatar(userUpdate.getAvatar());
        return userRepository.save(user);
    }

    @Override
    public User getUser(String keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId);
        if (user == null) {
           throw new RuntimeException("User not found");
        }
        return user;
    }
}
