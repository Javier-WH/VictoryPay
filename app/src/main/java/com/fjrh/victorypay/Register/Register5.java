package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private TextView name;
    private TextView ci;
    private TextView seccion;
    private TextView curso;
    private TextView genero;
    private TextView birthDate;
    private TextView age;
    private TextView birthCountry;
    private TextView birthState;
    private TextView birthMunicipio;
    private TextView birthParroquia;
    private TextView liveState;
    private TextView liveMunicipio;
    private TextView liveParroquia;
    private TextView address;
    private TextView procedence;
    private TextView diabetes;
    private TextView hipertension;
    private TextView dislexia;
    private TextView daltonismo;
    private TextView epilepsia;
    private TextView asma;
    private TextView alergia;
    private TextView TDAH;
    private TextView observations;
    private TextView motherName;
    private TextView motherCi;
    private TextView motherWork;
    private TextView fatherName;
    private TextView fatherCi;
    private TextView fatherWork;
    private TextView tutorName;
    private TextView tutorCi;
    private TextView tutorLink;
    private TextView phoneA;
    private TextView phoneB;
    private TextView email;
    private TextView preinscription;
    private TextView inscription;
    private TextView operationNumber;
    private TextView operationDate;
    private TextView code;

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
        name = findViewById(R.id.name5);
        ci = findViewById(R.id.ci5);
        seccion = findViewById(R.id.seccion5);
        curso = findViewById(R.id.curso5);
        genero = findViewById(R.id.gender5);
        birthDate = findViewById(R.id.birthDate5);
        age = findViewById(R.id.age5);
        birthCountry = findViewById(R.id.birthCountry5);
        birthState = findViewById(R.id.birthState5);
        birthMunicipio = findViewById(R.id.birthMunicipio5);
        birthParroquia = findViewById(R.id.birthParroquia5);
        liveState = findViewById(R.id.liveState5);
        liveMunicipio = findViewById(R.id.liveMunicipio5);
        liveParroquia = findViewById(R.id.liveParroquia5);
        address = findViewById(R.id.address5);
        procedence = findViewById(R.id.procedence5);
        diabetes = findViewById(R.id.diabetes5);
        hipertension = findViewById(R.id.hipertension5);
        dislexia = findViewById(R.id.dislexia5);
        daltonismo = findViewById(R.id.daltonismo5);
        epilepsia = findViewById(R.id.epilepsia5);
        asma = findViewById(R.id.asma5);
        alergia = findViewById(R.id.alergia5);
        TDAH = findViewById(R.id.TDHA5);
        observations = findViewById(R.id.observations5);
        motherName = findViewById(R.id.motherName5);
        motherCi = findViewById(R.id.motherCi5);
        motherWork = findViewById(R.id.motherWork5);
        fatherName = findViewById(R.id.fatherName5);
        fatherCi = findViewById(R.id.fatherCi5);
        fatherWork = findViewById(R.id.fatherWork5);
        tutorName = findViewById(R.id.tutorName5);
        tutorCi = findViewById(R.id.tutorCi5);
        tutorLink = findViewById(R.id.tutorLink5);
        phoneA = findViewById(R.id.phoneA5);
        phoneB = findViewById(R.id.phoneb5);
        email = findViewById(R.id.email5);
        preinscription = findViewById(R.id.preinscripcion5);
        inscription = findViewById(R.id.inscripcion5);
        operationNumber = findViewById(R.id.bankOperationNumber5);
        operationDate  = findViewById(R.id.bankDate5);
        code = findViewById(R.id.code5);
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

            if(data.containsKey("code")){
                code.setText(data.get("code"));
            }
            if(data.containsKey("studentName") && data.containsKey("studentLastName")){
                name.setText(data.get("studentName")+" "+ data.get("studentLastName"));
            }
            if(data.containsKey("seccion")){
                seccion.setText(data.get("seccion"));
            }
            if(data.containsKey("grade")){
                curso.setText(data.get("grade"));
            }
            if(data.containsKey("gender")){
                genero.setText(data.get("gender"));
            }
            if(data.containsKey("studentCi") && data.containsKey("studentNation")){
                ci.setText(data.get("studentNation")+data.get("studentCi"));
            }
            if(data.containsKey("birthDate")){
                birthDate.setText(data.get("birthDate"));
            }
            if(data.containsKey("age")){
                age.setText(data.get("age"));
            }
            if(data.containsKey("birthCountry")){
                birthCountry.setText(data.get("birthCountry"));
            }
            if(data.containsKey("birthEstado")){
                birthState.setText(data.get("birthEstado"));
            }
            if(data.containsKey("birthMunicipio")){
                birthMunicipio.setText(data.get("birthMunicipio"));
            }
            if(data.containsKey("birthParroquia")){
                birthParroquia.setText(data.get("birthParroquia"));
            }
            if(data.containsKey("liveEstate")){
                liveState.setText(data.get("liveEstate"));
            }
            if(data.containsKey("liveMunicipio")){
                liveMunicipio.setText(data.get("liveMunicipio"));
            }
            if(data.containsKey("liveParroquia")){
                liveParroquia.setText(data.get("liveParroquia"));
            }
            if(data.containsKey("address")){
                address.setText(data.get("address"));
            }
            if(data.containsKey("procedence")){
                procedence.setText(data.get("procedence"));
            }
            if(data.containsKey("diabetes")){
                diabetes.setText(data.get("diabetes").equals("true") ? "Si" : "No");
            }
            if(data.containsKey("hipertension")){
                hipertension.setText(data.get("hipertension").equals("true") ? "Si" : "No");
            }
            if(data.containsKey("dislexia")){
                dislexia.setText(data.get("dislexia").equals("true") ? "Si" : "No");
            }
            if(data.containsKey("dislexia")){
                dislexia.setText(data.get("dislexia").equals("true") ? "Si" : "No");
            }
            if(data.containsKey("daltonismo")){
                daltonismo.setText(data.get("daltonismo").equals("true") ? "Si" : "No");
            }
            if(data.containsKey("epilepsia")){
                epilepsia.setText(data.get("epilepsia").equals("true") ? "Si" : "No");
            }
            if(data.containsKey("asma")){
                asma.setText(data.get("asma").equals("true") ? "Si" : "No");
            }
            if(data.containsKey("alergia")){
                alergia.setText(data.get("alergia").equals("true") ? "Si" : "No");
            }
            if(data.containsKey("TDAH")){
                TDAH.setText(data.get("TDAH").equals("true") ? "Si" : "No");
            }
            if(data.containsKey("observations1_4")){
                observations.setText(data.get("observations1_4"));
            }
            if(data.containsKey("motherName")){
                motherName.setText(data.get("motherName"));
            }
            if(data.containsKey("motherCi") && data.containsKey("motherNationality")){
                motherCi.setText(data.get("motherNationality") + data.get("motherCi"));
            }
            if(data.containsKey("fatherName")){
                fatherName.setText(data.get("fatherName"));
            }
            if(data.containsKey("fatherCi") && data.containsKey("fatherCi")){
                fatherCi.setText(data.get("fatherNationality")+data.get("fatherCi"));
            }
            if(data.containsKey("motherWork")){
                motherWork.setText(data.get("motherWork"));
            }
            if(data.containsKey("fatherWork")){
                fatherWork.setText(data.get("fatherWork"));
            }
            if(data.containsKey("tutorName")){
                tutorName.setText(data.get("tutorName"));
            }
            if(data.containsKey("tutorCi") && data.containsKey("tutorNationality")){
                tutorCi.setText(data.get("tutorNationality") + data.get("tutorCi"));
            }
            if(data.containsKey("link3")){
                tutorLink.setText(data.get("link3"));
            }
            if(data.containsKey("phone1")){
                phoneA.setText(data.get("phone1"));
            }
            if(data.containsKey("phone2")){
                phoneB.setText(data.get("phone2"));
            }
            if(data.containsKey("email")){
                email.setText(data.get("email"));
            }
            if(data.containsKey("account")){
                operationNumber.setText(data.get("account"));
            }
            if(data.containsKey("date")){
                operationDate.setText(data.get("date"));
            }
            if(data.containsKey("preisciption")){
                preinscription.setText(data.get("preisciption")+" USD");
            }
            if(data.containsKey("inscription")){
                inscription.setText(data.get("inscription")+ "USD");
            }

        }
    }




}