package com.keycloak.keycloak.service;

import com.keycloak.keycloak.entity.User;

public interface KeyCloakAdminService {

    User createUser(String username, String password, String email, String firstName, String lastName);
}
