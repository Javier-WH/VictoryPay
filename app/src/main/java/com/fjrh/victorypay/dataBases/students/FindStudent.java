package com.fjrh.victorypay.dataBases.students;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.fjrh.victorypay.dataBases.DbHelper;

public class FindStudent extends DbHelper {
    Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;


    public FindStudent(@Nullable Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean isCiRegistered(String ci){

        String query = "SELECT * FROM students WHERE ci = ?";

        Cursor cursor = db.rawQuery(query, new String[]{ci});

        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }

    public boolean isCodeRegistered(String code){

        String query = "SELECT * FROM students WHERE code = ?";

        Cursor cursor = db.rawQuery(query, new String[]{code});

        if(cursor.moveToFirst()){
            return true;
        }

        return false;
    }

    public long findStudentParents(String motherCi, String fatherCi){

        String query = "SELECT * FROM parents WHERE mother_ci = ? AND father_ci = ?";

        Cursor cursor = db.rawQuery(query, new String[]{motherCi, fatherCi});

        if(cursor.moveToFirst()){
            long id = cursor.getInt(0);
            return id;
        }

        return -1;
    }

    public long findStudentTutor(String tutorCi){

        String query = "SELECT * FROM tutors WHERE tutor_ci = ?";

        Cursor cursor = db.rawQuery(query, new String[]{tutorCi});

        if(cursor.moveToFirst()){
            long id = cursor.getInt(0);
            return id;
        }

        return -1;
    }
}
