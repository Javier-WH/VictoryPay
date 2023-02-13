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
    private Button back;
    private Button next;
    private TextView stdName;
    private TextView seccion;
    private TextView grade;
    private TextView gender;
    private TextView motherName;
    private TextView fatherName;
    private TextView motherCi;
    private TextView fatherCi;
    private TextView tutorName;
    private TextView tutorCi;
    private TextView phoneA;
    private TextView phoneB;
    private TextView email;
    private TextView code;
    private Context context;

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
        stdName = findViewById(R.id.studentName5);
        seccion = findViewById(R.id.seccion5);
        grade = findViewById(R.id.grade5);
        gender = findViewById(R.id.gender5);
        motherName = findViewById(R.id.motherName5);
        fatherName = findViewById(R.id.fatherName5);
        motherCi = findViewById(R.id.motherCi5);
        fatherCi = findViewById(R.id.fatherCi5);
        tutorName = findViewById(R.id.tutorName5);
        tutorCi = findViewById(R.id.tutorCi5);
        phoneA = findViewById(R.id.phoneA5);
        phoneB = findViewById(R.id.phoneB5);
        email = findViewById(R.id.email5);
        code = findViewById(R.id.code5);
    }

    private void initEvents() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register4.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertStuden is = new InsertStuden(context);
                if(is.insert(data)){
                    Toast.makeText(context, "Se ha registrado conrrectamente al estudiante", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context, App.class);
                    startActivity(i);
                }
            }
        });

    }

    private void fillInputs() {
        Intent intentData = getIntent();

        if (intentData.hasExtra("data")) {
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");

            checkInputs();

            if (data.containsKey("studentName") && data.containsKey("studentLastName")) {
                stdName.setText(data.get("studentName") + " " + data.get("studentLastName"));
            }
            if (data.containsKey("seccion")) {
                seccion.setText(data.get("seccion"));
            }
            if (data.containsKey("grade")) {
                grade.setText(data.get("grade"));
            }
            if (data.containsKey("gender")) {
                gender.setText(data.get("gender"));
            }
            if (data.containsKey("code")) {
                code.setText(data.get("code"));
            }
            /////

            if (data.containsKey("motherName")) {
                motherName.setText(data.get("motherName"));
            }
            if (data.containsKey("motherCi") && data.containsKey("motherNationality")) {
                motherCi.setText(data.get("motherNationality") + " " + data.get("motherCi"));
            }

            if (data.containsKey("fatherName")) {
                fatherName.setText(data.get("fatherName"));
            }
            if (data.containsKey("fatherCi") && data.containsKey("fatherNationality")) {
                fatherCi.setText(data.get("fatherNationality") + " " + data.get("fatherCi"));
            }
            ///
            if (data.containsKey("tutorName")) {
                tutorName.setText(data.get("tutorName"));
            }
            if (data.containsKey("tutorCi") && data.containsKey("tutorNationality")) {
                tutorCi.setText(data.get("tutorNationality") + " " + data.get("tutorCi"));
            }
            //

            if (data.containsKey("phone1")) {
                phoneA.setText(data.get("phone1"));
            }
            if (data.containsKey("phone2")) {
                phoneB.setText(data.get("phone2"));
            }
            if (data.containsKey("email")) {
                email.setText(data.get("email"));
            }


        }
    }

    private void checkInputs() {

        String message = "No suministrado";


        if (data.containsKey("studentName") && data.containsKey("studentLastName")) {
            if(data.get("studentName").length() <=0){
                data.put("studentName", message);
            }
            if(data.get("studentLastName").length() <=0){
                data.put("studentLastName", message);
            }

        }
        if (data.containsKey("seccion")) {
            if(data.get("seccion").length() <=0){
                data.put("seccion", message);
            }
        }
        if (data.containsKey("grade")) {
            if(data.get("grade").length() <=0){
                data.put("grade", message);
            }
        }
        if (data.containsKey("gender")) {
            if(data.get("gender").length() <=0){
                data.put("gender", message);
            }
        }
        if (data.containsKey("code")) {
            if(data.get("code").length() <=0){
                data.put("code", message);
            }
        }
        ////

        if (data.containsKey("motherName")) {
            if(data.get("motherName").length() <=0){
                data.put("motherName", message);
            }
        }
        if (data.containsKey("motherCi") && data.containsKey("motherNationality")) {
            if(data.get("motherCi").length() <=0){
                data.put("motherCi", message);
            }
            if(data.get("motherNationality").length() <=0){
                data.put("motherNationality", message);
            }
        }

        if (data.containsKey("fatherName")) {
            if(data.get("fatherName").length() <=0){
                data.put("fatherName", message);
            }
        }
        if (data.containsKey("fatherCi") && data.containsKey("fatherNationality")) {
            if(data.get("fatherCi").length() <=0){
                data.put("fatherCi", message);
            }
            if(data.get("fatherNationality").length() <=0){
                data.put("fatherNationality", message);
            }
        }
        ///
        if (data.containsKey("tutorName")) {
            if(data.get("tutorName").length() <=0){
                data.put("tutorName", message);
            }
        }
        if (data.containsKey("tutorCi") && data.containsKey("tutorNationality")) {
            if(data.get("tutorCi").length() <=0){
                data.put("tutorCi", message);
            }
            if(data.get("tutorNationality").length() <=0){
                data.put("tutorNationality", message);
            }
        }
        //

        if (data.containsKey("phone1")) {
            if(data.get("phone1").length() <=0){
                data.put("phone1", message);
            }
        }
        if (data.containsKey("phone2")) {
            if(data.get("phone2").length() <=0){
                data.put("phone2", message);
            }
        }
        if (data.containsKey("email")) {
            if(data.get("email").length() <=0){
                data.put("email", message);
            }
        }


    }


}