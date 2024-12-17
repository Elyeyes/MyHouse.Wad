package com.mathieu_elyes.housewad.Service

import com.mathieu_elyes.housewad.DataModel.LoginOrRegisterData
import com.mathieu_elyes.housewad.DataModel.TokenResponseData

class UserService {
    public fun login(dataToLogin: LoginOrRegisterData, onSuccess: (Int, TokenResponseData?) -> Unit){
        Api().post<LoginOrRegisterData, TokenResponseData>(
                "https://polyhome.lesmoulinsdudev.com/api/users/auth", dataToLogin, onSuccess)
    }

    public fun register(dataToRegister: LoginOrRegisterData, onSuccess: (Int) -> Unit){
        Api().post<LoginOrRegisterData>("https://polyhome.lesmoulinsdudev.com/api/users/register", dataToRegister, onSuccess)
    }

//    public fun getUsers(onSuccess: (Int, ArrayList<Guest>?) -> Unit){
//        Api().get<ArrayList<Guest>>(https://polyhome.lesmoulinsdudev.com/api/users)
//    })
}