package com.expensetracker.expense.service;

import com.expensetracker.expense.dto.ExpenseDTO;
import com.expensetracker.expense.entity.Expense;
import java.util.List;

public interface ExpenseService {
    Expense addExpense(ExpenseDTO dto);
    List<Expense> getUserExpenses(int userId);
    Expense updateExpense(Long userId, ExpenseDTO dto);
    void deleteExpense(Long userId);


}
