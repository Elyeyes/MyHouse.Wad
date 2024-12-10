package com.mathieu_elyes.housewad

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mathieu_elyes.housewad.DataModel.LoginOrRegisterData
import com.mathieu_elyes.housewad.DataModel.TokenResponseData
import com.mathieu_elyes.housewad.Service.UserService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    public fun createUser(view: View)
    {
        val intent = Intent(this, RegisterActivity::class.java);
        startActivity(intent);
    }


    public fun login(view: View) {
        val login = findViewById<EditText>(R.id.editUsername).text.toString()
        val password = findViewById<EditText>(R.id.editPassword).text.toString()
        val dataToLogin = LoginOrRegisterData(login, password)
        UserService().login(dataToLogin, ::loginSuccess)
    }

    private fun loginSuccess(responseCode: Int, tokenResponse: TokenResponseData?)
    {
        val token = tokenResponse?.token
        System.out.println("token: " + token.toString())
        if (responseCode == 200)
        {
//            val intent = Intent(this, NavigationActivity::class.java);
            val intent = Intent(this, HomeActivity::class.java);
            saveToken(token.toString()!!)
            intent.putExtra("token", token.toString());
            startActivity(intent);
        }
    }

    private fun saveToken(token: String)
    {
        val tokenStorage = TokenStorage(this)
        mainScope.launch {
            tokenStorage.write(token)
        }
    }

}