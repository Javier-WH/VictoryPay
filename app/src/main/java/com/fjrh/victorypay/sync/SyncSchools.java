package com.fjrh.victorypay.sync;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

public class SyncSchools extends AsyncTask<String, Void, String> {

    private String urlString;
    private ArrayList<String> list;

    public SyncSchools(String urlString, ArrayList<String> list) {
        this.urlString = urlString + "/syncSchools";
        this.list = list;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... strings) {


        try {

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(10000); // 10 seconds timeout

            JSONArray jsonArray = new JSONArray(list);


            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(jsonArray.toString());
            writer.flush();

            //obtener respuesta
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();


            return response.toString();

        } catch (SocketTimeoutException e) {
            e.printStackTrace();

            return "{ \"error\": \"E-100\"}"; //"Se ha perdido la conexion con el servidor";
        } catch (IOException e) {
            e.printStackTrace();
            return "{ \"error\": \"E-200\"}";  //"No se ha podido crear el paquete a enviar";
        }

    }

    @Override
    protected void onPostExecute(String result) {
       /* try {



        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
}
