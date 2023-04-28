package com.fjrh.victorypay.sync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.App;
import com.fjrh.victorypay.Libraries.FetchManager;
import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.CleanDatabases;
import com.fjrh.victorypay.dataBases.params.Params;
import com.fjrh.victorypay.dataBases.register.GetRegister;
import com.fjrh.victorypay.dataBases.schools.School;
import com.fjrh.victorypay.dataBases.students.FindStudent;
import com.fjrh.victorypay.dataBases.students.InsertStuden;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class Sync extends AppCompatActivity {

    private static Context context;
    private static TextView message;
    private TextView cancelSync;
    private ProgressBar progressBar;
    private static TextView percent;
    private static int percentInt = 0;
    private static Sync syncActivity;
    private Thread syncThread;
    public static Handler handler;
    private static Params params;
    private static InsertStuden insertStuden;
    private static School school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        context = this;
        syncActivity = this;
        initComponets();
        initEvents();
        startLoad(1);
        handler = new Handler(Looper.getMainLooper());
        params = new Params(context);
        insertStuden = new InsertStuden(context);
        school = new School(context);
    }


    private void initComponets() {
        message = findViewById(R.id.message_sync);
        cancelSync = findViewById(R.id.cancelSync);
        progressBar = findViewById(R.id.progressBar_sync);
        percent = findViewById(R.id.percent_sync);
    }

    private void initEvents() {
        cancelSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncThread.interrupt();
                finish();
            }
        });
    }


    public static void setMessage(String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Sync.message.setText(message);
            }
        });
    }

    public static void addPercent(int plus) {

        percentInt += plus;
        if(plus == 0 || plus == 100){
            percentInt = plus;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {

                percent.setText(String.valueOf(percentInt) + "%");
            }
        });
    }

    public static void setOffline(boolean mode) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if(mode) {
                    Toast.makeText(context, "Se ha perdido la conexión con el servidor", Toast.LENGTH_LONG).show();
                   // params.insertParam("mode", "offline");
                    App.fillElements("offline");
                }else{
                   // params.insertParam("mode", "online");
                    App.fillElements("online");
                }

            }
        });
    }

    public void startLoad(int stept) {
        int UploadPercent = 30;
        int stdPercent = 30;
        int abnPercent = 30;
        int schoolPercern = 10;
        String Date = "01/01/1998 01:01:01";
        int sleepTime = 2000;
        String URL = new FetchManager(context).getFetchinAddress();

        //obtiene la lista de estudiantes
        ArrayList<HashMap<String, String>> studentList = new GetRegister(context).getRegisterList();

        //divide la lista en sublistas de 50 registros cada una
        ArrayList<ArrayList<HashMap<String, String>>> list = splitArrayList(studentList);

        //dependiendo del tamaño de la lista, obtiene el porcentaje a mostrar en cada bucle
        int studentPercent = UploadPercent / list.size();

        syncThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //sube los registros del servidor de 50 en 50
                    setMessage("...");
                    for (int i = 0 ; i < list.size() ; i++){
                        //Thread.sleep(sleepTime);
                        setMessage("Subiendo lista de registros " + (i+1) + " de " + list.size());
                        SyncStudents syncStudents = new SyncStudents( URL, list.get(i));
                        syncStudents.execute();
                        String studentsResponse = syncStudents.get();
                        if(!studentsResponse.equals("200")){
                            if(studentsResponse.equalsIgnoreCase("E-100")){
                                setOffline(true);
                            }
                            setMessage("Ocurrió un error -> " + studentsResponse);
                            Thread.sleep(sleepTime);
                            syncThread.interrupt();
                        }else{
                            setOffline(false);
                        }
                        addPercent(studentPercent);
                    }

                    /////descarga de registros
                    setMessage("Obteniendo numero de paginas...");
                    //pide la primera pagina de registros
                    DownloadRegister downloadRegister = new DownloadRegister(URL  + "/SyncRegister/getPage", "1", Date);
                    downloadRegister.execute();
                    String downloadResponse =  downloadRegister.get();
                    JSONObject response = new JSONObject(downloadResponse);

                    //si el objeto de respuesta tiene la clave error
                    if(response.has("error")){
                        setOffline(true);
                        setMessage("Ocurrió un error -> " + response.getString("error"));
                        Thread.sleep(sleepTime);
                        syncThread.interrupt();
                    }

                    //obtiene la meta data de las paginas de registro
                    int totalPages = Integer.parseInt( response.getString("totalPages"));


                    int downloadPercent = totalPages == 0 ? stdPercent : (stdPercent / totalPages); //el numero de porcentaje que aumenta con cada descarga de una nueva pagina


                    ////limpiar base de datos antes de agregar los registros
                    new CleanDatabases(context).cleanDB();

                    //el contenido de la pagina
                    JSONArray pageData = response.getJSONArray("pageData");
                    insertJSONregister(pageData);
                    addPercent(downloadPercent);

                    //lo mismo que lo anterior, pero iterando las paginas desde el indice 2 hasta el total de las paginas
                    for (int i = 2 ; i <= totalPages ; i++){

                        setMessage("Descargando pagina de registros " + i + " de " + totalPages);

                        downloadRegister = new DownloadRegister(URL + "/SyncRegister/getPage", String.valueOf(i), Date);
                        downloadRegister.execute();
                        downloadResponse =  downloadRegister.get();
                        response = new JSONObject(downloadResponse);
                        if(response.has("error")){
                            setOffline(true);
                            setMessage("Ocurrió un error -> " + response.getString("error"));
                            Thread.sleep(sleepTime);
                            syncThread.interrupt();
                        }

                        pageData = response.getJSONArray("pageData");
                        insertJSONregister(pageData);
                        addPercent(downloadPercent);

                    }
//////////syctroniza los abonos

                    setMessage("Obteniendo numero de paginas...");
                    //pide la primera pagina de registros
                    DownloadRegister downloadAbonosRegister = new DownloadRegister(URL  + "/SyncRegister/getAbonoPage", "1", Date);
                    downloadAbonosRegister.execute();
                    String downloadAbonosResponse =  downloadAbonosRegister.get();
                    JSONObject abonosResponse = new JSONObject(downloadAbonosResponse);

                    //si el objeto de respuesta tiene la clave error
                    if(abonosResponse.has("error")){
                        setOffline(true);
                        setMessage("Ocurrió un error -> " + abonosResponse.getString("error"));
                        Thread.sleep(sleepTime);
                        syncThread.interrupt();
                    }

                    //obtiene la meta data de las paginas de registro
                    int totalAbonosPages = Integer.parseInt( abonosResponse.getString("totalPages"));


                    int downloadAbonosPercent = totalAbonosPages == 0 ? abnPercent : (abnPercent / totalAbonosPages); //el numero de porcentaje que aumenta con cada descarga de una nueva pagina

                    //el contenido de la pagina
                    JSONArray pageAbonoData = abonosResponse.getJSONArray("pageData");
                    insertJSONregister(pageAbonoData);
                    addPercent(downloadAbonosPercent);

                    //lo mismo que lo anterior, pero iterando las paginas desde el indice 2 hasta el total de las paginas
                    for (int i = 2 ; i <= totalAbonosPages ; i++){

                        setMessage("Descargando pagina de registros " + i + " de " + totalAbonosPages);

                        downloadAbonosRegister = new DownloadRegister(URL + "/SyncRegister/getAbonoPage", String.valueOf(i), Date);
                        downloadAbonosRegister.execute();
                        downloadAbonosResponse =  downloadAbonosRegister.get();
                        abonosResponse = new JSONObject(downloadAbonosResponse);
                        if(abonosResponse.has("error")){
                            setOffline(true);
                            setMessage("Ocurrió un error -> " + abonosResponse.getString("error"));
                            Thread.sleep(sleepTime);
                            syncThread.interrupt();
                        }

                        pageAbonoData = abonosResponse.getJSONArray("pageData");
                        insertJSONregister(pageAbonoData);
                        addPercent(downloadPercent);

                    }

///sincroniza los pagos mensuales



                    setMessage("Obteniendo numero de paginas...");
                    //pide la primera pagina de registros
                    DownloadRegister downloadMonthlyRegister = new DownloadRegister(URL  + "/SyncRegister/getMonthlyPage", "1", Date);
                    downloadMonthlyRegister.execute();
                    String downloadMonthlyResponse =  downloadMonthlyRegister.get();
                    JSONObject monthlyResponse = new JSONObject(downloadMonthlyResponse);

                    //si el objeto de respuesta tiene la clave error
                    if(monthlyResponse.has("error")){
                        setOffline(true);
                        setMessage("Ocurrió un error -> " + monthlyResponse.getString("error"));
                        Thread.sleep(sleepTime);
                        syncThread.interrupt();
                    }

                    //obtiene la meta data de las paginas de registro
                    int totalMobthlyPages = Integer.parseInt( monthlyResponse.getString("totalPages"));


                    int downloadMontlyPercent = totalMobthlyPages == 0 ? abnPercent : (abnPercent / totalMobthlyPages); //el numero de porcentaje que aumenta con cada descarga de una nueva pagina

                    //el contenido de la pagina
                    JSONArray pageMontlyData = monthlyResponse.getJSONArray("pageData");
                    insertJSONregister(pageMontlyData);
                    addPercent(downloadMontlyPercent);

                    //lo mismo que lo anterior, pero iterando las paginas desde el indice 2 hasta el total de las paginas
                    for (int i = 2 ; i <= totalMobthlyPages ; i++){

                        setMessage("Descargando pagina de registros " + i + " de " + totalMobthlyPages);

                        downloadMonthlyRegister = new DownloadRegister(URL + "/SyncRegister/getMonthlyPage", String.valueOf(i), Date);
                        downloadMonthlyRegister.execute();
                        downloadMonthlyResponse =  downloadMonthlyRegister.get();
                        monthlyResponse = new JSONObject(downloadMonthlyResponse);
                        if(monthlyResponse.has("error")){
                            setOffline(true);
                            setMessage("Ocurrió un error -> " + monthlyResponse.getString("error"));
                            Thread.sleep(sleepTime);
                            syncThread.interrupt();
                        }

                        pageMontlyData = monthlyResponse.getJSONArray("pageData");
                        insertJSONregister(pageMontlyData);
                        addPercent(downloadMontlyPercent);

                    }






                    //syncroniza los colegios
                    setMessage("Obteniendo lista de colegios");
                    SyncSchools syncSchools = new SyncSchools(URL, school.getList());
                    syncSchools.execute();
                    String responseSchoolData = syncSchools.get();

                    if(getTypeOfJson(responseSchoolData).equals("JSONObject")){
                        JSONObject jsonResponse = new JSONObject(responseSchoolData);
                        if(jsonResponse.has("error")){
                            setOffline(true);
                            setMessage("Ocurrió un error -> " + jsonResponse.getString("error"));
                            Thread.sleep(sleepTime);
                            syncThread.interrupt();

                        }
                    }else {

                        JSONArray responseArray = new JSONArray(responseSchoolData);
                        int shoolpercert = schoolPercern / (responseArray.length() == 0 ? 1 : responseArray.length());

                        setMessage("Actualizando la lista es colegios");

                        for (int i = 0; i < responseArray.length(); i++) {

                            JSONObject register = responseArray.getJSONObject(i);
                            String schollname = register.getString("school");
                            school.insertSchool(schollname);
                            addPercent(shoolpercert);
                        }
                        addPercent(10);
                    }

                    addPercent(100);
                    setMessage("Actualizacón de las bases de datos completa...");

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    addPercent(0);
                    syncActivity.finish();
                }

            }
        });

        syncThread.start();

    }


    // esta función recibe un arraylist grande y regresa multiples arrayslist de 50 indices.
    public static ArrayList<ArrayList<HashMap<String, String>>> splitArrayList(ArrayList<HashMap<String, String>> originalList) {
        ArrayList<ArrayList<HashMap<String, String>>> subLists = new ArrayList<ArrayList<HashMap<String, String>>>();
        ArrayList<HashMap<String, String>> subList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < originalList.size(); i++) {
            if (i % 50 == 0 && i > 0) {
                subLists.add(subList);
                subList = new ArrayList<HashMap<String, String>>();
            }
            subList.add(originalList.get(i));
        }

        subLists.add(subList);

        return subLists;
    }

    //esta funcion convierte un JSON en valor y crea el registro
    public static void insertJSONregister(JSONArray arrayResponse){

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
                //inserta el registro
               // insertStuden.insertRegister(contentValues);
                insertStuden.execSQList((String) contentValues.get("insertion_query"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public static String getTypeOfJson(String jsonString) {
        try {
            Object json = new JSONObject(jsonString);
            if (json instanceof JSONObject) {
                return "JSONObject";
            } else if (json instanceof JSONArray) {
                return "JSONArray";
            }
        } catch (JSONException e) {
            // La cadena de texto no es un objeto JSON válido
        }

        return "No es un objeto JSON válido";
    }
}



