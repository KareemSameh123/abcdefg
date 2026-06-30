package com.example.financetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.financetracker.data.entity.Expense

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    fun getExpensesBetween(start: Long, end: Long): LiveData<List<Expense>>

    @Query("SELECT SUM(amount) FROM expenses WHERE date BETWEEN :start AND :end")
    suspend fun getTotalExpenseBetween(start: Long, end: Long): Double?
}
