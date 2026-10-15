package com.expensetracker.report.service;

import com.expensetracker.report.client.ExpenseClient;
import com.expensetracker.report.dto.CategorySummary;
import com.expensetracker.report.dto.ExpenseDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ExpenseClient expenseClient;

    public ReportService(ExpenseClient expenseClient) {
        this.expenseClient = expenseClient;
    }

    // ✅ Total spending
    public Double getTotalExpense(Long userId) {
        return expenseClient.getUserExpenses(userId)
                .stream()
                .mapToDouble(ExpenseDTO::getAmount)
                .sum();
    }

    // ✅ Category-wise summary
    public List<CategorySummary> getCategorySummary(Long userId) {

        List<ExpenseDTO> expenses = expenseClient.getUserExpenses(userId);

        Map<String, Double> map = new HashMap<>();

        for (ExpenseDTO e : expenses) {
            map.put(
                    e.getCategoryName(),
                    map.getOrDefault(e.getCategoryName(), 0.0) + e.getAmount()
            );
        }

        return map.entrySet()
                .stream()
                .map(e -> new CategorySummary(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    // ✅ Monthly report
    public Double getMonthlyExpense(Long userId, int month) {

        return expenseClient.getUserExpenses(userId)
                .stream()
                .filter(e -> e.getDate().getMonthValue() == month)
                .mapToDouble(ExpenseDTO::getAmount)
                .sum();
    }

    public List<ExpenseDTO> getExpenses(Long userId) {
        return expenseClient.getUserExpenses(userId);
    }

}
