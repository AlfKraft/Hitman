package com.hitmanbackend.service;

import com.hitmanbackend.dto.AccountDataDTO;
import com.hitmanbackend.dto.TestAccountDto;
import com.hitmanbackend.entities.CredentialsEntity;
import com.hitmanbackend.entities.PlayerDataEntity;
import com.hitmanbackend.entities.ScoreEntity;
import com.hitmanbackend.entities.TestAccountEntity;
import com.hitmanbackend.repositories.CredentialsRepository;
import com.hitmanbackend.repositories.PlayerRepository;
import com.hitmanbackend.repositories.ScoreRepository;
import com.hitmanbackend.repositories.TestAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class RegistrationService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CredentialsRepository credentialsRepository;

    @Autowired
    ScoreRepository scoreRepository;

    public void registerAccount(AccountDataDTO accountDto){
        if(accountDto.getPassword().contains(" ")){
            throw new RuntimeException("Passwords contains whitespace!");
        }
        if (!accountDto.getPassword().equals(accountDto.getRepeatedPassword())){
            throw new RuntimeException("Passwords don't match!");
        }
        if (credentialsRepository.existsByUsername(accountDto.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        PlayerDataEntity newPlayer = new PlayerDataEntity();
        newPlayer.setFirstName(accountDto.getFirstName());
        newPlayer.setLastName(accountDto.getLastName());
        newPlayer.setEmail(accountDto.getEmail());
        newPlayer.setBirthdate(accountDto.getBirthdate());
        newPlayer.setFacebook(accountDto.getFacebook());
        newPlayer.setSchoolAndSpeciality(accountDto.getSchoolAndSpeciality());
        newPlayer.setWorkPlace(accountDto.getWorkPlace());
        newPlayer.setHobbies(accountDto.getHobbies());
        newPlayer.setFavoritePlaces(accountDto.getMostVisitedPlaces());
        newPlayer.setPhoneNumber(accountDto.getPhoneNumber());
        newPlayer.setUsername(accountDto.getUsername());
        newPlayer.setEliminated(false);
        newPlayer.setRole("USER");
        newPlayer.setProfileImage("https://storage.cloud.google.com/player_img_bucket/bg.png");
        credentialsRepository.save(new CredentialsEntity(accountDto.getUsername(), encoder.encode(accountDto.getPassword())));
        playerRepository.save(newPlayer);
        scoreRepository.save(new ScoreEntity(newPlayer,0L));
    }
}
