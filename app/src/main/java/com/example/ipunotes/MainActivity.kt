package com.example.ipunotes

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.ipunotes.fragments.MainFragment
import com.example.ipunotes.fragments.SubjectFragment
import com.example.ipunotes.models.Subject
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialogue_theme.view.*
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
                R.id.themeOption -> {
                    openThemeDialogue()
                    return@setNavigationItemSelectedListener true
                }
                R.id.logoutOption -> {
                    auth.signOut()
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                    return@setNavigationItemSelectedListener true
                }
                R.id.profileOption -> {
                    startActivity(Intent(this, UserProfileActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                    return@setNavigationItemSelectedListener true
                }
                else -> {
                    return@setNavigationItemSelectedListener false
                }
            }
        }


    }

    private fun openThemeDialogue() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialogue_theme, null, false)


        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setCancelable(true)
        val mAlertDialog = mBuilder.show()

//        if(sharedPreferences.getBoolean(DARK_THEME, true)){
//            mDialogView.radioButtonDark.isChecked = true
//        }else{
//            mDialogView.radioButtonLight.isChecked = true
//        }

        mDialogView.radioButtonDark.setOnClickListener {
            mAlertDialog.dismiss()
////            Toast.makeText(this,"Something",Toast.LENGTH_SHORT).show()
//            sharedPreferencesEditor.putBoolean(DARK_THEME, true).commit()
//            sharedPreferencesEditor.putBoolean(THEME_CHANGED, true).commit()
        }

        mDialogView.radioButtonLight.setOnClickListener {
            mAlertDialog.dismiss()
////            Toast.makeText(this,"Other thing",Toast.LENGTH_SHORT).show()
//            sharedPreferencesEditor.putBoolean(DARK_THEME, false).commit()
//            sharedPreferencesEditor.putBoolean(THEME_CHANGED, true).commit()
        }

        mDialogView.radioButtonDark.setOnClickListener {
            mAlertDialog.dismiss()
////            Toast.makeText(this,"Other thing",Toast.LENGTH_SHORT).show()
//            sharedPreferencesEditor.putBoolean(DARK_THEME, false).commit()
//            sharedPreferencesEditor.putBoolean(THEME_CHANGED, true).commit()
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
