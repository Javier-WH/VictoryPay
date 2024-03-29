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
import com.fjrh.victorypay.R;
import com.fjrh.victorypay.conflict.ConflictActivity;
import com.fjrh.victorypay.dataBases.params.Params;
import com.fjrh.victorypay.dataBases.prices.Prices;
import com.fjrh.victorypay.dataBases.students.InsertStuden;
import com.fjrh.victorypay.dataBases.users.Users;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

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
            if (data.containsKey("studentName") && data.containsKey("studentLastName")) {
                name.setText(data.get("studentName") + " " + data.get("studentLastName"));
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
            if (data.containsKey("studentCi") && data.containsKey("studentNation")) {
                ci.setText(data.get("studentNation") + data.get("studentCi"));
            }
            if (data.containsKey("birthDate")) {
                birthDate.setText(data.get("birthDate"));
            }
            if (data.containsKey("age")) {
                age.setText(data.get("age"));
            }
            if (data.containsKey("birthCountry")) {
                birthCountry.setText(data.get("birthCountry"));
            }
            if (data.containsKey("birthEstado")) {
                birthState.setText(data.get("birthEstado"));
            }
            if (data.containsKey("birthMunicipio")) {
                birthMunicipio.setText(data.get("birthMunicipio"));
            }
            if (data.containsKey("birthParroquia")) {
                birthParroquia.setText(data.get("birthParroquia"));
            }
            if (data.containsKey("liveEstate")) {
                liveState.setText(data.get("liveEstate"));
            }
            if (data.containsKey("liveMunicipio")) {
                liveMunicipio.setText(data.get("liveMunicipio"));
            }
            if (data.containsKey("liveParroquia")) {
                liveParroquia.setText(data.get("liveParroquia"));
            }
            if (data.containsKey("address")) {
                address.setText(data.get("address"));
            }
            if (data.containsKey("procedence")) {
                procedence.setText(data.get("procedence"));
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
            if (data.containsKey("alergia")) {
                alergia.setText(data.get("alergia").equals("true") ? "Si" : "No");
            }
            if (data.containsKey("TDAH")) {
                TDAH.setText(data.get("TDAH").equals("true") ? "Si" : "No");
            }
            if (data.containsKey("observations1_4")) {
                observations.setText(data.get("observations1_4"));
            }
            if (data.containsKey("motherName")) {
                motherName.setText(data.get("motherName"));
            }
            if (data.containsKey("motherCi") && data.containsKey("motherNationality")) {
                motherCi.setText(data.get("motherNationality") + data.get("motherCi"));
            }
            if (data.containsKey("fatherName")) {
                fatherName.setText(data.get("fatherName"));
            }
            if (data.containsKey("fatherCi") && data.containsKey("fatherCi")) {
                fatherCi.setText(data.get("fatherNationality") + data.get("fatherCi"));
            }
            if (data.containsKey("motherWork")) {
                motherWork.setText(data.get("motherWork"));
            }
            if (data.containsKey("fatherWork")) {
                fatherWork.setText(data.get("fatherWork"));
            }
            if (data.containsKey("tutorName")) {
                tutorName.setText(data.get("tutorName"));
            }
            if (data.containsKey("tutorCi") && data.containsKey("tutorNationality")) {
                tutorCi.setText(data.get("tutorNationality") + data.get("tutorCi"));
            }
            if (data.containsKey("link3")) {
                tutorLink.setText(data.get("link3"));
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
            if (data.containsKey("account")) {
                operationNumber.setText(data.get("account"));
            }
            if (data.containsKey("date")) {
                operationDate.setText(data.get("date"));
            }
            if (data.containsKey("mount")) {
                mount.setText("USD " + data.get("mount"));

            }
            if(data.containsKey("mountBS")){
                montBs.setText("(Bs "+data.get("mountBS")+")");
            }

            if(data.containsKey("mount") && data.containsKey("savedAbono")) {
                double monto = Double.parseDouble(data.get("mount"));
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

            if(data.containsKey("mount") && data.containsKey("savedAbono")){
                double Tt = Double.parseDouble(data.get("mount")) + Double.parseDouble(data.get("savedAbono"));
                total.setText("USD " + String.valueOf(Tt));
            }

            if(data.containsKey("payMethod")){
                String payMethod = data.get("payMethod");
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
        String pattern = "MM-dd-yyyy hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        params = new Params(context).getParams();
        bar.setVisibility(View.VISIBLE);

        data.put("user", user.get("ci"));
        data.put("timeStamp", date.toString());
        data.put("tutor_code", CodeGenerator.getNewCode('T'));

        if (params.get("mode").equalsIgnoreCase("offline")) {
            insertOfflineStudent();
        } else {
            insetOnlineStudent();
        }
    }

    public void insertOfflineStudent() {
        InsertStuden is = new InsertStuden(context);
        bar.setVisibility(View.INVISIBLE);
        if (is.insert(data)) {
            Toast.makeText(context, "Se ha registrado localmente al estudiante", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context, App.class);
            startActivity(i);
        } else {
            Toast.makeText(context, "Ha ocurrido un problema al registrar al estudiante", Toast.LENGTH_LONG).show();
        }
    }

    public void insetOnlineStudent() {
        String URL = this.URL+"/addStudent";

        // Make new json object and put params in it
        JSONObject jsonParams = new JSONObject(data);


        // Building a request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonParams, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //revisa si existe un conflicto
                if (response.has("ERROR")) {
                    try {
                        String error = response.getString("ERROR");
                        String title = response.getString("title");
                        String message = response.getString("message");
                        String registerID = response.getString("registerID");
                        String code = response.getString("code");
                        JSONObject optionA = response.getJSONObject("optionA");
                        JSONObject optionB = response.getJSONObject("optionB");

                        Intent i = new Intent(context, ConflictActivity.class);
                        i.putExtra("error", error);
                        i.putExtra("title", title);
                        i.putExtra("message", message);
                        i.putExtra("registerID", registerID);
                        i.putExtra("code", code);
                        i.putExtra("nameA",optionA.getString("studentLastName") +" "+  optionA.getString("studentName"));
                        i.putExtra("codeA", optionA.getString("code"));
                        i.putExtra("studentNationA", optionA.getString("studentNation"));
                        i.putExtra("studentCiA", optionA.getString("studentCi"));
                        i.putExtra("gradeA", optionA.getString("grade"));
                        i.putExtra("seccionA", optionA.getString("seccion"));
                        i.putExtra("genderA", optionA.getString("gender"));
                        i.putExtra("ageA", optionA.getString("age"));
                        i.putExtra("nameB",optionB.getString("studentLastName") +" "+  optionB.getString("studentName"));
                        i.putExtra("codeB", optionB.getString("code"));
                        i.putExtra("studentNationB", optionB.getString("studentNation"));
                        i.putExtra("studentCiB", optionB.getString("studentCi"));
                        i.putExtra("gradeB", optionB.getString("grade"));
                        i.putExtra("seccionB", optionB.getString("seccion"));
                        i.putExtra("genderB", optionB.getString("gender"));
                        i.putExtra("ageB", optionB.getString("age"));

                        startActivity(i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    //si no hay conflictos muestra un mensaje
                    Toast.makeText(context, "Se ha actualizado la base de Datos Online", Toast.LENGTH_LONG).show();
                    ///insertar el registro offline
                    addJSONtoData(response);
                    insertOfflineStudent();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "Ocurrió un error";

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    new Params(context).insertParam("mode", "offline");
                    App.fillElements();
                    message = "Se perdió la conexión con el servidor";

                } else if (error instanceof AuthFailureError) {
                    message = "El servidor no puede autenticar al cliente";
                } else if (error instanceof ServerError) {
                    //aqui se manejan los errores con los codigos
                    if (error.networkResponse.statusCode == 412) {
                        message = "Datos incompletos";
                    } else if (error.networkResponse.statusCode == 404) {
                        message = "El usuario no está registrado";
                    }

                } else if (error instanceof NetworkError) {
                    message = "Error con la red de datos";
                } else if (error instanceof ParseError) {
                    message = "Error al intentar convertir los datos";
                }

                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                insertStudent();
                //error.printStackTrace();
            }
        });

        Volley.newRequestQueue(context).add(request);

    }

    public void addJSONtoData(JSONObject response) {
        data.clear();
        Iterator<String> keys = response.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                String value = response.getString(key);
                data.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}