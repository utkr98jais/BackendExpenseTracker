package com.uj.expenseManager.mapper;

import com.uj.expenseManager.dto.ExpenseDTO;
import com.uj.expenseManager.models.Expense;

public class ExpenseMapper {
    public static Expense toEntity(ExpenseDTO expenseDto) {
        return new Expense(expenseDto.getUsername(), expenseDto.getAmount(), expenseDto.getCategory(), expenseDto.getDate(), expenseDto.getDescription());
    }
}
