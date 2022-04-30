package com.example.DriveWithMe.service;

import com.example.DriveWithMe.model.ConfirmationToken;
import com.example.DriveWithMe.model.User;
import com.example.DriveWithMe.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

    @Autowired
    ConfirmationTokenRepository tokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public ConfirmationToken saveToken(User user) {
        String generatedToken = UUID.randomUUID().toString();
        ConfirmationToken token = new ConfirmationToken(generatedToken, user);
        tokenRepository.save(token);

        return token;
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void setConfirmed(ConfirmationToken token) {
        token.setConfirmed(true);
        tokenRepository.save(token);
    }
}
