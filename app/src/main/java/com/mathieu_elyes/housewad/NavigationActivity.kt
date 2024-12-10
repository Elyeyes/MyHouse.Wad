package com.mathieu_elyes.housewad

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class NavigationActivity : AppCompatActivity() {
    private var token: String = ""
    private var houseId: String = ""
    private val mainScope = MainScope()
    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val houseIdStorage = HouseIdStorage(this)
        val tokenStorage = TokenStorage(this);
        mainScope.async {
            token = tokenStorage.read() ?: ""
            houseId = houseIdStorage.read() ?: ""
            System.out.println("houseId=" + houseId + "tokendeHomeActivity=" + token)
        }

        replaceFragment(MenuFragment())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setBackground(null)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.rooms -> replaceFragment(RoomsFragment())
                R.id.modes -> replaceFragment(ModesFragment())
                R.id.guest -> replaceFragment(GuestFragment())
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