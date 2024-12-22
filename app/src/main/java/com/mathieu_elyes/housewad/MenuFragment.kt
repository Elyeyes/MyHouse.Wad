package com.mathieu_elyes.housewad

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.mathieu_elyes.housewad.DataModel.CommandData
import com.mathieu_elyes.housewad.DataModel.DeviceListData
import com.mathieu_elyes.housewad.Service.DeviceService
import com.mathieu_elyes.housewad.Service.HouseService
import kotlinx.coroutines.launch

class MenuFragment : Fragment() {
    private val devices: DeviceListData = DeviceListData(ArrayList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val textWelcome = view.findViewById<TextView>(R.id.textWelcome)
        textWelcome.text = "House: ${HouseService(requireActivity()).getHouse()}"

        view.findViewById<ImageButton>(R.id.buttonNDMode).setOnClickListener {
            changeDisplayMode()
        }
        view.findViewById<Button>(R.id.buttonShutdown).setOnClickListener {
            shutdown(it)
        }
        view.findViewById<ImageButton>(R.id.buttonQuitHouse).setOnClickListener {
            disconnectUser(it)
        }
        return view
    }

    public fun changeDisplayMode() {
        System.out.println("Night mode" + AppCompatDelegate.getDefaultNightMode())
        val switchIcon = requireActivity().findViewById<ImageButton>(R.id.buttonNDMode)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            System.out.println("Night mode")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            switchIcon.setImageResource(R.drawable.day)
        } else {
            switchIcon.setImageResource(R.drawable.night_icon)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        requireActivity().recreate()
    }

    public fun shutdown(view: View) {
        DeviceService(requireActivity()).loadDevices() { responseCode, responseBody ->
            if (responseCode == 200) {
                devices.clear()
                for (device in responseBody!!.devices) {
                    devices.add(device)
                }
                for (device in devices.devices) {
                    val commandData = if (device.type == "light") {
                        CommandData("TURN OFF")
                    } else {
                        CommandData("CLOSE")
                    }
                    DeviceService(requireActivity()).command(device.id, commandData, ::commandSuccess)
                }
            }
        }
    }
    private fun commandSuccess(responseCode: Int) {
        if (responseCode == 200) {
            return
        }
    }

    public fun disconnectUser(view: View) {
        HouseService(requireContext()).quitHouse()
        requireActivity().finish()
    }
}