package com.example.financetracker.widget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent

class WidgetUpdateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.example.financetracker.UPDATE_WIDGET") {
            val manager = AppWidgetManager.getInstance(context)
            val widgetIds = manager.getAppWidgetIds(
                ComponentName(context, FinanceWidgetProvider::class.java)
            )
            for (id in widgetIds) {
                FinanceWidgetProvider.updateAppWidget(context, manager, id)
            }
        }
    }
}
