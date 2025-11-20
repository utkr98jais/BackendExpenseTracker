package com.uj.expenseManager.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Categories {

    public static final Set<String> DEFAULT_CATEGORIES = new HashSet<>();
    static {
        String[] categories = {
            "Dining & Snacks",
            "Transport",
            "Utilities",
            "Entertainment",
            "Groceries",
            "Rent",
            "Bills",
            "Recharge",
            "Subscriptions",
            "Healthcare",
            "Education",
            "Shopping",
            "Travel",
            "Miscellaneous",
            "Income",
            "Toiletries",
            "Household",
            "Gifts",
            "Personal Care",
            "Savings",
            "Investments",
            "Taxes",
            "Insurance",
            "Automobile"
        };
        DEFAULT_CATEGORIES.addAll(Arrays.asList(categories));
    }
}
