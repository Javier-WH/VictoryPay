package com.fjrh.victorypay.dataBases.abono;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.fjrh.victorypay.dataBases.DbHelper;


public class Abono extends DbHelper {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public Abono(Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    public double getAbono(String tutorId){
        double abono = 0;

        String query = "SELECT * FROM abono WHERE tutor_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{tutorId});

        if(cursor.moveToFirst()){
            abono = Double.parseDouble(cursor.getString(2));
        }
        return abono;

    }

    public long insertAbono(String tutorId, double abono){

        ContentValues values = new ContentValues();
        values.put("tutor_id", tutorId);
        values.put("abono", abono);

        return db.replace("abono", null, values);
    }

}
