package com.uj.expenseManager.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

import static com.uj.expenseManager.constants.Categories.DEFAULT_CATEGORIES;

@Document(collection = "Users")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @Setter(AccessLevel.NONE)
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Set<String> categories = new HashSet<>(DEFAULT_CATEGORIES);

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
