package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.fjrh.victorypay.R;


import java.util.HashMap;

public class Register4 extends AppCompatActivity {

    private Context context;
    private Button back;
    private Button next;
    private Switch whatsaap1;
    private Switch whatsaap2;
    private EditText phone1;
    private EditText phone2;
    private EditText email;
    private HashMap<String, String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4);
        context = this;
        initComponents();
        initEvents();
        fillInputs();
    }

    private void initComponents(){
        back = findViewById(R.id.btnBack4);
        next = findViewById(R.id.btnNext4);
        whatsaap1 = findViewById(R.id.chkWhatsaap1);
        whatsaap2 = findViewById(R.id.chkWhatsaap2);
        phone1 = findViewById(R.id.TextPhone1);
        phone2 = findViewById(R.id.TextPhone2);
        email = findViewById(R.id.TextEmailAddress);


    }

    private void initEvents(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register3.class);
                data.putAll(getData());
                i.putExtra("data", data);
                startActivity(i);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isDataComplete()) {
                    Intent i = new Intent(context, Register4_1.class);
                    data.putAll(getData());
                    i.putExtra("data", data);
                    startActivity(i);
                }
            }
        });
    }
    public HashMap<String, String> getData(){
        HashMap<String, String> data = new HashMap<>();
        data.put("phone1", phone1.getText().toString());
        data.put("phone2", phone2.getText().toString());
        data.put("email", email.getText().toString());
        data.put("whatsaap1", whatsaap1.isChecked() ? phone1.getText().toString() : "No suministrado");
        data.put("whatsaap2", whatsaap2.isChecked() ? phone2.getText().toString() : "No suministrado");
        data.put("w1", String.valueOf(whatsaap1.isChecked()));
        data.put("w2", String.valueOf(whatsaap2.isChecked()));
        return data;
    }
    private void fillInputs(){
        Intent intentData =getIntent();

        if(intentData.hasExtra("data")){
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");

            if(data.containsKey("phone1")){
                phone1.setText(data.get("phone1"));
            }
            if(data.containsKey("phone2")){
                phone2.setText(data.get("phone2"));
            }
            if(data.containsKey("email")){
                email.setText(data.get("email"));
            }
            if(data.containsKey("w1")){
                whatsaap1.setChecked(Boolean.parseBoolean(data.get("w1")));
            }
            if(data.containsKey("w2")){
                whatsaap2.setChecked(Boolean.parseBoolean(data.get("w2")));
            }
        }
    }

    private boolean isDataComplete() {
        if(phone1.getText().toString().isEmpty()){
            Toast.makeText(context, "No ha suministrado un teléfono de contacto", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}