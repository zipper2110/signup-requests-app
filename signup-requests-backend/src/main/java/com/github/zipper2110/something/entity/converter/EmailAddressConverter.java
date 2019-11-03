package com.github.zipper2110.something.entity.converter;

import com.github.zipper2110.something.entity.EmailAddress;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EmailAddressConverter implements AttributeConverter<EmailAddress, String> {

    @Override
    public String convertToDatabaseColumn(EmailAddress attribute) {
        return attribute.getValue();
    }

    @Override
    public EmailAddress convertToEntityAttribute(String dbData) {
        return new EmailAddress(dbData);
    }
}
