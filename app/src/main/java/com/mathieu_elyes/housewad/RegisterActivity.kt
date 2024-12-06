package com.mathieu_elyes.housewad

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    public fun back(view: View)
    {
        finish()
    }

    public fun register(view: View)
    {
        val login = findViewById<EditText>(R.id.editUsername).text.toString()
        val password = findViewById<EditText>(R.id.editPassword).text.toString()
        val dataToRegister = LoginOrRegisterData(login, password)
        UserService().register(dataToRegister, ::registerSuccess)
    }

    private fun registerSuccess(responseCode: Int)
    {
        if (responseCode == 200)
        {
            finish()
        }
    }
}