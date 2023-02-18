package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.App;
import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.students.InsertStuden;

import java.util.HashMap;

public class Register5 extends AppCompatActivity {
    private Context context;
    private Button back;
    private Button next;



    private HashMap<String, String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register5);
        context = this;
        initComponets();
        initEvents();
        fillInputs();
    }

    private void initComponets() {
        back = findViewById(R.id.btnBack5);
        next = findViewById(R.id.btnNext5);

    }

    private void initEvents() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register4_1.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*InsertStuden is = new InsertStuden(context);
                if(is.insert(data)){
                    Toast.makeText(context, "Se ha registrado conrrectamente al estudiante", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context, App.class);
                    startActivity(i);
                }*/
            }
        });

    }

    private void fillInputs() {
        Intent intentData = getIntent();

        if (intentData.hasExtra("data")) {
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");


        }
    }




}