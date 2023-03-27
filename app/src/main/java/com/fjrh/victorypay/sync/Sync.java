package com.fjrh.victorypay.sync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.Libraries.FetchManager;
import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.students.FindStudent;

import java.util.ArrayList;
import java.util.HashMap;

public class Sync extends AppCompatActivity {

    private static Context context;
    private static TextView message;
    private TextView cancelSync;
    private ProgressBar progressBar;
    private static TextView percent;
    private static int percentInt = 0;
    private static Sync syncActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        context = this;
        syncActivity = this;
        initComponets();
        initEvents();
        startLoad(1);
    }



    private void initComponets(){
        message = findViewById(R.id.message_sync);
        cancelSync = findViewById(R.id.cancelSync);
        progressBar = findViewById(R.id.progressBar_sync);
        percent = findViewById(R.id.percent_sync);
    }

    private void initEvents(){
        cancelSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public static void setMessage(String message){
        Sync.message.setText(message);
    }

    public static void addPercent(int plus){
        percentInt += plus;
        percent.setText(String.valueOf( percentInt) + "%");
    }

    public static void startLoad(int stept){

        switch (stept){
            case 1:
                String URL = new FetchManager(context).getFetchinAddress();
                SyncStudents syncStudents = new SyncStudents(context, URL);
                syncStudents.execute();
                break;
            default:
                if (syncActivity != null) {
                    percentInt = 0;
                    syncActivity.finish();
                }
        }

    }



}