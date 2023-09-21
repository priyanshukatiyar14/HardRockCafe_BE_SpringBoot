package com.hardrockbe.hardrock.controller;

import com.hardrockbe.hardrock.model.auth;
import com.hardrockbe.hardrock.repos.authrepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


;

@RestController
@RequestMapping("/api/v1/auth")
public class authcontroller {
    @Autowired
    authrepository authRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @ResponseBody
    @PostMapping(value = "/signup", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<SignupResponse> addItem(@RequestBody auth auth, UriComponentsBuilder builder) {
        String hashedPassword = passwordEncoder.encode(auth.getPassword());
        auth.setPassword(hashedPassword);

        authRepo.save(auth);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/addUser/{id}").buildAndExpand(auth.getId()).toUri());

        String successMessage = "User successfully signed up";

        SignupResponse response = new SignupResponse(auth, successMessage);

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    // Define a custom response class to combine auth and success message
    public static class SignupResponse {
        private auth auth;
        private String message;

        public SignupResponse(auth auth, String message) {
            this.auth = auth;
            this.message = message;
        }

        public auth getAuth() {
            return auth;
        }

        public String getMessage() {
            return message;
        }
    }
}
