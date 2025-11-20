package com.uj.expenseManager.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Expenses")
@Getter
@ToString
@EqualsAndHashCode
public class Expense {

    @Id
    private String id;

    @Setter
    private String username;
    @Setter
    private String category;
    @Setter
    private Double amount;
    @Setter
    private String description;
    @Setter
    private Date date;

    public Expense(@NonNull String username, @NonNull Double amount, @NonNull String category, @NonNull Date date, String description) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.username = username;
    }
}
