package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.R;

import java.util.HashMap;

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
    private HashMap<String, String> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        context = this;
        initComponents();
        initEvents();
        fillInputs();

    }

    private void initComponents() {
        next = findViewById(R.id.btnNext2);
        back = findViewById(R.id.btnBack2);
        motherName = findViewById(R.id.textMotherName);
        fatherName = findViewById(R.id.textFatherName);
        motherCi = findViewById(R.id.textMotherCi);
        fatherCi = findViewById(R.id.textFatherCi);
        motherNationality = findViewById(R.id.textMotherNationality);
        fatherNationality = findViewById(R.id.textFatherNationality);

    }

    private void initEvents() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, Register1_3.class);
                data.putAll(getData());
                i.putExtra("data", data);
                startActivity(i);


            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDataComplete()) {
                    Intent i = new Intent(context, Register3.class);
                    data.putAll(getData());
                    i.putExtra("data", data);
                    startActivity(i);
                }
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

    private void changeMotherNationality() {
        if (motherXeno) {
            motherXeno = false;
            motherNationality.setText("V-");
        } else {
            motherXeno = true;
            motherNationality.setText("E-");
        }
    }

    private void changeFatherNationality() {
        if (fatherXeno) {
            fatherXeno = false;
            fatherNationality.setText("V-");
        } else {
            fatherXeno = true;
            fatherNationality.setText("E-");
        }
    }

    public HashMap<String, String> getData() {
        HashMap<String, String> data = new HashMap<>();
        data.put("motherName", motherName.getText().toString());
        data.put("motherCi", motherCi.getText().toString());
        data.put("motherNationality", motherXeno ? "E-" : "V-");
        data.put("fatherName", fatherName.getText().toString());
        data.put("fatherCi", fatherCi.getText().toString());
        data.put("fatherNationality", fatherXeno ? "E-" : "V-");
        return data;
    }

    private void fillInputs() {
        Intent intentData = getIntent();

        if (intentData.hasExtra("data")) {
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");

            if (data.containsKey("motherName")) {
                motherName.setText(data.get("motherName"));
            }
            if (data.containsKey("motherCi")) {
                motherCi.setText(data.get("motherCi"));
            }
            if (data.containsKey("motherNationality")) {
                motherNationality.setText(data.get("motherNationality"));
                if (motherNationality.getText().toString().equals("E-")) {
                    motherXeno = true;
                } else {
                    motherXeno = false;
                }
            }
            if (data.containsKey("fatherName")) {
                fatherName.setText(data.get("fatherName"));
            }
            if (data.containsKey("fatherCi")) {
                fatherCi.setText(data.get("fatherCi"));
            }
            if (data.containsKey("fatherNationality")) {
                fatherNationality.setText(data.get("fatherNationality"));
                if (fatherNationality.getText().toString().equals("E-")) {
                    fatherXeno = true;
                } else {
                    fatherXeno = false;
                }
            }

        }
    }

    private boolean isDataComplete() {

        if (motherName.getText().toString().isEmpty()) {
            Toast.makeText(context, "No ha suministrado un nombre para la madre", Toast.LENGTH_LONG).show();
            return false;
        }
        if (motherCi.getText().toString().isEmpty()) {
            Toast.makeText(context, "No ha suministrado una cédula para la madre", Toast.LENGTH_LONG).show();
            return false;
        }
        if (fatherName.getText().toString().isEmpty()) {
            Toast.makeText(context, "No ha suministrado un nombre para el padre", Toast.LENGTH_LONG).show();
            return false;
        }
        if (fatherCi.getText().toString().isEmpty()) {
            Toast.makeText(context, "No ha suministrado una cédula para el padre", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


}