package com.fjrh.victorypay.dataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CleanDatabases extends DbHelper{

    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public CleanDatabases(Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    public void cleanDB(){
        String[] tables = {"abonos", "addresses", "contact_infos", "inscription_payments", "medical_infos", "parents", "students", "tutors" };

        for(String table : tables){
            db.execSQL("DELETE FROM " + table);
            db.execSQL("VACUUM");
            db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='" + table + "'");
        }

    }
}
