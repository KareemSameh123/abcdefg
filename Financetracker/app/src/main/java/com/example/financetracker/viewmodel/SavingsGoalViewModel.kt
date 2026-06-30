package com.example.financetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.financetracker.data.AppDatabase
import com.example.financetracker.data.entity.SavingsGoal
import kotlinx.coroutines.launch

class SavingsGoalViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).savingsGoalDao()
    val allGoals: LiveData<List<SavingsGoal>> = dao.getAllGoals()

    fun insert(goal: SavingsGoal) = viewModelScope.launch {
        dao.insert(goal)
    }

    fun addToGoal(goal: SavingsGoal, amount: Double) = viewModelScope.launch {
        dao.update(goal.copy(currentAmount = goal.currentAmount + amount))
    }

    fun delete(goal: SavingsGoal) = viewModelScope.launch {
        dao.delete(goal)
    }
}
