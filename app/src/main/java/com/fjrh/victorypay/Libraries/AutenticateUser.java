package com.fjrh.victorypay.Libraries;

import android.content.Context;
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
import com.fjrh.victorypay.MainActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AutenticateUser {
    Context context;
    String URL = "http://192.168.1.105:4000/login";


    public AutenticateUser(Context context) {
        this.context = context;
    }


    public void getValidUser(String user, String password) {

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

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String message = "Ocurrió un error";
                    String code = "0";

                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        message = "El servidor no responde";
                        code = "1";

                    } else if (error instanceof AuthFailureError) {
                        message = "El servidor no puede autenticar al cliente";
                        code = "2";

                    } else if (error instanceof ServerError) {

                        if (error.networkResponse.statusCode == 412) {
                            message = "Datos incompletos";
                            code = "3";
                        }else if (error.networkResponse.statusCode == 404) {
                            message = "El usuario no está registrado";
                            code = "4";
                        }

                    } else if (error instanceof NetworkError) {
                        message = "Error con la red de datos";
                        code = "5";
                    } else if (error instanceof ParseError) {
                        message = "Error al intentar convertir los datos";
                        code = "6";
                    }

                    userData.put("ERROR", message);
                    userData.put("ERROR_CODE", code);

                    error.printStackTrace();
                }
            });

            Volley.newRequestQueue(context).add(request);

        } catch (JSONException ex) {
          ex.printStackTrace();
        }


    }
}
