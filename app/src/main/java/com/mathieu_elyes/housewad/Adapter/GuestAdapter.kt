package com.mathieu_elyes.housewad.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.mathieu_elyes.housewad.DataModel.GuestData
import com.mathieu_elyes.housewad.Storage.GuestIdStorage
import com.mathieu_elyes.housewad.NavigationActivity
import com.mathieu_elyes.housewad.R
import com.mathieu_elyes.housewad.Service.Api
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class GuestAdapter(private val context: Context,
                   private val guests: ArrayList<GuestData>) : BaseAdapter() {
    private var token: String = ""
    private val mainScope = MainScope()
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        System.out.println("guest position" + guests[position])
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
//        delete.setOnClickListener{
//            Api().delete<String>("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/users", guest.userLogin, ::deleteSuccess, token)
//        }
        if(guest.owner == 1)
        {
            guestName.text = guest.userLogin + " | Owner"
        }
        else
        {
            guestName.text = guest.userLogin + " | Guest"
        }
//        rowView.setOnClickListener {
//            val intent = Intent(context, NavigationActivity::class.java)
//            saveUserLogin(guest.userLogin)
//            context.startActivity(intent)
//        }
        return rowView
    }

    private fun saveUserLogin(userLogin: String)
    {
        val guestIdStorage = GuestIdStorage(context)
        mainScope.launch {
            guestIdStorage.write(userLogin)
        }
    }
}
