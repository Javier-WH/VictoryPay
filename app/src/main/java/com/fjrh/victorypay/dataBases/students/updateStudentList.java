package com.fjrh.victorypay.dataBases.students;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.fjrh.victorypay.dataBases.DbHelper;
import com.fjrh.victorypay.dataBases.abono.Abono;
import com.fjrh.victorypay.dataBases.params.Params;

public class updateStudentList extends DbHelper {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public updateStudentList(Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }





}
