package com.mathieu_elyes.housewad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.bottomnavigation.BottomNavigationView

class RoomsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rooms, container, false)
        view.findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            menu(it)
        }
        return view
    }

    fun menu(view: View) {
        ButtonHelper().replaceFragment(MenuFragment(), requireActivity().supportFragmentManager, R.id.fragmentContainerView)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        if (bottomNavigationView.menu.findItem(R.id.rooms).isVisible) {
            bottomNavigationView.menu.findItem(R.id.rooms).isVisible = false
            bottomNavigationView.menu.findItem(R.id.rooms_).isVisible = true
        }
        else{
            bottomNavigationView.menu.findItem(R.id.rooms).isVisible = true
            bottomNavigationView.menu.findItem(R.id.rooms_).isVisible = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}