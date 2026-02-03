package de.polyfish0.adolightstickemulator.ble.services

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.*
import android.bluetooth.BluetoothGattService
import java.util.UUID

class GenericAccessService: BluetoothGattService(UUID.fromString("00001800-0000-1000-8000-00805f9b34fb"), SERVICE_TYPE_PRIMARY) {
    private val deviceNameCharacteristic = BluetoothGattCharacteristic(
        UUID.fromString("00002A00-0000-1000-8000-00805f9b34fb"),
        PROPERTY_NOTIFY or PROPERTY_READ,
        PERMISSION_READ
    )

    private val appearanceCharacteristic = BluetoothGattCharacteristic(
        UUID.fromString("00002A01-0000-1000-8000-00805f9b34fb"),
        PROPERTY_READ,
        PERMISSION_READ
    )

    private val peripheralPreferredConnectionParametersCharacteristic = BluetoothGattCharacteristic(
        UUID.fromString("00002A04-0000-1000-8000-00805f9b34fb"),
        PROPERTY_READ,
        PERMISSION_READ
    )

    init {
        deviceNameCharacteristic.setValue(byteArrayOfInts(0x41, 0x64, 0x6F, 0x20, 0x4C, 0x69, 0x67, 0x68, 0x74, 0x20, 0x53, 0x74, 0x69, 0x63, 0x6B))
        appearanceCharacteristic.setValue(byteArrayOfInts(0x00, 0x00))
        peripheralPreferredConnectionParametersCharacteristic.setValue(byteArrayOfInts(0x08, 0x00, 0x0B, 0x00, 0x00, 0x00, 0xE8, 0x03))

        addCharacteristic(deviceNameCharacteristic)
        addCharacteristic(appearanceCharacteristic)
        addCharacteristic(peripheralPreferredConnectionParametersCharacteristic)
    }
}

fun byteArrayOfInts(vararg ints: Int) = ByteArray(ints.size) { pos -> ints[pos].toByte() }