package com.keycloak.keycloak.controller;



import com.keycloak.keycloak.dto.request.CreateUserDTO;
import com.keycloak.keycloak.dto.request.UpdateUserDTO;
import com.keycloak.keycloak.entity.User;
import com.keycloak.keycloak.service.KeyCloakAdminService;
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
    private final KeyCloakAdminService keyCloakAdminService;

    @GetMapping("/profile")
    public User getUser(@AuthenticationPrincipal Jwt jwt){
        String keycloakId = jwt.getSubject();
        return userService.getUser(keycloakId);
    }

    @PostMapping("/update")
    public User update(@RequestBody UpdateUserDTO userUpdate, @AuthenticationPrincipal Jwt jwt){
        String keycloakId = jwt.getSubject();
        return userService.updateUser(userUpdate, keycloakId);
    }

    @PostMapping("/create")
    public User create(@RequestBody CreateUserDTO dto){
        return keyCloakAdminService.createUser(
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail(),
                dto.getFirstName(),
                dto.getLastName()
        );
    }

}
