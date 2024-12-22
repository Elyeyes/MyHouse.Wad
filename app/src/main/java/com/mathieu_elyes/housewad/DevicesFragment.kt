package com.mathieu_elyes.housewad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mathieu_elyes.housewad.adapter.DeviceAdapter
import com.mathieu_elyes.housewad.datamodel.DeviceListData
import com.mathieu_elyes.housewad.service.DeviceService
import com.mathieu_elyes.housewad.service.FragmentService
import com.mathieu_elyes.housewad.service.HouseService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DevicesFragment : Fragment() {
    private var devices: DeviceListData = DeviceListData(ArrayList())
    private lateinit var deviceAdapter: DeviceAdapter
    private val mainScope = MainScope()
    private var devicesDisplayed = "all"

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_devices, container, false)

//        requireActivity(): Returns the Activity that the fragment is currently associated with. This is useful when you need to interact with the Activity itself or call methods that are specific to the Activity class.
//        requireContext(): Returns the Context that the fragment is currently associated with. This is useful when you need a Context for operations like accessing resources, starting services, or creating views.
//        In many cases, requireActivity() and requireContext() can be used interchangeably because an Activity is a subclass of Context. However, if you specifically need to interact with the Activity, you should use requireActivity(). If you only need a Context, requireContext() is more appropriate.

        deviceAdapter = DeviceAdapter(requireContext(), devices, devicesDisplayed)
        initDeviceList(view) //init la list avant le load des infos))
        loadDevices()
        view.findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            menu()
        }
        view.findViewById<ImageButton>(R.id.buttonReload).setOnClickListener {
            loadDevices()
        }
        view.findViewById<ImageButton>(R.id.buttonChangeDisplay).setOnClickListener {
            filterDevices()
        }
        return view
    }

    private fun menu() {
        FragmentService().replaceFragment(MenuFragment(), requireActivity().supportFragmentManager, R.id.fragmentContainerView)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setSelectedItemId(R.id.otherFragmentItem)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }

    private fun initDeviceList(view: View){
        val listView = view.findViewById<ListView>(R.id.listDevices)
        listView.adapter = deviceAdapter
    }

    private fun loadDevices(){
        mainScope.launch {
            if (isAdded) {
                DeviceService(requireActivity()).loadDevices(::loadDevicesSuccess)
            }
        }
    }

    private fun loadDevicesSuccess(responseCode: Int, responseBody: DeviceListData?) {
        if (!isAdded) return
        requireActivity().runOnUiThread {
            val buttonReload = requireActivity().findViewById<ImageButton>(R.id.buttonReload)
            if (responseCode == 200 && responseBody != null) {
                val textHouse = view?.findViewById<TextView>(R.id.textHouseName)
                textHouse?.text = getString(R.string.DevicesofHouse) + ": ${HouseService(requireActivity()).getHouse()}"
                devices.clear()
                for (device in responseBody.devices) {
                    if (device.type == devicesDisplayed) {
                        devices.add(device)
                        buttonReload.visibility = View.GONE
                    }else if(devicesDisplayed == "all"){
                        devices.add(device)
                        buttonReload.visibility = View.GONE
                    }
                }
                updateDevicesList()
            } else {
                val textHouse = requireActivity().findViewById<TextView>(R.id.textHouseName)
                textHouse.text = "Error, try reloading ->"
                buttonReload.visibility = View.VISIBLE
                Toast.makeText(requireActivity(), "House doesn't Exist.", Toast.LENGTH_LONG).show()
        }
//            errorLoadingHouse()
        }
    }
//    private fun errorLoadingHouse() {
//        if (isAdded) {
//            requireActivity().runOnUiThread {
//                AlertDialog.Builder(requireContext())
//                    .setTitle("Error")
//                    .setMessage("You need to load a house first")
//                    .show()
//            }
//        }
//    }

    private fun updateDevicesList() {
        if (isAdded) {
            requireActivity().runOnUiThread {
                deviceAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun filterDevices() {
        val buttonChangeDisplay = requireActivity().findViewById<ImageButton>(R.id.buttonChangeDisplay)
        when (devicesDisplayed){
            "all" -> {
                devicesDisplayed = "light"
                buttonChangeDisplay.setImageResource(R.drawable.lightbulb)
            }
            "light" -> {
                devicesDisplayed = "rolling shutter"
                buttonChangeDisplay.setImageResource(R.drawable.shutter)
            }
            "rolling shutter" -> {
                devicesDisplayed = "garage door"
                buttonChangeDisplay.setImageResource(R.drawable.garage)
            }
            else -> {
                devicesDisplayed = "all"
                buttonChangeDisplay.setImageResource(R.drawable.device)
            }
        }
        deviceAdapter.updateDevicesDisplayed(devicesDisplayed)
    }
}
