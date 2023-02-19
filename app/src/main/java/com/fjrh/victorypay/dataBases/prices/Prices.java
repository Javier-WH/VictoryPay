package com.fjrh.victorypay.dataBases.prices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fjrh.victorypay.dataBases.DbHelper;

import java.util.HashMap;

public class Prices extends DbHelper {
    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;

    public Prices(Context context){
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public HashMap<String, String> getPrices() throws SQLException {

        HashMap<String, String> pricesList = new HashMap<String, String>();

        Cursor cursor = db.rawQuery("SELECT * FROM prices", null);

        if(cursor.moveToFirst()){
            do {
                pricesList.put(cursor.getString(1), cursor.getString(2));
            }while (cursor.moveToNext());
        }

        return pricesList;
    }

    public long insertItem(String item, String price) throws SQLException {

        ContentValues values = new ContentValues();
        values.put("item", item);
        values.put("price", price);

        return db.replace("prices", null, values);
    }


}
