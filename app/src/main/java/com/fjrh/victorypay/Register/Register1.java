package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Register1 extends AppCompatActivity {
    private Context context;
    private Button next;
    private TextView code;
    private EditText name;
    private EditText lastName;
    private EditText ci;
    private TextView nationality;
    private Spinner seccion;
    private Spinner grade;
    private RadioButton rdbMasculino;
    private RadioButton rdbFemenino;
    private TextView birthDate;
    private TextView studentOld;
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
        code = findViewById(R.id.textCode);

        next = findViewById(R.id.btnNext);
        name = findViewById(R.id.student_name);
        lastName = findViewById(R.id.student_last_name);
        seccion = findViewById(R.id.seccion);
        grade = findViewById(R.id.grade);
        rdbMasculino = findViewById(R.id.rdbMasculino);
        rdbFemenino = findViewById(R.id.rdbFemenino);
        ci = findViewById(R.id.student_ci);
        nationality = findViewById(R.id.student_nationality);
        birthDate = findViewById(R.id.birthDate);
        studentOld = findViewById(R.id.student_old);

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
        //
        seccion.setOnItemSelectedListener(new generateCodeOnSelection());
        grade.setOnItemSelectedListener(new generateCodeOnSelection());
        //
        nationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nationality.getText().toString().equals("V-")){
                    nationality.setText("E-");
                }else{
                    nationality.setText("V-");
                }
            }
        });
        //
        birthDate.setOnClickListener(new View.OnClickListener() {

            Calendar calendar = Calendar.getInstance();

            
            int _day = calendar.get(Calendar.DAY_OF_MONTH);
            int _month = calendar.get(Calendar.MONTH);
            int _year = calendar.get(Calendar.YEAR);


            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {



                        birthDate.setText(dayOfMonth + " / " +  (month + 1) + " / " + year);
                        int age = getAge(dayOfMonth, month + 1, year, _day, _month, _year);
                        studentOld.setText(age+ " años");
                    }
                }, _day, _month, _year );

                //Establece la fecha maxima del calendario, que es la fecha actual
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, 7);
                dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                //establece la fecha minima del calendario

                String minDate="31/12/"+(_year - 20);
                try {
                    Date initDate= new SimpleDateFormat("dd/MM/yyyy").parse(minDate);
                    Calendar caMinDate = Calendar.getInstance();
                    caMinDate.setTime(initDate);
                    caMinDate.add(Calendar.DATE, 7);
                    dpd.getDatePicker().setMinDate(caMinDate.getTimeInMillis());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dpd.show();

            }
        });

    }
//

    private int getAge(int day, int month, int year, int currentDay, int currentMonth, int currentYear){

        int age = currentYear - year;

        if((currentMonth + 1) > month){
            age--;
        }else if( (currentMonth + 1) == month){
            if(currentDay > day){
                age--;
            }
        }

        return age;
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
        data.put("studentCi", ci.getText().toString());
        data.put("studentNation", nationality.getText().toString());

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
            if(data.containsKey("studentCi")){
                ci.setText(data.get("studentCi"));
            }
            if(data.containsKey("studentNation")){
                nationality.setText(data.get("studentNation"));
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
        if(sec.equals("-")){
            sec = "N";
        }
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
            Toast.makeText(context, "No ha suministrado el nombre", Toast.LENGTH_LONG).show();
            return false;
        }
        if(lastName.getText().toString().isEmpty()){
            Toast.makeText(context, "No ha suministrado el apellido", Toast.LENGTH_LONG).show();
            return false;
        }

        if(ci.getText().toString().isEmpty()){
            Toast.makeText(context, "No ha suministrado la cédula", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}