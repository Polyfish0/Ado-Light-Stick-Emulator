package de.polyfish0.adolightstickemulator.ble

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class BLEService: Service() {
    private val binder = LocalBinder()
    private val NOTIFICATION_ID = 1024
    private val CHANNEL_ID = "AdoLightStickEmulatorServiceChannel"
    private lateinit var gattManager: GATTManager

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundServiceWithNotification()
        gattManager = GATTManager(applicationContext, GATTCallbacks())
    }

    fun startForegroundServiceWithNotification() {
        val manager = getSystemService(NotificationManager::class.java)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Light Stick Emulator Connection",
                NotificationManager.IMPORTANCE_LOW
            )
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Ado Light Stick Emulator")
            .setContentText("Keeping connection alive...")
            .setSmallIcon(android.R.drawable.stat_sys_data_bluetooth)
            .setOngoing(true)
            .build()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE)
        }else {
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    override fun onDestroy() {
        gattManager.stop()
    }

    inner class LocalBinder: Binder() {
        fun getService(): BLEService = this@BLEService
    }
}