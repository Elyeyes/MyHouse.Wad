package com.mathieu_elyes.housewad

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mathieu_elyes.housewad.adapter.DeviceSetupAdapter
import com.mathieu_elyes.housewad.datamodel.DeviceSetupData
import com.mathieu_elyes.housewad.datamodel.ModeData
import com.mathieu_elyes.housewad.service.BaseService
import com.mathieu_elyes.housewad.service.DeviceService
import com.mathieu_elyes.housewad.service.ModeService
import com.mathieu_elyes.housewad.service.UserService
import com.mathieu_elyes.housewad.storage.ModeStorage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ModeEditActivity : AppCompatActivity(), DeviceSetupAdapter.DeviceSetupAdapterCallback  {
    private val mainScope = MainScope()
    private var devices: ArrayList<DeviceSetupData> = ArrayList()
    private var mode: ModeData = ModeData("Default", devices)
    private lateinit var deviceSetupAdapter: DeviceSetupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_edit)

        deviceSetupAdapter = DeviceSetupAdapter(this, devices, this)
        initDeviceMode()
        loadDeviceMode()

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
    }

//    private suspend fun readMode() {
//        val modeStorage = ModeStorage(this)
//        mode = modeStorage.read() ?: ModeData("Default", ArrayList())
//    }

    private fun loadDeviceMode() {
        val textDeviceOfMode = findViewById<TextView>(R.id.textDeviceOfMode)
        textDeviceOfMode.text = getString(R.string.DevicesOfMode) + ": ${mode.name}"
        devices.clear()
        devices.addAll(mode.deviceSetupList)
        deviceSetupAdapter.notifyDataSetChanged()
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