package com.fjrh.victorypay.Currency;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjrh.victorypay.Libraries.APIRequest;
import com.fjrh.victorypay.Libraries.Alert;
import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.prices.Prices;

import org.json.JSONException;
import java.io.IOException;
import java.util.HashMap;

public class Currency extends AppCompatActivity {

    private Context context;
    private HashMap<String, Double> dolarPriceList;

    private Handler handler;
    private TextView dolarTodayPrice;
    private TextView sicad1Price;
    private TextView sicad2Price;
    private TextView cucutaPrice;
    private Button aceptar;
    private Prices prices;
    private boolean activeEvents = false;
    private EditText rate;

    private ImageView DolarTodayLogo;
    private ImageView Sicad1Logo;
    private ImageView Sicad2Logo;
    private ImageView CucutaLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        context = this;
        handler = new Handler(Looper.getMainLooper());
        initComponents();
        fillComponents();
        initEvents();
        fillInputs();
    }

    private void initComponents(){

        dolarTodayPrice = findViewById(R.id.dolar_today_price);
        sicad1Price = findViewById(R.id.sicad1_price);
        sicad2Price = findViewById(R.id.sicad2_price);
        cucutaPrice = findViewById(R.id.cucuta_price);
        aceptar = findViewById(R.id.btnNext_currency);
        prices = new Prices(context);
        rate = findViewById(R.id.selectedRate);

        DolarTodayLogo = findViewById(R.id.DolarTodayLogo);
        Sicad1Logo = findViewById(R.id.Sicad1Logo);
        Sicad2Logo = findViewById(R.id.Sicad2Logo);;
        CucutaLogo = findViewById(R.id.CucutaLogo);;
    }

    private void  initEvents(){
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rate.getText().toString().equalsIgnoreCase(prices.getPrices().get("Dolar"))){
                    setDolarPrice(rate.getText().toString());
                    Alert.showMessage(context, "Tasa de cambio actualizada", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                }else{
                    finish();
                }
            }
        });

        DolarTodayLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeEvents){
                    rate.setText(dolarTodayPrice.getText().toString());
                }
            }
        });
        Sicad1Logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeEvents){
                    rate.setText(sicad1Price.getText().toString());
                }
            }
        });
        Sicad2Logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeEvents){
                    rate.setText(sicad2Price.getText().toString());
                }
            }
        });
        CucutaLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeEvents){
                    rate.setText(cucutaPrice.getText().toString());
                }
            }
        });


    }

    private void fillInputs(){
        String dolarPrice = prices.getPrices().get("Dolar");
        rate.setText(dolarPrice);
    }

    public void setDolarTodayPrice(String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                dolarTodayPrice.setText(data);

            }
        });
    }


    public void setSicad1Price(String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                sicad1Price.setText(data);

            }
        });
    }

    public void setSicad2Price(String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                sicad2Price.setText(data);

            }
        });
    }

    public void setCucutaPrice(String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                cucutaPrice.setText(data);

            }
        });
    }

    private void setDolarPrice(String price){
        prices.insertItem("Dolar", price);

    }


    private void fillComponents(){

        Thread auxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                activeEvents = false;
                HashMap<String, Double> dolarPriceList = null;
                try {
                    dolarPriceList = new APIRequest().getUSDValues();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(dolarPriceList.containsKey("dolartoday")){
                    setDolarTodayPrice(String.valueOf(dolarPriceList.get("dolartoday")));
                }

                if(dolarPriceList.containsKey("sicad1")){
                    setSicad1Price(String.valueOf(dolarPriceList.get("sicad1")));
                }

                if(dolarPriceList.containsKey("sicad2")){
                    setSicad2Price(String.valueOf(dolarPriceList.get("sicad2")));
                }

                if(dolarPriceList.containsKey("efectivo_cucuta")){
                    setCucutaPrice(String.valueOf(dolarPriceList.get("efectivo_cucuta")));
                }

                activeEvents = true;
            }
        });
        auxThread.start();

    }






}