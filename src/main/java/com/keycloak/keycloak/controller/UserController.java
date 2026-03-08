package com.keycloak.keycloak.controller;


import com.keycloak.keycloak.entity.User;
import com.keycloak.keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")

public class UserController {

    private final UserService userService;

    @GetMapping("/{keycloakId}")
    public User getUser(@PathVariable String keycloakId){
        return userService.getUser(keycloakId);
    }

    @PostMapping("/{keycloakId}")
    public User update(@RequestBody User userUpdate,@PathVariable String keycloakId){
        return userService.updateUser(userUpdate, keycloakId);
    }

    @PostMapping
    public User create(@AuthenticationPrincipal Jwt jwt){
        return userService.createUser(jwt);
    }

}
