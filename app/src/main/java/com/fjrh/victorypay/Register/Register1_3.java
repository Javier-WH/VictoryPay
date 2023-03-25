package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.schools.School;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Register1_3 extends AppCompatActivity {
    private Context context;
    private Button back;
    private Button next;
    private EditText address;
    private EditText procedence;
    private HashMap<String, String> data;
    private Boolean loading = false;
    private ScrollView suggestions;
    private LinearLayout suggestionsLayout;

    private School schoolDb;
    private ArrayList<String> schoolsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1_3);
        context = this;
        initComponents();
        initEvents();
        fillInputs();

    }

    private void initComponents(){
        back = findViewById(R.id.btnBack1_3);
        next = findViewById(R.id.btnNext1_3);
        address = findViewById(R.id.address1_3);
        procedence = findViewById(R.id.procedencia1_3);
        suggestions = findViewById(R.id.suggestions1_3);
        suggestionsLayout = findViewById(R.id.sugestionLayout1_3);
        schoolDb = new School(context);
        schoolsList = schoolDb.getList();
    }

    private void initEvents(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register1_2.class);
                data.putAll(getData());
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDataComplete()) {
                    if(isSchoolRegistered(procedence.getText().toString())) {
                        Intent i = new Intent(context, Register1_4.class);
                        data.putAll(getData());
                        i.putExtra("data", data);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(context, Register1_3_1.class);
                        data.putAll(getData());
                        i.putExtra("data", data);
                        startActivity(i);
                    }
                }
            }
        });

        procedence.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(loading){
                    suggestionsLayout.removeAllViews();
                    loading = false;
                }else {
                    refresSuggestionDialog(procedence.getText().toString());
                }
            }
        });

        procedence.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                suggestionsLayout.removeAllViews();
            }
        });

    }

    private void fillInputs() {
        Intent intentData = getIntent();

        if (intentData.hasExtra("data")) {
            loading = true;
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");

            if(data.containsKey("address")){
                address.setText(data.get("address"));
            }
            if(data.containsKey("procedence_school")){
                procedence.setText(data.get("procedence_school"));
            }

        }
    }
    public HashMap<String, String> getData() {

        HashMap<String, String> data = new HashMap<>();
        data.put("address", address.getText().toString().trim());
        data.put("procedence_school", procedence.getText().toString().trim());
        return data;
    }

    private void addAsuggest(String text){
        TextView suggest = new TextView(context);
        suggest.setText(text);
        suggest.setTextSize(15f);
        suggest.setTextColor(getResources().getColor(R.color.white));
        suggest.setPadding(0, 10, 0, 0);
        //suggest.setTypeface(suggest.getTypeface(), Typeface.BOLD_ITALIC);

        suggest.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        suggestionsLayout.addView(suggest);

        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procedence.setText(text);
                suggestionsLayout.removeAllViews();
            }
        });
    }

    private void refresSuggestionDialog(String filter){
        suggestionsLayout.removeAllViews();
        if(filter.length() > 0){
            for (int i = 0 ; i < schoolsList.size(); i++){

                if(schoolsList.get(i).toLowerCase().contains(filter.toLowerCase())){
                    addAsuggest(schoolsList.get(i));
                }
            }
        }
    }

    private boolean isSchoolRegistered(String school){
        return schoolsList.contains(school);
    }



    private boolean isDataComplete(){

        if(address.getText().toString().isEmpty()){
            Toast.makeText(context, "No ha suministrado una dirección para el estudiante", Toast.LENGTH_LONG).show();
            return false;
        }

        if(procedence.getText().toString().isEmpty()){
            Toast.makeText(context, "No ha suministrado un colegio de procedencia", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }


}