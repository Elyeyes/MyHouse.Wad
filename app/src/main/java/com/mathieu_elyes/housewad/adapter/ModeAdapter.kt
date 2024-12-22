package com.mathieu_elyes.housewad.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.mathieu_elyes.housewad.datamodel.ModeData
import com.mathieu_elyes.housewad.R

class ModeAdapter(private val context: Context,
private val modes: ArrayList<ModeData>) : BaseAdapter() {
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
        delete.setOnClickListener {
            modes.removeAt(position)
            (context as? Activity)?.runOnUiThread {
                notifyDataSetChanged()
            }
        }
        apply.setOnClickListener {

        }
        return rowView
    }
}