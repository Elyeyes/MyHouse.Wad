package com.mathieu_elyes.housewad.service

import android.content.Context
import com.mathieu_elyes.housewad.datamodel.HouseData

class HouseService(context: Context): BaseService(context) {
    fun loadHouses(onSuccess: (Int, ArrayList<HouseData>?) -> Unit){
        Api().get<ArrayList<HouseData>>("https://polyhome.lesmoulinsdudev.com/api/houses", onSuccess, getToken())
    }

    fun quitHouse() {
        clearHouseId()
    }

    fun getHouse(): String? {
        return getHouseId()
    }
}