package com.branches.utils;

import org.springframework.stereotype.Component;

@Component
public class CnpjFormatter {
    public String execute(String cnpj) {
        return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }
}
