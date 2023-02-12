package com.fjrh.victorypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fjrh.victorypay.Register.Register1;

public class App extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        context = getBaseContext();

        initComponents();


    }

    private void initComponents(){
        ImageView register = findViewById(R.id.imgRegister);
        ImageView payment = findViewById(R.id.imgPayment);
        ImageView tickets = findViewById(R.id.imgTickets);
        ImageView currencyCalculator = findViewById(R.id.imgCurrencyCalculator);
        ImageView currency = findViewById(R.id.imgCurrency);
        ImageView search = findViewById(R.id.imgSearch);
        ImageView prices = findViewById(R.id.imgPrices);
        ImageView sync = findViewById(R.id.imgSync);
        ImageView config = findViewById(R.id.imgConfig);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register1.class);
                startActivity(i);
            }
        });



    }



}