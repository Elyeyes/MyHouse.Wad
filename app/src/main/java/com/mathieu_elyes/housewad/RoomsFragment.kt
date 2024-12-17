package com.mathieu_elyes.housewad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mathieu_elyes.housewad.Adapter.DeviceAdapter
import com.mathieu_elyes.housewad.DataModel.DeviceData
import com.mathieu_elyes.housewad.DataModel.DeviceListData
import com.mathieu_elyes.housewad.Service.Api
import com.mathieu_elyes.housewad.Storage.HouseIdStorage
import com.mathieu_elyes.housewad.Storage.TokenStorage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject

class RoomsFragment : Fragment() {
    private var devices: DeviceListData = DeviceListData(ArrayList())
    private lateinit var deviceAdapter: DeviceAdapter
    private var test : Int =0
    // Pour récupérer le token il faut le mainScope
    private var token: String = ""
    private var houseId: String = "" // trouver le type de houseId
    private val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rooms, container, false)

//        requireActivity(): Returns the Activity that the fragment is currently associated with. This is useful when you need to interact with the Activity itself or call methods that are specific to the Activity class.
//        requireContext(): Returns the Context that the fragment is currently associated with. This is useful when you need a Context for operations like accessing resources, starting services, or creating views.
//        In many cases, requireActivity() and requireContext() can be used interchangeably because an Activity is a subclass of Context. However, if you specifically need to interact with the Activity, you should use requireActivity(). If you only need a Context, requireContext() is more appropriate.

        deviceAdapter = DeviceAdapter(requireContext(), devices)
        initDeviceList(view) //init la list avant le load des infos))
        val tokenStorage = TokenStorage(requireContext());
        val houseStorage = HouseIdStorage(requireContext());
        mainScope.async {
            token = tokenStorage.read() ?: ""
            houseId = houseStorage.read() ?: ""
            loadDevices() //mettre le load des info (péripherique, maison, ou guest) ici
        }
        view.findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            menu(it)
        }
        return view
    }

    public fun menu(view: View) {
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
        Api().get<DeviceListData>("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/devices", ::loadDevicesSuccess, token)
    }

    private fun loadDevicesSuccess(responseCode: Int, responseBody: DeviceListData?) {
        test +=1
        System.out.println("Api + $test")
        if (responseCode == 200 && responseBody != null) {
            System.out.println(responseBody)
            devices.clear()
            for (device in responseBody.devices) {
                devices.add(device)
                System.out.println(device)
            }
            updateDevicesList()
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