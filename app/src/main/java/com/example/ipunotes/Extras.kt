package com.example.ipunotes

import android.content.Context
import androidx.preference.PreferenceManager

const val THEME_KEY = "theme"
const val LANGUAGE_KEY = "language"
const val LIGHT_THEME = 0
const val DARK_THEME = 1
const val BLACK_THEME = 2
const val THEME_CHANGED = "theme changed"
const val LANGUAGE_CHANGED = "language changed"
const val ALL_SUBJECTS_KEY = "AllSubjects"
const val NOTES_KEY = "Notes"
const val PRACTICAL_FILES_KEY = "PracticalFiles"
const val EXAMS_KEY = "Exams"
const val VIDEOS_KEY = "Videos"
const val TABLE_NAME = "MySubjects"
const val COL_SUBJECT_ID = "Id"
const val COL_SUBJECT_NAME = "Name"

object Extras {
    fun changeTheme(context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        when (sharedPreferences.getString(THEME_KEY, LIGHT_THEME.toString())?.toInt()) {
            LIGHT_THEME -> context.setTheme(R.style.LightTheme)
            DARK_THEME -> context.setTheme(R.style.DarkTheme)
            BLACK_THEME -> context.setTheme(R.style.BlackTheme)
        }
    }

    fun getLabel(string: String): String {
        var label = string.substring(0, 1)
        val words = string.split(" ")
        if (words.size > 1) {
            label += words[1].substring(0, 1)
        }
        return label
    }

}