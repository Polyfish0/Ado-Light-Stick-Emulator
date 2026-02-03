package de.polyfish0.adolightstickemulator.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGattServer
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import de.polyfish0.adolightstickemulator.ble.services.DeviceInformationService
import de.polyfish0.adolightstickemulator.ble.services.GenericAccessService
import de.polyfish0.adolightstickemulator.ble.services.UnknownService

@SuppressLint("MissingPermission")
class GATTManager(context: Context, gattCallbacks: GATTCallbacks) {
    private val bluetoothManager = context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
    private val deviceInformationService = DeviceInformationService()
    private val genericAccessService = GenericAccessService()
    private val unknownService = UnknownService()
    private val advertiseManager: BLEAdvertiseManager
    private val gatt: BluetoothGattServer

    init {
        bluetoothManager.adapter.setName("Ado Light Stick")
        gatt = bluetoothManager.openGattServer(context, gattCallbacks)
        //gatt.addService(deviceInformationService)
        //gatt.addService(genericAccessService)
        gatt.addService(unknownService)

        advertiseManager = BLEAdvertiseManager(context, gatt.services)
        advertiseManager.startAdvertise()
    }

    fun stop() {
        advertiseManager.stopAdvertise()
        gatt.close()
    }
}