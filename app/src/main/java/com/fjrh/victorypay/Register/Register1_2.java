package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fjrh.victorypay.Libraries.ReadJson;
import com.fjrh.victorypay.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Register1_2 extends AppCompatActivity {

    private Context context;
    private Button back;
    private Button next;
    private HashMap<String, String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1_2);
        context = this;
        initComponents();
        initEvents();
        fillElements();
        ///



    }

    private void initComponents(){
        back = findViewById(R.id.btnBack1_2);
        next = findViewById(R.id.btnNext1_2);
    }

    private void initEvents(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register1.class);

                i.putExtra("data", data);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register2.class);

                i.putExtra("data", data);
                startActivity(i);
            }
        });

    }

    private void fillElements(){
        Intent intentData = getIntent();

        if (intentData.hasExtra("data")) {
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");

        }
    }


}