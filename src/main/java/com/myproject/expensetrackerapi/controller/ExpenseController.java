package com.myproject.expensetrackerapi.controller;

import com.myproject.expensetrackerapi.entity.Expense;
import com.myproject.expensetrackerapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseService.getAllExpenses(page);
    }

    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable("id") Long id)
    {
        return  expenseService.getExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteById(@RequestParam("id") Long id) {
        expenseService.deleteExpenseById(id);
    }

    @PostMapping("/expenses")
    public Expense saveExpenseDetails(@Valid @RequestBody Expense expense) {

        return  expenseService.saveExpense(expense);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpenseDetails(@RequestBody Expense expense, @PathVariable Long id) {

        return expenseService.updateExpenseDetails(id,expense);
    }

    @GetMapping("/expensess")
    public List<Expense> getAllExpensesPagination(Pageable page) {
        return expenseService.getAllExpensesByPage(page).toList();
    }

    @GetMapping("expenses/category")
    public List<Expense> getAllExpensesByCategory(@RequestParam String category, Pageable page) {
        return expenseService.readByCategory(category, page);
    }

    @GetMapping("/expenses/name")
    public List<Expense> getAllExpensesByName(@RequestParam String name, Pageable page) {
        return  expenseService.readByName(name,page);
    }

    @GetMapping("/expenses/date")
    public List<Expense> getAllExpensesByDate(
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            Pageable page) {
        return expenseService.readByDate(startDate,endDate,page);
    }
}
