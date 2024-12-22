package com.mathieu_elyes.housewad.service

import android.content.Context
import com.mathieu_elyes.housewad.datamodel.ModeData

class ModeService(context: Context): BaseService(context) {
    fun loadMode(): ModeData?{
        return getMode()
    }

    fun saveMode(modeData: ModeData){
        writeMode(modeData)
    }

//    fun deleteMode(modeName: String){
//        removeMode(modeName)
//    }
}