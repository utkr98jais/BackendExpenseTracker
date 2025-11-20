package com.uj.expenseManager.service;

import com.uj.expenseManager.dto.ExpenseCategoryWise;
import com.uj.expenseManager.dto.ExpenseResponseCategoryWise;
import com.uj.expenseManager.exception.ResponseStatusException;
import com.uj.expenseManager.models.Expense;
import com.uj.expenseManager.models.User;
import com.uj.expenseManager.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    public ExpenseService(ExpenseRepository expenseRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }


    public ExpenseResponseCategoryWise getExpensesCategoryWise(String username) {
        List<ExpenseCategoryWise> expenseCategoryWiseList = expenseRepository.getExpenseCategoryWise(username);
        double totalExpenseAmountForUser = expenseCategoryWiseList.stream().mapToDouble(ExpenseCategoryWise::totalAmount).sum();
        return new ExpenseResponseCategoryWise(totalExpenseAmountForUser, expenseCategoryWiseList);
    }

    /**
     * Adds a new expense after validating the user and category.
     * @param expense the Expense object to be added
     * @return the saved Expense object
     * @throws IllegalArgumentException if the user does not exist or the category does not belong to the user
     */
    public Expense addExpense(Expense expense) {
        User userInDB = userService.getUserByUsername(expense.getUsername());

        // Validate that the category belongs to the user
        String category = expense.getCategory();
        if (!userInDB.getCategories().contains(category)) {
            throw new ResponseStatusException("Category '" + category + "' is not a valid category. Please add it to your categories list.");
        }
        return expenseRepository.save(expense);
    }

    /**
     * Deletes an expense by its expenseId.
     * @param expenseId the ID of the expense to be deleted
     * @return the deleted Expense object, or null if not found
     */
    public Expense deleteExpense(String expenseId) {
        Expense expenseInDB = expenseRepository.findById(expenseId).orElse(null);
        if (expenseInDB == null) {
            return null;
        }
        expenseRepository.deleteById(expenseId);
        return expenseInDB;
    }

}
