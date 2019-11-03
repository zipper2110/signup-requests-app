package com.github.zipper2110.something.entity.converter;

import com.github.zipper2110.something.entity.Password;

import javax.persistence.AttributeConverter;

public class PasswordConverter implements AttributeConverter<Password, String> {
    @Override
    public String convertToDatabaseColumn(Password attribute) {
        return attribute.getValue();
    }

    @Override
    public Password convertToEntityAttribute(String dbData) {
        return new Password(dbData);
    }
}
