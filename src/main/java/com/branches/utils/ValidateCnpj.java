package com.branches.utils;

import com.branches.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ValidateCnpj {

    public void execute(String cnpj) {
        // Valida se contém apenas números
        if (!cnpj.matches("\\d+")) {
            throw new BadRequestException("CNPJ deve conter apenas números");
        }

        if (cnpj.length() == 14) {
//            validateCnpj(cnpj);

            return;
        }

        throw new BadRequestException("CNPJ deve ter 14 dígitos");
    }

    private void validateCnpj(String cnpj) {
        // Verifica se todos os dígitos são iguais
        if (cnpj.matches("(\\d)\\1{13}")) {
            throw new BadRequestException("CNPJ inválido");
        }

        try {
            // Calcula o primeiro dígito verificador
            int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weight1[i];
            }
            int firstDigit = sum % 11;
            firstDigit = firstDigit < 2 ? 0 : 11 - firstDigit;

            // Calcula o segundo dígito verificador
            int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            sum = 0;
            for (int i = 0; i < 13; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weight2[i];
            }
            int secondDigit = sum % 11;
            secondDigit = secondDigit < 2 ? 0 : 11 - secondDigit;

            // Verifica se os dígitos calculados correspondem aos informados
            if (Character.getNumericValue(cnpj.charAt(12)) != firstDigit ||
                Character.getNumericValue(cnpj.charAt(13)) != secondDigit) {
                throw new BadRequestException("CNPJ inválido");
            }
        } catch (Exception e) {
            throw new BadRequestException("CNPJ inválido");
        }
    }
}

