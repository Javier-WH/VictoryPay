package com.fjrh.victorypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class App extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        context = getBaseContext();
        /*
        Intent extraData = getIntent();
        String userName = extraData.getStringExtra("userName");
        */

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
                Intent i = new Intent(context, Register.class);
                startActivity(i);
            }
        });



    }



}