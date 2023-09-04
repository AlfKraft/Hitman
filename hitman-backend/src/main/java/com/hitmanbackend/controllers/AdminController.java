package com.hitmanbackend.controllers;

import com.hitmanbackend.service.HitmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    HitmanService hitmanService;

    @GetMapping("hitman-backend/codes")
    @PreAuthorize("hasRole('ADMIN')")
    public void createEliminationCodesForPlayers(){
        hitmanService.createANewGameCycle();
    }

    /*
    @GetMapping("hitman-backend/nullifyScores")
    @PreAuthorize("hasRole('ADMIN')")
    public void nullifyAllScores(){
        hitmanService.nullifyAllScores();

    }

     */
}
