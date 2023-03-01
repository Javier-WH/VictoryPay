package com.fjrh.victorypay.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.fjrh.victorypay.Libraries.DateSelector;
import com.fjrh.victorypay.R;

import java.time.Year;
import java.util.Calendar;

public class Payment2 extends AppCompatActivity {
    private Context context;
    private TextView date;
    private Button back;
    private Button next;
    private EditText operation;
    private EditText mount;
    private TextView price;




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
        back = findViewById(R.id.back_payment2);
        next = findViewById(R.id.next_payment2);
        operation = findViewById(R.id.operarion_payment2);
        mount = findViewById(R.id.mount_payment2);
        price = findViewById(R.id.montPrice_payment2);

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

    }

}