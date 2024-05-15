package com.semillero.ecosistema.controllers;

import com.semillero.ecosistema.dtos.UserDto;
import com.semillero.ecosistema.exceptions.ValidateTokenException;
import com.semillero.ecosistema.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login/oauth2")
public class OAuth2Controller {

    private final UserAuthService userAuthService;

    @Autowired
    public OAuth2Controller(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/code/google")
    public Mono<ResponseEntity<Map>> handleGoogleCode(@RequestBody Map<String, Object> payload) {
        String code = (String) payload.get("code");

        return userAuthService.getGoogleOauth2AccessToken(code)
                .map(tokenResponse   -> {
                    String authHeader = tokenResponse.getToken_type() + " " + tokenResponse.getId_token();
                    try {
                        Map authUser = userAuthService.authUser(authHeader);
                        return ResponseEntity.ok().body(authUser);
                    } catch (GeneralSecurityException | IOException e) {
                        throw new ValidateTokenException("Failed to validate token");
                    }
                })
                .onErrorResume(e -> {
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
                    errorResponse.put("exception", e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse));
                });
    }
}