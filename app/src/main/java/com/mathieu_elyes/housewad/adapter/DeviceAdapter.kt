package com.mathieu_elyes.housewad.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.mathieu_elyes.housewad.datamodel.CommandData
import com.mathieu_elyes.housewad.datamodel.DeviceListData
import com.mathieu_elyes.housewad.storage.DeviceStorage
import com.mathieu_elyes.housewad.R
import com.mathieu_elyes.housewad.service.DeviceService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DeviceAdapter(private val context: Context,
                    private val devices: DeviceListData,
                    private var devicesDisplayed: String) : BaseAdapter() {
    private val mainScope = MainScope()
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return devices[position]
    }

    override fun getCount(): Int {
        return devices.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_devices, parent, false)
        val deviceName = rowView.findViewById<TextView>(R.id.textDeviceName)

        val deviceSwitch = rowView.findViewById<Switch>(R.id.switchLight)

        val deviceButtonUp = rowView.findViewById<ImageButton>(R.id.ibuttonDeviceUp)
        val deviceStop = rowView.findViewById<Button>(R.id.buttonDeviceStop)
        val deviceButtonDown = rowView.findViewById<ImageButton>(R.id.ibuttonDeviceDown)

        val deviceIcon = rowView.findViewById<ImageView>(R.id.imageDevice)
        deviceName.text = devices[position].id
        when (devices[position].type) {
            "rolling shutter" -> {
                if(devices[position].openingMode!=2){
                    if(devices[position].opening == 0.0 || devices[position].opening == 1.0){ // le shutter peut etre toujour entrain de "monter" mais au max donc egal à 0
                        deviceStop.text = "${(devices[position].opening!! * 100).toInt()}%"
                    }else{
                        deviceStop.text = context.getString(R.string.Stop)
                    }
                }else{
                    deviceStop.text = "${(devices[position].opening!! * 100).toInt()}%"
                }
                deviceIcon.setImageResource(R.drawable.shutter)
            }

            "garage door" -> {
                if(devices[position].openingMode!=2){
                    if(devices[position].opening == 0.0 || devices[position].opening == 1.0){
                        deviceStop.text = "${(devices[position].opening!! * 100).toInt()}%"
                    }else{
                        deviceStop.text = context.getString(R.string.Stop)
                    }
                }else{
                    deviceStop.text = "${(devices[position].opening!! * 100).toInt()}%"
                }
                deviceIcon.setImageResource(R.drawable.garage)
            }
            "light" -> {
                deviceSwitch.visibility = View.VISIBLE
                deviceStop.visibility = View.GONE
                deviceButtonDown.visibility = View.GONE
                deviceButtonUp.visibility = View.GONE
                deviceSwitch.isChecked = devices[position].power!! > 0
                deviceIcon.setImageResource(R.drawable.lightbulb)
                deviceName.setPadding(55 ,0,0,0)
            }
        }

        deviceSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val commandData = CommandData("TURN ON")
                DeviceService(this.context).command(devices[position].id, commandData, ::commandSuccess)
            } else {
                val commandData = CommandData("TURN OFF")
                DeviceService(this.context).command(devices[position].id, commandData, ::commandSuccess)
            }
        }

        deviceButtonUp.setOnClickListener {
            val commandData = CommandData("OPEN")
            DeviceService(this.context).command(devices[position].id, commandData, ::commandSuccess)
        }
        deviceStop.setOnClickListener {
            val commandData = CommandData("STOP")
            DeviceService(this.context).command(devices[position].id, commandData, ::commandSuccess)
        }
        deviceButtonDown.setOnClickListener {
            val commandData = CommandData("CLOSE")
            DeviceService(this.context).command(devices[position].id, commandData, ::commandSuccess)
            }
        return rowView
    }

    private fun commandSuccess(responseCode: Int) {
        if (responseCode == 200) {
            mainScope.launch {
                DeviceService(context).loadDevices { responseCode, responseBody ->
                    if (responseCode == 200) {
                        devices.clear()
                        for (device in responseBody!!.devices) {
                            if (device.type == devicesDisplayed) {
                                devices.add(device)
                            }else if(devicesDisplayed == "all"){
                                devices.add(device)
                            }
                        }
                        Handler(Looper.getMainLooper()).post {
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    fun updateDevicesDisplayed(newDevicesDisplayed: String) {
        devicesDisplayed = newDevicesDisplayed
        commandSuccess(200)
    }
}