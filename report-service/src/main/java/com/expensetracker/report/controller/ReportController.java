package com.expensetracker.report.controller;

import com.expensetracker.report.dto.CategorySummary;
import com.expensetracker.report.dto.ExpenseDTO;
import com.expensetracker.report.service.ExportService;
import com.expensetracker.report.service.ReportService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService service;
    private final ExportService exportService;

    public ReportController(ReportService service, ExportService exportService) {
        this.service = service;
        this.exportService = exportService;
    }

    @GetMapping("/total/{userId}")
    public Double total(@PathVariable Long userId) {
        System.out.println("total hit 1");
        return service.getTotalExpense(userId);
    }

    @GetMapping("/category/{userId}")
    public List<CategorySummary> category(@PathVariable Long userId) {
        System.out.println("Cat hit 1");
        return service.getCategorySummary(userId);
    }

    @GetMapping("/monthly/{userId}/{month}")
    public Double monthly(@PathVariable Long userId, @PathVariable int month) {
        System.out.println("Mon hit 1");
        return service.getMonthlyExpense(userId, month);
    }

    @GetMapping("/export/excel/{userId}")
    public ResponseEntity<byte[]> exportExcel(@PathVariable Long userId) {
        List<ExpenseDTO> expenses = service.getExpenses(userId);
        byte[] data = exportService.exportToExcel(expenses);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    @GetMapping("/export/pdf/{userId}")
    public ResponseEntity<byte[]> exportPdf(@PathVariable Long userId) {
        List<ExpenseDTO> expenses = service.getExpenses(userId);
        byte[] data = exportService.exportToPdf(expenses);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }
}