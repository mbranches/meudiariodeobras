package com.branches.usertenant.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

@Converter
public class UserTenantAuthoritiesConverter implements AttributeConverter<Authorities, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Authorities attribute) {
        return objectMapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public Authorities convertToEntityAttribute(String dbData) {
        return objectMapper.readValue(dbData, Authorities.class);
    }
}
