package com.keycloak.keycloak.repository;

import com.keycloak.keycloak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByKeycloakId(String keycloakId);
}
