package com.fjrh.victorypay.dataBases.register;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;
import com.fjrh.victorypay.dataBases.DbHelper;


public class CleanRegisterTable extends DbHelper {

    Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public CleanRegisterTable(@Nullable Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void clean(){
        db.execSQL("DELETE FROM registers");
    }
}
