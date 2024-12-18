package com.mathieu_elyes.housewad

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.MainScope

class NavigationActivity : AppCompatActivity() {
    private var token: String = ""
    private var houseId: String = ""
    private val mainScope = MainScope()
    private val handler = Handler(Looper.getMainLooper())
    private var isNavigationEnabled = true
    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        //faire à la place getToken ou getHouseId dans la fonction dappel d'api
        replaceFragment(MenuFragment())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setBackground(null)
        bottomNavigationView.setOnItemSelectedListener { item ->
            if (isNavigationEnabled) {
                isNavigationEnabled = false
                handler.postDelayed({ isNavigationEnabled = true }, 1000)
            when (item.itemId) {
                R.id.rooms -> replaceFragment(DevicesFragment())
                R.id.modes -> replaceFragment(ModesFragment())
                R.id.guest -> replaceFragment(GuestFragment())
            }
            }
            true
        }
    }

    public fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        fragment.arguments = Bundle().apply {
//            putString("token", token)
//        }
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment, "${fragment.javaClass.simpleName}")
//        fragmentTransaction.addToBackStack(null) // Permet de revenir au fragment précédent
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack() // Revient au fragment précédent
        } else {
            super.onBackPressed() // Quitte l'activité
        }
    }

}