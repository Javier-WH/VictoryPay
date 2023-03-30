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
import java.util.HashMap;

public class DownloadRegister extends AsyncTask<String, Void, String> {

    private String urlString;
    private String page;
    private String date;

    public DownloadRegister(String urlString, String page, String date) {
        this.urlString = urlString + "/SyncRegister/getPage";
        this.page = page;
        this.date = date;
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

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page", page);
            jsonObject.put(  "date", date);



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


            return response.toString();

        } catch (SocketTimeoutException e) {
            e.printStackTrace();

            return "{ \"error\": \"E-100\"}"; //"Se ha perdido la conexion con el servidor";
        } catch (IOException e) {
            e.printStackTrace();
            return "{ \"error\": \"E-200\"}";  //"No se ha podido crear el paquete a enviar";
        } catch (JSONException e) {
            e.printStackTrace();
            return "{ \"error\": \"E-300\"}"; // "No se ha podido escribir el objeto de envío";
        }

    }

    @Override
    protected void onPostExecute(String result) {
        /*

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

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            params.insertParam("lastUpdatedDate", currentDate);

        }
*/

    }


}


