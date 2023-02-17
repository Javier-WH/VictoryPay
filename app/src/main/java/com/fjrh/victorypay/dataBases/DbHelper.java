package com.fjrh.victorypay.dataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.fjrh.victorypay.dataBases.strings.CreatorString;


public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "batalla.db";




    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //crea la base de datos users
        db.execSQL(CreatorString.getCreateUserString());

        //crea la base de datos students
        db.execSQL(CreatorString.getCreateStudensString());

        //crea la base de datos parents
        db.execSQL(CreatorString.getCreateParentsString());

        //crea la base de datos tutors
        db.execSQL(CreatorString.getCreateTutorsString());

        //crea la base de datos contact_info
        db.execSQL(CreatorString.getCreateContactInfoString());

        //crea la base de datos schools
        db.execSQL(CreatorString.getCreateSchoolsString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS students");
        db.execSQL("DROP TABLE IF EXISTS parents");
        db.execSQL("DROP TABLE IF EXISTS tutors");
        db.execSQL("DROP TABLE IF EXISTS contact_info");
        db.execSQL("DROP TABLE IF EXISTS schools");
        onCreate(db);

    }


}
