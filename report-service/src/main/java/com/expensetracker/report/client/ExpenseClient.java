package com.expensetracker.report.client;

import com.expensetracker.report.dto.ExpenseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "EXPENSE-SERVICE")
public interface ExpenseClient {

    @GetMapping("/expenses/{userId}")
    List<ExpenseDTO> getUserExpenses(@PathVariable Long userId);
}
