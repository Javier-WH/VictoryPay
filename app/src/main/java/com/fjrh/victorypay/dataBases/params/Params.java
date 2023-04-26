package com.fjrh.victorypay.dataBases.params;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.fjrh.victorypay.dataBases.DbHelper;

import java.util.HashMap;

public class Params extends DbHelper {
    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;
    public Params(@Nullable Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public HashMap<String, String> getParams(){
        HashMap<String, String> params = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT * FROM params", null);
        if(cursor.moveToFirst()){
            do{
                params.put(cursor.getString(1), cursor.getString(2));

            }while (cursor.moveToNext());

        }
        return params;
    }

    public long insertParam(String paramName, String paramValue) throws SQLException {

        ContentValues values = new ContentValues();
        values.put("param", paramName);
        values.put("value", paramValue);
        return db.replace("params", null, values);
    }
    ///

    public void insertParamsList(ContentValues values){


        db.replace("params", null, values);
    }


}
