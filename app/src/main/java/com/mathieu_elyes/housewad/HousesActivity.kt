package com.mathieu_elyes.housewad

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mathieu_elyes.housewad.adapter.HouseAdapter
import com.mathieu_elyes.housewad.datamodel.HouseData
import com.mathieu_elyes.housewad.service.HouseService
import com.mathieu_elyes.housewad.service.UserService
import kotlinx.coroutines.MainScope
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
        }else{
            runOnUiThread {
                Toast.makeText(this, "Houses Loading Failed. Please Retry.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateHousesList(){
        runOnUiThread{
            houseAdapter.notifyDataSetChanged()
        }
    }

    fun disconnectUser(view: View)
    {
        mainScope.launch {
            UserService(this@HousesActivity).logout()
        }
        finish()
    }

    fun reload(view: View)
    {
        loadHouses()
    }
}