package com.mathieu_elyes.housewad.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.mathieu_elyes.housewad.ModeEditActivity
import com.mathieu_elyes.housewad.datamodel.ModeData
import com.mathieu_elyes.housewad.R
import com.mathieu_elyes.housewad.service.DeviceService
import com.mathieu_elyes.housewad.storage.ModeStorage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ModeAdapter(private val context: Context,
private val modes: ArrayList<ModeData>) : BaseAdapter() {
    private val mainScope = MainScope()
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return modes[position]
    }

    override fun getCount(): Int {
        return modes.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_modes, parent, false)
        val mode = getItem(position) as ModeData
        val modeName = rowView.findViewById<TextView>(R.id.textModeName)
        val delete = rowView.findViewById<ImageButton>(R.id.imageButtonRemove)
        val apply = rowView.findViewById<Button>(R.id.ButtonApply)
        modeName.text = mode.name

        delete.setOnClickListener {
            modes.removeAt(position)
            (context as? Activity)?.runOnUiThread {
                notifyDataSetChanged()
            }
        }

        apply.setOnClickListener {
            for(device in modes[position].deviceSetupList){
                System.out.println(modes[position].deviceSetupList)
                System.out.println("device id: " + device.id + " command: " + device.commandSetup + " mode: " + modes[position].name)
                val deviceId = device.id
                val commandData = device.commandSetup
                DeviceService(this.context).command(deviceId, commandData, ::commandSuccess)
            }
        }

        rowView.setOnClickListener {
            mainScope.launch {
                saveMode(modes[position])
                val intent = Intent(context, ModeEditActivity::class.java)
                context.startActivity(intent)
            }
        }

        return rowView
    }

    private fun commandSuccess(responseCode: Int) {
        if (responseCode == 200) {
            (context as? Activity)?.runOnUiThread {
                Toast.makeText((context as? Activity), "Mode Applied", Toast.LENGTH_LONG).show()
            }
        }else{
            (context as? Activity)?.runOnUiThread {
                Toast.makeText((context as? Activity), "Error, Please Try Again", Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun saveMode(mode: ModeData)
    {
        val modeStorage = ModeStorage(context)
        modeStorage.write(mode)
        System.out.println("nom: " + mode.name + " " + mode.deviceSetupList)
    }
}