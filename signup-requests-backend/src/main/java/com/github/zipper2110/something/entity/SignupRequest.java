package com.github.zipper2110.something.entity;

import com.github.zipper2110.something.entity.converter.EmailAddressConverter;
import com.github.zipper2110.something.entity.converter.PasswordConverter;
import com.github.zipper2110.something.entity.converter.UsernameConverter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class SignupRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotNull
    @Convert(converter = EmailAddressConverter.class)
    private EmailAddress emailAddress;

    @NotBlank
    private String fullName;

    @NotNull
    @Convert(converter = UsernameConverter.class)
    private Username username;

    @NotNull
    @Convert(converter = PasswordConverter.class)
    private Password password;

    public String getId() {
        return id;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }
}
