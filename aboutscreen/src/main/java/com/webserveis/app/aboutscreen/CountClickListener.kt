package com.webserveis.app.aboutscreen

import androidx.preference.Preference

class CountClickListener(private val callback: CountClickListener) : Preference.OnPreferenceClickListener {
    private var lastClicked: Long = -1L
    private var clickCount: Int = 0

    override fun onPreferenceClick(preference: Preference?): Boolean {

        val currentTime = System.currentTimeMillis()
        val diffTime =  currentTime - lastClicked

        if (diffTime <= 350L) {
            clickCount++
            if (clickCount > 3) {
                clickCount = 0
                callback.onCountClick()
            }

        } else {
            clickCount = 0
        }

        lastClicked = currentTime

        return true
    }

    interface CountClickListener {
        fun onCountClick()
    }

}