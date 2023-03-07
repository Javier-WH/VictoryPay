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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Payment2 extends AppCompatActivity {
    private Context context;
    private TextView date;

    private Button next;
    private EditText operation;
    private EditText mount;
    private TextView dateTitle;
    private TextView operationTitle;
    private RadioButton check;
    private RadioButton cash;
    private CheckBox verified;
    private TextView currency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment2);
        context = this;
        initComponents();
        initEvents();
    }

    private void initComponents(){
        date = findViewById(R.id.date_payment2);

        next = findViewById(R.id.next_payment2);
        operation = findViewById(R.id.operarion_payment2);
        mount = findViewById(R.id.mount_payment2);
        check = findViewById(R.id.check_payment2);
        cash = findViewById(R.id.cash_payment2);
        dateTitle =  findViewById(R.id.dateTitle_payment2);
        operationTitle = findViewById(R.id.operationTitle_payment2);
        verified = findViewById(R.id.chkVerified_payment2);
        currency = findViewById(R.id.currency_payment2);
    }

    private void initEvents(){
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int year = Year.now().getValue();
        date.setOnClickListener(new DateSelector(context, date, "01/01/"+(year -1), day+"/"+month+"/"+year));


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(areDataComplete()){
                    Intent i = new Intent(context, Payment0.class);
                    i.putExtra("paymentData", getPaymentData());
                    startActivity(i);

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

                String curr = isChecked ? "Bs" : "USD";
                togleBsDollar(curr);

            }
        });

        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curr = currency.getText().toString().equals("USD") ? "Bs" : "USD";
                togleBsDollar(curr);
            }
        });

    }

    public void togleBsDollar(String type){
        if(type.equals("Bs")){
            currency.setText("Bs");
            currency.setTextColor(getResources().getColor(R.color.bolivar));
            mount.setTextColor(getResources().getColor(R.color.bolivar));
        }else{
            currency.setText("USD");
            currency.setTextColor(getResources().getColor(R.color.dolar));
            mount.setTextColor(getResources().getColor(R.color.dolar));
        }
    }


    private HashMap<String, String> getPaymentData(){

        HashMap<String, String> paymentData = new HashMap<>();

        paymentData.put("type", check.isChecked() ? "Deposito" : "Efectivo");
        paymentData.put("date", date.getText().toString());
        paymentData.put("operation", operation.getText().toString());
        paymentData.put("verified", verified.isChecked() ? "Si" : "No");
        paymentData.put("mount", mount.getText().toString());
        paymentData.put("currency", currency.getText().toString());
        paymentData.put("dolarPrice", new Prices(context).getPrices().get("Dolar"));

        return paymentData;
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