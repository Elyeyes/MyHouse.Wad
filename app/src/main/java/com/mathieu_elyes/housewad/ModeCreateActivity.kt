package com.mathieu_elyes.housewad

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        mainScope.launch {
            val storedMode = ModeService(this@ModeCreateActivity).loadMode()!!
//            if (storedMode != null && storedMode.deviceSetupList.isNotEmpty())
            if (storedMode.deviceSetupList.isNotEmpty()) {
                mode = storedMode
                devices.clear()
                devices.addAll(mode.deviceSetupList)
                deviceSetupAdapter.notifyDataSetChanged()
            } else {
                DeviceService(this@ModeCreateActivity).loadDevices(::initDevicesSuccess)
            }
        }
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
                mode.deviceSetupList = devices
                deviceSetupAdapter.notifyDataSetChanged()
            }
        } else {
            Toast.makeText(this, "Error, Please Try Again", Toast.LENGTH_LONG).show()
        }
    }

    private fun back() {
        finish()
    }

    private fun save() {
        val textModeName = findViewById<EditText>(R.id.editModeName)
        if (textModeName.text.isNotEmpty()) {
            mode.name = textModeName.text.toString()
        }
        mainScope.launch {
            ModeService(this@ModeCreateActivity).saveMode(mode)
            Toast.makeText(this@ModeCreateActivity, "Mode Saved", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onDeviceCommandChanged() {
        deviceSetupAdapter.notifyDataSetChanged()
    }
}