package com.keycloak.keycloak.controller;



import com.keycloak.keycloak.dto.request.CreateUserDTO;
import com.keycloak.keycloak.dto.request.UpdateUserDTO;
import com.keycloak.keycloak.entity.User;
import com.keycloak.keycloak.service.KeyCloakAdminService;
import com.keycloak.keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.keycloak.keycloak.storage.StorageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")

public class UserController {

    private final UserService userService;
    private final KeyCloakAdminService keyCloakAdminService;
    private final StorageService storageService;

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

    @Value("${minio.bucket.name}")
    private String minioBucketName;

    @PostMapping(value = "/upload-avatar", consumes = "multipart/form-data")
    public String uploadAvatar(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        String fileName = keycloakId + "_" + file.getOriginalFilename();
        try {
            storageService.uploadFile(minioBucketName, fileName, file.getInputStream(), file.getContentType());
            return "http://localhost:9000/" + minioBucketName + "/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
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
