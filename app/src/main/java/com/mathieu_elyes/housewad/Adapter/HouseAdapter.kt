package com.mathieu_elyes.housewad.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mathieu_elyes.housewad.DataModel.HouseData
import com.mathieu_elyes.housewad.R

class HouseAdapter(private val context: Context,
                   private val houses: ArrayList<HouseData>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return houses[position]
    }

    override fun getCount(): Int {
        return houses.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_houses, parent, false)
        val house = getItem(position) as HouseData
        val houseName = rowView.findViewById<TextView>(R.id.textHouseName)
        houseName.text = house.name
        return rowView
    }
}