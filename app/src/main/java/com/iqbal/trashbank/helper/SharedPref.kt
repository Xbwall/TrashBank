package com.iqbal.trashbank.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    private val PREF_NAME = "SharePreference"
    private val sp:SharedPreferences
    val editor:SharedPreferences.Editor


    init {
        sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sp.edit()
    }

    fun setStatusLogin(status:Boolean){
        sp.edit().putBoolean(PREF_NAME,status).apply()
    }

    //STRING
    fun putString(key: String, value: String){
        editor.putString(key, value).apply()
    }

    fun getString(key: String ): String?{
        return sp.getString(key, null)
    }

}