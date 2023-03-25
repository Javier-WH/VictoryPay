package com.fjrh.victorypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjrh.victorypay.Register.Register1;
import com.fjrh.victorypay.dataBases.params.Params;
import com.fjrh.victorypay.dataBases.users.Users;
import com.fjrh.victorypay.payment.Payment0;
import com.fjrh.victorypay.payment.Payment2;
import com.fjrh.victorypay.sync.Sync;

import java.util.HashMap;

public class App extends AppCompatActivity {
    private static Context context;
    private ImageView register;
    private ImageView payment;
    private ImageView tickets;
    private ImageView currencyCalculator;
    private ImageView currency;
    private ImageView search;
    private ImageView prices;
    private ImageView sync;
    private ImageView config;
    private static TextView textOnline;
    private static ImageView icon;
    private static TextView updated;
    private TextView name;
    private static HashMap<String, String> params;
    private HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        context = this;
        params = new Params(context).getParams();

        initComponents();
        initEvents();
        fillElements();

        if(user.containsKey("name")){
            name.setText(user.get("name"));
        }

        /*
        //solo carga la primera vez en cargar la activiti
        if(params.get("mode").equals("online") && params.get("needLoad").equals("true") && params.get("loadAtStart").equals("true")){
            callUpdate();
            new Params(context).insertParam("needLoad", "false");
        }

         */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent i = new Intent(context, MainActivity.class);
        startActivity(i);

    }

    private void initComponents() {
        register = findViewById(R.id.imgRegister);
        payment = findViewById(R.id.imgPayment);
        tickets = findViewById(R.id.imgTickets);
        currencyCalculator = findViewById(R.id.imgCurrencyCalculator);
        currency = findViewById(R.id.imgCurrency);
        search = findViewById(R.id.imgSearch);
        prices = findViewById(R.id.imgPrices);
        sync = findViewById(R.id.imgSync);
        config = findViewById(R.id.imgConfig);
        textOnline = findViewById(R.id.textOnline);
        icon = findViewById(R.id.iconOnline);
        updated = findViewById(R.id.textUpdated);
        name = findViewById(R.id.userNameApp);
        user = new Users(context).getUsers();
    }

    private void initEvents() {
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
                Intent i = new Intent(context, Payment2.class);
                startActivity(i);
            }
        });

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Sync.class);
                startActivity(i);
            }
        });

    }

    public static void fillElements() {
        params = new Params(context).getParams();
        if(params.containsKey("mode")){
            String mode = params.get("mode");
            textOnline.setText(mode);
            textOnline.setTextColor(mode.equals("online") ? Color.parseColor("#12CC03") :Color.parseColor("#CD1B0A"));
            icon.setImageResource(mode.equals("online") ? android.R.drawable.presence_online : android.R.drawable.presence_busy);
            updated.setVisibility(mode.equals("online") ? View.INVISIBLE : View.VISIBLE);
        }
    }

    public void callUpdate(){

        if(params.containsKey("mode")){
            if(params.get("mode").equals("online")){
                Intent i = new Intent(context, Sync.class);
                startActivity(i);
            }

        }
    }

}