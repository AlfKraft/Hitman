package com.hitmanbackend.service;

import com.hitmanbackend.entities.CredentialsEntity;
import com.hitmanbackend.entities.PlayerDataEntity;
import com.hitmanbackend.repositories.CredentialsRepository;
import com.hitmanbackend.repositories.PlayerRepository;
import com.hitmanbackend.service.Account;
import com.hitmanbackend.entities.TestAccountEntity;
import com.hitmanbackend.repositories.TestAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    CredentialsRepository credentialsRepository;
    @Autowired
    PlayerRepository playerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PlayerDataEntity> account = playerRepository.findAccountByUsername(username);
        if (!credentialsRepository.existsByUsername(username) ){
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        if (account.isEmpty()){
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        Optional<CredentialsEntity> creds = credentialsRepository.findByUsername(username);
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(account.get().getRole()));
        return new Account(account.get().getUsername(), creds.get().getPassword(), authorities);

    }
}
