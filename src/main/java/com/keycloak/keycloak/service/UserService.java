package com.keycloak.keycloak.service;

import com.keycloak.keycloak.entity.User;
import org.springframework.security.oauth2.jwt.Jwt;

public interface UserService {

    User updateUser(User userUpdate, String keycloakId);

    User getUser(String keycloakId);

}
