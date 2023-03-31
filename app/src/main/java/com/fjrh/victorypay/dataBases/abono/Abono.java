package com.fjrh.victorypay.dataBases.abono;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public double getAbono(String tutor_code){
        if(tutor_code.equals("-1")){
            return 0;
        }

        double abono = 0;

        String query = "SELECT * FROM abonos WHERE tutor_code = ?";

        Cursor cursor = db.rawQuery(query, new String[]{tutor_code});


        if(cursor.getCount() > 0 && cursor.moveToFirst()){

            abono = Double.parseDouble(cursor.getString(2));
        }


        return abono;

    }



    public long insertAbono(String tutorId, double abono, String abonoDate){

        ContentValues values = new ContentValues();
        values.put("tutor_id", tutorId);
        values.put("abono", abono);

        if(abonoDate != null){
            values.put("abonoDate", abonoDate);
        }

        return db.replace("abono", null, values);
    }

}
