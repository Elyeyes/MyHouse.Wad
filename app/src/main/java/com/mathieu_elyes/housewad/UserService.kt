package com.mathieu_elyes.housewad

import android.content.Intent
import android.view.View
import android.widget.EditText

class UserService {
    public fun login(dataToLogin: LoginOrRegisterData, onSuccess: (Int, TokenResponse?) -> Unit){
        Api().post<LoginOrRegisterData, TokenResponse>(
                "https://polyhome.lesmoulinsdudev.com/api/users/auth", dataToLogin, onSuccess)
    }

    public fun register(dataToRegister: LoginOrRegisterData, onSuccess: (Int) -> Unit){
        Api().post<LoginOrRegisterData>("https://polyhome.lesmoulinsdudev.com/api/users/register", dataToRegister, onSuccess)
    }

//    public fun getUsers({
//        Api().get<ArrayList<Order>>(https://polyhome.lesmoulinsdudev.com/api/users)
//    })
}