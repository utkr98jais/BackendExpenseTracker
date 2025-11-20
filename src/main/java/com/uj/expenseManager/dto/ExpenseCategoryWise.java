package com.uj.expenseManager.dto;

import com.uj.expenseManager.models.Expense;

import java.util.List;

public record ExpenseCategoryWise(String category, double totalAmount, List<Expense> expenses) {}
