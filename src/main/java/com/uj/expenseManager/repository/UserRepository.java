package com.uj.expenseManager.repository;

import com.uj.expenseManager.models.User;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Aggregation(pipeline = {
            "{ $addFields: { fullName: { $concat: ['$firstName', ' ', '$lastName'] } } }",
            "{ $match: { fullName: { $regex: ?0, $options: 'i' } } }"
    })
    List<User> findByName(String namePart);

    Optional<User> findByUsername(String username);

}
