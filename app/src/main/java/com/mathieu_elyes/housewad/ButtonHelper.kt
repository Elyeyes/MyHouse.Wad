package com.mathieu_elyes.housewad

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class ButtonHelper {
    public fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager, fragmentContainerView: Int) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(fragmentContainerView, fragment)
//        fragmentTransaction.addToBackStack(null) // Permet de revenir au fragment précédent
        fragmentTransaction.commit()
    }
}