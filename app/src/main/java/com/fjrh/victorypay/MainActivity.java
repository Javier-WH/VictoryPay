package com.fjrh.victorypay;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
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
import com.fjrh.victorypay.Config.ConfigFetchingAddress;
import com.fjrh.victorypay.Libraries.AutoCompleteOption;
import com.fjrh.victorypay.Libraries.CodeGenerator;
import com.fjrh.victorypay.Libraries.FetchManager;
import com.fjrh.victorypay.Libraries.Venezuela;
import com.fjrh.victorypay.dataBases.DbHelper;
import com.fjrh.victorypay.dataBases.params.Params;
import com.fjrh.victorypay.dataBases.users.Users;
import com.fjrh.victorypay.dataBases.prices.Prices;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button btnAcceptar;
    private EditText user;
    private EditText password;
    private Context context;
    private Users localUser;
    private Switch rememberMe;
    private Params params;
    private ProgressBar progressBar;
    private String URL = "http://192.168.1.105:4000/login";
    private static Venezuela venezuela;
    private FetchManager fetchManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        btnAcceptar = findViewById(R.id.btnAcept);
        user = findViewById(R.id.txtUser);
        password = findViewById(R.id.txtPass);
        btnAcceptar.setOnClickListener(new btnAcceptEvent());
        rememberMe = findViewById(R.id.remeberMe);
        progressBar = findViewById(R.id.progressBarrLogin);
        localUser = new Users(context);
        params = new Params(context);
        fetchManager = new FetchManager(context);
        fetchManager.checkFetching();

        URL = fetchManager.getFetchinAddress() + "/login";

        checkPrices();
        fillInputs();

        params.insertParam("needLoad", "true");
        params.insertParam("loadAtStart", "true");


        //***////
        HashMap<String, String> userData = new HashMap<>();
        userData.put("user", "Xaver");
        userData.put("password", "123");
        userData.put("name", "Francisco Javier Rodríguez Hernández");
        userData.put("ci", "16193765");

            try {
                localUser.insertUsers(userData);
                params.insertParam("remember", "true");
            } catch (Exception e) {
                e.printStackTrace();
            }


        ///****//

    }


    
    
    public static Venezuela getVenezuela() {
        return venezuela;
    }


    class btnAcceptEvent implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (user.getText().toString().equals("CONFIG")) {
                goToFetchingConfig();
            } else {
                loading(true);
                validateUser(user.getText().toString().trim(), password.getText().toString().trim());
            }

        }
    }

    public void goToFetchingConfig() {
        Intent i = new Intent(context, ConfigFetchingAddress.class);
        startActivity(i);
    }


    public void fillInputs() {
        HashMap<String, String> localUserData = localUser.getUsers();
        HashMap<String, String> params = this.params.getParams();

        if (localUserData.containsKey("user")) {
            user.setText(localUserData.get("user"));
        }
        if (localUserData.containsKey("password")) {
            password.setText(localUserData.get("password"));
        }
        ////
        if (params.containsKey("remember")) {
            rememberMe.setChecked(Boolean.parseBoolean(params.get("remember")));
        }



    }

    private void loading(boolean isLoading) {
        btnAcceptar.setText(isLoading ? "Espere..." : "INGRESAR");
        btnAcceptar.setEnabled(!isLoading);
        user.setEnabled(!isLoading);
        password.setEnabled(!isLoading);
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);

    }


    private void checkPrices() {
        //agrega los precios si no existen ***offline****
        Prices prices = new Prices(context);

        HashMap<String, String> pricesList = prices.getPrices();

        int pricesCount = pricesList.size();

        if (pricesCount <= 0) {
            prices.insertItem("Inscripción", "100");
            prices.insertItem("Mensualidad", "50");
            prices.insertItem("Dolar", "24");
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


                        if (rememberMe.isChecked()) {
                            localUser.insertUsers(userData);
                            params.insertParam("remember", "true");
                        } else {
                            localUser.deleteUsers(userData);
                            params.insertParam("remember", "false");
                        }

                        Intent i = new Intent(context, App.class);
                        params.insertParam("mode", "online");
                        venezuela = new Venezuela(context);
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
                        if (localUserData.size() > 0) {
                            if (!rememberMe.isChecked()) {
                                localUser.deleteUsers(localUserData);

                                params.insertParam("remember", "false");
                            }
                            params.insertParam("mode", "offline");
                            message = "No hay respuesta del servidor, iniciando en modo offline";
                            Intent i = new Intent(context, App.class);
                            startActivity(i);
                        } else {
                            message = "El servidor no responde, y el usuario no está validado o los datos no son correctos";
                        }

                    } else if (error instanceof AuthFailureError) {
                        message = "El servidor no puede autenticar al cliente";
                    } else if (error instanceof ServerError) {

                        if (error.networkResponse.statusCode == 412) {
                            message = "Datos incompletos";
                        } else if (error.networkResponse.statusCode == 404) {
                            message = "El usuario no está registrado";
                        }

                    } else if (error instanceof NetworkError) {
                        message = "Error con la red de datos";
                    } else if (error instanceof ParseError) {
                        message = "Error al intentar convertir los datos";
                    }

                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    venezuela = new Venezuela(context);//carga el json con todos los estaods, provincias, parroquias y ciudades de venezuela
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

