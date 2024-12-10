package com.ecl.punch.auth;

import com.ecl.punch.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticatonController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody com.ecl.punch.models.User request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(service.authenticate(request));

    }

    @PostMapping("/microsoft")
    public ResponseEntity<AuthenticationResponse> microsoftAuthentication(
            @RequestBody com.ecl.punch.models.User request
    ) {
        return ResponseEntity.ok(service.microsoftAuthentication(request));

    }

}