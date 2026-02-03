package de.polyfish0.adolightstickemulator.ble

import android.Manifest
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import android.os.ParcelUuid
import android.util.Log
import androidx.annotation.RequiresPermission

class BLEAdvertiseManager(context: Context, services: List<BluetoothGattService>) {
    private val bluetoothManager = context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
    private val advertData: AdvertiseData
    private val advertSettings = AdvertiseSettings.Builder()
        .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
        .setConnectable(true)
        .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
        .build()

    private val advertiseCallback = object : AdvertiseCallback() {
        override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
            Log.d("Ado", "Advertising started")
        }
        override fun onStartFailure(errorCode: Int) {
            Log.e("Ado", "Advertising failed: $errorCode")
        }
    }

    init {
        var dataBuilder = AdvertiseData.Builder()
            .setIncludeDeviceName(true)

        services.forEach {
            dataBuilder.addServiceUuid(ParcelUuid(it.uuid))
        }

        advertData = dataBuilder.build()
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_ADVERTISE)
    fun startAdvertise() {
        bluetoothManager.adapter.bluetoothLeAdvertiser.startAdvertising(advertSettings, advertData, advertiseCallback)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_ADVERTISE)
    fun stopAdvertise() {
        bluetoothManager.adapter.bluetoothLeAdvertiser.stopAdvertising(advertiseCallback)
    }
}