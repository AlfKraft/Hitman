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
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
@Service
public class RegistrationService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    UploadObject uploadObject;

    @Autowired
    CredentialsRepository credentialsRepository;

    @Autowired
    ScoreRepository scoreRepository;

    Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    SimpleDateFormat regOpenFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void registerAccount(AccountDataDTO accountDto) throws Exception {
        Instant nowUtc = Instant.now();

        DateTimeZone estonia = DateTimeZone.forID("Europe/Tallinn");
        DateTime nowEstonia = nowUtc.toDateTime(estonia);
        Date now = nowEstonia.plusHours(3).toDate();
        Date openRegistration = regOpenFormat.parse("2023-09-18T11:59");
        Date closeRegistration = regOpenFormat.parse("2023-09-24T20:00");
        if(now.before(openRegistration)){
            throw new Exception("Registration is not open yet.");
        }
        if(now.after(closeRegistration)){
            throw new Exception("Registration is closed.");
        }

        if (accountDto.getProfileImage().isEmpty() || accountDto.getProfileImage() == null){
            throw new Exception("Profile image is missing from the request.");
        }
        if (accountDto.getUsername() == null || accountDto.getUsername().isBlank()){
            throw new Exception("Username is missing from the request.");
        }
        if (accountDto.getPassword() == null || accountDto.getPassword().isBlank()){
            throw new Exception("Password is missing from the request.");
        }
        if (accountDto.getFirstName() == null || accountDto.getFirstName().isBlank()){
            throw new Exception("First name is missing from the request.");
        }
        if (accountDto.getLastName() == null || accountDto.getLastName().isBlank()){
            throw new Exception("Last name is missing from the request.");
        }
        if (accountDto.getBirthdate() == null || accountDto.getBirthdate().isBlank()){
            throw new Exception("Birthdate is missing from the request.");
        }
        if (accountDto.getEmail() == null || accountDto.getEmail().isBlank()){
            throw new Exception("Email is missing from the request.");
        }
        if (accountDto.getHobbies() == null || accountDto.getHobbies().isBlank()){
            throw new Exception("Hobbies are missing from the request.");
        }
        if (accountDto.getFacebook() == null || accountDto.getFacebook().isBlank()){
            throw new Exception("Social media  link is missing from the request.");
        }
        if (accountDto.getMostVisitedPlaces() == null || accountDto.getMostVisitedPlaces().isBlank()){
            throw new Exception("Favorite places are missing from the request.");
        }
        if (accountDto.getPhoneNumber() == null || accountDto.getPhoneNumber().isBlank()){
            throw new Exception("Phone number is missing from the request.");
        }
        if (accountDto.getProfileImage() == null){
            throw new Exception("Image missing is from the request.");
        }
        if (accountDto.getSchoolAndSpeciality() == null || accountDto.getSchoolAndSpeciality().isBlank()){
            throw new Exception("School and specialty are missing from the request.");
        }
        if (accountDto.getWorkPlace() == null || accountDto.getWorkPlace().isBlank()){
            throw new Exception("Work place is missing from the request.");
        }


        if (credentialsRepository.existsByUsernameIgnoreCase(accountDto.getUsername())) {
            logger.error(accountDto.getUsername()+ ": Username is already taken!");
            throw new Exception("Username is already taken!");
        }
        if(accountDto.getUsername().contains(" ")){
            logger.error(accountDto.getUsername()+ ": Username contains whitespace!");
            throw new Exception("Username contains whitespace!");
        }
        if(accountDto.getPassword().contains(" ")){
            logger.error(accountDto.getUsername()+ ": Passwords contains whitespace!");
            throw new Exception("Passwords contains whitespace!");
        }
        if (!accountDto.getPassword().equals(accountDto.getRepeatedPassword())){
            logger.error(accountDto.getUsername()+ ": Passwords don't match!");
            throw new Exception("Passwords don't match!");
        }
        if(playerRepository.existsByEmail(accountDto.getEmail())){
            throw new Exception("User with this email has already been created");
        }
        if (playerRepository.existsByPhoneNumberContaining(accountDto.getPhoneNumber())){
            throw new Exception("User with this phone number has already been created");
        }

        if (accountDto.getProfileImage().getSize() > 5 * 1024 * 1024){
            Long fileSize = accountDto.getProfileImage().getSize() / (1024 * 1024);
            throw new Exception("File size is too big: %d MB. File size must be under 5MB.".formatted(fileSize));
        }

        Date birthdate = inputFormat.parse(accountDto.getBirthdate());
        if (!birthdate.before(now)){
            throw new Exception("You must be 18 or older to participate in this game.");
        }

        checkExtension(accountDto.getProfileImage().getOriginalFilename());

        String imagePath = "";
        logger.info(accountDto.getUsername()+ ": Creating entity");
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
        newPlayer.setApproved(false);

        logger.info(accountDto.getUsername()+ ": Saving image!");

        imagePath = uploadObject.uploadObject(accountDto.getProfileImage(), accountDto.getFirstName(), accountDto.getLastName());
        logger.info(accountDto.getUsername()+ ": Saving image completed!");

        if (imagePath.equals("")){
            logger.error(accountDto.getUsername()+ ": Saving image failed!");
            throw new Exception("Couldn't upload file");
        }
        newPlayer.setProfileImage(imagePath);
        logger.info(accountDto.getUsername()+ ": Creating entity completed");

        logger.info(accountDto.getUsername()+ ": Save users credentials");
        credentialsRepository.save(new CredentialsEntity(accountDto.getUsername(), encoder.encode(accountDto.getPassword())));
        logger.info(accountDto.getUsername()+ ": Save users credentials: completed");

        logger.info(accountDto.getUsername()+ ": Save user");
        playerRepository.save(newPlayer);
        logger.info(accountDto.getUsername()+ ": Save user: completed");

        logger.info(accountDto.getUsername()+ ": Add score");
        scoreRepository.save(new ScoreEntity(newPlayer,0L));
        logger.info(accountDto.getUsername()+ ": Add score: completed");

    }

    private boolean checkExtension(String fileName) throws Exception {
        String[] allowedExtensions = {".png", ".jpeg", ".jpg"};

        for (String ext:
             allowedExtensions) {
            if (fileName.toLowerCase().endsWith(ext)){
                return true;
            }

        }
        throw new Exception("Not permitted file type");
    }
}
