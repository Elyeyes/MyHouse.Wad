package com.mathieu_elyes.housewad

import android.content.Context
import android.content.SharedPreferences

object BackgroundPreference {
    private const val prefs = "background_prefs"
    private const val key = "background"

    fun setBackground(context: Context, background: Int) {
        val prefs: SharedPreferences = context.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt(key, background)
        editor.apply()
    }

    fun getBackground(context: Context): Int {
        val prefs: SharedPreferences = context.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        System.out.println("currentBackground" + prefs)
        return prefs.getInt(key, R.drawable.after_noon)
    }
}