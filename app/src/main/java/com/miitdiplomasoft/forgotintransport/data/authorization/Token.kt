package com.miitdiplomasoft.forgotintransport.data.authorization

import android.content.Context

object Token {
    fun saveAuthorizationToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }

    fun getAuthorizationToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", "")
    }
}
