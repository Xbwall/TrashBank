package com.iqbal.trashbank.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPref(activity: Activity) {
    val login = "login"
    val mypref = "MAIN_PREF"
    val sp: SharedPreferences

    val name = "name"
    val id_user = "0"
    val id_role = "0"
    val nohp = "nohp"
    val role_name = "role_name"

    init {
        sp = activity.getSharedPreferences(mypref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status:Boolean){
        sp.edit().putBoolean(login,status).apply()
    }

    fun getStatus():Boolean{
        return sp.getBoolean(login, false)
    }

    fun setString(key: String, vlue: String){
        return sp.edit().putString(key,vlue).apply()
    }

    fun getString(key: String):String{
        return sp.getString(key,"")!!
    }
}