package com.mathieu_elyes.housewad

import android.content.Intent
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
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class HomeActivity : AppCompatActivity() {
    private var houses: ArrayList<HouseData> = ArrayList()
    private lateinit var houseAdapter: HouseAdapter
    // Pour récupérer le token il faut le mainScope
    private var token: String = ""
    private val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        houseAdapter = HouseAdapter(this, houses)
        initHousesList() //init la list avant le load des infos))
        val tokenStorage = TokenStorage(this);
        mainScope.async {
            token = tokenStorage.read() ?: ""
//            loadOrders()  //mettre le load des info (péripherique, maison, ou guest) ici
            loadHouses()
        }
    }

    private fun initHousesList(){
        val listView = findViewById<ListView>(R.id.listHouses)
        listView.adapter = houseAdapter
    }

    private fun loadHouses(){
        Api().get<ArrayList<HouseData>>("https://polyhome.lesmoulinsdudev.com/api/houses", ::loadHousesSuccess, token)
    }

    private fun loadHousesSuccess(responseCode: Int, loadedHouses: List<HouseData>?){
        System.out.println(loadedHouses)
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
}