package com.uj.expenseManager.dto;

import java.util.List;

public record ExpenseResponseCategoryWise(double totalExpenseAmountForUser, List<ExpenseCategoryWise> expenseCategoryWiseList) {}
