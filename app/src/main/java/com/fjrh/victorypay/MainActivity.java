package com.fjrh.victorypay;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.fjrh.victorypay.dataBases.schools.School;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button btnAcceptar;
    private EditText user;
    private EditText password;
    private Context currentContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentContext = this;
        btnAcceptar = findViewById(R.id.btnAcept);
        user = findViewById(R.id.txtUser);
        password = findViewById(R.id.txtPass);
        btnAcceptar.setOnClickListener(new btnAcceptEvent());
        checkUsers();

        /*School school = new School(this);
        school.insertSchool("Unidad Educativa Colegio Batalla de la Victoria");
        school.insertSchool("Unidad Educativa Colegio Padre Juan de Barnuevo");
        school.insertSchool("Unidad Educativa Colegio Madre Candelaria");
        school.insertSchool("Unidad Educativa Colegio José Ramón Camejo");
        school.insertSchool("Unidad Educativa Liceo Nuestra Señora de Altagracia");
        school.insertSchool("Unidad Educativa Liceo Ramón Buenahora");
        school.insertSchool("Unidad Educativa Liceo José Francisco Torrealba");
*/


    }


    class btnAcceptEvent implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            HashMap<String, String> userData = getUser(user.getText().toString(), password.getText().toString());

            if(userData.size() > 0){
                Intent i = new Intent(currentContext, App.class);
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
           Toast.makeText(currentContext, "Usuario o contraseña incorrecto", Toast.LENGTH_LONG).show();

        }else{
               data.put("userName",userInput);
               data.put("id", user.get("id"));
        }
        return data;
    }

    private void checkUsers(){
        //agrega un usuario si no existe ***offline****

        int userCount = new GetUser(currentContext).getUsers().size();
        if(userCount == 0){
            try {
                new InsertUsers(this).insertUsers(new HashMap<String, String>(){{put("user", "admin"); put("password", "admin");}});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

