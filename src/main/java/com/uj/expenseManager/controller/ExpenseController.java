package com.uj.expenseManager.controller;

import com.uj.expenseManager.dto.ExpenseDTO;
import com.uj.expenseManager.mapper.ExpenseMapper;
import com.uj.expenseManager.models.Expense;
import com.uj.expenseManager.dto.ExpenseResponseCategoryWise;
import com.uj.expenseManager.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@Valid @RequestBody ExpenseDTO expenseDTO) {
        Expense expense = ExpenseMapper.toEntity(expenseDTO);
        Expense savedExpense = expenseService.addExpense(expense);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteExpense(@RequestParam String expenseId, @RequestParam String username) {
        Expense deletedExpense = expenseService.deleteExpense(expenseId);
        if (deletedExpense == null) {
            return new ResponseEntity<>("Expense not found, for the expenseId: " + expenseId, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("Expense deleted successfully, for expenseId: " + expenseId);
    }

    @GetMapping
    public ResponseEntity<ExpenseResponseCategoryWise> getExpensesCategoryWise(@RequestParam String username) {
        ExpenseResponseCategoryWise response = expenseService.getExpensesCategoryWise(username);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

}
