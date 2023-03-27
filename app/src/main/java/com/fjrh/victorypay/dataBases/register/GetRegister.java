package com.fjrh.victorypay.dataBases.register;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;
import com.fjrh.victorypay.dataBases.DbHelper;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class GetRegister extends DbHelper {

    Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public GetRegister(@Nullable Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<HashMap<String, String>> getRegisterList(){

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT register_code, user, description, type, insertion_query, rollback_query, metadata FROM registers", null);

        if(cursor.moveToFirst()){

            do {
              HashMap<String, String> data = new HashMap<>();

              for (int i = 0 ; i < cursor.getColumnCount() ; i++){
                  data.put(cursor.getColumnName(i), cursor.getString(i));
              }

              list.add(data);
            }while (cursor.moveToNext());

        }
        return list;
    }



}
