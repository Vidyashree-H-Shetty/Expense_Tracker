package com.expensetracker.expense.controller;

import com.expensetracker.expense.dto.ExpenseDTO;
import com.expensetracker.expense.entity.Expense;
import com.expensetracker.expense.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public Expense addExpense(@RequestBody ExpenseDTO dto) {
        return service.addExpense(dto);
    }

//    @GetMapping("/all")
//    public List<Expense> getAllExpenses() {
//        System.out.println("Get hit");
//        return service.getAllExpenses();
//    }

    @GetMapping("/str")
    public String getStr() {
        System.out.println("Get hit");
        return "Hello World";
    }

    @GetMapping("/{userId}")
    public List<Expense> getExpenses(@PathVariable int userId) {
        System.out.println("Get hit");
        return service.getUserExpenses(userId);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody ExpenseDTO dto) {
        return service.updateExpense(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        service.deleteExpense(id);
        return ResponseEntity.ok("Deleted successfully");
    }

}
