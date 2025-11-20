package com.uj.expenseManager.repository;

import com.uj.expenseManager.models.UserToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends MongoRepository<UserToken, String> {

    public Optional<UserToken> findByUsername(String username);
}
