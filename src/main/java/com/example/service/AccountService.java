package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<Account> register(Account acc){
        if(acc.getUsername() == null || acc.getPassword().length() < 4) return ResponseEntity.status(400).body(null);

        Optional<Account> optionalAcc = accountRepository.findByUsername(acc.getUsername());
        if(optionalAcc.isPresent()) return ResponseEntity.status(409).body(null);

        Account savedAcc = accountRepository.save(acc);
        return ResponseEntity.status(200).body(savedAcc);
    }
}
