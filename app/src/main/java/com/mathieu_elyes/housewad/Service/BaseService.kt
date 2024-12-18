package com.mathieu_elyes.housewad.Service

import android.content.Context
import com.mathieu_elyes.housewad.Storage.DeviceStorage
import com.mathieu_elyes.housewad.Storage.HouseIdStorage
import com.mathieu_elyes.housewad.Storage.TokenStorage
import kotlinx.coroutines.runBlocking

abstract class BaseService(val context: Context) {

    protected fun getToken(): String? {
        return runBlocking { TokenStorage(context).read() }
    }

    protected fun clearToken() {
        runBlocking { TokenStorage(context).clear() }
    }

    protected fun getHouseId(): String? {
        return runBlocking { HouseIdStorage(context).read() }
    }

    protected fun getDeviceId(): String? {
        return runBlocking { DeviceStorage(context).read() }
    }
}