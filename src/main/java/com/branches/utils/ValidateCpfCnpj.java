package com.branches.utils;

import com.branches.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ValidateCpfCnpj {

    public void execute(String cpfCnpj) {
        // Valida se contém apenas números
        if (!cpfCnpj.matches("\\d+")) {
            throw new BadRequestException("CPF/CNPJ deve conter apenas números");
        }

        // Verifica o tamanho e chama a validação apropriada
        if (cpfCnpj.length() == 11) {
            validateCpf(cpfCnpj);

            return;
        }

        if (cpfCnpj.length() == 14) {
            validateCnpj(cpfCnpj);

            return;
        }

        throw new BadRequestException("CPF/CNPJ inválido. CPF deve ter 11 dígitos e CNPJ deve ter 14 dígitos");
    }

    private void validateCpf(String cpf) {
        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            throw new BadRequestException("CPF inválido");
        }

        try {
            // Calcula o primeiro dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) {
                firstDigit = 0;
            }

            // Calcula o segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) {
                secondDigit = 0;
            }

            // Verifica se os dígitos calculados correspondem aos informados
            if (Character.getNumericValue(cpf.charAt(9)) != firstDigit ||
                Character.getNumericValue(cpf.charAt(10)) != secondDigit) {
                throw new BadRequestException("CPF inválido");
            }
        } catch (Exception e) {
            throw new BadRequestException("CPF inválido");
        }
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

