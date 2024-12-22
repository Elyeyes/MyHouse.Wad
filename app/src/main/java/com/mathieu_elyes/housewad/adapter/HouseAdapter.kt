package com.mathieu_elyes.housewad.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mathieu_elyes.housewad.datamodel.HouseData
import com.mathieu_elyes.housewad.storage.HouseIdStorage
import com.mathieu_elyes.housewad.NavigationActivity
import com.mathieu_elyes.housewad.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class HouseAdapter(private val context: Context,
                   private val houses: ArrayList<HouseData>) : BaseAdapter() {
    private val mainScope = MainScope()
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
        if (house.owner) {
            houseName.text = context.getString(R.string.House) + ": " + house.houseId.toString() + " " + context.getString(R.string.Owner)
        } else {
            houseName.text = context.getString(R.string.House) + ": " + house.houseId.toString() + " " + context.getString(R.string.Guest)

        }
        rowView.setOnClickListener {
            val intent = Intent(context, NavigationActivity::class.java)
            saveHouseId(house.houseId.toString())
            context.startActivity(intent)
        }
        return rowView
    }

    private fun saveHouseId(houseId: String)
    {
        val houseIdStorage = HouseIdStorage(context)
        mainScope.launch {
            houseIdStorage.write(houseId)
        }
    }
}