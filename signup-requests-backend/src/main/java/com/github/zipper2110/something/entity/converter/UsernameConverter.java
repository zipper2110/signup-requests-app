package com.github.zipper2110.something.entity.converter;

import com.github.zipper2110.something.entity.Username;

import javax.persistence.AttributeConverter;

public class UsernameConverter  implements AttributeConverter<Username, String> {

    @Override
    public String convertToDatabaseColumn(Username attribute) {
        return attribute.getValue();
    }

    @Override
    public Username convertToEntityAttribute(String dbData) {
        return new Username(dbData);
    }
}
