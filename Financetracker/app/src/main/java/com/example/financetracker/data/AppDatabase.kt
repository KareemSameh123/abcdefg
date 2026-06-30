package com.example.financetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.financetracker.data.dao.*
import com.example.financetracker.data.entity.*

@Database(
    entities = [
        Expense::class,
        Income::class,
        SavingsGoal::class,
        Bill::class,
        BillSplit::class,
        BillSplitPerson::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun incomeDao(): IncomeDao
    abstract fun savingsGoalDao(): SavingsGoalDao
    abstract fun billDao(): BillDao
    abstract fun billSplitDao(): BillSplitDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "finance_tracker_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
