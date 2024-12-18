package com.mathieu_elyes.housewad.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.mathieu_elyes.housewad.DataModel.CommandData
import com.mathieu_elyes.housewad.DataModel.DeviceListData
import com.mathieu_elyes.housewad.Storage.DeviceStorage
import com.mathieu_elyes.housewad.R
import com.mathieu_elyes.housewad.Service.DeviceService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DeviceAdapter(private val context: Context,
                    private val devices: DeviceListData) : BaseAdapter() {
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

        if (devices[position].type == "rolling shutter"){
            deviceStop.text = "${devices[position].opening}%"
            deviceStop.visibility = View.VISIBLE
            deviceButtonDown.visibility = View.VISIBLE
            deviceButtonUp.visibility = View.VISIBLE
            deviceIcon.setImageResource(R.drawable.shutter)
        }
        else if (devices[position].type == "light") {
            deviceSwitch.visibility = View.VISIBLE
            deviceSwitch.isChecked = devices[position].power!! > 0
            deviceIcon.setImageResource(R.drawable.lightbulb)
            deviceName.setPadding(55 ,0,0,0)
        }
        else if (devices[position].type == "garage door"){
            deviceStop.text = "${devices[position].opening}%"
            deviceStop.visibility = View.VISIBLE
            deviceButtonDown.visibility = View.VISIBLE
            deviceButtonUp.visibility = View.VISIBLE
            deviceIcon.setImageResource(R.drawable.garage)
        }

        deviceSwitch.setOnCheckedChangeListener { _, isChecked ->
            System.out.println("switch cliqué ICI")
            if (isChecked) {
                DeviceService(this.context).lightOn(devices[position].id, ::commandSuccess)
//                notifyDataSetChanged()
//                devices[position].power = 1
            } else {
                DeviceService(this.context).lightOff(devices[position].id, ::commandSuccess)
//                devices[position].power = 0
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
            System.out.println("DOWN")
            val commandData = CommandData("CLOSE")
            DeviceService(this.context).command(devices[position].id, commandData, ::commandSuccess)
        }
        return rowView
    }

    private fun commandSuccess(responseCode: Int) {
        if (responseCode == 200) {
            System.out.println("Command success")
        }
    }

    private fun saveDeviceId(deviceId: String) {
        val deviceStorage = DeviceStorage(context)
        mainScope.launch {
            deviceStorage.write(deviceId)
        }
    }
}