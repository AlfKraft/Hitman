package com.hitmanbackend.controllers;

import com.hitmanbackend.dto.AccountDataDTO;
import com.hitmanbackend.dto.TestAccountDto;
import com.hitmanbackend.dto.TestFile;
import com.hitmanbackend.entities.TestAccountEntity;
import com.hitmanbackend.mappers.AccountMapper;
import com.hitmanbackend.repositories.TestAccountRepository;
import com.hitmanbackend.responses.ErrorMessage;
import com.hitmanbackend.responses.LoginResponse;
import com.hitmanbackend.responses.MessageResponse;
import com.hitmanbackend.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Hello";
    }

    @PostMapping("/hitman-backend/register")
    public ResponseEntity<?> registerAccount(@ModelAttribute AccountDataDTO accountDto){

        try {
            registrationService.registerAccount(accountDto);
            return ResponseEntity.ok().body(new MessageResponse("You are registered"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }

    }



}
