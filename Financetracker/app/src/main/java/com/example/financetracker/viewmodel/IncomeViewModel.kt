package com.example.financetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.financetracker.WidgetUpdateHelper
import com.example.financetracker.data.AppDatabase
import com.example.financetracker.data.entity.Income
import kotlinx.coroutines.launch

class IncomeViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).incomeDao()
    val allIncomes: LiveData<List<Income>> = dao.getAllIncomes()

    fun insert(income: Income) = viewModelScope.launch {
        dao.insert(income)
        WidgetUpdateHelper.sendUpdateBroadcast(getApplication())
    }

    fun update(income: Income) = viewModelScope.launch {
        dao.update(income)
        WidgetUpdateHelper.sendUpdateBroadcast(getApplication())
    }

    fun delete(income: Income) = viewModelScope.launch {
        dao.delete(income)
        WidgetUpdateHelper.sendUpdateBroadcast(getApplication())
    }
}
