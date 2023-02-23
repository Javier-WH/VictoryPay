package com.fjrh.victorypay.onlineDataBases.students;

import android.content.Context;
import android.content.Intent;
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
import com.fjrh.victorypay.App;
import com.fjrh.victorypay.dataBases.params.Params;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class OnlineInsertStudent {

    public void insetStudent(Context context, HashMap<String, String> data) {
        String URL = "http://192.168.1.105:4000/addStudent";

        // Make new json object and put params in it
        JSONObject jsonParams = new JSONObject(data);


        // Building a request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonParams, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ///respuesta
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "Ocurrió un error";

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    new Params(context).insertPatam("mode", "offline");
                    message = "Se perdió la conexión con el servidor";
                    App.fillElements();

                } else if (error instanceof AuthFailureError) {
                    message = "El servidor no puede autenticar al cliente";
                } else if (error instanceof ServerError) {
                //aqui se manejan los errores con los codigos
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

                //error.printStackTrace();
            }
        });

        Volley.newRequestQueue(context).add(request);

    }

}
