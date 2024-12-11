package com.mathieu_elyes.housewad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        return view
    }

    public fun disconnectUser(view: View) {
        requireActivity().finish()
    }

//    public fun disconnectUser(view: View)
//    {
//        Try getActivity().finish() after calling popBackStackImmediate()
//        val fragmentTransaction: FragmentTransaction =
//            requireActivity().supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(
//            R.id.fragmentContainerView, fragment)
////        fragmentTransaction.addToBackStack(null) // Permet de revenir au fragment précédent
//        fragmentTransaction.commit()
//        finish()
//    }

}