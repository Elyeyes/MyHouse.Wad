package com.mathieu_elyes.housewad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mathieu_elyes.housewad.Adapter.DeviceAdapter
import com.mathieu_elyes.housewad.DataModel.DeviceListData
import com.mathieu_elyes.housewad.Service.DeviceService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DevicesFragment : Fragment() {
    private var devices: DeviceListData = DeviceListData(ArrayList())
    private lateinit var deviceAdapter: DeviceAdapter
    private var test : Int =0
    private val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_devices, container, false)

//        requireActivity(): Returns the Activity that the fragment is currently associated with. This is useful when you need to interact with the Activity itself or call methods that are specific to the Activity class.
//        requireContext(): Returns the Context that the fragment is currently associated with. This is useful when you need a Context for operations like accessing resources, starting services, or creating views.
//        In many cases, requireActivity() and requireContext() can be used interchangeably because an Activity is a subclass of Context. However, if you specifically need to interact with the Activity, you should use requireActivity(). If you only need a Context, requireContext() is more appropriate.

        deviceAdapter = DeviceAdapter(requireContext(), devices)
        initDeviceList(view) //init la list avant le load des infos))
        loadDevices()
        view.findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            menu()
        }

        view.findViewById<ImageButton>(R.id.buttonReload).setOnClickListener {
            loadDevices()
        }
        return view
    }

    public fun menu() {
        ButtonHelper().replaceFragment(MenuFragment(), requireActivity().supportFragmentManager, R.id.fragmentContainerView)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setSelectedItemId(R.id.otherFragmentItem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

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
                val buttonReload = requireActivity().findViewById<ImageButton>(R.id.buttonReload)
                if (responseCode == 200 && responseBody != null) {
                    System.out.println("oui: " + responseBody)
                    devices.clear()
                    for (device in responseBody.devices) {
                        devices.add(device)
                        System.out.println("mes devices : " + device)
                        buttonReload.visibility = View.GONE
                    }
                    updateDevicesList()
                } else {
                    val textHouseName = requireActivity().findViewById<TextView>(R.id.textHouseName)
                    textHouseName.text = "Error, try reloading ->"
                    buttonReload.visibility = View.VISIBLE
        }
    }

    private fun updateDevicesList() {
        if (isAdded) {
            requireActivity().runOnUiThread {
                deviceAdapter.notifyDataSetChanged()
            }
        }
    }
}