package com.mathieu_elyes.housewad

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mathieu_elyes.housewad.adapter.DeviceSetupAdapter
import com.mathieu_elyes.housewad.datamodel.CommandData
import com.mathieu_elyes.housewad.datamodel.DeviceListData
import com.mathieu_elyes.housewad.datamodel.DeviceSetupData
import com.mathieu_elyes.housewad.datamodel.ModeData
import com.mathieu_elyes.housewad.service.DeviceService
import com.mathieu_elyes.housewad.service.ModeService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ModeCreateActivity : AppCompatActivity(), DeviceSetupAdapter.DeviceSetupAdapterCallback  {
    private val mainScope = MainScope()
    private var devices: ArrayList<DeviceSetupData> = ArrayList()
    private var mode: ModeData = ModeData("Default", devices)
    private lateinit var deviceSetupAdapter: DeviceSetupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_create)

        deviceSetupAdapter = DeviceSetupAdapter(this, devices, this)
        initDeviceMode()

        findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            back()
        }
        findViewById<ImageButton>(R.id.buttonSave).setOnClickListener {
            save()
        }
    }

    private fun initDeviceMode() {
        val listView = findViewById<ListView>(R.id.listDevicesOfMode)
        listView.adapter = deviceSetupAdapter
        mode = ModeService(this).loadMode()!!
        mainScope.launch {
            DeviceService(this@ModeCreateActivity).loadDevices(::initDevicesSuccess)
        }
    }

//    private suspend fun readMode() {
//        val modeStorage = ModeStorage(this)
//        mode = modeStorage.read() ?: ModeData("Default", ArrayList())
//    }

    private fun loadDeviceMode() {
        devices.clear()
        devices.addAll(mode.deviceSetupList)
        deviceSetupAdapter.notifyDataSetChanged()
    }


    private fun initDevicesSuccess(responseCode: Int, responseBody: DeviceListData?) {
        if (responseCode == 200 && responseBody != null) {
            runOnUiThread {
                devices.clear()
                for (device in responseBody.devices) {
                    val command = if (device.type == "light") {
                        CommandData("TURN OFF")
                    } else {
                        CommandData("CLOSE")
                    }
                    devices.add(DeviceSetupData(device.id, command))
                }
                deviceSetupAdapter.notifyDataSetChanged()
            }
        } else {
//            errorLoadingHouse()
        }
    }



    private fun back() {
        finish()
    }

    private fun save() {
        ModeService(this).saveMode(mode)
        System.out.println("mode: $mode")
        finish()
    }

    override fun onDeviceCommandChanged() {
        loadDeviceMode()
    }
}