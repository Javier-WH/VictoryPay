package com.fjrh.victorypay.sync;


import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.text.style.IconMarginSpan;
import android.util.Log;
import android.widget.Toast;

import com.fjrh.victorypay.App;
import com.fjrh.victorypay.Register.Register5;
import com.fjrh.victorypay.dataBases.params.Params;
import com.fjrh.victorypay.dataBases.register.GetRegister;
import com.fjrh.victorypay.dataBases.students.FindStudent;
import com.fjrh.victorypay.dataBases.students.InsertStuden;
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
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;


public class SyncStudents extends AsyncTask<String, Void, String> {
    private String urlString;
    private ArrayList<HashMap<String, String>> studentList;
    private JSONArray arrayResponse;
    private Context context;
    private InsertStuden insertStuden;

    public SyncStudents(Context context, String urlString) {
        this.urlString = urlString + "/SyncRegister";
        this.studentList = studentList;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Sync.setMessage("Empaquetando lista de registros offline...");
        studentList = new GetRegister(context).getRegisterList();
        insertStuden = new InsertStuden(context);
        Sync.addPercent(10);
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

            JSONArray jsonArray = new JSONArray(studentList);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", "01/01/1998 01:01:01");
            jsonObject.put("data", jsonArray);

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

            arrayResponse = new JSONArray(response.toString());

            int responseCode = connection.getResponseCode();


            return String.valueOf(responseCode);

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "timeout";
        } catch (IOException e) {
            e.printStackTrace();
            return "package";
        } catch (JSONException e) {
            e.printStackTrace();
            return "write";
        }


    }

    @Override
    protected void onPostExecute(String result) {
        Sync.addPercent(50); ///////////////////////////
        if (Objects.isNull(result)) {
            return;
        }

        if (result.equals("timeout")) {
            new Params(context).insertParam("mode", "offline");
            App.fillElements();
            Toast.makeText(context, "Se ha perdido la conexion con el servidor", Toast.LENGTH_LONG).show();
            Sync.startLoad(0);
            return;
        }
        if (result.equals("package")) {
            Toast.makeText(context, "No se ha podido crear el paquete a enviar", Toast.LENGTH_LONG).show();
            Sync.startLoad(0);
            return;
        }
        if (result.equals("write")) {
            Toast.makeText(context, "No se ha podido escribir el objeto de envío", Toast.LENGTH_LONG).show();
            Sync.startLoad(0);
            return;
        }

        new Params(context).insertParam("mode", "online");
        App.fillElements();

        Sync.addPercent(20); ///////////////////////////
        Sync.setMessage("intentando sincronizar los registros");///////////////////////



        try {
            for (int i = 0; i < arrayResponse.length(); i++) {
                JSONObject student = arrayResponse.getJSONObject(i);

                ContentValues contentValues = new ContentValues();
                Iterator<String> keys = student.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    if(key.equalsIgnoreCase("id")){
                        continue;
                    }

                    Object value = student.get(key);

                    if (value instanceof String) {
                        contentValues.put(key, (String) value);
                    } else if (value instanceof Integer) {
                        contentValues.put(key, (Integer) value);
                    } else if (value instanceof Long) {
                        contentValues.put(key, (Long) value);
                    } else if (value instanceof Double) {
                        contentValues.put(key, (Double) value);
                    } else if (value instanceof Boolean) {
                        contentValues.put(key, (Boolean) value);
                    }else if (value instanceof JSONObject) { // Comprobamos si la variable es un JSONObject
                        contentValues.put(key, value.toString());
                    } else {
                        contentValues.putNull(key);
                    }
                }
                insertStuden.insertRegister(contentValues);
                insertStuden.execSQList((String) contentValues.get("insertion_query"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            Toast.makeText(context, "Datos actualizados correctamente", Toast.LENGTH_LONG).show();
            Sync.setMessage("registros sincronizados");///////////////////////
            Sync.addPercent(20); ///////////////////////////
            Sync.startLoad(0);
        }


    }


}
