package com.example.ipunotes

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.ipunotes.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val settingsFragment = SettingsFragment()
    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private val sharedPreferencesEditor by lazy {
        sharedPreferences.edit()
    }
    private val sharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            Log.e("Settings", "preference changed")
            when (key) {
                THEME_KEY -> {
                    sharedPreferencesEditor.putBoolean(THEME_CHANGED, true).commit()
                    recreate()
                }
                LANGUAGE_KEY -> {
                    sharedPreferencesEditor.putBoolean(LANGUAGE_CHANGED, true).commit()
                    recreate()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        Extras.changeTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction().replace(R.id.settingsContainer, settingsFragment)
            .commit()

        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }

}
