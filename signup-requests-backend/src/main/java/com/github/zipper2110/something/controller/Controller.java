package com.github.zipper2110.something.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping(value = "/user/signup", method = RequestMethod.POST)
    public String signUp(@RequestBody SignupRequestDTO signupRequestDTO) {

        return "something";
    }

    private static class SignupRequestDTO {
        private final String username;
        private final String password;
        private final String email;
        private final String fullName;

        private SignupRequestDTO(String username, String password, String email, String fullName) {
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
