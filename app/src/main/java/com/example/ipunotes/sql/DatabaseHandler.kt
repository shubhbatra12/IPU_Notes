package com.example.ipunotes.sql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ipunotes.COL_SUBJECT_ID
import com.example.ipunotes.COL_SUBJECT_NAME
import com.example.ipunotes.DB_NAME
import com.example.ipunotes.TABLE_NAME
import com.example.ipunotes.models.Subject

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DB_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = """create table $TABLE_NAME (
                    $COL_SUBJECT_ID text primary key, 
                    $COL_SUBJECT_NAME text)"""
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun getMySubjects(): ArrayList<Subject>{
        val db = readableDatabase
        val list = ArrayList<Subject>()
        val query = "select * from $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        val nameColIndex = cursor.getColumnIndex(COL_SUBJECT_NAME)
        val idColIndex = cursor.getColumnIndex(COL_SUBJECT_ID)
        while (cursor.moveToNext()){
            val id = cursor.getString(idColIndex)
            val name = cursor.getString(nameColIndex)
            list.add(Subject(id,name))
        }
        cursor.close()
        return list
    }

    fun addNewSubject(subject: Subject){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_SUBJECT_ID, subject.id)
            put(COL_SUBJECT_NAME, subject.name)
        }
        db.insert(TABLE_NAME, null, values)
    }
    
    fun removeSubject(subject: Subject){
        val db = writableDatabase
        val query = "delete from $TABLE_NAME where $COL_SUBJECT_ID = '${subject.id}'"
        db.rawQuery(query, null).close()
    }
}