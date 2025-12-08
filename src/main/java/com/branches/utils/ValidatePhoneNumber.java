package com.branches.utils;

import com.branches.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ValidatePhoneNumber {
    public void execute(String telefone) {
        //somente numeros com 10 ou 11 digitos
        if (!telefone.matches("\\d{10,11}")) {
            throw new BadRequestException("Telefone inválido. Deve conter apenas números e ter 10 ou 11 dígitos");
        }

    }
}
