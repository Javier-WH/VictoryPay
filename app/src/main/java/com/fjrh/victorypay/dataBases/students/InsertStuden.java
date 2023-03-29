package com.fjrh.victorypay.dataBases.students;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.fjrh.victorypay.App;
import com.fjrh.victorypay.Libraries.FetchManager;
import com.fjrh.victorypay.Register.Register5;
import com.fjrh.victorypay.dataBases.DbHelper;
import com.fjrh.victorypay.dataBases.params.Params;
import com.fjrh.victorypay.dataBases.register.Register;


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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class InsertStuden extends DbHelper {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private HashMap<String, String> params;
    private String urlString;
    private String requestMessage;
    private Thread thread = null;

    public InsertStuden(Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        params = new Params(context).getParams();
        urlString = new FetchManager(context).getFetchinAddress();

    }


    public void insert(Register register, Register paymentRegister) {

        db.beginTransaction();

        try {
            //inserta datos del estudiante
            ContentValues values = new ContentValues();
            values.put("register_code", register.getRegister_code());
            values.put("user", register.getUser());
            values.put("description", register.getDescription());
            values.put("type", register.getType());
            values.put("insertion_query", register.getInsertion_query());
            values.put("rollback_query", register.getRollback_query());
            values.put("metadata", register.getMetadata().toString());

            insertRegister(values);
            String sql = register.getInsertion_query();
            execSQList(sql);


            //insereta datos del pago de la inscripción
            ContentValues paymentValues = new ContentValues();
            paymentValues.put("register_code", paymentRegister.getRegister_code());
            paymentValues.put("user", paymentRegister.getUser());
            paymentValues.put("description", paymentRegister.getDescription());
            paymentValues.put("type", paymentRegister.getType());
            paymentValues.put("insertion_query", paymentRegister.getInsertion_query());
            paymentValues.put("rollback_query", paymentRegister.getRollback_query());
            paymentValues.put("metadata", paymentRegister.getMetadata().toString());


            insertRegister(paymentValues);
            String paymentSql = paymentRegister.getInsertion_query();
            execSQList(paymentSql);


            db.setTransactionSuccessful();

        } catch (SQLException e) {
            Log.e("XXX", e.getMessage());
            // Manejar cualquier excepción aquí
        } finally {
            // Finalizar la transacción

            db.endTransaction();

            if (params.get("mode").equalsIgnoreCase("offline")) {
                Register5.closeActivity();
            } else {

                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SendRegister sendStudent = new SendRegister(register, "/addStudent");
                            sendStudent.execute();
                            String studetResponse = sendStudent.get();
                            if(!studetResponse.equals("200")){
                                thread.interrupt();
                            }

                            SendRegister sendPayment = new SendRegister(paymentRegister, "/addStudent/inscriptionPayment");
                            sendPayment.execute();
                            String paymentResponse = sendPayment.toString();
                            if(!paymentResponse.equals("200")){
                                thread.interrupt();
                            }

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            Register5.closeActivity();
                        }


                    }
                });
                thread.start();


            }
        }
    }


    private class SendRegister extends AsyncTask<String, Void, String> {

        private Register register;
        private String endPoint;

        public SendRegister(Register register, String endPoint) {
            this.register = register;
            this.endPoint = endPoint;
        }

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... strings)  {

            try {

                URL url = new URL(urlString + endPoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(10000); // 10 seconds timeout

                JSONObject jsonObject = register.getJSONdata();


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
                if (jsonResponse.has("error")) {
                    requestMessage = jsonResponse.getString("error");
                }

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
            if (Objects.isNull(result)) {
                return;
            }

            if (result.equals("timeout")) {
                new Params(context).insertParam("mode", "offline");
                App.fillElements();
                Toast.makeText(Register5.getAcrivity(), "Se ha perdido la conexion con el servidor", Toast.LENGTH_LONG).show();
                Register5.closeActivity();
                return;
            }
            if (result.equals("package")) {
                Toast.makeText(context, "No se ha podido crear el paquete a enviar", Toast.LENGTH_LONG).show();
                Register5.closeActivity();
                return;
            }
            if (result.equals("write")) {
                Toast.makeText(context, "No se ha podido escribir el objeto de envío", Toast.LENGTH_LONG).show();
                Register5.closeActivity();
                return;
            }

            int code = Integer.parseInt(result);

            if (code >= 300 || code < 200) {
                Toast.makeText(context, requestMessage, Toast.LENGTH_LONG).show();
                Register5.closeActivity();
                return;
            }

            Toast.makeText(context, "Se han registrado los datos de manera remota correctamente", Toast.LENGTH_LONG).show();

        }

    }

    ////

    public void insertRegister(ContentValues values) throws SQLException {
        //obtiene el código del registro a ingresar
        String register_code = values.getAsString("register_code");

        //comprueba si ese registro no ha sido ingresado
        Cursor cursor = db.rawQuery("SELECT * FROM registers WHERE register_code = ?", new String[]{register_code});

        //si el registro no ha sido ingresado, entonces lo ingresa
        if (cursor.getCount() <= 0) {
            db.insert("registers", null, values);
        }

    }

    public void execSQList(String sql) throws SQLException {
        //convierte el String de statements a un array de statements
        String[] statements = sql.split(";");

        // Eliminar cualquier espacio en blanco o saltos de línea al principio o al final de cada instrucción
        for (int i = 0; i < statements.length; i++) {
            statements[i] = statements[i].trim();
        }

        // Eliminar cualquier instrucción SQL vacía
        List<String> statementList = new ArrayList<>();
        for (String statement : statements) {
            if (!statement.isEmpty()) {
                statementList.add(statement);
            }
        }

        //ejecuta las instrucciones SQL
        for (String statement : statementList) {
            db.execSQL(statement);

        }

    }


}
