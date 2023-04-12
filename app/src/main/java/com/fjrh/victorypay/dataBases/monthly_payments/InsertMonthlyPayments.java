package com.fjrh.victorypay.dataBases.monthly_payments;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fjrh.victorypay.dataBases.DbHelper;
import com.fjrh.victorypay.dataBases.register.Register;
import com.fjrh.victorypay.dataBases.students.InsertStuden;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InsertMonthlyPayments extends DbHelper {

    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public InsertMonthlyPayments(Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    public boolean insertOffline(Register register) {

        try {
            JSONArray array = register.getMetadata().getJSONArray("data");
            JSONObject student0 = array.getJSONObject(0);
            String registerCode = student0.getString("register_code");


            ContentValues values = new ContentValues();
            values.put("register_code", registerCode);
            values.put("user", register.getUser());
            values.put("description", register.getDescription());
            values.put("type", register.getType());
            values.put("insertion_query", register.getInsertion_query());
            values.put("rollback_query", register.getRollback_query());
            values.put("metadata", register.getMetadata().get("data").toString());
            new InsertStuden(context).insertRegister(values);

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }
}
