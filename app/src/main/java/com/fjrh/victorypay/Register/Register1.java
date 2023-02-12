package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fjrh.victorypay.R;

public class Register1 extends AppCompatActivity {
    private Context context;
    private Button next;
    private TextView code;
    private EditText name;
    private EditText lastName;
    private Spinner seccion;
    private Spinner grade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        context = this;
        initComponents();
        initEvents();
    }

    private void initComponents(){
        next = findViewById(R.id.btnNext);
        code = findViewById(R.id.textCode);
        name = findViewById(R.id.student_name);
        lastName = findViewById(R.id.student_last_name);
        seccion = findViewById(R.id.seccion);
        grade = findViewById(R.id.grade);
    }

    private void initEvents(){

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register2.class);
                startActivity(i);
            }
        });


    }

}