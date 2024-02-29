package com.cm.leadapp.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MySharedPref @Inject constructor(@ApplicationContext val context: Context) {

    companion object {
        const val SALE_ID = "sale_id"
        const val USER_NAME = "user_name"
        const val LAST_SYNC_TIME = "last_sync_time"
    }

    var pref: SharedPreferences

    init {
        pref = EncryptedSharedPreferences.create(
            context,
            "LeadApp",
            getMasterKey(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private val editor = pref.edit()

    private fun getMasterKey(): MasterKey {
        return MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    var saleId: String
        get() = getString(SALE_ID)
        set(value) {
            saveString(SALE_ID, value)
        }

    var userName: String
        get() = getString(USER_NAME)
        set(value) {
            saveString(USER_NAME, value)
        }

    var lastSyncTime: Long
        get() = getLong(LAST_SYNC_TIME)
        set(value) {
            saveLong(LAST_SYNC_TIME, value)
        }

    private fun saveString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    private fun getString(key: String, defaultValue: String = ""): String {
        return pref.getString(key, defaultValue) ?: defaultValue
    }

    private fun saveLong(key: String, value: Long) {
        editor.putLong(key, value).apply()
    }

    private fun getLong(key: String, defaultValue: Long = 0L): Long {
        return pref.getLong(key, defaultValue)
    }
}