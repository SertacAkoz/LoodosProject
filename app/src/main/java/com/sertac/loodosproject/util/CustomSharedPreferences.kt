package com.sertac.loodosproject.util

import android.content.Context
import android.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CustomSharedPreferences  @Inject constructor(
    @ApplicationContext context : Context
){
    private val EMAIL_KEY = "EMAIL"
//    private val PASSWORD_KEY = "PASSWORD"

    val prefs = PreferenceManager.getDefaultSharedPreferences(context)



    fun getEmail(): String {
        return prefs.getString(EMAIL_KEY, "")!!
    }
    fun saveEmail(email: String) {
        prefs.edit().putString(EMAIL_KEY, email).apply()
    }

    fun clear(){
        prefs.edit().clear()
    }
}