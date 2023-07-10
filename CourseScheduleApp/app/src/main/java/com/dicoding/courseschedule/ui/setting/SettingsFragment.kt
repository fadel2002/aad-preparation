package com.dicoding.courseschedule.ui.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.*
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var themeSharedPreferences: SharedPreferences
    private lateinit var notificationSharedPreference: SharedPreferences
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        themeSharedPreferences = requireContext().getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE)
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            val selectedMode = NightMode.valueOf(newValue.toString().uppercase(Locale.US))
            themeSharedPreferences.edit().apply {
                this.putInt(DARK_MODE, selectedMode.value)
                this.apply()
            }
            updateTheme(selectedMode.value)
            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        notificationSharedPreference = requireContext().getSharedPreferences(NOTIFICATION_PREFERENCE, Context.MODE_PRIVATE)
        val notificationPreference = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        notificationPreference?.setOnPreferenceChangeListener { preference, newValue ->
            val alarmReceiver = DailyReminder()
            if (newValue as Boolean) {
                alarmReceiver.setDailyReminder(requireContext())
                notificationSharedPreference.edit().apply{
                    this.putBoolean(NOTIFICATION_STATUS, newValue)
                    this.apply()
                }
            } else {
                alarmReceiver.cancelAlarm(requireContext())
                notificationSharedPreference.edit().apply{
                    this.clear()
                    this.apply()
                }
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}