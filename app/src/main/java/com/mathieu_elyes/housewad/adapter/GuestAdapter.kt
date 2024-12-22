package com.mathieu_elyes.housewad.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.mathieu_elyes.housewad.datamodel.GuestData
import com.mathieu_elyes.housewad.R
import com.mathieu_elyes.housewad.service.UserService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class GuestAdapter(private val context: Context,
                   private val guests: ArrayList<GuestData>) : BaseAdapter() {
    private val mainScope = MainScope()
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return guests[position]
    }

    override fun getCount(): Int {
        return guests.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_guests, parent, false)
        val guest = getItem(position) as GuestData
        val guestName = rowView.findViewById<TextView>(R.id.textGuestName)
        val delete = rowView.findViewById<ImageButton>(R.id.imageButtonRemove)
        if(guest.owner == 1)
        {
            guestName.text = guest.userLogin + " | " + context.getString(R.string.Owner)
            delete.visibility = View.INVISIBLE
        }
        else
        {
            guestName.text = guest.userLogin + " | " + context.getString(R.string.Guest)
        }
        delete.setOnClickListener {
            mainScope.launch {
                UserService(context).deleteGuest(guest.userLogin) { responseCode ->
                    if (responseCode == 200) {
                        guests.removeAt(position)
                        (context as? Activity)?.runOnUiThread {
                            notifyDataSetChanged()
                        }
                    }else if(responseCode == 403){
                        (context as? Activity)?.runOnUiThread {
                            Toast.makeText((context as? Activity), "Idiot your are not the owner", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        (context as? Activity)?.runOnUiThread {
                            Toast.makeText((context as? Activity), "Guest Not Deleted", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
        return rowView
    }
}
