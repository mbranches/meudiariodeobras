package com.branches.auth.controller;

import com.branches.auth.dto.request.LoginRequest;
import com.branches.auth.dto.response.LoginResponse;
import com.branches.auth.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<LoginResponse> execute(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = loginService.execute(request);

        return ResponseEntity.ok(response);
    }
}
