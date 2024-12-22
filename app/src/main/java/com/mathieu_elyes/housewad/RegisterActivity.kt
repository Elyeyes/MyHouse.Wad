package com.mathieu_elyes.housewad

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mathieu_elyes.housewad.datamodel.LoginOrRegisterData
import com.mathieu_elyes.housewad.service.UserService

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun back(view: View)
    {
        finish()
    }

    fun register(view: View)
    {
        val login = findViewById<EditText>(R.id.editUsername).text.toString()
        val password = findViewById<EditText>(R.id.editPassword).text.toString()
        val dataToRegister = LoginOrRegisterData(login, password)
        UserService(this).register(dataToRegister, ::registerSuccess)
    }

    private fun registerSuccess(responseCode: Int)
    {
        if (responseCode == 200)
        {
            finish()
        }else if (responseCode == 409){
            runOnUiThread {
                Toast.makeText(this, "Username Taken. Please try again.", Toast.LENGTH_LONG).show()
            }
        }else if (responseCode == 400){
            runOnUiThread {
                Toast.makeText(this, "Username or Password Invalid. Please try again.", Toast.LENGTH_LONG).show()
            }
        }else{
            runOnUiThread {
                Toast.makeText(this, "Register Failed. Please try again.", Toast.LENGTH_LONG).show()
            }
        }
    }
}