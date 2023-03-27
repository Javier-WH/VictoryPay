package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fjrh.victorypay.App;
import com.fjrh.victorypay.Libraries.CodeGenerator;
import com.fjrh.victorypay.Libraries.FetchManager;
import com.fjrh.victorypay.Libraries.NumberFormater;
import com.fjrh.victorypay.R;
import com.fjrh.victorypay.conflict.ConflictActivity;
import com.fjrh.victorypay.dataBases.params.Params;
import com.fjrh.victorypay.dataBases.prices.Prices;
import com.fjrh.victorypay.dataBases.register.CreateRegister;
import com.fjrh.victorypay.dataBases.register.Register;
import com.fjrh.victorypay.dataBases.students.InsertStuden;
import com.fjrh.victorypay.dataBases.users.Users;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class Register5 extends AppCompatActivity {
    private static Context context;
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
    private TextView mount;
    private TextView operationNumber;
    private TextView operationDate;
    private TextView code;
    private TextView inscriptionLabel;
    private TextView montBs;
    private TextView abono;
    private TextView total;
    private TextView operationNumberTitle;
    private TextView operationDateTitle;
    private ProgressBar bar;
    private HashMap<String, String> params;
    private HashMap<String, String> data;
    private FetchManager fetchManager;
    private String URL;
    private HashMap<String, String> user;
    private static Register5 registerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register5);
        context = this;
        initComponets();
        initEvents();
        fillInputs();
        registerActivity = this;
    }

    public static void closeActivity(){
        Intent i = new Intent(context, App.class);
        context.startActivity(i);
    }


    public static Register5 getAcrivity(){
        return registerActivity;
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
        mount = findViewById(R.id.mont5);
        inscriptionLabel = findViewById(R.id.inscriptionLabel5);
        operationNumber = findViewById(R.id.bankOperationNumber5);
        operationDate = findViewById(R.id.bankDate5);
        code = findViewById(R.id.code5);
        params = new Params(context).getParams();
        bar = findViewById(R.id.progress5);
        fetchManager = new FetchManager(context);
        URL = fetchManager.getFetchinAddress();
        montBs = findViewById(R.id.montBs);
        abono = findViewById(R.id.abono5);
        total = findViewById(R.id.total5);
        operationNumberTitle = findViewById(R.id.operationNumberTitle5);
        operationDateTitle = findViewById(R.id.operationDateTitle5);
        user = new Users(context).getUsers();
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
                insertStudent();
            }
        });

    }

    private void fillInputs() {
        Intent intentData = getIntent();

        if (intentData.hasExtra("data")) {
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");

            if (data.containsKey("code")) {
                code.setText(data.get("code"));
            }
            if (data.containsKey("name") && data.containsKey("lastName")) {
                name.setText(data.get("name") + " " + data.get("lastName"));
            }
            if (data.containsKey("seccion")) {
                seccion.setText(data.get("seccion"));
            }
            if (data.containsKey("grade")) {
                curso.setText(data.get("grade"));
            }
            if (data.containsKey("gender")) {
                genero.setText(data.get("gender"));
            }
            if (data.containsKey("ci") && data.containsKey("nation")) {
                ci.setText(data.get("nation") + data.get("ci"));
            }
            if (data.containsKey("birthdate")) {
                birthDate.setText(data.get("birthdate"));
            }
            if (data.containsKey("age")) {
                age.setText(data.get("age"));
            }
            if (data.containsKey("birth_country")) {
                birthCountry.setText(data.get("birth_country"));
            }
            if (data.containsKey("birth_state")) {
                birthState.setText(data.get("birth_state"));
            }
            if (data.containsKey("birth_municipio")) {
                birthMunicipio.setText(data.get("birth_municipio"));
            }
            if (data.containsKey("birth_parroquia")) {
                birthParroquia.setText(data.get("birth_parroquia"));
            }
            if (data.containsKey("live_state")) {
                liveState.setText(data.get("live_state"));
            }
            if (data.containsKey("live_municipio")) {
                liveMunicipio.setText(data.get("live_municipio"));
            }
            if (data.containsKey("live_parroquia")) {
                liveParroquia.setText(data.get("live_parroquia"));
            }
            if (data.containsKey("address")) {
                address.setText(data.get("address"));
            }
            if (data.containsKey("procedence_school")) {
                procedence.setText(data.get("procedence_school"));
            }
            if (data.containsKey("diabetes")) {
                diabetes.setText(data.get("diabetes").equals("true") ? "Si" : "No");
            }
            if (data.containsKey("hipertension")) {
                hipertension.setText(data.get("hipertension").equals("true") ? "Si" : "No");
            }
            if (data.containsKey("dislexia")) {
                dislexia.setText(data.get("dislexia").equals("true") ? "Si" : "No");
            }

            if (data.containsKey("daltonismo")) {
                daltonismo.setText(data.get("daltonismo").equals("true") ? "Si" : "No");
            }
            if (data.containsKey("epilepsia")) {
                epilepsia.setText(data.get("epilepsia").equals("true") ? "Si" : "No");
            }
            if (data.containsKey("asma")) {
                asma.setText(data.get("asma").equals("true") ? "Si" : "No");
            }
            if (data.containsKey("alergias")) {
                alergia.setText(data.get("alergias").equals("true") ? "Si" : "No");
            }
            if (data.containsKey("TDAH")) {
                TDAH.setText(data.get("TDAH").equals("true") ? "Si" : "No");
            }
            if (data.containsKey("observations")) {
                observations.setText(data.get("observations"));
            }
            if (data.containsKey("mother_name")) {
                motherName.setText(data.get("mother_name"));
            }
            if (data.containsKey("mother_ci") && data.containsKey("mother_nation")) {
                motherCi.setText(data.get("mother_nation") + data.get("mother_ci"));
            }
            if (data.containsKey("father_name")) {
                fatherName.setText(data.get("father_name"));
            }
            if (data.containsKey("father_ci") && data.containsKey("father_nation")) {
                fatherCi.setText(data.get("father_nation") + data.get("father_ci"));
            }
            if (data.containsKey("mother_work")) {
                motherWork.setText(data.get("mother_work"));
            }
            if (data.containsKey("father_work")) {
                fatherWork.setText(data.get("father_work"));
            }
            if (data.containsKey("tutor_name")) {
                tutorName.setText(data.get("tutor_name"));
            }
            if (data.containsKey("tutor_ci") && data.containsKey("tutor_nation")) {
                tutorCi.setText(data.get("tutor_nation") + data.get("tutor_ci"));
            }
            if (data.containsKey("tutor_link")) {
                tutorLink.setText(data.get("tutor_link"));
            }
            if (data.containsKey("phone1")) {
                phoneA.setText(data.get("phone1"));
            }
            if (data.containsKey("phone2")) {
                phoneB.setText(data.get("phone2"));
            }
            if (data.containsKey("email")) {
                email.setText(data.get("email"));
            }
            if (data.containsKey("operation_number")) {
                operationNumber.setText(data.get("operation_number"));
            }
            if (data.containsKey("date")) {
                operationDate.setText(data.get("date"));
            }
            if (data.containsKey("inscription")) {
                double cuantity = Double.parseDouble(data.get("inscription"));
                mount.setText("USD "+NumberFormater.setFormat(cuantity, 2));


            }
            if(data.containsKey("mountBS")){
                double cuantity = Double.parseDouble(data.get("mountBS"));
                montBs.setText("(Bs "+NumberFormater.setFormat(cuantity, 2)+")");
            }

            if(data.containsKey("inscription") && data.containsKey("savedAbono")) {
                double monto = Double.parseDouble(data.get("inscription"));
                double savedAbono = Double.parseDouble(data.get("savedAbono"));

                if ((monto + savedAbono) < getMonthlyPrice()) {
                    inscriptionLabel.setText("Preinscripción (Abono)");
                } else {
                    inscriptionLabel.setText("Inscripción");
                }
            }

            if(data.containsKey("savedAbono")){
                abono.setText("USD " + data.get("savedAbono"));
            }

            if(data.containsKey("inscription") && data.containsKey("savedAbono")){
                double Tt = Double.parseDouble(data.get("inscription")) + Double.parseDouble(data.get("savedAbono"));
                total.setText("USD " +  NumberFormater.setFormat(Tt, 2));
            }

            if(data.containsKey("cash")){
                String payMethod = data.get("cash");
                operationDateTitle.setVisibility( payMethod.equals("1") ? View.GONE : View.VISIBLE);
                operationNumberTitle.setVisibility( payMethod.equals("1") ? View.GONE : View.VISIBLE);
                operationNumber.setVisibility( payMethod.equals("1") ? View.GONE : View.VISIBLE);
                operationDate.setVisibility( payMethod.equals("1") ? View.GONE : View.VISIBLE);
            }

        }


    }

    private float getMonthlyPrice() {
        Prices prices = new Prices(context);
        HashMap<String, String> list = prices.getPrices();
        float monthPrice = Float.parseFloat(list.get("Inscripción"));
        return monthPrice;
    }
//////

    public void insertStudent() {
        params = new Params(context).getParams();
        bar.setVisibility(View.VISIBLE);
        Register register = new CreateRegister(context, data).getRegister();

        InsertStuden is = new InsertStuden(context);
        is.insert(register);

    }


}