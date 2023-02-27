package com.fjrh.victorypay.Config;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private Button search;
    private ImageView back;
    private ImageView config;
    private TextView message;
    private FetchManager fetchManager;
    private Context context;
    private ProgressBar progressBar;
    private ConstraintLayout configLayout;
    private ConstraintLayout confSearhLayout;
    private EditText redSearch;
    private EditText portSearch;
    private EditText minSearch;
    private EditText maxSearch;
    private Button btnSetSearch;
    private int C3C, min, max, confPort;


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
        search = findViewById(R.id.test_FM);
        progressBar = findViewById(R.id.fetcherProgressBar);
        fetchManager = new FetchManager(context);
        back = findViewById(R.id.backButton);
        config = findViewById(R.id.configSearch);
        message = findViewById(R.id.testMessage);
        configLayout = findViewById(R.id.configLayout);
        confSearhLayout = findViewById(R.id.confSearhLayout);
        redSearch = findViewById(R.id.redSearch);
        portSearch = findViewById(R.id.portSearch);
        minSearch = findViewById(R.id.minSearch);
        maxSearch = findViewById(R.id.maxSearch);
        btnSetSearch = findViewById(R.id.btnSetSearch);
        C3C = 1;
        min = 1;
        max = 255;
        confPort = 4000;
        setConfigValues();
    }

    private void setConfigValues(){
        redSearch.setText(String.valueOf(C3C));
        minSearch.setText(String.valueOf(min));
        maxSearch.setText(String.valueOf(max));
        portSearch.setText(String.valueOf(confPort));
    }

    private void fillComponents() {
        protocol.setText(fetchManager.getServerProtocol());
        ip.setText(fetchManager.getServerAddress());
        port.setText(fetchManager.getServerPort());
    }

    public void showHideConfig(){
        setConfigValues();
        if(configLayout.getVisibility() == View.VISIBLE){
            configLayout.setVisibility(View.INVISIBLE);
            confSearhLayout.setVisibility(View.VISIBLE);
        }else{
            configLayout.setVisibility(View.VISIBLE);
            confSearhLayout.setVisibility(View.INVISIBLE);
        }
    }


    //inicia los eventos
    private void initEvents() {
        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideConfig();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading(true);
                testAddress(protocol.getText().toString(), ip.getText().toString(), port.getText().toString());

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading(true);
                searchAddress("192", "168", String.valueOf(C3C), String.valueOf(min), String.valueOf(confPort));
            }
        });

        btnSetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkConfigSearhData()) {
                    C3C = Integer.parseInt(redSearch.getText().toString());
                    min = Integer.parseInt(minSearch.getText().toString());
                    max = Integer.parseInt(maxSearch.getText().toString());
                    confPort = Integer.parseInt(portSearch.getText().toString());
                    showHideConfig();
                }
            }
        });

    }

    //revisa que los datos de la configuracion para la busqueda esten dentro de los parametros correctos
    private boolean checkConfigSearhData(){
        String red = redSearch.getText().toString();
        String port = portSearch.getText().toString();
        String min = minSearch.getText().toString();
        String max = maxSearch.getText().toString();

        if(red.isEmpty() || Integer.parseInt(red) < 0 || Integer.parseInt(red) > 255){
            Toast.makeText(context, "Los valores de la red son inconrrectos", Toast.LENGTH_LONG).show();
            return false;
        }
        if(port.isEmpty() || Integer.parseInt(port) <= 0 || Integer.parseInt(port) > 65535){
            Toast.makeText(context, "Los valores del puerto son inconrrectos", Toast.LENGTH_LONG).show();
            return false;
        }
        if(min.isEmpty() || Integer.parseInt(min) <= 0 || Integer.parseInt(min) > 255){
            Toast.makeText(context, "El valor minimo es incorrecto", Toast.LENGTH_LONG).show();
            return false;
        }
        if(max.isEmpty() || Integer.parseInt(max) <= 0 || Integer.parseInt(max) > 255){
            Toast.makeText(context, "El valor maximo es incorrecto", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void loading(boolean isLoading) {
        accept.setEnabled(!isLoading);
        search.setEnabled(!isLoading);
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
        message.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }


//prueba si la direcccion sumninistrada es corecta
    private void testAddress(String _protocol, String _ip, String _port) {
        String URL = _protocol + "://" + _ip + ":" + _port + "/test";
        message.setText("Probando -> " + _protocol + "://" + _ip + ":" + _port);


        Request request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                fetchManager.setServerProtocol(protocol.getText().toString());
                fetchManager.setServerPort(port.getText().toString());
                fetchManager.setServerAddress(ip.getText().toString());
                new Params(context).insertParam("mode", "online");
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("OCURRIÓ UN ERROR");
                builder.setMessage("No se ha podido establecer una conexión con la dirección suministrada");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                loading(false);
            }
        });

        Volley.newRequestQueue(context).add(request);
    }

    ///


    //escanea todas las direcciones y consigue una que de una respuesta correcta (con un server activo que responda correctamente)
    private void searchAddress(String C1, String C2, String C3, String C4, String _port) {

        String _ip = C1 + "." + C2 + "." + C3 + "." + C4;
        String URL = "http://" + _ip + ":" + _port + "/test";
        message.setText("Probando -> "+ _ip + ":" + _port);

        Request request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                protocol.setText("http");
                ip.setText(_ip);
                port.setText(_port);
                loading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int c4 = Integer.parseInt(C4);
                c4++;

                if(c4 > max){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("OCURRIÓ UN ERROR");
                    builder.setMessage("No se ha encontrado ningun servidor en la red local");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    loading(false);

                    return;
                }
                String C4V = String.valueOf(c4);
                searchAddress(C1, C2, C3, C4V, _port);
            }
        });

        Volley.newRequestQueue(context).add(request);
    }


}