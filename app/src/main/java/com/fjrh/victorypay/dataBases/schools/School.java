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
    DbHelper dbHelper;
    SQLiteDatabase db;

    public School(Context context){
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertSchool(String schoolName) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("school", schoolName);

        return db.replace("schools", null, values);
    }

    public ArrayList<String> getList(){

        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM schools", null);

        if(cursor.moveToFirst()){
            do {
                list.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        return  list;
    }

}
