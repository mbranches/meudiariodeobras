package com.branches.utils;

import com.branches.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateCnpjTest {

    private ValidateCnpj validateCnpj;

    @BeforeEach
    void setUp() {
        validateCnpj = new ValidateCnpj();
    }

    @Test
    void shouldValidateValidCpf() {
        // CPF válido: 111.444.777-35
        assertDoesNotThrow(() -> validateCnpj.execute("11144477735"));
    }

    @Test
    void shouldValidateValidCnpj() {
        // CNPJ válido: 11.222.333/0001-81
        assertDoesNotThrow(() -> validateCnpj.execute("11222333000181"));
    }

    @Test
    void shouldThrowExceptionForInvalidCpf() {
        BadRequestException exception = assertThrows(
            BadRequestException.class,
            () -> validateCnpj.execute("12345678901")
        );
        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidCnpj() {
        BadRequestException exception = assertThrows(
            BadRequestException.class,
            () -> validateCnpj.execute("12345678901234")
        );
        assertEquals("CNPJ inválido", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNonNumericInput() {
        BadRequestException exception = assertThrows(
            BadRequestException.class,
            () -> validateCnpj.execute("111.444.777-35")
        );
        assertEquals("CPF/CNPJ deve conter apenas números", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidLength() {
        BadRequestException exception = assertThrows(
            BadRequestException.class,
            () -> validateCnpj.execute("123456789")
        );
        assertEquals("CPF/CNPJ inválido. CPF deve ter 11 dígitos e CNPJ deve ter 14 dígitos", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForCpfWithAllSameDigits() {
        BadRequestException exception = assertThrows(
            BadRequestException.class,
            () -> validateCnpj.execute("11111111111")
        );
        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForCnpjWithAllSameDigits() {
        BadRequestException exception = assertThrows(
            BadRequestException.class,
            () -> validateCnpj.execute("11111111111111")
        );
        assertEquals("CNPJ inválido", exception.getMessage());
    }
}

