package com.mathieu_elyes.housewad

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mathieu_elyes.housewad.adapter.ModeAdapter
import com.mathieu_elyes.housewad.datamodel.CommandData
import com.mathieu_elyes.housewad.datamodel.DeviceSetupData
import com.mathieu_elyes.housewad.datamodel.ModeData
import com.mathieu_elyes.housewad.service.FragmentService

class ModesFragment : Fragment() {
    private var modes: ArrayList<ModeData> = ArrayList()
    private lateinit var modeAdapter: ModeAdapter
//    private val mainScope = MainScope()
//    private lateinit var mode: ModeData
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modes, container, false)
        modeAdapter = ModeAdapter(requireContext(), modes)
        initModesList(view) //init la list avant le load des infos))
        loadModes()
//        mainScope.launch {
//            readMode()
//        }
//        System.out.println("test read: " + mode)
        view.findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            menu()
        }
        view.findViewById<ImageButton>(R.id.iButtonAdd).setOnClickListener {
            val intent = Intent(context, ModeEditActivity::class.java)
            startActivity(intent)
        }
        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }

    private fun menu() {
        FragmentService().replaceFragment(MenuFragment(), requireActivity().supportFragmentManager, R.id.fragmentContainerView)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setSelectedItemId(R.id.otherFragmentItem)
    }

    private fun initModesList(view: View){
        val listView = view.findViewById<ListView>(R.id.listModes)
        listView.adapter = modeAdapter
    }

//    private suspend fun readMode() {
//        val modeStorage = ModeStorage(requireActivity())
//        mode = modeStorage.read() ?: ModeData("Default", ArrayList())
//    }

    private fun loadModes() {
        modes.clear()
        val deviceSetupList = ArrayList<DeviceSetupData>()
        deviceSetupList.add(DeviceSetupData("Shutter 1.1", CommandData("OPEN")))
        deviceSetupList.add(DeviceSetupData("Shutter 1.2", CommandData("OPEN")))
        val dayMode = ModeData("Day", deviceSetupList)
        val deviceSetupList2 = ArrayList<DeviceSetupData>()
        deviceSetupList2.add(DeviceSetupData("Shutter 1.1", CommandData("CLOSE")))
        deviceSetupList2.add(DeviceSetupData("Shutter 1.2", CommandData("CLOSE")))
        val nightMode = ModeData("Night", deviceSetupList2)
        modes.add(nightMode)
        modes.add(dayMode)
        modeAdapter.notifyDataSetChanged()
    }
}