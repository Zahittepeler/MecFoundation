package com.halasteknoloji.meckurbanyonetim.utils

import android.content.Context
import android.content.SharedPreferences

object LocalDataManager {

    private var sharedPreferences: SharedPreferences? = null
    private var sharedPreferencesEditor: SharedPreferences.Editor? = null


    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("MEC_Management", Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences?.edit()
    }

    fun setPreferences(prefName: String, prefValue: String?) {
        sharedPreferencesEditor?.putString(prefName, prefValue)
        sharedPreferencesEditor?.commit()

    }

    fun getPreferencesStrVal(prefName: String): String? {
        return sharedPreferences?.getString(prefName, null)
    }


}