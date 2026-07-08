package com.coreai.iality
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import android.annotation.SuppressLint
import androidx.core.app.NotificationManagerCompat
object NotificationHelper {

    const val CHANNEL_ID = "iality_notifications"

    fun createChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                "IALITY",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificaciones de IALITY"
            }

            val manager = context.getSystemService(NotificationManager::class.java)

            manager.createNotificationChannel(channel)
        }
    }
    @SuppressLint("MissingPermission")
    fun showNotification(
        context: Context,
        title: String,
        message: String
    ) {

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_notify_chat)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(100, builder.build())
    }
}