package com.mathieu_elyes.housewad

import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mathieu_elyes.housewad.Adapter.HouseAdapter
import com.mathieu_elyes.housewad.DataModel.HouseData
import com.mathieu_elyes.housewad.Service.Api
import com.mathieu_elyes.housewad.Service.HouseService
import com.mathieu_elyes.housewad.Service.UserService
import com.mathieu_elyes.housewad.Storage.TokenStorage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HousesActivity : AppCompatActivity() {
    private val mainScope = MainScope()
    private var houses: ArrayList<HouseData> = ArrayList()
    private lateinit var houseAdapter: HouseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_houses)
        houseAdapter = HouseAdapter(this, houses)
        initHousesList() //init la list avant le load des infos))
        loadHouses()
    }

    private fun initHousesList(){
        val listView = findViewById<ListView>(R.id.listHouses)
        listView.adapter = houseAdapter
    }

    private fun loadHouses() {
        mainScope.launch {
            HouseService(this@HousesActivity).loadHouses(::loadHousesSuccess)
        }
    }

    private fun loadHousesSuccess(responseCode: Int, loadedHouses: List<HouseData>?){
        if (responseCode == 200 && loadedHouses != null){
            houses.clear()
            houses.addAll(loadedHouses)
            updateHousesList()
        }
    }

    private fun updateHousesList(){
        runOnUiThread{
            houseAdapter.notifyDataSetChanged()
        }
    }

    public fun disconnectUser(view: View)
    {
        mainScope.launch {
            UserService(this@HousesActivity).logout()
        }
        finish()
    }

    public fun reload(view: View)
    {
        loadHouses()
    }
}