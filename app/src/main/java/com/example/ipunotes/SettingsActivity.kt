package com.example.ipunotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.ipunotes.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), PreferenceManager.OnPreferenceTreeClickListener {

    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        Extras.changeTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction().replace(R.id.settingsContainer, settingsFragment).commit()
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        Log.d("Settings Activity", "preference clicked: ${preference?.key}")
        return false
    }
}
