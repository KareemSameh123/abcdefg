package com.example.financetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.financetracker.data.entity.BillSplit
import com.example.financetracker.data.entity.BillSplitPerson

@Dao
interface BillSplitDao {
    @Insert
    suspend fun insertSplit(split: BillSplit): Long

    @Insert
    suspend fun insertPerson(person: BillSplitPerson)

    @Insert
    suspend fun insertPersons(persons: List<BillSplitPerson>)

    @Query("SELECT * FROM bill_splits ORDER BY date DESC")
    fun getAllSplits(): LiveData<List<BillSplit>>

    @Query("SELECT * FROM bill_split_persons WHERE billSplitId = :splitId")
    fun getPersonsForSplit(splitId: Long): LiveData<List<BillSplitPerson>>
}
