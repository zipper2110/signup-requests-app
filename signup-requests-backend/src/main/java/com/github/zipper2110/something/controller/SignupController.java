package com.github.zipper2110.something.controller;

import com.github.zipper2110.something.entity.EmailAddress;
import com.github.zipper2110.something.entity.Password;
import com.github.zipper2110.something.entity.SignupRequest;
import com.github.zipper2110.something.entity.Username;
import com.github.zipper2110.something.service.SignupRequestService;
import lombok.val;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@RestController
public class SignupController {

    private SignupRequestService service;

    public SignupController(SignupRequestService service) {
        this.service = service;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void signUp(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        val signupRequest = new SignupRequest();
        signupRequest.setUsername(new Username(signupRequestDTO.getUsername()));
        signupRequest.setPassword(new Password(signupRequestDTO.getPassword()));
        signupRequest.setFullName(signupRequestDTO.getFullName());
        signupRequest.setEmailAddress(new EmailAddress(signupRequestDTO.getEmail()));
        SignupRequest created = service.create(signupRequest);
        // TODO: handle the case where we cannot create a request
    }

    // I intentionally have not implemented data validation (only something basic is here),
    // since I expect that the approval system will have that responsibility for it

    private static class SignupRequestDTO {

        @NotBlank
        private String username;

        @NotEmpty
        private String password;

        @NotBlank
        private String email;

        @NotBlank
        private String fullName;

        public SignupRequestDTO(@NotBlank String username,
                                @NotEmpty String password,
                                @NotBlank String email,
                                @NotBlank String fullName) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.fullName = fullName;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        public String getFullName() {
            return fullName;
        }
    }
}
