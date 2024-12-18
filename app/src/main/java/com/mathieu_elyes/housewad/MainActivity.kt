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
import com.mathieu_elyes.housewad.Storage.TokenStorage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        UserService(this).login(dataToLogin, ::loginSuccess)
    }

    private fun loginSuccess(responseCode: Int, tokenResponse: TokenResponseData?)
    {
        val token = tokenResponse?.token.toString()
        System.out.println("mon token: " + token)
        if (responseCode == 200)
        {
            mainScope.launch {
                saveToken(token)
                val intent = Intent(this@MainActivity, HousesActivity::class.java);
                startActivity(intent);
            }
        }
    }

    private suspend fun saveToken(token: String)
    {
        val tokenStorage = TokenStorage(this)
        tokenStorage.write(token)
    }
}
