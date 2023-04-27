package com.fjrh.victorypay.pricesPanel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.prices.Prices;

import java.util.HashMap;

public class Prices_Panel extends AppCompatActivity {

    private Button cancel;
    private Button accept;
    private EditText inscription;
    private EditText monthly;
    private EditText dolar;

    private Prices prices;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices_panel);
        context = this;
        initComponents();
        initEvents();
        fillInputs();
    }

    private void initComponents(){

        cancel = findViewById(R.id.btnCancel_prices);
        accept = findViewById(R.id.accept_prices);
        inscription = findViewById(R.id.inscription_prices);
        monthly = findViewById(R.id.monthly_prices);
        dolar = findViewById(R.id.dolar_price);
        prices = new Prices(context);
    }

    private void initEvents(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        accept.setOnClickListener(new SaveData());
    }

    private void fillInputs(){
        HashMap<String, String> pricesList = prices.getPrices();

        if(pricesList.containsKey("Inscripción")){
            inscription.setText(pricesList.get("Inscripción"));
        }
        if(pricesList.containsKey("Mensualidad")){
            monthly.setText(pricesList.get("Mensualidad"));
        }
        if(pricesList.containsKey("Dolar")){
            dolar.setText(pricesList.get("Dolar"));
        }
    }


    class SaveData implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            if(inscription.getText().toString().isEmpty()){
                return;
            }
            if(monthly.getText().toString().isEmpty()){
                return;
            }
            if(dolar.getText().toString().isEmpty()){
                return;
            }

            try {
                prices.insertItem("Inscripción", inscription.getText().toString());
                prices.insertItem("Mensualidad", monthly.getText().toString());
                prices.insertItem("Dolar", dolar.getText().toString());
                finish();
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }

}