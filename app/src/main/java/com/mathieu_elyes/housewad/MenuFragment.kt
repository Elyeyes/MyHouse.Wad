package com.mathieu_elyes.housewad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.mathieu_elyes.housewad.Service.HouseService

class MenuFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        view.findViewById<ImageButton>(R.id.buttonQuitHouse).setOnClickListener {
            disconnectUser(it)
        }
        return view
    }

    public fun disconnectUser(view: View) {
        HouseService(requireContext()).quitHouse()
        requireActivity().finish()
    }
}