package com.branches.utils;

import org.springframework.stereotype.Component;

@Component
public class TelefoneFormatter {
    public String execute(String telefone) {
        String formattedTelefone;

        switch (telefone.length()) {
            case 10 -> formattedTelefone = String.format("(%s) %s-%s",
                    telefone.substring(0, 2),
                    telefone.substring(2, 6),
                    telefone.substring(6, 10)
            );
            case 11 -> formattedTelefone = String.format("(%s) %s-%s",
                    telefone.substring(0, 2),
                    telefone.substring(2, 7),
                    telefone.substring(7, 11)
            );
            default -> formattedTelefone = telefone; // retorna sem formatacao
        }

        return formattedTelefone;
    }
}
