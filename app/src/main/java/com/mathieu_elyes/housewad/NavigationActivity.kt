package com.mathieu_elyes.housewad

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
//        val intent: Intent = getIntent()
//        token = intent.getStringExtra("token").toString()

        replaceFragment(MenuFragment())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setBackground(null)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.rooms -> replaceFragment(RoomsFragment())
                R.id.modes -> replaceFragment(ModesFragment())
                R.id.guest -> {
                    replaceFragment(GuestFragment())
                    if (bottomNavigationView.menu.findItem(R.id.rooms).isVisible) {
                        bottomNavigationView.menu.findItem(R.id.rooms).isVisible = false
                    }
                    else{
                        bottomNavigationView.menu.findItem(R.id.rooms).isVisible = true
                    }}
//
//                bottomNavigationView.menu.findItem(R.id.rooms).isVisible = false}
            }
            true
        }
    }
//
//    private fun test(){
//        val buttonTest: Button = findViewById(R.id.buttonTest)
//        buttonTest.setOnClickListener {
//            replaceFragment(MenuFragment())
//            true
//        }
//        System.out.println("ButtonTest" + "Button ID: ${buttonTest.id}, Text: ${buttonTest.text}")
//    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        fragment.arguments = Bundle().apply {
//            putString("token", token)
//        }
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
}