package com.hitmanbackend.service;

import com.hitmanbackend.dto.TestAccountDto;
import com.hitmanbackend.entities.ScoreEntity;
import com.hitmanbackend.entities.TestAccountEntity;
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
    TestAccountRepository testAccountRepository;

    @Autowired
    ScoreRepository scoreRepository;

    public void registerAccount(TestAccountDto accountDto){
        if (!accountDto.getPassword().equals(accountDto.getRepeatedPassword())){
            throw new RuntimeException("Passwords don't match!");
        }
        if (testAccountRepository.existsByUsername(accountDto.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        TestAccountEntity entity = new TestAccountEntity();
        entity.setUsername(accountDto.getUsername());
        entity.setPassword(encoder.encode(accountDto.getPassword()));
        entity.setAboutInfo(accountDto.getAboutInfo());
        entity.setRole("USER");
        entity.setName(accountDto.getFirstName() + " " + accountDto.getLastName());
        entity.setEliminated(false);
        testAccountRepository.save(entity);
        scoreRepository.save(new ScoreEntity(entity,0L));
    }
}
