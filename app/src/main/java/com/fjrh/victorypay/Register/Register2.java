package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fjrh.victorypay.R;

public class Register2 extends AppCompatActivity {
    private Context context;
    private Button next;
    private Button back;
    private EditText motherName;
    private EditText fatherName;
    private EditText motherCi;
    private EditText fatherCi;
    private TextView motherNationality;
    private TextView fatherNationality;
    private boolean motherXeno = false;
    private boolean fatherXeno = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        context = this;
        initComponents();
        initEvents();

    }

    private void initComponents(){
        next = findViewById(R.id.btnNext2);
        back = findViewById(R.id.btnBack2);
        motherName = findViewById(R.id.textMotherName);
        fatherName = findViewById(R.id.textFatherName);
        motherCi = findViewById(R.id.textMotherCi);
        fatherCi = findViewById(R.id.textFatherCi);
        motherNationality = findViewById(R.id.textMotherNationality);
        fatherNationality = findViewById(R.id.textFatherNationality);
    }

    private void initEvents(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register1.class);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register3.class);
                startActivity(i);
            }
        });


        motherNationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMotherNationality();
            }
        });
        fatherNationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFatherNationality();
            }
        });


    }

    private void changeMotherNationality(){
        if(motherXeno){
            motherXeno = false;
            motherNationality.setText("V-");
        }else{
            motherXeno = true;
            motherNationality.setText("E-");
        }
    }

    private void changeFatherNationality(){
        if(fatherXeno){
            fatherXeno = false;
            fatherNationality.setText("V-");
        }else{
            fatherXeno = true;
            fatherNationality.setText("E-");
        }
    }

}