package com.example.ipunotes.fragments

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.ipunotes.*

class SettingsFragment : PreferenceFragmentCompat() {

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }
    private val sharedPreferencesEditor by lazy {
        sharedPreferences.edit()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        loadDefaultPreferences()

        sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                THEME_KEY -> {
                    sharedPreferencesEditor.putBoolean(THEME_CHANGED, true).commit()
                    activity?.recreate()
                }
                LANGUAGE_KEY -> {
                    sharedPreferencesEditor.putBoolean(LANGUAGE_CHANGED, true).commit()
                    activity?.recreate()
                }
            }
        }
    }

    private fun loadDefaultPreferences() {
        findPreference<ListPreference>(THEME_KEY)?.setValueIndex(
            sharedPreferences.getString(
                THEME_KEY,
                "0"
            )!!.toInt()
        )
        findPreference<ListPreference>(LANGUAGE_KEY)?.setValueIndex(
            sharedPreferences.getString(
                LANGUAGE_KEY,
                "0"
            )!!.toInt()
        )

    }

}