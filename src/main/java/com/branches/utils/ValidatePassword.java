package com.branches.utils;

import com.branches.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ValidatePassword {
    public void execute(String password) {
        if (password.length() < 6) {
            throw new BadRequestException("A senha deve ter no mínimo 6 caracteres.");
        }

        String passwordPattern = "^[a-zA-Z0-9_]+$";
        if (!password.matches(passwordPattern)) {
            throw new BadRequestException("A senha pode conter somente letras, números e o caractere '_'");
        }
    }
}
