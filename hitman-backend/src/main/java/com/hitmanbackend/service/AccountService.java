package com.hitmanbackend.service;

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
    TestAccountRepository testAccountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<TestAccountEntity> account = testAccountRepository.findAccountByUsername(username);
        if (account.isEmpty()){
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(account.get().getRole()));
        return new Account(account.get().getUsername(), account.get().getPassword(), authorities);

    }
}
