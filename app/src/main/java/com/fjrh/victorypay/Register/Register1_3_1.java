package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.schools.School;

import java.util.HashMap;

public class Register1_3_1 extends AppCompatActivity {
    private HashMap<String, String> data;
    Context context;
    private Button back;
    private Button next;
    private Button skipt;
    private TextView school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1_3_1);
        context = this;
        initComponents();
        initEvents();
        fillInputs();
    }

    private void initComponents(){
        back = findViewById(R.id.btnBack1_3_1);
        next = findViewById(R.id.btnNext1_3_1);
        skipt = findViewById(R.id.btnSkipt1_3_1);
        school = findViewById(R.id.school1_3_1);
    }

    private void initEvents(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register1_3.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                School school = new School(context);
                school.insertSchool(data.get("procedence"));
                goForward();
            }
        });

        skipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward();
            }
        });


    }

    private void goForward(){
        Intent i = new Intent(context, Register1_4.class);
        i.putExtra("data", data);
        startActivity(i);

    }



    private void fillInputs() {
        Intent intentData = getIntent();
        if (intentData.hasExtra("data")) {
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");
            if(data.containsKey("procedence")){
                school.setText(data.get("procedence"));
            }
        }
    }
}