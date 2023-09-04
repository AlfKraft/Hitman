package com.hitmanbackend.controllers;

import com.hitmanbackend.requests.LoginRequest;
import com.hitmanbackend.responses.ErrorMessage;
import com.hitmanbackend.responses.LoginResponse;
import com.hitmanbackend.responses.MessageResponse;
import com.hitmanbackend.security.JwtTokenProvider;
import com.hitmanbackend.service.Account;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("hitman-backend/login")
    public ResponseEntity<?> authenicateuser(@RequestBody LoginRequest request) throws Exception{
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            final Account account = (Account) authentication.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtTokenProvider.generateJwt(account.getUsername(), account.getAuthorities().iterator().next().getAuthority());
            logger.info("%s had a successful login".formatted(account.getUsername()));
            return ResponseEntity.ok(new LoginResponse(account.getUsername(),jwtToken,account.getAuthorities().iterator().next().getAuthority()));
        } catch (BadCredentialsException exception) {
            logger.error(exception.getMessage());
            return ResponseEntity.badRequest().body(new ErrorMessage(exception.getMessage()));
        }

    }

    @GetMapping("hitman-backend/verify")
    public ResponseEntity<?> verifyUser(@RequestHeader("Authorization") String token){
        Claims claims = jwtTokenProvider.parseToken(token.substring(7));
        String refreshToken = jwtTokenProvider.generateJwt(claims.getSubject(), claims.get("role").toString());
        return ResponseEntity.ok(new LoginResponse(claims.getSubject(),refreshToken,claims.get("role").toString()));
    }
}
