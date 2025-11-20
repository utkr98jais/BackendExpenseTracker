package com.uj.expenseManager.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("UserTokens")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class UserToken {

    @Id
    @Setter(AccessLevel.NONE)
    private String id;
    private String username;
    private String token;
    private long createdAt;
}
