package com.mathieu_elyes.housewad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mathieu_elyes.housewad.adapter.ModeAdapter
import com.mathieu_elyes.housewad.datamodel.DeviceSetupData
import com.mathieu_elyes.housewad.datamodel.ModeData
import com.mathieu_elyes.housewad.service.FragmentService
import com.mathieu_elyes.housewad.service.ModeService

class ModesFragment : Fragment(), ModeAdapter.ModeAdapterCallback{
    private var modes: ArrayList<ModeData> = ArrayList()
    private var devices: ArrayList<DeviceSetupData> = ArrayList()
    private var mode: ModeData = ModeData("Default", devices)
    private lateinit var modeAdapter: ModeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modes, container, false)
        modeAdapter = ModeAdapter(requireContext(), modes, this)
        initModesList(view) //init la list avant le load des infos))
        loadModes()
        view.findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            menu()
        }
        view.findViewById<ImageButton>(R.id.iButtonAdd).setOnClickListener {
            Toast.makeText(requireActivity(), "Coming Next Update", Toast.LENGTH_LONG).show()
//            val intent = Intent(context, ModeCreateActivity::class.java)
//            startActivity(intent)
        }
        return view
    }

    private fun menu() {
        FragmentService().replaceFragment(MenuFragment(), requireActivity().supportFragmentManager, R.id.fragmentContainerView)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setSelectedItemId(R.id.otherFragmentItem)
    }

    private fun initModesList(view: View){
        val listView = view.findViewById<ListView>(R.id.listModes)
        listView.adapter = modeAdapter
    }

    private fun loadModes() {
        modes.clear()
        if (ModeService(requireActivity()).loadMode() != null) {
            mode = ModeService(requireActivity()).loadMode()!!
            modes.add(mode)
        }else{
            ModeService(requireActivity()).saveMode(mode)
            modes.add(mode)
        }
        modeAdapter.notifyDataSetChanged()
    }

    override fun onModeIntent() {
        menu()
    }
}