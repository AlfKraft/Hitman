package com.hitmanbackend.controllers;

import com.hitmanbackend.dto.Leader;
import com.hitmanbackend.repositories.AdminControlRepository;
import com.hitmanbackend.requests.CheckpointCompletionRequest;
import com.hitmanbackend.requests.CompleteMissionRequest;
import com.hitmanbackend.requests.EliminationRequest;
import com.hitmanbackend.responses.*;
import com.hitmanbackend.security.JwtTokenProvider;
import com.hitmanbackend.service.*;
import io.jsonwebtoken.Claims;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    HitmanService hitmanService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CheckpointService checkpointService;
    @Autowired
    private UserService userService;
    @Autowired
    private MissionService missionService;

    @Autowired
    private AdminControlRepository adminControlRepository;


    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("hitman-backend/getMyTarget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getTargetInfo(@RequestHeader("Authorization") String token){

        Claims claims = jwtTokenProvider.parseToken(token.substring(7));
        String refreshToken = jwtTokenProvider.generateJwt(claims.getSubject(), claims.get("role").toString());
        try{
            if(adminControlRepository.findById(1L).get().getEnabled()){
                throw new Exception("Can't complete the action during time out");
            }
            Target target = hitmanService.getPlayerTarget(claims.getSubject());
            return ResponseEntity.ok(target);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }

    @PostMapping("hitman-backend/eliminateTarget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> elimination(@RequestHeader("Authorization") String token, @RequestBody EliminationRequest request){
        logger.info("This should be elimination code %s".formatted(request.getEliminationCode()));
        Claims claims = jwtTokenProvider.parseToken(token.substring(7));
        String refreshToken = jwtTokenProvider.generateJwt(claims.getSubject(), claims.get("role").toString());
        logger.info("%s elimination started".formatted(claims.getSubject()));
        try{
            if(adminControlRepository.findById(1L).get().getEnabled()){
                throw new Exception("Can't complete the action during time out");
            }
            Target target = hitmanService.eliminateTarget(claims.getSubject(), request.getEliminationCode());
            logger.info("%s's elimination was successful".formatted(claims.getSubject()));
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

    @GetMapping("hitman-backend/leaderBoard")
    public ResponseEntity<?> getTopPlayers(){
        try {
            List<Leader> leaderboard = userService.getTopPlayers();
            return ResponseEntity.ok(leaderboard);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }

    @GetMapping("hitman-backend/myMissions")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getMyMissions(@RequestHeader("Authorization") String token){
        try {
            Claims claims = jwtTokenProvider.parseToken(token.substring(7));
            MissionsResponse missions = missionService.getMyMissions(claims.getSubject());
            return ResponseEntity.ok(missions);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }

    @PostMapping("hitman-backend/completeMission")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> completeMission(@RequestHeader("Authorization") String token, @RequestBody CompleteMissionRequest request){
        try {
            if(adminControlRepository.findById(1L).get().getEnabled()){
                throw new Exception("Can't complete the action during time out");
            }
            Claims claims = jwtTokenProvider.parseToken(token.substring(7));
            missionService.completeMission(claims.getSubject(), request.getMissionId(), request.getMissionCode());
            return ResponseEntity.ok(new MessageResponse("Nice work! Mission completed!"));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }

    @GetMapping("hitman-backend/getMyCheckpoints")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getMyCheckpoints(@RequestHeader("Authorization") String token){
        try {

            Claims claims = jwtTokenProvider.parseToken(token.substring(7));
            CheckpointListResponse missions = checkpointService.getMyCheckpoints(claims.getSubject());
            return ResponseEntity.ok(missions);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }
    @PostMapping("hitman-backend/completeCheckpoint")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> completeCheckpoint(@RequestHeader("Authorization") String token, @RequestBody CheckpointCompletionRequest request){
        try {
            if(adminControlRepository.findById(1L).get().getEnabled()){
                throw new Exception("Can't complete the action during time out");
            }
            Claims claims = jwtTokenProvider.parseToken(token.substring(7));
            checkpointService.completeCheckpoint(claims.getSubject(), request);
            return ResponseEntity.ok(new MessageResponse("Nice work! Checkpoint completed!"));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }



}
