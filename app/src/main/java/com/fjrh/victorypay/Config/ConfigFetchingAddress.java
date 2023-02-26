package com.fjrh.victorypay.Config;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fjrh.victorypay.App;
import com.fjrh.victorypay.Libraries.FetchManager;
import com.fjrh.victorypay.Libraries.Venezuela;
import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.params.Params;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ConfigFetchingAddress extends AppCompatActivity {

    private EditText protocol;
    private EditText ip;
    private EditText port;
    private Button accept;
    private Button test;
    private FetchManager fetchManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_fetching_address);
        context = this;
        initComponents();
        fillComponents();
        initEvents();
    }

    private void initComponents() {
        protocol = findViewById(R.id.protocol_FM);
        ip = findViewById(R.id.IP_FM);
        port = findViewById(R.id.port_FM);
        accept = findViewById(R.id.accept_FM);
        test = findViewById(R.id.test_FM);
        fetchManager = new FetchManager(context);
    }

    private void fillComponents() {
        protocol.setText(fetchManager.getServerProtocol());
        ip.setText(fetchManager.getServerAddress());
        port.setText(fetchManager.getServerPort());
    }

    private void initEvents() {

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // "http://192.168.1.105:4000";
                String _protocol = protocol.getText().toString();
                String _ip = ip.getText().toString();
                String _port = port.getText().toString();
                String address = _protocol+"://"+_ip+":"+_port+"/test";

                testAddress(address);

                //fetchManager.setServerProtocol(protocol.getText().toString());
                //fetchManager.setServerPort(port.getText().toString());
                //fetchManager.setServerAddress(ip.getText().toString());
            }
        });

    }


    private void testAddress(String URL) {

        Request request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(context).add(request);
    }




}