package com.example.financetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.financetracker.data.entity.SavingsGoal

@Dao
interface SavingsGoalDao {
    @Insert
    suspend fun insert(goal: SavingsGoal)

    @Update
    suspend fun update(goal: SavingsGoal)

    @Delete
    suspend fun delete(goal: SavingsGoal)

    @Query("SELECT * FROM savings_goals ORDER BY dateCreated DESC")
    fun getAllGoals(): LiveData<List<SavingsGoal>>
}
