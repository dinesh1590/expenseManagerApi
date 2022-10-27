package com.myproject.expensetrackerapi.service;

import com.myproject.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public interface ExpenseService {

    Page<Expense> getAllExpenses(Pageable page);
    Expense getExpenseById(Long id);

    void deleteExpenseById(Long id);

    Expense saveExpense(Expense expense);

    Expense updateExpenseDetails(Long id, Expense expense);

    Page<Expense> getAllExpensesByPage(Pageable page);

    List<Expense> readByCategory(String category, Pageable page);

    List<Expense> readByName(String name, Pageable page);

    List<Expense> readByDate(Date startDate, Date endDate, Pageable page);

}
