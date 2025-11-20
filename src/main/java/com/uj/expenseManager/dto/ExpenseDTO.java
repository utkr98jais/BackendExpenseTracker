package com.uj.expenseManager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ExpenseDTO {
    @NotBlank(message = "User ID is required")
    private String username;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Amount is required")
    private Double amount;

    private Date date;

    private String description;

}
