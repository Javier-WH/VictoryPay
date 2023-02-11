package com.fjrh.victorypay.dataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class InsertUsers extends DbHelper {

    Context context;

    public InsertUsers(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertUsers(HashMap<String, String> user) throws Exception {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user", user.get("user"));
        values.put("password", user.get("password"));
        return db.insert("users", null, values);
    }



}
