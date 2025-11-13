package com.branches.user.controller;

import com.branches.user.dto.request.ExistsUserByEmailRequest;
import com.branches.user.dto.response.ExistsUserByEmailResponse;
import com.branches.user.service.ExistsUserByEmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ExistsUserByEmailController {
    private final ExistsUserByEmailService existsUserByEmailService;

    @GetMapping("/api/users/exists-by-email")
    public ResponseEntity<ExistsUserByEmailResponse> execute(@RequestBody @Valid ExistsUserByEmailRequest request) {
        boolean response = existsUserByEmailService.execute(request.email());

        return ResponseEntity.ok(new ExistsUserByEmailResponse(response));
    }
}
