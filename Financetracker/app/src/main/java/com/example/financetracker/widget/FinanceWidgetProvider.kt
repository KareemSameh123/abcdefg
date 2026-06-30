package com.example.financetracker.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.financetracker.R
import com.example.financetracker.data.AppDatabase
import com.example.financetracker.ui.AddExpenseActivity
import com.example.financetracker.ui.AddIncomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class FinanceWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            CoroutineScope(Dispatchers.IO).launch {
                val dao = AppDatabase.getInstance(context).expenseDao()
                val now = Calendar.getInstance()
                now.set(Calendar.DAY_OF_MONTH, 1)
                now.set(Calendar.HOUR_OF_DAY, 0)
                now.set(Calendar.MINUTE, 0)
                now.set(Calendar.SECOND, 0)
                now.set(Calendar.MILLISECOND, 0)
                val start = now.timeInMillis
                now.add(Calendar.MONTH, 1)
                val end = now.timeInMillis - 1
                val total = dao.getTotalExpenseBetween(start, end) ?: 0.0

                val views = RemoteViews(context.packageName, R.layout.widget_layout)
                views.setTextViewText(R.id.tv_widget_total, "Month expenses: $%.2f".format(total))

                val expenseIntent = Intent(context, AddExpenseActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                val expensePending = PendingIntent.getActivity(
                    context, 0, expenseIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                views.setOnClickPendingIntent(R.id.btn_widget_expense, expensePending)

                val incomeIntent = Intent(context, AddIncomeActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                val incomePending = PendingIntent.getActivity(
                    context, 1, incomeIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                views.setOnClickPendingIntent(R.id.btn_widget_income, incomePending)

                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }
}
