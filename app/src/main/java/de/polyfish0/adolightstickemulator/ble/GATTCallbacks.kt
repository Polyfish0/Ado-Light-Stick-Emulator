package de.polyfish0.adolightstickemulator.ble

import android.bluetooth.BluetoothGattServerCallback

import android.bluetooth.*
import android.util.Log

class GATTCallbacks : BluetoothGattServerCallback() {

    companion object {
        private const val TAG = "AdoLightStick"
    }

    override fun onConnectionStateChange(device: BluetoothDevice?, status: Int, newState: Int) {
        val stateStr = when (newState) {
            BluetoothProfile.STATE_CONNECTED -> "CONNECTED"
            BluetoothProfile.STATE_DISCONNECTED -> "DISCONNECTED"
            else -> "STATE: $newState"
        }
        Log.d(TAG, "onConnectionStateChange: Device=${device?.address}, Status=$status, NewState=$stateStr")
        super.onConnectionStateChange(device, status, newState)
    }

    override fun onServiceAdded(status: Int, service: BluetoothGattService?) {
        Log.d(TAG, "onServiceAdded: Status=$status, UUID=${service?.uuid}")
        super.onServiceAdded(status, service)
    }

    override fun onCharacteristicReadRequest(
        device: BluetoothDevice?,
        requestId: Int,
        offset: Int,
        characteristic: BluetoothGattCharacteristic?
    ) {
        Log.d(TAG, "READ Request: Device=${device?.address}, UUID=${characteristic?.uuid}, RequestId=$requestId, Offset=$offset")

        // TODO: Hier musst du normalerweise server.sendResponse(...) aufrufen,
        // um die Daten an den Client zu senden.
    }

    override fun onCharacteristicWriteRequest(
        device: BluetoothDevice?,
        requestId: Int,
        characteristic: BluetoothGattCharacteristic?,
        preparedWrite: Boolean,
        responseNeeded: Boolean,
        offset: Int,
        value: ByteArray?
    ) {
        val dataString = value?.toHexString() ?: "null"
        Log.d(TAG, "WRITE Request: UUID=${characteristic?.uuid}, Val=$dataString, RespNeeded=$responseNeeded, Offset=$offset")

        // TODO: Wenn responseNeeded == true, hier server.sendResponse(...) mit status GATT_SUCCESS senden
    }

    override fun onDescriptorReadRequest(
        device: BluetoothDevice?,
        requestId: Int,
        offset: Int,
        descriptor: BluetoothGattDescriptor?
    ) {
        Log.d(TAG, "DESCRIPTOR READ: UUID=${descriptor?.uuid}, RequestId=$requestId")
        // TODO: server.sendResponse(...)
    }

    override fun onDescriptorWriteRequest(
        device: BluetoothDevice?,
        requestId: Int,
        descriptor: BluetoothGattDescriptor?,
        preparedWrite: Boolean,
        responseNeeded: Boolean,
        offset: Int,
        value: ByteArray?
    ) {
        val dataString = value?.toHexString() ?: "null"
        Log.d(TAG, "DESCRIPTOR WRITE: UUID=${descriptor?.uuid}, Val=$dataString, RespNeeded=$responseNeeded")

        // TODO: Wenn responseNeeded == true, hier server.sendResponse(...) senden
    }

    override fun onNotificationSent(device: BluetoothDevice?, status: Int) {
        Log.d(TAG, "onNotificationSent: Device=${device?.address}, Status=$status")
        super.onNotificationSent(device, status)
    }

    override fun onMtuChanged(device: BluetoothDevice?, mtu: Int) {
        Log.d(TAG, "onMtuChanged: Device=${device?.address}, MTU=$mtu")
        super.onMtuChanged(device, mtu)
    }

    private fun ByteArray.toHexString(): String {
        return joinToString("-") { "%02X".format(it) }
    }
}