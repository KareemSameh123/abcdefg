package com.example.financetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.financetracker.data.entity.Income

@Dao
interface IncomeDao {
    @Insert
    suspend fun insert(income: Income)

    @Update
    suspend fun update(income: Income)

    @Delete
    suspend fun delete(income: Income)

    @Query("SELECT * FROM incomes ORDER BY date DESC")
    fun getAllIncomes(): LiveData<List<Income>>

    @Query("SELECT * FROM incomes WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    fun getIncomesBetween(start: Long, end: Long): LiveData<List<Income>>

    @Query("SELECT SUM(amount) FROM incomes WHERE date BETWEEN :start AND :end")
    suspend fun getTotalIncomeBetween(start: Long, end: Long): Double?
}
