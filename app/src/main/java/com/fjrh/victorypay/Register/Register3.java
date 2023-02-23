package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.R;

import java.util.HashMap;

public class Register3 extends AppCompatActivity {

    private Context context;
    private Button back;
    private Button next;
    private RadioButton rdbMother;
    private RadioButton rdBFather;
    private RadioButton rdbOther;
    private TextView labelName;
    private TextView labelCi;
    private EditText tutorName;
    private EditText tutorCi;
    private TextView tutorNationality;
    private Boolean tutorXeno;
    private EditText link;
    private TextView labelLink;
    private HashMap<String, String> data;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        context = this;
        initComponets();
        initEvents();
        enableTutorData(false);
        fillInputs();
    }

    private void initComponets(){
        next = findViewById(R.id.btnNext3);
        back = findViewById(R.id.btnBack3);
        rdbMother = findViewById(R.id.rdbMother);
        rdBFather = findViewById(R.id.rdbFather);
        rdbOther = findViewById(R.id.rdbOther);
        labelName = findViewById(R.id.labelTutorName);
        labelCi = findViewById(R.id.labelTutorCi);
        tutorName = findViewById(R.id.textTutorName);
        tutorCi = findViewById(R.id.textTutorCi);
        tutorNationality = findViewById(R.id.textTutorNationality);
        tutorXeno = false;
        link = findViewById(R.id.link3);
        labelLink = findViewById(R.id.labelLinkk3);


    }

    private void enableTutorData(boolean enabled){
        labelName.setEnabled(enabled);
        labelCi.setEnabled(enabled);
        tutorName.setEnabled(enabled);
        tutorCi.setEnabled(enabled);
        tutorNationality.setEnabled(enabled);
        tutorXeno = false;
        tutorNationality.setText("V-");
        tutorName.setText("");
        tutorCi.setText("");
        tutorName.setHint(enabled ? "Nombre del representante" : "");
        tutorCi.setHint(enabled ? "Cédula del representante" : "");
        labelLink.setEnabled(enabled);
        link.setEnabled(enabled);
        link.setHint(enabled ? "Vínculo" : "");

    }

    private void initEvents(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register2.class);
                data.putAll(getData());
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDataComplete()){
                    Intent i = new Intent(context, Register4.class);
                    data.putAll(getData());
                    i.putExtra("data", data);
                    startActivity(i);
                }

            }
        });

        rdbOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enableTutorData(isChecked);
            }
        });

        tutorNationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTutorNationality();
            }
        });

    }

    private void changeTutorNationality(){
        if(tutorXeno){
            tutorXeno = false;
            tutorNationality.setText("V-");
        }else{
            tutorXeno = true;
            tutorNationality.setText("E-");
        }
    }

    public HashMap<String, String> getData(){
        HashMap<String, String> data = new HashMap<>();

        if(rdbMother.isChecked()){
            data.put("tutorSelected", "1");
            data.put("tutorName", this.data.get("motherName"));
            data.put("tutorCi", this.data.get("motherCi"));
            data.put("tutorNationality", this.data.get("motherNationality"));
            data.put("link3", "Es la Madre");
        }else if(rdBFather.isChecked()){
            data.put("tutorSelected", "2");
            data.put("tutorName", this.data.get("fatherName"));
            data.put("tutorCi", this.data.get("fatherCi"));
            data.put("tutorNationality", this.data.get("fatherNationality"));
            data.put("link3", "Es el Padre");
        }else if(rdbOther.isChecked()){
            data.put("tutorSelected", "3");
            data.put("tutorName", tutorName.getText().toString().trim());
            data.put("tutorCi", tutorCi.getText().toString().trim());
            data.put("tutorNationality", tutorNationality.getText().toString());
            data.put("link3", link.getText().toString().trim());
        }

        return data;
    }

    private void fillInputs(){
        Intent intentData =getIntent();

        if(intentData.hasExtra("data")){
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");

            if(data.containsKey("tutorSelected")){

                if (data.get("tutorSelected").equals("1")){
                    rdbMother.setChecked(true);
                    tutorName.setText("");
                    tutorCi.setText("");
                    tutorNationality.setText("V-");
                }else if(data.get("tutorSelected").equals("2")){
                    rdBFather.setChecked(true);
                    tutorName.setText("");
                    tutorCi.setText("");
                    tutorNationality.setText("V-");
                }else if(data.get("tutorSelected").equals("3")){
                    rdbOther.setChecked(true);
                    tutorName.setText(data.get("tutorName"));
                    tutorCi.setText(data.get("tutorCi"));
                    tutorNationality.setText(data.get("tutorNationality"));
                    link.setText(data.get("link3"));
                }
            }


        }
    }

    private boolean isDataComplete() {

        if (rdbOther.isChecked()) {
            if(tutorName.getText().toString().isEmpty()){
                Toast.makeText(context, "No ha suministrado un nombre para el tutor", Toast.LENGTH_LONG).show();
                return false;
            }

            if(tutorCi.getText().toString().isEmpty()){
                Toast.makeText(context, "No ha suministrado una cédula para el tutor", Toast.LENGTH_LONG).show();
                return false;
            }

            if(link.getText().toString().isEmpty()){
                Toast.makeText(context, "No ha suministrado un vinculo para el tutor", Toast.LENGTH_LONG).show();
                return false;
            }

        }

        return true;
    }


}