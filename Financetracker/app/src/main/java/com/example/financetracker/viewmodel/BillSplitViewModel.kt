package com.example.financetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.financetracker.data.AppDatabase
import com.example.financetracker.data.entity.BillSplit
import com.example.financetracker.data.entity.BillSplitPerson
import kotlinx.coroutines.launch

class BillSplitViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).billSplitDao()
    val allSplits: LiveData<List<BillSplit>> = dao.getAllSplits()

    fun saveBillSplit(total: Double, description: String, persons: List<BillSplitPerson>) =
        viewModelScope.launch {
            val splitId = dao.insertSplit(BillSplit(totalAmount = total, description = description))
            val personsWithId = persons.map { it.copy(billSplitId = splitId) }
            dao.insertPersons(personsWithId)
        }

    fun getPersonsForSplit(splitId: Long): LiveData<List<BillSplitPerson>> =
        dao.getPersonsForSplit(splitId)
}
