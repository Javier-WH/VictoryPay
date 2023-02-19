package com.fjrh.victorypay.Register;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import com.fjrh.victorypay.Libraries.DateSelector;
import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.prices.Prices;

import java.time.Year;
import java.util.Calendar;
import java.util.HashMap;

public class Register4_1 extends AppCompatActivity {
    Context context;
    private HashMap<String, String> data;
    private Button back;
    private Button next;
    private RadioButton cash;
    private RadioButton bank;
    private EditText account;
    private EditText mount;
    private TextView date;
    private TextView titleA;
    private TextView titleB;
    private TextView labelPrice;


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
        mount = findViewById(R.id.mont4_1);
        date = findViewById(R.id.date1_4);
        titleA = findViewById(R.id.titleA4_1);
        titleB = findViewById(R.id.titleB4_1);
        labelPrice = findViewById(R.id.price4_1);
        labelPrice.setText(getMonthlyPrice() + " USD");
    }

    private void initEvents(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register4.class);
                data.putAll(getData());
                i.putExtra("data", data);
                startActivity(i);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Register5.class);
                data.putAll(getData());
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int year = Year.now().getValue();
        date.setOnClickListener(new DateSelector(context, date, "01/01/"+(year -2), day+"/"+month+"/"+year));

        //

        bank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enableImputs(isChecked);
            }
        });


    }

    private void enableImputs(Boolean enabled){

        if(enabled){
            account.setVisibility(View.VISIBLE);
            date.setVisibility(View.VISIBLE);
            date.setText("Selecciona una fecha");
            titleB.setVisibility(View.VISIBLE);
            titleA.setVisibility(View.VISIBLE);
        }else{
            account.setVisibility(View.INVISIBLE);
            date.setVisibility(View.INVISIBLE );
            titleB.setVisibility(View.INVISIBLE);
            titleA.setVisibility(View.INVISIBLE);
        }
        account.setText("");
    }



    private void fillInputs() {
        Intent intentData = getIntent();

        if (intentData.hasExtra("data")) {
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");
            if(data.containsKey("payMethod")){
                if(data.get("payMethod").equals("1")){
                    cash.setChecked(true);
                }else{
                    bank.setChecked(true);
                }
            }
            if(data.containsKey("account")){
                account.setText(data.get("account"));
            }
            if(data.containsKey("date")){
                date.setText(data.get("date"));
            }
            if(data.containsKey("mount")){
                mount.setText(data.get("mount"));
            }


        }
    }

    public HashMap<String, String> getData() {
        HashMap<String, String> data = new HashMap<>();
        data.put("payMethod", cash.isChecked() ? "1" : "2");
        data.put("account", account.getText().toString());
        data.put("date", date.getText().toString());
        data.put("mount", mount.getText().toString());
        return data;
    }

    private float getMonthlyPrice(){

        Prices prices = new Prices(context);

        HashMap<String, String> list = prices.getPrices();

        float monthPrice = Float.parseFloat(list.get("Inscripción"));

        return monthPrice;
    }
}