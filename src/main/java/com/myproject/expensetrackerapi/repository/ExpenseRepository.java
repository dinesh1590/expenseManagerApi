package com.myproject.expensetrackerapi.repository;

import com.myproject.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {


    //select * from tbl_expenses where category=?
    Page<Expense> findByCategory(String category,  Pageable page);

    //select * from tbl_expenses where name LIKE '%keyword%'
    Page<Expense> findByNameContaining(String keyword, Pageable page);

    //select * from tbl_expenses where date between 'startDate' and 'endDate'
    Page<Expense> findByDateBetween(Date startDate, Date endDate, Pageable page);

    //select * from tbl_expense where user_id=?
     Page<Expense> findByUserId(Long userId,Pageable page);

     //select * from tbl_expenses where user_id=? and id=?
    Optional<Expense> findByUserIdAndId(Long userId,Long expenseId);


    // select * from tbl_expenses where user_id=? and category=?
    Page<Expense> findByUserIdAndCategory(Long UserId, String category,Pageable page);

   // select * from tbl_expenses where user_id=? name LIKE '%keyword%'
    Page<Expense> findByUserIdAndNameContaining(Long UserId, String keyword,Pageable page);

    // select * from tbl_expenses where user_id=? and date between 'startDate' and 'endDate'
    Page<Expense> findByUserIdAndDateBetween(Long UserId, Date startDate, Date endDate, Pageable page);
}
