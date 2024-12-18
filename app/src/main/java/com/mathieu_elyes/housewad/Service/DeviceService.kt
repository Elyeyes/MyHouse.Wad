package com.mathieu_elyes.housewad.Service

import android.content.Context
import com.mathieu_elyes.housewad.DataModel.DeviceListData

class DeviceService(context: Context): BaseService(context) {
    public fun loadDevices(onSuccess: (Int, DeviceListData?) -> Unit) {
        Api().get<DeviceListData>("https://polyhome.lesmoulinsdudev.com/api/houses/${getHouseId()}/devices", onSuccess, getToken())
    }
}