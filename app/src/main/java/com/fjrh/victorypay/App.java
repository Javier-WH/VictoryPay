package com.fjrh.victorypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fjrh.victorypay.Register.Register1;
import com.fjrh.victorypay.payment.Payment1;

public class App extends AppCompatActivity {
    private Context context;
    private  ImageView register;
    private ImageView payment;
    private ImageView tickets ;
    private ImageView currencyCalculator ;
    private ImageView currency ;
    private ImageView search ;
    private ImageView prices ;
    private ImageView sync ;
    private ImageView config ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        context = this;

        initComponents();
        initEvents();

    }

    private void initComponents(){
        register = findViewById(R.id.imgRegister);
        payment = findViewById(R.id.imgPayment);
        tickets = findViewById(R.id.imgTickets);
        currencyCalculator = findViewById(R.id.imgCurrencyCalculator);
        currency = findViewById(R.id.imgCurrency);
        search = findViewById(R.id.imgSearch);
        prices = findViewById(R.id.imgPrices);
        sync = findViewById(R.id.imgSync);
        config = findViewById(R.id.imgConfig);

    }

    private void initEvents(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register1.class);
                startActivity(i);
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Payment1.class);
                startActivity(i);
            }
        });


    }



}