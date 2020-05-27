package com.example.ipunotes

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.ipunotes.fragments.MainFragment
import com.example.ipunotes.fragments.SubjectFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainFragment = MainFragment()
    private val subjectFragment = SubjectFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        Extras.changeTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val drawerToggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            initiatePortraitSetup()
        }else{
            initiateLandscapeSetup()
        }
    }

    private fun initiateLandscapeSetup() {
        supportFragmentManager.beginTransaction().replace(R.id.container1, mainFragment).replace(R.id.container2,subjectFragment).commit()
    }

    private fun initiatePortraitSetup() {
        supportFragmentManager.beginTransaction().replace(R.id.container, mainFragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_manu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settingsOption->{
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
