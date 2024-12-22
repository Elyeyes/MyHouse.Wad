package com.mathieu_elyes.housewad.service

import android.content.Context
import com.mathieu_elyes.housewad.storage.DeviceStorage
import com.mathieu_elyes.housewad.storage.HouseIdStorage
import com.mathieu_elyes.housewad.storage.TokenStorage
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

    protected fun clearHouseId() {
        runBlocking { HouseIdStorage(context).clear() }
    }

    protected fun getDeviceId(): String? {
        return runBlocking { DeviceStorage(context).read() }
    }

    protected fun clearDeviceId() {
        runBlocking { DeviceStorage(context).clear() }
    }
}