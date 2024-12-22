package com.mathieu_elyes.housewad.adapter

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
import com.mathieu_elyes.housewad.R
import com.mathieu_elyes.housewad.datamodel.CommandData
import com.mathieu_elyes.housewad.datamodel.DeviceSetupData
import com.mathieu_elyes.housewad.service.ModeService

class DeviceSetupAdapter(private val context: Context,
    private val devices: ArrayList<DeviceSetupData>,
     private val callback: DeviceSetupAdapterCallback) : BaseAdapter() {
    interface DeviceSetupAdapterCallback {
        fun onDeviceCommandChanged()
    }

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

        val textState = rowView.findViewById<TextView>(R.id.textState)

        val deviceSwitch = rowView.findViewById<Switch>(R.id.switchLight)
        val deviceButtonUp = rowView.findViewById<ImageButton>(R.id.ibuttonDeviceUp)
        val deviceStop = rowView.findViewById<Button>(R.id.buttonDeviceStop)
        val deviceButtonDown = rowView.findViewById<ImageButton>(R.id.ibuttonDeviceDown)

        val deviceIcon = rowView.findViewById<ImageView>(R.id.imageDevice)
        deviceName.text = devices[position].id

        textState.visibility = View.VISIBLE
        deviceSwitch.visibility = View.VISIBLE
        deviceStop.visibility = View.GONE
        deviceButtonDown.visibility = View.GONE
        deviceButtonUp.visibility = View.GONE
        when {
            devices[position].id.startsWith("Shutter") -> deviceIcon.setImageResource(R.drawable.shutter)
            devices[position].id.startsWith("Gara") -> deviceIcon.setImageResource(R.drawable.garage)
            devices[position].id.startsWith("Light") -> deviceIcon.setImageResource(R.drawable.lightbulb)
        }

        when(devices[position].commandSetup.command){
            "TURN ON" -> {
                deviceSwitch.isChecked = true
                textState.text = context.getString(R.string.TurnOn)
            }
            "TURN OFF" -> {
                deviceSwitch.isChecked = false
                textState.text = context.getString(R.string.TurnOff)
            }
            "OPEN" -> {
                deviceSwitch.isChecked = true
                textState.text = context.getString(R.string.Open)
            }
            "CLOSE" -> {
                deviceSwitch.isChecked = false
                textState.text = context.getString(R.string.Close)
            }
        }

        deviceSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (devices[position].id.startsWith("Light")) {
                if (isChecked) {
                    devices[position].commandSetup = CommandData("TURN ON")
                } else {
                    devices[position].commandSetup = CommandData("TURN OFF")
                }
            } else {
                if (isChecked) {
                    devices[position].commandSetup = CommandData("OPEN")
                } else {
                    devices[position].commandSetup = CommandData("CLOSE")
                }
            }
            notifyDataSetChanged()
            callback.onDeviceCommandChanged()
            System.out.println("DEVICESETUP " + devices)
        }
        return rowView
    }
}