package com.fjrh.victorypay.dataBases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class GetUser extends DbHelper {

    Context context;

    public GetUser(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public HashMap<String, String> logUser(String userName, String password) throws Exception{

        HashMap<String, String> user = new HashMap<>();

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE user = ? AND password = ? ",  new String[]{userName, password} );

        if(cursor.moveToFirst()){
            user.put(cursor.getString(1), cursor.getString(2));
            user.put("id", cursor.getString(0));
        }

        cursor.close();
        return user;
    }


}

