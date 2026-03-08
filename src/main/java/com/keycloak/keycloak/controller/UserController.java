package com.keycloak.keycloak.controller;



import com.keycloak.keycloak.dto.request.CreateUserDTO;
import com.keycloak.keycloak.entity.User;
import com.keycloak.keycloak.service.KeyCloakAdminService;
import com.keycloak.keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")

public class UserController {

    private final UserService userService;
    private final KeyCloakAdminService keyCloakAdminService;

    @GetMapping("/{keycloakId}")
    public User getUser(@PathVariable String keycloakId){
        return userService.getUser(keycloakId);
    }

    @PostMapping("/{keycloakId}")
    public User update(@RequestBody User userUpdate,@PathVariable String keycloakId){
        return userService.updateUser(userUpdate, keycloakId);
    }

    @PostMapping("/create")
    public User create(@RequestBody CreateUserDTO dto){
        return keyCloakAdminService.createUser(
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail()
        );
    }

}
