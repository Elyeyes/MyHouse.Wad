package com.mathieu_elyes.housewad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.google.android.material.bottomnavigation.BottomNavigationView

class ModesFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modes, container, false)
        view.findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            menu(it)
        }
        return view
    }

    fun menu(view: View) {
        ButtonHelper().replaceFragment(MenuFragment(), requireActivity().supportFragmentManager, R.id.fragmentContainerView)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setSelectedItemId(R.id.otherFragmentItem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}