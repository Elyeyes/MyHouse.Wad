package com.mathieu_elyes.housewad

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment

object BackgroundUtil {
    fun changeBackground(context: Context, view: View) {
        val currentBackground = BackgroundPreference.getBackground(context)
        val newBackground = if (currentBackground == R.drawable.after_noon) R.drawable.night else R.drawable.after_noon
        BackgroundPreference.setBackground(context, newBackground)

        // Update the background of the view
        view.rootView.setBackgroundResource(newBackground)

        // Update the background of the activity's window decor view if context is an activity
        if (context is Activity) {
            context.window.decorView.setBackgroundResource(newBackground)
        }
    }
}