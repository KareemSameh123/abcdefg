package com.example.financetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.financetracker.data.AppDatabase
import com.example.financetracker.data.entity.Bill
import kotlinx.coroutines.launch
import java.util.Calendar

class BillViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).billDao()
    val allBills: LiveData<List<Bill>> = dao.getAllBills()

    fun insert(bill: Bill) = viewModelScope.launch {
        dao.insert(bill)
    }

    fun markPaid(bill: Bill) = viewModelScope.launch {
        if (bill.isRecurring) {
            val cal = Calendar.getInstance().apply { timeInMillis = bill.dueDate }
            when (bill.frequency) {
                "monthly" -> cal.add(Calendar.MONTH, 1)
                "yearly" -> cal.add(Calendar.YEAR, 1)
                "weekly" -> cal.add(Calendar.WEEK_OF_YEAR, 1)
            }
            dao.update(bill.copy(dueDate = cal.timeInMillis, isPaid = false))
        } else {
            dao.update(bill.copy(isPaid = true))
        }
    }

    fun delete(bill: Bill) = viewModelScope.launch {
        dao.delete(bill)
    }
}
