package com.fjrh.victorypay.dataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.fjrh.victorypay.dataBases.strings.CreatorString;


public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE users");
        db.execSQL("DROP TABLE students");
        onCreate(db);

    }


}
