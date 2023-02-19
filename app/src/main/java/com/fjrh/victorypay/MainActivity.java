package com.fjrh.victorypay;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fjrh.victorypay.Libraries.Message;
import com.fjrh.victorypay.Libraries.Venezuela;
import com.fjrh.victorypay.dataBases.DbHelper;
import com.fjrh.victorypay.dataBases.GetUser;
import com.fjrh.victorypay.dataBases.InsertUsers;
import com.fjrh.victorypay.dataBases.prices.Prices;
import com.fjrh.victorypay.dataBases.schools.School;

import org.json.JSONException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button btnAcceptar;
    private EditText user;
    private EditText password;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        btnAcceptar = findViewById(R.id.btnAcept);
        user = findViewById(R.id.txtUser);
        password = findViewById(R.id.txtPass);
        btnAcceptar.setOnClickListener(new btnAcceptEvent());
        checkUsers();
        checkPrices();

/*
        School school = new School(this);
        school.insertSchool("Unidad Educativa Colegio Batalla de la Victoria");
        school.insertSchool("Unidad Educativa Colegio Padre Juan de Barnuevo");
        school.insertSchool("Unidad Educativa Colegio Madre Candelaria");
        school.insertSchool("Unidad Educativa Colegio José Ramón Camejo");
        school.insertSchool("Unidad Educativa Liceo Nuestra Señora de Altagracia");
        school.insertSchool("Unidad Educativa Liceo Ramón Buenahora");
        school.insertSchool("Unidad Educativa Liceo José Francisco Torrealba");
*/


        /*
        String date = "2023-02-19 12:21:30";
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM schools WHERE updatedAT < ?", new String[]{date});

        if(cursor.moveToFirst()){
            do {
                Log.e("registro", cursor.getString(1));
            }while (cursor.moveToNext());
        }
        */






    }


    class btnAcceptEvent implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            HashMap<String, String> userData = getUser(user.getText().toString(), password.getText().toString());

            if(userData.size() > 0){
                Intent i = new Intent(context, App.class);
                i.putExtra("userName", userData.get("userName"));
                i.putExtra("id", userData.get("id"));
                startActivity(i);
            }

        }
    }

    private HashMap<String, String> getUser(String userInput, String passwordInput){

        HashMap<String, String> user = null;
        HashMap<String, String> data = new HashMap<>();

        try {
            user = new GetUser(getBaseContext()).logUser(userInput, passwordInput);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!user.containsKey(userInput)){
           Toast.makeText(context, "Usuario o contraseña incorrecto", Toast.LENGTH_LONG).show();

        }else{
               data.put("userName",userInput);
               data.put("id", user.get("id"));
        }
        return data;
    }

    private void checkUsers(){
        //agrega un usuario si no existe ***offline****

        int userCount = new GetUser(context).getUsers().size();
        if(userCount == 0){
            try {
                new InsertUsers(this).insertUsers(new HashMap<String, String>(){{put("user", "admin"); put("password", "admin");}});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkPrices(){
        //agrega los precios si no existen ***offline****
        Prices prices = new Prices(context);

        HashMap<String, String > pricesList = prices.getPrices();

        int pricesCount = pricesList.size();

        if(pricesCount <=0){

            prices.insertItem("Inscripción", "100");
            prices.insertItem("Mensualidad", "50");

        }



    }

}

