package com.fjrh.victorypay.sync;

import androidx.appcompat.app.AppCompatActivity;

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
import com.fjrh.victorypay.dataBases.params.Params;
import com.fjrh.victorypay.dataBases.register.GetRegister;
import com.fjrh.victorypay.dataBases.students.FindStudent;

import java.util.ArrayList;
import java.util.HashMap;
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
    private static Handler handler;
    private static Params params;


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
        if(plus == 0){
            percentInt = 0;
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
                    params.insertParam("mode", "offline");
                }else{
                    params.insertParam("mode", "online");
                }
                App.fillElements();
            }
        });
    }

    public void startLoad(int stept) {
        int sleepTime = 2000;
        String URL = new FetchManager(context).getFetchinAddress();

        //obtiene la lista de estudiantes
        ArrayList<HashMap<String, String>> studentList = new GetRegister(context).getRegisterList();

        //divide la lista en sublistas de 50 registros cada una
        ArrayList<ArrayList<HashMap<String, String>>> list = splitArrayList(studentList);

        //dependiendo del tamaño de la lista, obtiene el porcentaje a mostrar en cada bucle
        int studentPercent = 50 / list.size();

        syncThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //sube los registros del servidor de 50 en 50
                    setMessage("...");
                    for (int i = 0 ; i < list.size() ; i++){
                       // Thread.sleep(sleepTime);
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

                    /////falta descargar los registros.

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
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

}