package com.hitmanbackend.controllers;

import com.hitmanbackend.dto.User;
import com.hitmanbackend.entities.AdminControlValuesEntity;
import com.hitmanbackend.entities.CredentialsEntity;
import com.hitmanbackend.entities.MissionEntity;
import com.hitmanbackend.repositories.AdminControlRepository;
import com.hitmanbackend.repositories.CredentialsRepository;
import com.hitmanbackend.requests.CheckpointCreationRequest;
import com.hitmanbackend.requests.GenNewPasswordRequest;
import com.hitmanbackend.requests.MissionCreationRequest;
import com.hitmanbackend.requests.PlayerIdRequest;
import com.hitmanbackend.responses.*;
import com.hitmanbackend.service.CheckpointService;
import com.hitmanbackend.service.HitmanService;
import com.hitmanbackend.service.MissionService;
import com.hitmanbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {

    @Autowired
    HitmanService hitmanService;

    @Autowired
    UserService userService;

    @Autowired
    CheckpointService checkpointService;

    @Autowired
    MissionService missionService;
    @Autowired
    AdminControlRepository adminControlRepository;

    @Autowired
    CredentialsRepository credentialsRepository;
    @Autowired
    PasswordEncoder encoder;

    @GetMapping("hitman-backend/codes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEliminationCodesForPlayers(){
        try {
            GameResponse gameResponse = hitmanService.createANewGameCycle();
            return ResponseEntity.ok(gameResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }

    }
    @GetMapping("hitman-backend/images")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> genarateNewURLsForImages(){
        try {
            userService.generateNewURLsForPlayersImages();
            return ResponseEntity.ok(new MessageResponse("OK"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }

    }
    @GetMapping("hitman-backend/gameData")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getGameData(){
        try {
            GameResponse gameResponse = hitmanService.getGameData();
            return ResponseEntity.ok(gameResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }

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
    @PostMapping("hitman-backend/createNewCheckpoint")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewCheckpoint(@RequestBody CheckpointCreationRequest request){
        try {
            checkpointService.createNewCheckpoint(request);
            return ResponseEntity.ok(new MessageResponse("OK"));
        }catch (Exception exception){
            return ResponseEntity.badRequest().body(new ErrorMessage(exception.getMessage()));
        }
    }


    @GetMapping("hitman-backend/getMissions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getMissions(){
        try {
            MissionsResponse response = missionService.getAllMissions();
            return ResponseEntity.ok(response);
        }catch (Exception exception){
            return ResponseEntity.badRequest().body(new ErrorMessage(exception.getMessage()));
        }
    }

    @GetMapping("hitman-backend/getCheckpoints")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCheckpoints(){
        try {
            CheckpointListResponse response = checkpointService.getCheckpoints();
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

    @PostMapping("hitman-backend/getPlayerData")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPlayerData(@RequestBody PlayerIdRequest request){
        try {
            PlayerCardDataForAdmin playerCardDataForAdmin = userService.getPlayerData(request);
            return ResponseEntity.ok(playerCardDataForAdmin);
        } catch (Exception exception){
            return ResponseEntity.badRequest().body(new ErrorMessage(exception.getMessage()));
        }
    }
    @GetMapping("hitman-backend/timeout")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> timeout(){
        try {
            Optional<AdminControlValuesEntity> adminControlValues = adminControlRepository.findById(1L);
            if (adminControlValues.isPresent()){
                adminControlValues.get().setEnabled(!adminControlValues.get().getEnabled());
                adminControlRepository.save(adminControlValues.get());
            }
            return ResponseEntity.ok("OK");

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }

    }
    @GetMapping("hitman-backend/getTimeOut")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTimeOut(){
        try {
            Optional<AdminControlValuesEntity> adminControlValues = adminControlRepository.findById(1L);
            if (adminControlValues.isPresent()){
                return ResponseEntity.ok(adminControlValues.get());
            }
            throw new Exception("Error toggling time out");

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }

    }

    @PostMapping("hitman-backend/genNewPassword")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> genNewPassword(@RequestBody GenNewPasswordRequest request){
        try {
            Optional<CredentialsEntity> creds = credentialsRepository.findByUsername(request.getUsername());
            if (creds.isPresent()){
                creds.get().setPassword(encoder.encode(request.getNewPassword()));
                credentialsRepository.save(creds.get());
                return ResponseEntity.badRequest().body(new MessageResponse("Password changed successfully!"));
            }
            throw new Exception("Couldn't find credentials.");

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
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
