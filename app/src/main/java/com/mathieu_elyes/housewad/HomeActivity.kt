package com.mathieu_elyes.housewad

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mathieu_elyes.housewad.Adapter.HouseAdapter
import com.mathieu_elyes.housewad.DataModel.HouseData
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
        initHousesList() //init la list avant le load des infos
        houses.add(HouseData(1, "Maison 1"))
        val tokenStorage = TokenStorage(this);
        mainScope.async {
            token = tokenStorage.read() ?: ""
//            loadOrders()  //mettre le load des info (péripherique, maison, ou guest) ici
            System.out.println("tokendeHomeActivity=" + token)
        }
    }

    private fun initHousesList(){
        val listView = findViewById<ListView>(R.id.listHouses)
        listView.adapter = houseAdapter
    }

    private fun updateHousesList(){
        runOnUiThread{
            houseAdapter.notifyDataSetChanged()
        }
    }
}