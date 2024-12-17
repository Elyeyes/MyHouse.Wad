package com.mathieu_elyes.housewad.DataModel

data class DeviceListData(var devices: ArrayList<DeviceData>){
    val size: Int
        get() = devices.size

    operator fun get(position: Int): DeviceData {
        return devices[position]
    }

    fun clear() {
        devices.clear()
    }

    fun add(device: DeviceData) {
        devices.add(device)
    }

}