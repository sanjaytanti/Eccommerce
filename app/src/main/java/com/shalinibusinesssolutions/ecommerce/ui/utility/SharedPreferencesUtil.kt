package com.shalinibusinesssolutions.ecommerce.ui.utility

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil {
    private var sp: SharedPreferences? = null

    fun read(context: Context): Boolean {
        val value: Boolean?
        sp = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        value = sp!!.getBoolean("login", false)
        return value
    }

    fun write(context: Context, login: Boolean) {
        sp = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val editor = sp!!.edit()
        editor.putBoolean("login", login)
        editor.apply()
    }

    fun clear(context: Context) {
        sp = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val editor = sp!!.edit()
        editor.putString("Id", "")
        editor.putString("UserName", "")
        editor.clear()
        editor.apply()

        write(context, false)
    }

    fun setUserId(id: String, context: Context) {
        sp = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val editor = sp!!.edit()
        editor.putString("Id", id)

        editor.apply()
    }

    fun getUserId(context: Context): String? {
        sp = context.getSharedPreferences("MyPref", 0)
        return sp!!.getString("Id", "")
    }

    fun setUserName(UserName: String, context: Context) {
        sp = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val editor = sp!!.edit()
        editor.putString("UserName", UserName)

        editor.apply()
    }

    fun getUserName(context: Context): String? {
        sp = context.getSharedPreferences("MyPref", 0)
        return sp!!.getString("UserName", "")
    }


}