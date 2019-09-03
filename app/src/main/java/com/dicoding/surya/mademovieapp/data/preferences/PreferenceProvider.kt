package com.dicoding.surya.mademovieapp.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by suryamudti on 27/08/2019.
 */
class PreferenceProvider(val context: Context) {

    private val KEY_DAILY_REMINDER = "DAILY_REMINDER"
    private val KEY_RELEASE_REMINDER = "RELEASE_REMINDER"

    private val appContext = context.applicationContext

    private val preferences : SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    // Daily Reminder
    fun setDailyReminder(daily : Boolean){
        preferences.edit().putBoolean(
            KEY_DAILY_REMINDER,
            daily
        ).apply()
    }

    fun isDailyReminderEnabled() : Boolean{
        return preferences.getBoolean(KEY_DAILY_REMINDER, true)
    }

    // Release Reminder
    fun setReleaseReminder(release : Boolean){
        preferences.edit().putBoolean(
            KEY_RELEASE_REMINDER,
            release
        ).apply()
    }

    fun isReleaseReminderEnabled() : Boolean{
        return preferences.getBoolean(KEY_RELEASE_REMINDER, true)
    }






}