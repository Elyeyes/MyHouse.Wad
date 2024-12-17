package com.mathieu_elyes.housewad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mathieu_elyes.housewad.Adapter.GuestAdapter
import com.mathieu_elyes.housewad.DataModel.GuestData
import com.mathieu_elyes.housewad.DataModel.LoginOrRegisterData
import com.mathieu_elyes.housewad.Service.Api
import com.mathieu_elyes.housewad.Storage.HouseIdStorage
import com.mathieu_elyes.housewad.Storage.TokenStorage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class GuestFragment : Fragment() {
    private var guests: ArrayList<GuestData> = ArrayList()
    private lateinit var guestAdapter: GuestAdapter
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
        val view = inflater.inflate(R.layout.fragment_guest, container, false)
//        setContentView(R.layout.fragment_guest)

//        requireActivity(): Returns the Activity that the fragment is currently associated with. This is useful when you need to interact with the Activity itself or call methods that are specific to the Activity class.
//        requireContext(): Returns the Context that the fragment is currently associated with. This is useful when you need a Context for operations like accessing resources, starting services, or creating views.
//        In many cases, requireActivity() and requireContext() can be used interchangeably because an Activity is a subclass of Context. However, if you specifically need to interact with the Activity, you should use requireActivity(). If you only need a Context, requireContext() is more appropriate.

        guestAdapter = GuestAdapter(requireContext(), guests)
        initGuestsList(view) //init la list avant le load des infos))
        val tokenStorage = TokenStorage(requireContext());
        val houseStorage = HouseIdStorage(requireContext());
        mainScope.async {
            token = tokenStorage.read() ?: ""
            houseId = houseStorage.read() ?: ""
            loadGuests() //mettre le load des info (péripherique, maison, ou guest) ici
        }
        view.findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            menu(it)
        }

//        view.findViewById<ImageButton>(R.id.buttonAddGuest).setOnClickListener {
//            addGuests(it)
//        }

        return view
    }

    private fun menu(view: View) {
        ButtonHelper().replaceFragment(MenuFragment(), requireActivity().supportFragmentManager, R.id.fragmentContainerView)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setSelectedItemId(R.id.otherFragmentItem)
    }

//    private fun addGuests(view: View){
//        val guestName = view.findViewById<EditText>(R.id.editGuestName).text.toString()
//        Api().post<String, String>("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/users", guestName, ::addGuestsSuccess, token)
//    }

    private fun addGuestSuccess(responseCode: Int){
        if (responseCode == 200) {
            loadGuests()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initGuestsList(view: View){
        val listView = view.findViewById<ListView>(R.id.listGuests)
        listView.adapter = guestAdapter
    }

    private fun loadGuests(){
        Api().get<ArrayList<GuestData>>("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/users", ::loadGuestsSuccess, token)
    }

    private fun loadGuestsSuccess(responseCode: Int, loadedGuests: List<GuestData>?){
        System.out.println(loadedGuests)
        if (responseCode == 200 && loadedGuests != null){
            guests.clear()
            guests.addAll(loadedGuests)
            updateGuestsList()
        }
    }

    private fun updateGuestsList(){
        requireActivity().runOnUiThread{
            guestAdapter.notifyDataSetChanged()
        }
    }
}