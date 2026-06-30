package com.example.financetracker

import android.content.Context
import android.content.Intent
import com.example.financetracker.widget.WidgetUpdateReceiver

object WidgetUpdateHelper {
    fun sendUpdateBroadcast(context: Context) {
        context.sendBroadcast(Intent(context, WidgetUpdateReceiver::class.java).apply {
            action = "com.example.financetracker.UPDATE_WIDGET"
        })
    }
}
