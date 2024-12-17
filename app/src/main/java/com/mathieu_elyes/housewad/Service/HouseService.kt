package com.mathieu_elyes.housewad.Service

import android.content.Context
import com.mathieu_elyes.housewad.DataModel.HouseData

class HouseService(context: Context): BaseService(context) {
    public fun loadHouses(onSuccess: (Int, ArrayList<HouseData>?) -> Unit){
        Api().get<ArrayList<HouseData>>("https://polyhome.lesmoulinsdudev.com/api/houses", onSuccess, getToken())
    }
//
//    public fun login(dataToLogin: LoginOrRegisterData, onSuccess: (Int, TokenResponse?) -> Unit){
//        Api().post<LoginOrRegisterData, TokenResponse>(
//            "https://polyhome.lesmoulinsdudev.com/api/users/auth", dataToLogin, onSuccess)
//    }
//
//    public fun login(dataToLogin: LoginOrRegisterData, onSuccess: (Int, TokenResponse?) -> Unit){
//        Api().post<LoginOrRegisterData, TokenResponse>(
//            "https://polyhome.lesmoulinsdudev.com/api/users/auth", dataToLogin, onSuccess)
//    }
//
//    public fun login(dataToLogin: LoginOrRegisterData, onSuccess: (Int, TokenResponse?) -> Unit){
//        Api().post<LoginOrRegisterData, TokenResponse>(
//            "https://polyhome.lesmoulinsdudev.com/api/users/auth", dataToLogin, onSuccess)
//    }
}