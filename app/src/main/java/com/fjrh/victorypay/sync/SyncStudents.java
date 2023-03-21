package com.fjrh.victorypay.sync;


import android.content.Context;
import android.os.AsyncTask;
import android.text.style.IconMarginSpan;
import android.util.Log;
import com.fjrh.victorypay.dataBases.students.FindStudent;
import com.fjrh.victorypay.dataBases.students.UpdateStudentList;
import com.fjrh.victorypay.dataBases.users.Users;

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
import java.util.Objects;


public class SyncStudents extends AsyncTask <String, Void, String> {
    private String urlString;
    private ArrayList<HashMap<String, String>> studentList;
    private JSONArray arrayResponse;
    private Context context;
    private String user;


    public SyncStudents(Context context, String urlString){
        this.urlString = urlString;
        this.studentList = studentList;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Sync.setMessage("Se está actualizando la lista de estudiantes...");
        studentList = new FindStudent(context).getOfflineStudentList();
        Log.i("XXX", "45 StudentSize " + studentList.size());
        user = new Users(context).getUsers().get("ci");

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
                jsonObject.put("user", user);
                jsonObject.put("date", 0);

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
        if(Objects.isNull(result)){
            return;
        }


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

        if(list.size() > 0 ){
            new UpdateStudentList(context).update(list);
        }

        Sync.addPercent(20);



    }

}
