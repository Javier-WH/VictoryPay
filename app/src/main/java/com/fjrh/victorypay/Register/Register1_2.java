package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.fjrh.victorypay.Libraries.ReadJson;
import com.fjrh.victorypay.Libraries.Venezuela;
import com.fjrh.victorypay.MainActivity;
import com.fjrh.victorypay.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Register1_2 extends AppCompatActivity {

    private Context context;
    private Button back;
    private Button next;
    private Spinner birthCountry;
    private Spinner birthEstate;
    private Spinner birthMunicipio;
    private Spinner birthParroquia;
    private Spinner birthCity;
    private Spinner liveEstate;
    private Spinner liveMunicipio;
    private Spinner liveParroquia;
    private Spinner liveCity;
    private HashMap<String, String> data;
    private Venezuela venezuela;
    private ArrayAdapter<String> stateAdapter;
    private ArrayAdapter<String> fillerAdapter;
    private String[] indexs;
    private boolean loading = false;
    private int muniCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1_2);
        context = this;
        venezuela = MainActivity.getVenezuela();
        stateAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.estados));
        fillerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.extranjero));
        initComponents();
        initEvents();
        fillElements();
        ///

    }

    private void initComponents() {
        back = findViewById(R.id.btnBack1_2);
        next = findViewById(R.id.btnNext1_2);
        birthCountry = findViewById(R.id.country1_2);
        birthEstate = findViewById(R.id.birth_estado1_2);
        birthMunicipio = findViewById(R.id.birth_municipio1_2);
        birthParroquia = findViewById(R.id.birth_parroquia1_2);
        liveEstate = findViewById(R.id.live_estado1_2);
        liveMunicipio = findViewById(R.id.live_municipio1_2);
        liveParroquia = findViewById(R.id.live_parroquia1_2);

        initBirthSpiners();
        liveEstate.setSelection(10);


    }

    private void initEvents() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register1.class);
                data.putAll(getData());
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register1_3.class);
                data.putAll(getData());
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        //

        birthCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initBirthSpiners();

                if(loading){
                    birthEstate.setSelection(Integer.parseInt(indexs[1]));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        birthEstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                birthMunicipio.setAdapter(getMunicipios(birthCountry, birthEstate));

                if(birthEstate.getSelectedItem().toString().equals("Guárico")){
                    birthMunicipio.setSelection(7);
                }

                if(loading){
                    birthMunicipio.setSelection(Integer.parseInt(indexs[2]));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        birthMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                birthParroquia.setAdapter(getParroquias(birthCountry, birthEstate, birthMunicipio));
                if(loading){
                    birthParroquia.setSelection(Integer.parseInt(indexs[3]));
                    muniCounter++;
                    if(muniCounter >= 2){
                        loading = false;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        liveEstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                liveMunicipio.setAdapter(getMunicipios(null, liveEstate));

                if(liveEstate.getSelectedItem().toString().equals("Guárico")){
                    liveMunicipio.setSelection(7);
                }

                if(loading){
                    liveMunicipio.setSelection(Integer.parseInt(indexs[5]));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        liveMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                liveParroquia.setAdapter(getParroquias(null, liveEstate, liveMunicipio));

                if(loading){
                    liveParroquia.setSelection(Integer.parseInt(indexs[6]));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public HashMap<String, String> getData() {
        HashMap<String, String> data = new HashMap<>();
        data.put("birthCountry", birthCountry.getSelectedItem().toString());
        data.put("birthEstado", birthEstate.getSelectedItem().toString());
        data.put("birthMunicipio", birthMunicipio.getSelectedItem().toString());
        data.put("birthParroquia", birthParroquia.getSelectedItem().toString());

        data.put("liveEstate", liveEstate.getSelectedItem().toString());
        data.put("liveMunicipio", liveMunicipio.getSelectedItem().toString());
        data.put("liveParroquia", liveParroquia.getSelectedItem().toString());

        //los indices de los spiners
        String spinersIndex = "" +
                birthCountry.getSelectedItemPosition() + "XXX" +
                birthEstate.getSelectedItemPosition() + "XXX" +
                birthMunicipio.getSelectedItemPosition() + "XXX" +
                birthParroquia.getSelectedItemPosition() + "XXX" +
                liveEstate.getSelectedItemPosition() + "XXX" +
                liveMunicipio.getSelectedItemPosition() + "XXX" +
                liveParroquia.getSelectedItemPosition();


        data.put("spinerIndex", spinersIndex);

        return data;
    }


    private void fillElements() {
        Intent intentData = getIntent();

        if (intentData.hasExtra("data")) {
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");
            if (data.containsKey("spinerIndex")) {
                loading = true;
                indexs = data.get("spinerIndex").split("XXX");
                birthCountry.setSelection(Integer.parseInt(indexs[0]));
                liveEstate.setSelection(Integer.parseInt(indexs[4]));

            }
        }
    }

    private void initBirthSpiners() {
        birthEstate.setAdapter(getEstado(birthCountry));

        if (birthCountry.getSelectedItem().toString().equals("Venezuela")) {
            birthEstate.setSelection(10);
        }
    }



    private ArrayAdapter<String> getEstado(Spinner country) {
        if (!country.getSelectedItem().toString().equals("Venezuela")) {
            return fillerAdapter;
        }
        return stateAdapter;

    }

    private ArrayAdapter<String> getMunicipios(Spinner country, Spinner estate) {

        if (country != null && !country.getSelectedItem().toString().equals("Venezuela")) {
            return fillerAdapter;
        }
        String estado = estate.getSelectedItem().toString();
        try {
            List<String> arrayMunicipios = venezuela.getMunicipios(estado);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayMunicipios);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            return adapter;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    private ArrayAdapter<String> getParroquias(Spinner country, Spinner estate, Spinner mun) {
        if (country != null && !country.getSelectedItem().toString().equals("Venezuela")) {
            return fillerAdapter;
        }

        String estado = estate.getSelectedItem().toString();
        String municipio = mun.getSelectedItem().toString();

        try {
            List<String> arrayParroquias = venezuela.getParroquias(estado, municipio);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayParroquias);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            return adapter;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}