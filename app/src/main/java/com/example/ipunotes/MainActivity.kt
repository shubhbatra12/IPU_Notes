package com.example.ipunotes

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.ipunotes.fragments.MainFragment
import com.example.ipunotes.fragments.SubjectFragment
import com.example.ipunotes.models.Subject
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import kotlinx.android.synthetic.main.drawer_header.view.*
import kotlinx.android.synthetic.main.drawer_header.view.userNameDrawer

class MainActivity : AppCompatActivity() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }
    private val viewModel by lazy {
        ViewModelProvider(Extras.myApp).get(AppViewModel::class.java)
    }
    private val subjectFragment by lazy {
        when {
            viewModel.getMySubjectsList().isNotEmpty() -> {
                SubjectFragment(viewModel.getMySubjectsList()[0])
            }
            viewModel.getAllSubjectsList().isNotEmpty() -> {
                SubjectFragment(viewModel.getAllSubjectsList()[0])
            }
            else -> {
                SubjectFragment(Subject("", ""))
            }
        }
    }

    private val mainFragment = MainFragment()
    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private val sharedPreferencesEditor by lazy {
        sharedPreferences.edit()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        Extras.changeTheme(this)
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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

    if (auth.currentUser?.phoneNumber.isNullOrEmpty()) {
        navView.getHeaderView(0).userNameDrawer.text = auth.currentUser?.displayName
        navView.getHeaderView(0).userIdDrawer.text = auth.currentUser?.email
        Picasso.get().load(auth.currentUser?.photoUrl).into(navView.getHeaderView(0).imgViewDrawer)

    } else {
        navView.getHeaderView(0).userNameDrawer.text = auth.currentUser?.phoneNumber
        navView.getHeaderView(0).userIdDrawer.visibility = View.INVISIBLE
    }



        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.settingsOption -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    return@setNavigationItemSelectedListener true
                }
                R.id.logoutOption -> {
                    auth.signOut()
                    finish()
                    return@setNavigationItemSelectedListener true
                }
                R.id.profileOption -> {
                    startActivity(Intent(this,ProfileActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                    return@setNavigationItemSelectedListener true
                }
                else -> {
                    return@setNavigationItemSelectedListener false
                }
            }
        }


    }

    private fun initiateLandscapeSetup() {
        supportFragmentManager.beginTransaction().replace(R.id.container1, mainFragment)
            .replace(R.id.container2, subjectFragment).commit()
    }

    private fun initiatePortraitSetup() {
        supportFragmentManager.beginTransaction().replace(R.id.container, mainFragment).commit()
    }


    override fun onResume() {
        super.onResume()
        if (sharedPreferences.getBoolean(THEME_CHANGED, false)) {
            sharedPreferencesEditor.putBoolean(THEME_CHANGED, false).commit()
            recreate()
        } else if (sharedPreferences.getBoolean(LANGUAGE_CHANGED, false)) {
            sharedPreferencesEditor.putBoolean(LANGUAGE_CHANGED, false).commit()
            recreate()
        }
    }
}
