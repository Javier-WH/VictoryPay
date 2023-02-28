package com.fjrh.victorypay.dataBases.users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.fjrh.victorypay.dataBases.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Users extends DbHelper {

    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public Users(@Nullable Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public HashMap<String, String> logUser(String userName, String password) {

        HashMap<String, String> user = new HashMap<>();

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE user = ? AND password = ? ", new String[]{userName, password});

        if (cursor.moveToFirst()) {
            user.put("user", cursor.getString(1));
            user.put("password", cursor.getString(2));
            user.put("id", cursor.getString(0));
        }

        cursor.close();
        return user;
    }

    public HashMap<String, String> getUsers() {

        HashMap<String, String> userData = new HashMap<>();

        Cursor cursor = db.rawQuery("SELECT * FROM users", null);

        if (cursor.moveToFirst()) {
            userData.put("user", cursor.getString(1));
            userData.put("password", cursor.getString(2));
            userData.put("name", cursor.getString(3));
            userData.put("ci", cursor.getString(4));
        }
        return userData;
    }


    public long insertUsers(HashMap<String, String> user) throws Exception {

        ContentValues values = new ContentValues();

        values.put("user", user.get("user"));
        values.put("password", user.get("password"));
        values.put("name", user.get("name"));
        values.put("ci", user.get("ci"));
        values.put("id", "1");

        return db.replace("users", null, values);
    }

    public void deleteUsers(HashMap<String, String> user) {
        db.delete("users", "user = ? and password = ?", new String[]{user.get("user"), user.get("password")});
    }




}

