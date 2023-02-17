package com.fjrh.victorypay.dataBases.schools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.fjrh.victorypay.dataBases.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class School extends DbHelper {
    Context context;

    public School(Context context){
        super(context);
        this.context = context;
    }

    public long insertSchool(String schoolName) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("school", schoolName);

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.replace("schools", null, values);
    }

    public ArrayList<String> getList(){

        ArrayList<String> list = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM schools", null);

        if(cursor.moveToFirst()){
            do {
                list.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        return  list;
    }

}
