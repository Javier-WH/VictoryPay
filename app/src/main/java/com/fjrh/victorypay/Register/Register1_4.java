package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.fjrh.victorypay.R;

import java.util.HashMap;

public class Register1_4 extends AppCompatActivity {

    private Context context;
    private Button back;
    private Button next;
    private Switch diabetes;
    private Switch hiperTension;
    private Switch dislexia;
    private Switch daltonismo;
    private Switch epilepsia;
    private Switch asma;
    private Switch alergia;
    private Switch TDAH;
    private EditText observations1_4;
    private HashMap<String, String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1_4);
        context = this;

        initComponents();
        initEvents();
        fillInputs();
    }

    private void initComponents(){
        back = findViewById(R.id.btnBack1_4);
        next = findViewById(R.id.btnNext1_4);
        diabetes = findViewById(R.id.diabetes1_4);
        hiperTension = findViewById(R.id.hipertension1_4);
        dislexia = findViewById(R.id.dislexia1_4);
        daltonismo = findViewById(R.id.daltonismo1_4);
        epilepsia = findViewById(R.id.epilepsia1_4);
        asma = findViewById(R.id.asma1_4);
        alergia = findViewById(R.id.alergia1_4);
        TDAH = findViewById(R.id.TDAH1_4);
        observations1_4 = findViewById(R.id.observations1_4);
    }

    private void initEvents(){
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
                Intent i = new Intent(context, Register2.class);
                data.putAll(getData());
                i.putExtra("data", data);
                startActivity(i);
            }
        });

    }

    private void fillInputs() {
        Intent intentData = getIntent();

        if (intentData.hasExtra("data")) {
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");

            if(data.containsKey("diabetes")){
                diabetes.setChecked( Boolean.parseBoolean(data.get("diabetes")));
            }
            if(data.containsKey("hipertension")){
                hiperTension.setChecked( Boolean.parseBoolean(data.get("hipertension")));
            }
            if(data.containsKey("dislexia")){
                dislexia.setChecked( Boolean.parseBoolean(data.get("dislexia")));
            }
            if(data.containsKey("daltonismo")){
                daltonismo.setChecked( Boolean.parseBoolean(data.get("daltonismo")));
            }
            if(data.containsKey("epilepsia")){
                epilepsia.setChecked( Boolean.parseBoolean(data.get("epilepsia")));
            }
            if(data.containsKey("asma")){
                asma.setChecked( Boolean.parseBoolean(data.get("asma")));
            }
            if(data.containsKey("alergia")){
                alergia.setChecked( Boolean.parseBoolean(data.get("alergia")));
            }
            if(data.containsKey("TDAH")){
                TDAH.setChecked( Boolean.parseBoolean(data.get("TDAH")));
            }
            if(data.containsKey("observations1_4")){
                observations1_4.setText(data.get("observations1_4"));
            }

        }
    }

    public HashMap<String, String> getData() {
        HashMap<String, String> data = new HashMap<>();
        data.put("diabetes", String.valueOf(diabetes.isChecked()));
        data.put("hipertension", String.valueOf(hiperTension.isChecked()));
        data.put("dislexia", String.valueOf(dislexia.isChecked()));
        data.put("daltonismo", String.valueOf(daltonismo.isChecked()));
        data.put("epilepsia", String.valueOf(epilepsia.isChecked()));
        data.put("asma", String.valueOf(asma.isChecked()));
        data.put("alergia", String.valueOf(alergia.isChecked()));
        data.put("TDAH", String.valueOf(TDAH.isChecked()));
        data.put("observations1_4", observations1_4.getText().toString().trim());
        return data;
    }

}