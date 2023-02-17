package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.Bank;

import java.util.HashMap;

public class Register4_1 extends AppCompatActivity {
    Context context;
    private HashMap<String, String> data;
    private Button back;
    private Button next;
    private RadioButton cash;
    private RadioButton bank;
    private EditText account;
    private EditText preisciption;
    private EditText inscription;
    private EditText bankName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4_1);
        context = this;
        initComponents();
        initEvents();
        fillInputs();
    }

    private void initComponents(){
        back = findViewById(R.id.btnBack4_1);
        next = findViewById(R.id.btnNext4_1);
        cash = findViewById(R.id.cash4_1);
        bank = findViewById(R.id.bank4_1);
        account = findViewById(R.id.backAccount4_1);
        preisciption = findViewById(R.id.preInscription4_1);
        inscription = findViewById(R.id.inscription4_1);
        bankName = findViewById(R.id.bankName4_1);
    }

    private void initEvents(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register4.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register5.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String accountNumber = account.getText().toString();
                if(accountNumber.length() > 4){
                    accountNumber = accountNumber.substring(0,4);
                }
                bankName.setText(Bank.getBankName(accountNumber));
            }
        });

    }


    private void fillInputs() {
        Intent intentData = getIntent();

        if (intentData.hasExtra("data")) {
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");
        }
    }

    public HashMap<String, String> getData() {
        HashMap<String, String> data = new HashMap<>();
        //data.put("motherName", motherName.getText().toString());
        return data;
    }
}