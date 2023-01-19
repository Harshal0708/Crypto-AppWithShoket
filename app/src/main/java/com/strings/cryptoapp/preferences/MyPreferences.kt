package com.strings.cryptoapp.preferences

import android.content.Context
import com.strings.cryptoapp.Response.DataXX
import com.google.gson.Gson

class MyPreferences(context: Context) {
    val PREFERENCES_NAME = "SharedPreferencesCrypto"
    val LOGIN_USER_DETAIL = "LoginUserDetail"
    val REMEMBER_ME = "RememberMe"
    val TOKEN = "Token"

    val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun setLogin(data: DataXX?) {
        val editor = preferences.edit()
        editor.putString(LOGIN_USER_DETAIL, Gson().toJson(data))
        editor.apply()
        editor.commit()
    }

    fun getLogin(): String {
        val login = preferences.getString(LOGIN_USER_DETAIL, "")
        return login.toString()
    }

    fun setRemember(isChecked: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(REMEMBER_ME, isChecked)
        editor.apply()
        editor.commit()
    }

    fun getRemember(): Boolean {
        val remember = preferences.getBoolean(REMEMBER_ME, false)
        return remember
    }

    fun setToken(token: String) {
        val editor = preferences.edit()
        editor.putString(TOKEN, token)
        editor.apply()
        editor.commit()
    }

    fun getToken(): String {
        val remember = preferences.getString(TOKEN, "")
        return remember.toString()
    }


}