package com.fjrh.victorypay.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.Libraries.DateSelector;
import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.prices.Prices;
import com.fjrh.victorypay.dataBases.students.FindStudent;

import org.json.JSONException;

import java.time.Year;
import java.util.Calendar;
import java.util.HashMap;

public class Payment2 extends AppCompatActivity {
    private Context context;
    private TextView date;
    private Button back;
    private Button next;
    private EditText operation;
    private EditText mount;
    private TextView price;
    private TextView dateTitle;
    private TextView operationTitle;
    private RadioButton check;
    private RadioButton cash;
    private HashMap <String, String> prices;
    private  HashMap<String, String> studentData;
    private int monthPrice;

    private TextView ci;
    private TextView code;
    private TextView name;
    private TextView grade;
    private TextView seccion;
    private TextView message1;
    private TextView message2;
    private CheckBox verified;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment2);
        context = this;
        initComponents();
        initEvents();
        fillComponents();
    }

    private void initComponents(){
        date = findViewById(R.id.date_payment2);
        back = findViewById(R.id.back_payment2);
        next = findViewById(R.id.next_payment2);
        operation = findViewById(R.id.operarion_payment2);
        mount = findViewById(R.id.mount_payment2);
        price = findViewById(R.id.montPrice_payment2);
        check = findViewById(R.id.check_payment2);
        cash = findViewById(R.id.cash_payment2);
        dateTitle =  findViewById(R.id.dateTitle_payment2);
        operationTitle = findViewById(R.id.operationTitle_payment2);
        prices = new Prices(context).getPrices();
        monthPrice = Integer.parseInt(prices.get("Inscripción"));
        ci = findViewById(R.id.ci_payment2);
        grade = findViewById(R.id.grade_payment2);
        code = findViewById(R.id.code_payment2);
        name = findViewById(R.id.name_payument2);
        seccion = findViewById(R.id.seccion_payment2);
        message1 = findViewById(R.id.messagePayment1);
        message2 = findViewById(R.id.mount_payment2);
        verified = findViewById(R.id.chkVerified_payment2);

    }

    private void initEvents(){
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int year = Year.now().getValue();
        date.setOnClickListener(new DateSelector(context, date, "01/01/"+(year -1), day+"/"+month+"/"+year));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(areDataComplete()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("ADVERTENCIA");
                    builder.setMessage("¿Desea confirmar el pago de " + mount.getText().toString() + "USD para " + name.getText().toString() + " " + ci.getText().toString() + "?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Confirmar Pago", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HashMap<String, String> data = new HashMap<>();

                            Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
                        }
                    });

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                date.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                operation.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                dateTitle.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                operationTitle.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                verified.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                if(!isChecked){
                    date.setText("Seleccione una fecha");
                    operation.setText("");
                }
            }
        });

        mount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String m = mount.getText().toString();
                if(m.isEmpty() || Integer.parseInt(m) == 0){
                    message1.setText("No paga nada");
                    return;
                }

                int monto = Integer.parseInt(m);
                int countMount = monto / monthPrice;
                int module = monto % monthPrice;

                if(countMount <= 0){
                    message1.setText("Abona " + monto + " USD");
                }else{
                    String meses = countMount == 1 ? "mes" : "meses";
                    if(module > 0){
                        message1.setText("Paga " + countMount + " "+ meses + " y abona "+ module + " USD");
                    }else{
                        message1.setText("Paga " + countMount + " "+meses);
                    }
                }
            }
        });
    }

    private void fillComponents(){
        Intent i = getIntent();
        if(i.hasExtra("code")){
            String _code = i.getStringExtra("code");
            studentData = new FindStudent(context).findStudentByCode(_code);

            ci.setText("C.I. " + studentData.get("nation")+studentData.get("ci"));
            code.setText("Código: "+ studentData.get("code"));
            name.setText(studentData.get("name") + " " + studentData.get("lastName"));
            grade.setText(studentData.get("grade"));
            seccion.setText(studentData.get("seccion"));

        }
        price.setText(monthPrice + " USD");

    }

    private boolean areDataComplete(){

        if(mount.getText().toString().isEmpty()){
            Toast.makeText(context, "No ha suministrado ningun monto", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(check.isChecked()){

            if(date.getText().toString().equals("Seleccione una fecha")){
                Toast.makeText(context, "El depósito debe tener una fecha ", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(operation.getText().toString().isEmpty()){
                Toast.makeText(context, "No ha suministrado ningun numero de operación", Toast.LENGTH_SHORT).show();
                return false;
            }


        }

        return true;
    }


}