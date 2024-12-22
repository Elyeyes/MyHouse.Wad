package com.mathieu_elyes.housewad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mathieu_elyes.housewad.adapter.GuestAdapter
import com.mathieu_elyes.housewad.datamodel.GuestData
import com.mathieu_elyes.housewad.service.FragmentService
import com.mathieu_elyes.housewad.service.UserService

class GuestFragment : Fragment() {
    private var guests: ArrayList<GuestData> = ArrayList()
    private lateinit var guestAdapter: GuestAdapter
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guest, container, false)
        val verify = view.findViewById<TextView>(R.id.textVerify)

//        requireActivity(): Returns the Activity that the fragment is currently associated with. This is useful when you need to interact with the Activity itself or call methods that are specific to the Activity class.
//        requireContext(): Returns the Context that the fragment is currently associated with. This is useful when you need a Context for operations like accessing resources, starting services, or creating views.
//        In many cases, requireActivity() and requireContext() can be used interchangeably because an Activity is a subclass of Context. However, if you specifically need to interact with the Activity, you should use requireActivity(). If you only need a Context, requireContext() is more appropriate.

        guestAdapter = GuestAdapter(requireContext(), guests)
        initGuestsList(view) //init la list avant le load des infos))
        loadGuests()
        view.findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            menu()
        }

        view.findViewById<ImageButton>(R.id.buttonAddGuest).setOnClickListener {
            val guestName = view.findViewById<EditText>(R.id.editGuestName)
            if (guestName.text.toString() != "") {
                addGuests(guestName.text.toString())
                guestName.text.clear()
            }else{
                verify.text = "Please enter a name"
            }
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

    private fun initGuestsList(view: View){
        val listView = view.findViewById<ListView>(R.id.listGuests)
        listView.adapter = guestAdapter
    }

    private fun loadGuests(){
        UserService(requireActivity()).loadGuest(::loadGuestsSuccess)
        }

    private fun loadGuestsSuccess(responseCode: Int, loadedGuests: List<GuestData>?){
        if (responseCode == 200 && loadedGuests != null){
            guests.clear()
            guests.addAll(loadedGuests)
            updateGuestsList()
        }else if(responseCode == 500){
            requireActivity().runOnUiThread {
                Toast.makeText(requireActivity(), "Error, Please Try Again", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addGuests(userLogin: String){
        UserService(requireActivity()).addGuest(userLogin, ::addGuestSuccess)
    }

    private fun addGuestSuccess(responseCode: Int){
        if (responseCode == 200) {
            loadGuests()
        }else if(responseCode == 400){
            requireActivity().runOnUiThread {
                Toast.makeText(requireActivity(), "Invalid Username, Try Again", Toast.LENGTH_LONG).show()
            }
        }else{
            requireActivity().runOnUiThread {
                Toast.makeText(requireActivity(), "Error, Please Try Again", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateGuestsList(){
        requireActivity().runOnUiThread{
            guestAdapter.notifyDataSetChanged()
        }
    }
}