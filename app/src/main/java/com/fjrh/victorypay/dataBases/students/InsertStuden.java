package com.fjrh.victorypay.dataBases.students;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import com.fjrh.victorypay.dataBases.DbHelper;
import com.fjrh.victorypay.dataBases.abono.Abono;
import com.fjrh.victorypay.dataBases.params.Params;
import com.fjrh.victorypay.dataBases.prices.Prices;
import com.fjrh.victorypay.dataBases.register.CreateRegister;
import com.fjrh.victorypay.dataBases.register.Register;


import java.util.HashMap;
import java.util.Map;

public class InsertStuden extends DbHelper {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private HashMap<String, String> params;




    public InsertStuden(Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        params = new Params(context).getParams();

    }



    public boolean insert(HashMap<String, String> data) {
       Register register = new CreateRegister(context, data).getRegister();
        Log.i("XXX", register.getInsertion_query());

        return false;

    }

}
