package com.mathieu_elyes.housewad

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mathieu_elyes.housewad.adapter.DeviceSetupAdapter
import com.mathieu_elyes.housewad.datamodel.DeviceSetupData
import com.mathieu_elyes.housewad.datamodel.ModeData
import com.mathieu_elyes.housewad.service.ModeService
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
        mainScope.launch {
            val storedMode = ModeService(this@ModeEditActivity).loadMode()!!
//            if (storedMode != null && storedMode.deviceSetupList.isNotEmpty())
            if (storedMode.deviceSetupList.isNotEmpty()) {
                mode = storedMode
                devices.clear()
                devices.addAll(mode.deviceSetupList)
                deviceSetupAdapter.notifyDataSetChanged()
            }
        }
    }

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
        mainScope.launch {
            ModeService(this@ModeEditActivity).saveMode(mode)
            Toast.makeText(this@ModeEditActivity, "Mode Saved", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onDeviceCommandChanged() {
        deviceSetupAdapter.notifyDataSetChanged()
    }
}