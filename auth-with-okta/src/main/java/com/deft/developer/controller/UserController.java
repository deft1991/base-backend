package com.deft.developer.controller;

import com.deft.developer.dto.OAuth2UserCreateDto;
import com.okta.sdk.authc.credentials.ClientCredentials;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.AuthorizationMode;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/*
 * Created by sgolitsyn on 4/17/20
 */
@RestController
public class UserController {
    private ClientRegistration registration;

    public UserController(ClientRegistrationRepository registrations) {
        this.registration = registrations.findByRegistrationId("okta");
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return new ResponseEntity<>("", HttpStatus.OK);
        } else {
            return ResponseEntity.ok().body(user.getAttributes());
        }
    }

    @PostMapping("/api/users")
    public ResponseEntity<?> createUser(OAuth2UserCreateDto userCreateDto) {
        if (userCreateDto == null) {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        } else {
            Client client = Clients.builder()
                    .setOrgUrl("https://dev-991236.okta.com")
                    .setAuthorizationMode(AuthorizationMode.SSWS)
                    .setClientId("0oa5ksbd9t1I3g6ND4x6")
                    .setScopes(new HashSet<>(Arrays.asList("okta.users.read", "okta.apps.read")))
                    .setClientCredentials(new TokenClientCredentials("00Aa8K_n7O_WLXM8LNKWkpS-m07sLtV7dLDSs6RIUC"))

                    .build();
            User user = UserBuilder.instance()
                    .setEmail("qweqwe@mail.ru")
                    .setFirstName("Joe")
                    .setLastName("Coder")
                    .setPassword("!1Abcdef".toCharArray())
                    .setSecurityQuestion("Favorite security question?")
                    .setSecurityQuestionAnswer("None of them!")
                    .putProfileProperty("division", "Seven") // key/value pairs predefined in the user profile schema
                    .setActive(true)
                    .buildAndCreate(client);
            return ResponseEntity.ok().body(user);
        }
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(HttpServletRequest request,
                                    @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken) {
        // send logout URL to client so they can initiate logout
        String logoutUrl = this.registration.getProviderDetails()
                .getConfigurationMetadata().get("end_session_endpoint").toString();

        Map<String, String> logoutDetails = new HashMap<>();
        logoutDetails.put("logoutUrl", logoutUrl);
        logoutDetails.put("idToken", idToken.getTokenValue());
        request.getSession(false).invalidate();
        return ResponseEntity.ok().body(logoutDetails);
    }
}
