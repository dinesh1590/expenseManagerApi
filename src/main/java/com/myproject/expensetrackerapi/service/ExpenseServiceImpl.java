package com.myproject.expensetrackerapi.service;

import com.myproject.expensetrackerapi.entity.Expense;
import com.myproject.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.myproject.expensetrackerapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired
    private ExpenseRepository expenseRepo;

    @Autowired
    private UserService userService;

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepo.findByUserId(userService.getLoggedInUser().getId(),page);
    }

    @Override
    public Expense getExpenseById(Long id) {

        Optional<Expense> expense=expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(),id);
        if(expense.isPresent())
        {
            return expense.get();
        }
        throw new ResourceNotFoundException("Expense is not found for the id "+id);
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense=getExpenseById(id);
        expenseRepo.delete(expense);
    }

    @Override
    public Expense saveExpense(Expense expense) {

        expense.setUser(userService.getLoggedInUser());
        return expenseRepo.save(expense);
    }

    @Override
    public Expense updateExpenseDetails(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);

        existingExpense.setName(expense.getName() !=null ? expense.getName() : existingExpense.getName());
        existingExpense.setCategory(expense.getCategory() !=null ? expense.getCategory() : existingExpense.getCategory());
        existingExpense.setDescription(expense.getDescription() !=null ? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setDate(expense.getDate() !=null ? expense.getDate() : existingExpense.getDate());
        existingExpense.setAmount(expense.getAmount() !=null ? expense.getAmount() : existingExpense.getAmount());

        return  expenseRepo.save(existingExpense);
    }

    @Override
    public Page<Expense> getAllExpensesByPage(Pageable page) {
        return expenseRepo.findAll(page);
    }

    @Override
    public List<Expense> readByCategory(String category, Pageable page) {
        List<Expense> es= expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(),category,page).toList();

        if(es.isEmpty()) {
            throw new ResourceNotFoundException("please check category name :"+category) ;
        }
        return es;
    }

    @Override
    public List<Expense> readByName(String name, Pageable page) {
        return expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(),name,page).toList();
    }

    @Override
    public List<Expense> readByDate(Date startDate, Date endDate, Pageable page) {
        if(startDate==null) {
            startDate =new Date(0);
        }
        if(endDate==null) {
            endDate=new Date(System.currentTimeMillis());
        }
        Page<Expense> pages = expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(),startDate,endDate,page);

        return pages.toList();
    }
}
