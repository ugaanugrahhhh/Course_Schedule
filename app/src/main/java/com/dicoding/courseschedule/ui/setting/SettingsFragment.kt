package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder

class SettingsFragment : PreferenceFragmentCompat() {

    private val dailyReminder = DailyReminder()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        // TODO 10: Update theme based on value in ListPreference Done
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            newValue as? String
            when (newValue) {
                getString(R.string.pref_dark_on) -> {
                    updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                }

                getString(R.string.pref_dark_off) -> {
                    updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                }

                getString(R.string.pref_dark_auto) -> {
                    updateTheme(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }

                else -> {
                    // Handle other cases if needed
                }
            }
            true
        }

        // TODO 11: Schedule and cancel notification in DailyReminder based on SwitchPreferenceCompat Done
        val notifySwitchPreference =
            findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        notifySwitchPreference?.setOnPreferenceChangeListener { _, newValue ->
            newValue as Boolean

            if (newValue) {
                dailyReminder.setDailyReminder(requireContext())
            } else {
                dailyReminder.cancelAlarm(requireContext())
            }

            true
        }
    }

    private fun updateTheme(nightMode: Int) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
    }
}


