package com.example.financetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.financetracker.data.entity.Bill

@Dao
interface BillDao {
    @Insert
    suspend fun insert(bill: Bill)

    @Update
    suspend fun update(bill: Bill)

    @Delete
    suspend fun delete(bill: Bill)

    @Query("SELECT * FROM bills ORDER BY dueDate ASC")
    fun getAllBills(): LiveData<List<Bill>>
}
