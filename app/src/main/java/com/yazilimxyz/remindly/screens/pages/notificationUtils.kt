package com.yazilimxyz.remindly.screens.pages

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.yazilimxyz.remindly.R
import android.content.Intent
import android.provider.Settings


fun checkNotificationPermission(context: Context): Boolean {
    return NotificationManagerCompat.from(context).areNotificationsEnabled()
}

fun sendNotification(context: Context, title: String, message: String) {
    val channelId = "default_channel_id"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Default Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Default Channel Description"
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val notificationBuilder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    try {
        with(NotificationManagerCompat.from(context)) {
            notify(1, notificationBuilder.build())
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
        // Hata mesajı veya kullanıcıya bilgi verme işlemi yapılabilir
    }
}

fun openNotificationSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    }
    context.startActivity(intent)
}