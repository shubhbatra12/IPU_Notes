package com.example.ipunotes

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.ipunotes.fragments.MainFragment
import com.example.ipunotes.fragments.SubjectFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainFragment = MainFragment()
    private val subjectFragment = SubjectFragment()
    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private val sharedPreferencesEditor by lazy {
        sharedPreferences.edit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Extras.changeTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val drawerToggle =
            ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            initiatePortraitSetup()
        } else {
            initiateLandscapeSetup()
        }
    }

    private fun initiateLandscapeSetup() {
        supportFragmentManager.beginTransaction().replace(R.id.container1, mainFragment)
            .replace(R.id.container2, subjectFragment).commit()
    }

    private fun initiatePortraitSetup() {
        supportFragmentManager.beginTransaction().replace(R.id.container, mainFragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_manu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingsOption -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.testingOption -> {
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    supportFragmentManager.beginTransaction().replace(R.id.container, mainFragment)
                        .commit()
                } else {
                    Toast.makeText(this,"This is for Accessing Main fragment In potrait",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.testingOptioN -> {
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    supportFragmentManager.beginTransaction().replace(R.id.container, subjectFragment)
                        .commit()
                } else {
                    Toast.makeText(this,"This is for Accessing Subject fragment In potrait",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (sharedPreferences.getBoolean(THEME_CHANGED, false)) {
            sharedPreferencesEditor.putBoolean(THEME_CHANGED, false).commit()
            recreate()
        }
        else if(sharedPreferences.getBoolean(LANGUAGE_CHANGED, false)){
            sharedPreferencesEditor.putBoolean(LANGUAGE_CHANGED, false).commit()
            recreate()
        }
    }
}
