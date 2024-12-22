package com.mathieu_elyes.housewad.service

import android.content.Context
import com.mathieu_elyes.housewad.datamodel.CommandData
import com.mathieu_elyes.housewad.datamodel.DeviceListData

class DeviceService(context: Context): BaseService(context) {
    fun loadDevices(onSuccess: (Int, DeviceListData?) -> Unit) {
        Api().get<DeviceListData>("https://polyhome.lesmoulinsdudev.com/api/houses/${getHouseId()}/devices", onSuccess, getToken())
    }
    fun command(deviceId: String, command: CommandData, onSuccess: (Int) -> Unit){
        System.out.println("command de : " +  deviceId + "command effectué: "+ command + "sur maison: " + getHouseId() + "avec token: " + getToken())
        Api().post<CommandData>("https://polyhome.lesmoulinsdudev.com/api/houses/${getHouseId()}/devices/$deviceId/command", command, onSuccess, getToken())
    }
}