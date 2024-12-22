package com.mathieu_elyes.housewad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mathieu_elyes.housewad.Adapter.ModeAdapter
import com.mathieu_elyes.housewad.DataModel.ModeData
import com.mathieu_elyes.housewad.Service.FragmentService

class ModesFragment : Fragment() {
    private var modes: ArrayList<ModeData> = ArrayList()
    private lateinit var modeAdapter: ModeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modes, container, false)
        modeAdapter = ModeAdapter(requireContext(), modes)
        initModesList(view) //init la list avant le load des infos))
        loadModes()
        view.findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            menu(it)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    public fun menu(view: View) {
        FragmentService().replaceFragment(MenuFragment(), requireActivity().supportFragmentManager, R.id.fragmentContainerView)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setSelectedItemId(R.id.otherFragmentItem)
    }

    private fun initModesList(view: View){
        val listView = view.findViewById<ListView>(R.id.listModes)
        listView.adapter = modeAdapter
    }

    private fun loadModes(){
        modes.clear()
//        modes.add()
        modeAdapter.notifyDataSetChanged()
    }
}