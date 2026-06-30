package com.example.financetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.financetracker.WidgetUpdateHelper
import com.example.financetracker.data.AppDatabase
import com.example.financetracker.data.entity.Expense
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).expenseDao()
    val allExpenses: LiveData<List<Expense>> = dao.getAllExpenses()

    fun insert(expense: Expense) = viewModelScope.launch {
        dao.insert(expense)
        WidgetUpdateHelper.sendUpdateBroadcast(getApplication())
    }

    fun update(expense: Expense) = viewModelScope.launch {
        dao.update(expense)
        WidgetUpdateHelper.sendUpdateBroadcast(getApplication())
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        dao.delete(expense)
        WidgetUpdateHelper.sendUpdateBroadcast(getApplication())
    }
}
