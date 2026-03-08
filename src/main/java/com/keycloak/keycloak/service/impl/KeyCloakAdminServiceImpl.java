package com.keycloak.keycloak.service.impl;

import com.keycloak.keycloak.entity.User;
import com.keycloak.keycloak.repository.UserRepository;
import com.keycloak.keycloak.service.KeyCloakAdminService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyCloakAdminServiceImpl implements KeyCloakAdminService {

    @Value("${idp.realm}")
    private String realm;

    private final Keycloak keycloak;
    private final UserRepository userRepository;

    @Override
    public User createUser(String username, String password, String email, String firstName, String lastName) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(username);
        userRepresentation.setEmail(email);
        userRepresentation.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        credentialRepresentation.setTemporary(false);
        userRepresentation.setEmailVerified(true);
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        Response userResponse = keycloak.realm(realm).users().create(userRepresentation);

        if(userResponse.getStatus() == 201){
            String keycloakId = userResponse.getHeaderString("Location");
            keycloakId = keycloakId.substring(keycloakId.lastIndexOf("/") + 1);

            User user = new User();
            user.setKeycloakId(keycloakId);
            user.setUsername(username);
            user.setEmail(email);
            return userRepository.save(user);
        }
        return null;
    }
}
