package com.mathieu_elyes.housewad.Service

import android.content.Context
import com.mathieu_elyes.housewad.DataModel.GuestData
import com.mathieu_elyes.housewad.DataModel.LoginOrRegisterData
import com.mathieu_elyes.housewad.DataModel.TokenResponseData
import com.mathieu_elyes.housewad.DataModel.UserData

class UserService(context: Context): BaseService(context) {
    public fun login(dataToLogin: LoginOrRegisterData, onSuccess: (Int, TokenResponseData?) -> Unit){
        Api().post<LoginOrRegisterData, TokenResponseData>("https://polyhome.lesmoulinsdudev.com/api/users/auth", dataToLogin, onSuccess)
    }

    public fun logout() {
        clearToken()
    }

    public fun register(dataToRegister: LoginOrRegisterData, onSuccess: (Int) -> Unit){
        Api().post<LoginOrRegisterData>("https://polyhome.lesmoulinsdudev.com/api/users/register", dataToRegister, onSuccess)
    }

    public fun loadGuest(onSuccess: (Int, ArrayList<GuestData>?) -> Unit){
        Api().get<ArrayList<GuestData>>("https://polyhome.lesmoulinsdudev.com/api/houses/${getHouseId()}/users", onSuccess, getToken())
    }

    public fun addGuest(userLogin: String, onSuccess: (Int) -> Unit){
        val user = UserData(userLogin)
        Api().post<UserData>("https://polyhome.lesmoulinsdudev.com/api/houses/${getHouseId()}/users",user, onSuccess, getToken())
    }

    public fun deleteGuest(userLogin: String, onSuccess: (Int) -> Unit){
        val user = UserData(userLogin)
        Api().delete<UserData>("https://polyhome.lesmoulinsdudev.com/api/houses/${getHouseId()}/users",user, onSuccess, getToken())
    }
}