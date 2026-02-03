package de.polyfish0.adolightstickemulator.ble.services

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_READ
import android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ
import android.bluetooth.BluetoothGattService
import java.util.UUID

class DeviceInformationService: BluetoothGattService(UUID.fromString("0000180A-0000-1000-8000-00805f9b34fb"), SERVICE_TYPE_PRIMARY) {
    private val PnPIDCharacteristic = BluetoothGattCharacteristic(
        UUID.fromString("00002A50-0000-1000-8000-00805f9b34fb"),
        PROPERTY_READ,
        PERMISSION_READ
    )

    init {
        PnPIDCharacteristic.setValue(byteArrayOfInts(0x02, 0x8A, 0x24, 0x66, 0x82, 0x01, 0x00))
        addCharacteristic(PnPIDCharacteristic)
    }
}