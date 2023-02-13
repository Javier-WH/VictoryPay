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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.R;

import java.util.HashMap;
import java.util.Random;

public class Register1 extends AppCompatActivity {
    private Context context;
    private Button next;
    private TextView code;
    private EditText name;
    private EditText lastName;
    private Spinner seccion;
    private Spinner grade;
    private RadioButton rdbMasculino;
    private RadioButton rdbFemenino;
    private HashMap<String, String> data = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        context = this;
        initComponents();
        initEvents();
        fillInputs();


    }

    private void initComponents(){
        next = findViewById(R.id.btnNext);
        code = findViewById(R.id.textCode);
        name = findViewById(R.id.student_name);
        lastName = findViewById(R.id.student_last_name);
        seccion = findViewById(R.id.seccion);
        grade = findViewById(R.id.grade);
        rdbMasculino = findViewById(R.id.rdbMasculino);
        rdbFemenino = findViewById(R.id.rdbFemenino);

        code.setText(generateCode());

    }

    private void initEvents(){

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDataComplete()){
                    Intent i = new Intent(context, Register2.class);
                    data.putAll(getData());
                    i.putExtra("data", data);
                    startActivity(i);
                }
            }
        });
        seccion.setOnItemSelectedListener(new generateCodeOnSelection());
        grade.setOnItemSelectedListener(new generateCodeOnSelection());
    }

    //
    private HashMap<String, String> getData(){
        HashMap<String, String> data = new HashMap<>();

        data.put("code", code.getText().toString());
        data.put("studentName", name.getText().toString());
        data.put("studentLastName", lastName.getText().toString());
        data.put("seccion", seccion.getSelectedItem().toString());
        data.put("grade", "" + grade.getSelectedItem());
        data.put("gender", rdbMasculino.isChecked() ? "Masculino" : "Femenino");

        return data;
    }

    private void fillInputs(){
        Intent intentData =getIntent();

        if(intentData.hasExtra("data")){
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");

            if(data.containsKey("studentName")){
                name.setText(data.get("studentName"));
            }
            if(data.containsKey("studentLastName")){
                lastName.setText(data.get("studentLastName"));
            }
            if(data.containsKey("seccion")){
                seccion.setSelection(((ArrayAdapter) seccion.getAdapter()).getPosition(data.get("seccion")));
            }
            if(data.containsKey("grade")){
                grade.setSelection(((ArrayAdapter) grade.getAdapter()).getPosition(data.get("grade")));
            }
            if(data.containsKey("gender")){
                if(data.get("gender").equals("Femenino")){
                    rdbFemenino.setChecked(true);
                }
            }
            if(data.containsKey("code")){
                code.setText(data.get("code"));
            }

        }
    }

    private String generateCode(){
        Random random = new Random();
        int randomNumber = random.nextInt(999999999 - 100000000) + 100000000;
        String sec = seccion.getSelectedItem().toString().replace("Seccion ", "");
        char gra = grade.getSelectedItem().toString().charAt(0);
        String code = sec + gra +"-"+ randomNumber;
        return code;
    }

    class generateCodeOnSelection implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            code.setText(generateCode());
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private boolean isDataComplete(){

        if(name.getText().toString().isEmpty()){
            Toast.makeText(context, "No ha suministrado un nombre", Toast.LENGTH_LONG).show();
            return false;
        }
        if(lastName.getText().toString().isEmpty()){
            Toast.makeText(context, "No ha suministrado un apellido", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}