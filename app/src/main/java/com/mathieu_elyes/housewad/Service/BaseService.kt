package com.mathieu_elyes.housewad.Service

import android.content.Context
import android.devicelock.DeviceId
import com.mathieu_elyes.housewad.DataModel.HouseData
import com.mathieu_elyes.housewad.Storage.DeviceStorage
import com.mathieu_elyes.housewad.Storage.GuestIdStorage
import com.mathieu_elyes.housewad.Storage.HouseIdStorage
import com.mathieu_elyes.housewad.Storage.TokenStorage
import kotlinx.coroutines.runBlocking

abstract class BaseService(val context: Context) {

    protected fun getToken(): String? {
        return runBlocking { TokenStorage(context).read() }
    }

    protected fun getHouseId(): String? {
        return runBlocking { HouseIdStorage(context).read() }
    }

    protected fun getDeviceId(): String? {
        return runBlocking { DeviceStorage(context).read() }
    }

    protected fun getGuestId(): String? {
        return runBlocking { GuestIdStorage(context).read() }
    }
}