package com.mathieu_elyes.housewad

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables")
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
                R.id.rooms_ -> replaceFragment(RoomsFragment())
                R.id.modes -> replaceFragment(ModesFragment())
                R.id.modesOrGuest_ -> {
                    if (bottomNavigationView.menu.findItem(R.id.modesOrGuest_).title == "Guest"){
                        replaceFragment(GuestFragment())
                    } else {
                        replaceFragment(ModesFragment())
                    }
                }
                R.id.guest -> replaceFragment(GuestFragment())
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