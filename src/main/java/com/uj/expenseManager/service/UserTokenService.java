package com.uj.expenseManager.service;

import com.uj.expenseManager.exception.ResponseStatusException;
import com.uj.expenseManager.models.UserToken;
import com.uj.expenseManager.repository.UserTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {

    private final UserTokenRepository userTokenRepository;

    public UserTokenService(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    public void saveToken(String username, String token) {
        UserToken userToken = UserToken.builder().username(username).token(token).build();
        userTokenRepository.save(userToken);
    }

    public void saveOrUpdateToken(String username, String token) {
        userTokenRepository.findByUsername(username)
                .ifPresentOrElse(
                        existingToken -> {
                            existingToken.setToken(token);
                            existingToken.setCreatedAt(System.currentTimeMillis());
                            userTokenRepository.save(existingToken);
                        },
                        () -> {
                            UserToken newToken = UserToken.builder()
                                    .username(username)
                                    .token(token)
                                    .createdAt(System.currentTimeMillis())
                                    .build();
                            userTokenRepository.save(newToken);
                        }
                );
    }

    public boolean checkIfTokenIsPresent(String username) {
        return userTokenRepository.findByUsername(username).isPresent();
    }

    public UserToken getTokenByUsername(String username) {
        return userTokenRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "User " + username + " is not logged in! Access Denied!"));
    }

    public void deleteTokenByUsername(String username) {
        userTokenRepository.delete(getTokenByUsername(username));
    }


}
