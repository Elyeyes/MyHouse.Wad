package com.mathieu_elyes.housewad.Service

import android.content.Context
import com.mathieu_elyes.housewad.DataModel.CommandData
import com.mathieu_elyes.housewad.DataModel.DeviceListData

class DeviceService(context: Context): BaseService(context) {
    public fun loadDevices(onSuccess: (Int, DeviceListData?) -> Unit) {
        Api().get<DeviceListData>("https://polyhome.lesmoulinsdudev.com/api/houses/${getHouseId()}/devices", onSuccess, getToken())
    }
    public fun command(deviceId: String, command: CommandData, onSuccess: (Int) -> Unit){
        System.out.println("command de : " +  deviceId + "command effectué: "+ command + "sur maison: " + getHouseId() + "avec token: " + getToken())
        Api().post<CommandData>("https://polyhome.lesmoulinsdudev.com/api/houses/${getHouseId()}/devices/$deviceId/command", command, onSuccess, getToken())
    }

    public fun lightOn(deviceId: String, onSuccess: (Int) -> Unit){
        Api().post<String>("https://polyhome.lesmoulinsdudev.com/api/houses/${getHouseId()}/devices/$deviceId/command", "TURN ON", onSuccess, getToken())
    }

    public fun lightOff(deviceId: String, onSuccess: (Int) -> Unit){
        Api().post<String>("https://polyhome.lesmoulinsdudev.com/api/houses/${getHouseId()}/devices/$deviceId/command", "TURN OFF", onSuccess, getToken())
    }
}