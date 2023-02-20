package com.fjrh.victorypay;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fjrh.victorypay.dataBases.params.Params;
import com.fjrh.victorypay.dataBases.users.Users;
import com.fjrh.victorypay.dataBases.prices.Prices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button btnAcceptar;
    private EditText user;
    private EditText password;
    private Context context;
    private Users localUser;
    private String URL = "http://192.168.1.105:4000/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        btnAcceptar = findViewById(R.id.btnAcept);
        user = findViewById(R.id.txtUser);
        password = findViewById(R.id.txtPass);
        btnAcceptar.setOnClickListener(new btnAcceptEvent());
        localUser = new Users(context);
        checkPrices();
        fillInputs();

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
            loading(true);
            validateUser(user.getText().toString(), password.getText().toString());

        }
    }

    public void fillInputs(){
        HashMap<String, String> localUserData = localUser.getUsers();

        if(localUserData.containsKey("user")){
            user.setText(localUserData.get("user"));
        }
        if(localUserData.containsKey("password")){
            password.setText(localUserData.get("password"));
        }

    }

    private void loading(boolean isLoading){
            btnAcceptar.setText(isLoading ? "Espere..." : "INGRESAR");
            btnAcceptar.setEnabled(!isLoading);
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

    /////////////////////



    public void validateUser(String user, String password) {

        HashMap<String, String> userData = new HashMap<>();

        try {

            // Make new json object and put params in it
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("user", user);
            jsonParams.put("password", password);


            // Building a request
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonParams, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {

                        userData.put("user", response.getString("user"));
                        userData.put("password", response.getString("password"));
                        userData.put("name", response.getString("name"));
                        userData.put("ci", response.getString("ci"));


                        localUser.insertUsers(userData);


                        Intent i = new Intent(context, App.class);
                        new Params(context).insertPatam("mode", "online");
                        loading(false);
                        startActivity(i);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String message = "Ocurrió un error";

                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                        HashMap<String, String> localUserData = localUser.logUser(user, password);
                        if(localUserData.size() >0){
                            message = "No hay respuesta del servidor, iniciando en modo offline";
                            Intent i = new Intent(context, App.class);
                            new Params(context).insertPatam("mode", "offline");
                            loading(false);
                            startActivity(i);
                        }else{
                            message = "El servidor no responde, no hay ningun usuario validado";
                        }

                    } else if (error instanceof AuthFailureError) {
                        message = "El servidor no puede autenticar al cliente";
                    } else if (error instanceof ServerError) {

                        if (error.networkResponse.statusCode == 412) {
                            message = "Datos incompletos";
                        }else if (error.networkResponse.statusCode == 404) {
                            message = "El usuario no está registrado";
                        }

                    } else if (error instanceof NetworkError) {
                        message = "Error con la red de datos";
                    } else if (error instanceof ParseError) {
                        message = "Error al intentar convertir los datos";
                    }

                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    loading(false);
                    //error.printStackTrace();
                }
            });

            Volley.newRequestQueue(context).add(request);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

    }

}//

