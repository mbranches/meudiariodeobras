package com.branches.auth.controller;

import com.branches.auth.dto.request.RegisterRequest;
import com.branches.auth.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/api/auth/register")
    public ResponseEntity<Void> execute(@RequestBody @Valid RegisterRequest request) {
        registerService.execute(request);

        return ResponseEntity.noContent().build();
    }
}
