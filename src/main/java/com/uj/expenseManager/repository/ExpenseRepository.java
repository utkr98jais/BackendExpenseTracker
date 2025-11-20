package com.uj.expenseManager.repository;

import com.uj.expenseManager.models.Expense;
import com.uj.expenseManager.dto.ExpenseCategoryWise;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    @Aggregation(pipeline = {
        "{ $match: { username: ?0 } }",
        "{ $group: { _id: '$category', totalAmount: { $sum: '$amount' }, expenses: { $push: '$$ROOT' } } }",
        "{ $project: { category: '$_id', totalAmount: 1, expenses: 1, _id: 0 } }"
    })
    List<ExpenseCategoryWise> getExpenseCategoryWise(String username);
}

