package com.fjrh.victorypay.conflict;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.fjrh.victorypay.Libraries.FetchManager;
import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.params.Params;
import com.fjrh.victorypay.dataBases.students.InsertStuden;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class ConflictActivity extends AppCompatActivity {

    private Context context;
    private Button back;
    private Button next;
    private TextView title;
    private TextView message;
    private TextView codeA;
    private TextView codeB;
    private TextView ciA;
    private TextView ciB;
    private TextView nationA;
    private TextView nationB;
    private TextView nameA;
    private TextView nameB;
    private TextView gradeA;
    private TextView gradeB;
    private TextView seccionA;
    private TextView seccionB;
    private TextView genderA;
    private TextView genderB;
    private TextView ageA;
    private TextView ageB;
    private ConstraintLayout optionA;
    private ConstraintLayout optionB;
    private String option = "";
    private String registerID = "0";
    private String code = "0";
    private String URL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conflict);
        context = this;
       initComponents();
       initEvents();
       fillViews();
    }

    private void initComponents(){
        back = findViewById(R.id.btnBack_conlifct);
        next = findViewById(R.id.btnNext_conflic);
        title = findViewById(R.id.title_conflict);
        message = findViewById(R.id.message_conflict);
        codeA = findViewById(R.id.codeA);
        codeB = findViewById(R.id.codeB);
        ciA = findViewById(R.id.ciA);
        ciB = findViewById(R.id.ciB);
        nationA = findViewById(R.id.nationalityA);
        nationB = findViewById(R.id.nationalityB);
        nameA = findViewById(R.id.nameA);
        nameB = findViewById(R.id.nameB);
        gradeA = findViewById(R.id.gradeA);
        gradeB = findViewById(R.id.gradeB);
        seccionA = findViewById(R.id.seccionA);
        seccionB = findViewById(R.id.seccionB);
        genderA = findViewById(R.id.genderA);
        genderB = findViewById(R.id.genderB);
        ageA = findViewById(R.id.ageA);
        ageB = findViewById(R.id.ageB);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        URL = new FetchManager(context).getFetchinAddress();

    }

    private void initEvents(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(option.isEmpty()){
                    Toast.makeText(context, "Debe seleccionar una opción", Toast.LENGTH_LONG).show();
                    return;
                }
                insetOnlineStudent();


            }
        });

        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselect();
                option = "A";
                optionA.setBackgroundColor(getResources().getColor(R.color.purple_500));
            }
        });
        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselect();
                option = "B";
                optionB.setBackgroundColor(getResources().getColor(R.color.purple_500));
            }
        });

    }



    private void unselect(){
        optionA.setBackgroundColor(getResources().getColor(R.color.unselected));
        optionB.setBackgroundColor(getResources().getColor(R.color.unselected));
        option = "";
    }

    private void fillViews(){
        Intent intentData = getIntent();

        if(intentData.hasExtra("registerID")){
            registerID = intentData.getStringExtra("registerID");
        }
        if(intentData.hasExtra("code")){
            code = intentData.getStringExtra("code");
        }
        if(intentData.hasExtra("title")){
            title.setText(intentData.getStringExtra("title"));
        }
        if(intentData.hasExtra("message")){
            message.setText(intentData.getStringExtra("message"));
        }
        if(intentData.hasExtra("nameA")){
            nameA.setText(intentData.getStringExtra("nameA"));
        }
        if(intentData.hasExtra("codeA")){
            codeA.setText(intentData.getStringExtra("codeA"));
        }
        if(intentData.hasExtra("studentNationA")){
            nationA.setText(intentData.getStringExtra("studentNationA"));
        }
        if(intentData.hasExtra("studentCiA")){
            ciA.setText(intentData.getStringExtra("studentCiA"));
        }
        if(intentData.hasExtra("gradeA")){
            gradeA.setText(intentData.getStringExtra("gradeA"));
        }
        if(intentData.hasExtra("seccionA")){
            seccionA.setText(intentData.getStringExtra("seccionA"));
        }
        if(intentData.hasExtra("genderA")){
            genderA.setText(intentData.getStringExtra("genderA"));
        }
        if(intentData.hasExtra("ageA")){
            ageA.setText(intentData.getStringExtra("ageA"));
        }

        if(intentData.hasExtra("nameB")){
            nameB.setText(intentData.getStringExtra("nameB"));
        }
        if(intentData.hasExtra("codeB")){
            codeB.setText(intentData.getStringExtra("codeB"));
        }
        if(intentData.hasExtra("studentNationB")){
            nationB.setText(intentData.getStringExtra("studentNationB"));
        }
        if(intentData.hasExtra("studentCiB")){
            ciB.setText(intentData.getStringExtra("studentCiB"));
        }
        if(intentData.hasExtra("gradeB")){
            gradeB.setText(intentData.getStringExtra("gradeB"));
        }
        if(intentData.hasExtra("seccionB")){
            seccionB.setText(intentData.getStringExtra("seccionB"));
        }
        if(intentData.hasExtra("genderB")){
            genderB.setText(intentData.getStringExtra("genderB"));
        }
        if(intentData.hasExtra("ageB")){
            ageB.setText(intentData.getStringExtra("ageB"));
        }

    }


    public void insetOnlineStudent() {
        String URL = this.URL+"/resolveInsertion";

        HashMap<String, String> data = new HashMap<>();
        data.put("registerID", registerID);
        data.put("option", option);

        // Make new json object and put params in it
        JSONObject jsonParams = new JSONObject(data);


        // Building a request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonParams, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                if(response.has("ERROR")){
                    try {
                        Toast.makeText(context, response.getString("ERROR"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{

                    Toast.makeText(context, "Se ha actualizado la base de Datos Online", Toast.LENGTH_LONG).show();

                    InsertStuden is = new InsertStuden(context);
                  /*  if (is.insert(parseResponse(response))) {
                        Toast.makeText(context, "Se ha registrado localmente al estudiante", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(context, App.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(context, "Ha ocurrido un problema al registrar al estudiante", Toast.LENGTH_LONG).show();
                    }
*/
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(context).add(request);

    }//

    public HashMap<String, String> parseResponse(JSONObject response) {
        HashMap<String, String> data = new HashMap<>();
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

        return data;
    }

}