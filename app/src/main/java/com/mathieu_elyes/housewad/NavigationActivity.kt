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

class NavigationActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private var isNavigationEnabled = true
    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
//        val rootView: View = findViewById(android.R.id.content)
//        rootView.setBackgroundResource(R.attr.background)
        replaceFragment(MenuFragment())
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setBackground(null)
        bottomNavigationView.setSelectedItemId(R.id.otherFragmentItem)
        bottomNavigationView.setOnItemSelectedListener { item ->
            if (isNavigationEnabled) {
                handler.postDelayed({ isNavigationEnabled = true }, 500)
                isNavigationEnabled = false
                when (item.itemId) {
                    R.id.devices -> replaceFragment(DevicesFragment())
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