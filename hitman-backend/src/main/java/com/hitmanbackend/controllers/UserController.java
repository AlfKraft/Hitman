package com.hitmanbackend.controllers;

import com.hitmanbackend.requests.EliminationRequest;
import com.hitmanbackend.responses.ErrorMessage;
import com.hitmanbackend.responses.PlayerCardData;
import com.hitmanbackend.security.JwtTokenProvider;
import com.hitmanbackend.service.HitmanService;
import com.hitmanbackend.service.Target;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    HitmanService hitmanService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @GetMapping("hitman-backend/getMyTarget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getTargetInfo(@RequestHeader("Authorization") String token){
        Claims claims = jwtTokenProvider.parseToken(token.substring(7));
        String refreshToken = jwtTokenProvider.generateJwt(claims.getSubject(), claims.get("role").toString());

        try{
            Target target = hitmanService.getPlayerTarget(claims.getSubject());
            return ResponseEntity.ok(target);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }

    @PostMapping("hitman-backend/eliminateTarget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> elimination(@RequestHeader("Authorization") String token, @RequestBody EliminationRequest request){
        Claims claims = jwtTokenProvider.parseToken(token.substring(7));
        String refreshToken = jwtTokenProvider.generateJwt(claims.getSubject(), claims.get("role").toString());
        try{
            Target target = hitmanService.eliminateTarget(claims.getSubject(), request.getEliminationCode());
            return ResponseEntity.ok(target);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }

    @GetMapping("hitman-backend/getMyStats")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getPlayerStats(@RequestHeader("Authorization") String token){
        Claims claims = jwtTokenProvider.parseToken(token.substring(7));
        String refreshToken = jwtTokenProvider.generateJwt(claims.getSubject(), claims.get("role").toString());
        try{
            PlayerCardData playerCardData = hitmanService.getPlayerCardData(claims.getSubject());
            return ResponseEntity.ok(playerCardData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }
}