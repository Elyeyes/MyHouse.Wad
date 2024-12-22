package com.mathieu_elyes.housewad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.mathieu_elyes.housewad.DataModel.CommandData
import com.mathieu_elyes.housewad.DataModel.DeviceListData
import com.mathieu_elyes.housewad.Service.DeviceService
import com.mathieu_elyes.housewad.Service.HouseService

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
        SetSpinner(view)

        val textWelcome = view.findViewById<TextView>(R.id.textWelcome)
        textWelcome.text = getString(R.string.House) + ": " + HouseService(requireContext()).getHouse()

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

    private fun changeDisplayMode() {
        val switchIcon = requireActivity().findViewById<ImageButton>(R.id.buttonNDMode)
        val sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            switchIcon.setImageResource(R.drawable.day)
            editor.putBoolean("NightMode", true)
        } else {
            switchIcon.setImageResource(R.drawable.night_icon)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            editor.putBoolean("NightMode", false)
        }
        editor.apply()
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

    public fun SetSpinner(view: View) {
        val languages = arrayOf(getString(R.string.selectLanguage), getString(R.string.English), getString(R.string.French))
        val spinner = view.findViewById<Spinner>(R.id.spinnerLanguage)
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val langCode = when (position) {
                    1 -> "en"
                    2 -> "fr"
                    else -> ""
                }
                if (langCode.isNotEmpty()) {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(langCode))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    public fun disconnectUser(view: View) {
        HouseService(requireContext()).quitHouse()
        requireActivity().finish()
    }
}