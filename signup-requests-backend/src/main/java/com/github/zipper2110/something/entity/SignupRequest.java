package com.github.zipper2110.something.entity;

import com.github.zipper2110.something.entity.converter.EmailAddressConverter;
import com.github.zipper2110.something.entity.converter.PasswordConverter;
import com.github.zipper2110.something.entity.converter.UsernameConverter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @NotNull
    private Status status = Status.INITIALIZED;

    private boolean hasPendingStatusNotification = false;

    public String getId() {
        return id;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignupRequest that = (SignupRequest) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "SignupRequest{" +
                "id='" + id + '\'' +
                ", emailAddress=" + emailAddress +
                ", fullName='" + fullName + '\'' +
                ", username=" + username +
                ", password=" + password +
                ", status=" + status +
                '}';
    }

    public enum Status {
        INITIALIZED,
        WAITING_FOR_APPROVAL,
        APPROVED,
        REJECTED
    }
}
