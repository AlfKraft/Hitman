package com.hitmanbackend.controllers;

import com.hitmanbackend.dto.User;
import com.hitmanbackend.requests.MissionCreationRequest;
import com.hitmanbackend.requests.PlayerIdRequest;
import com.hitmanbackend.responses.ErrorMessage;
import com.hitmanbackend.responses.MessageResponse;
import com.hitmanbackend.responses.MissionsResponse;
import com.hitmanbackend.service.HitmanService;
import com.hitmanbackend.service.MissionService;
import com.hitmanbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    HitmanService hitmanService;

    @Autowired
    UserService userService;

    @Autowired
    MissionService missionService;
    @GetMapping("hitman-backend/codes")
    @PreAuthorize("hasRole('ADMIN')")
    public void createEliminationCodesForPlayers(){
        hitmanService.createANewGameCycle();
    }

    @PostMapping("hitman-backend/createNewMission")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewMission(@RequestBody MissionCreationRequest request){
        try {
            missionService.createNewMission(request);
            return ResponseEntity.ok(new MessageResponse("OK"));
        }catch (Exception exception){
            return ResponseEntity.badRequest().body(new ErrorMessage(exception.getMessage()));
        }
    }

    @GetMapping("hitman-backend/getMissions")
    public ResponseEntity<?> getMissions(){
        try {
            MissionsResponse response = missionService.getAllMissions();
            return ResponseEntity.ok(response);
        }catch (Exception exception){
            return ResponseEntity.badRequest().body(new ErrorMessage(exception.getMessage()));
        }
    }

    @GetMapping("hitman-backend/getPlayers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPlayers(){
        try {
            List<User> usersData = userService.getAllPlayers();
            return ResponseEntity.ok(usersData);
        }catch (Exception exception){
            return ResponseEntity.badRequest().body(new ErrorMessage(exception.getMessage()));
        }
    }

    @PostMapping("hitman-backend/eliminatePlayer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminatePlayer(@RequestBody PlayerIdRequest request){
        try {
            userService.eliminatePlayer(request);
            return ResponseEntity.ok(new MessageResponse("OK"));
        } catch (Exception exception){
            return ResponseEntity.badRequest().body(new ErrorMessage(exception.getMessage()));
        }
    }
    @PostMapping("hitman-backend/revivePlayer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> revivePlayer(@RequestBody PlayerIdRequest request){
        try {
            userService.revivePlayer(request);
            return ResponseEntity.ok(new MessageResponse("OK"));
        } catch (Exception exception){
            return ResponseEntity.badRequest().body(new ErrorMessage(exception.getMessage()));
        }
    }


    /*
    @GetMapping("hitman-backend/nullifyScores")
    @PreAuthorize("hasRole('ADMIN')")
    public void nullifyAllScores(){
        hitmanService.nullifyAllScores();

    }

     */
}
