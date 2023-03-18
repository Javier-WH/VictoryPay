package com.fjrh.victorypay.sync;


import android.os.AsyncTask;
import android.text.style.IconMarginSpan;
import android.util.Log;
import com.fjrh.victorypay.dataBases.students.FindStudent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class SyncStudents extends AsyncTask <String, Void, String> {
    private String urlString;
    private ArrayList<HashMap<String, String>> studentList;
    private JSONArray arrayResponse;

    public SyncStudents(String urlString, ArrayList<HashMap<String, String>> studentList){
        this.urlString = urlString;
        this.studentList = studentList;
    }

    @Override
    protected void onPreExecute() {
        Sync.setMessage("Se está actualizando la lista de estudiantes...");
    }

    @Override
    protected String doInBackground(String... strings) {

            try {

                URL url = new URL(urlString + "/syncStudents");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(10000); // 10 seconds timeout

                JSONArray jsonArray = new JSONArray(studentList);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("datos", jsonArray);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(jsonObject.toString());
                writer.flush();

                //obtener respuesta
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                arrayResponse = jsonResponse.getJSONArray("data");

                int responseCode = connection.getResponseCode();
                return String.valueOf(responseCode); // 2xx codes indicate successful response
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            } catch (JSONException e) {
                e.printStackTrace();
                return "error2";
            }


    }

    @Override
    protected void onPostExecute(String result) {
        int code = Integer.parseInt(result);
        if(code >= 300 || code < 200){
            return;
        }
        ArrayList<JSONObject> list = new ArrayList<JSONObject>();
        try {

            for (int i = 0; i < arrayResponse.length(); i++) {
                JSONObject item = arrayResponse.getJSONObject(i);
                list.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Sync.addPercent(20);



    }

}
