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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.Libraries.DateSelector;
import com.fjrh.victorypay.Libraries.NumberFormater;
import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.abono.Abono;
import com.fjrh.victorypay.dataBases.prices.Prices;
import com.fjrh.victorypay.dataBases.students.FindStudent;

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
    private TextView labelPriceBs;
    private TextView lblAbono;
    private TextView tasa;
    private TextView currency;
    private double dblAbono;
    private double abonadoDouble;
    private Prices prices;
    private double monthlyPrice;
    private HashMap<String, String> listPrices;

    private boolean editFlag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4_1);
        context = this;
        initComponents();
        initEvents();
        fillInputs();
        setAbonoText();
    }

    private void initComponents(){
        prices = new Prices(context);
        listPrices = prices.getPrices();
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
        labelPriceBs = findViewById(R.id.price4_3);
        lblAbono = findViewById(R.id.lblAbono);
        tasa = findViewById(R.id.tasa4_5);
        monthlyPrice = getMonthlyPrice();
        labelPrice.setText(String.valueOf(monthlyPrice));//precio en dolares
        labelPriceBs.setText(NumberFormater.setFormat(monthlyPrice * Double.parseDouble(listPrices.get("Dolar")), 2)); //precio en bolivares
        currency = findViewById(R.id.currency_register4_1);
        tasa.setText( NumberFormater.setFormat(Double.parseDouble( listPrices.get("Dolar")), 2));// tasa de cambio expresada en Bs
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
                if(isDataComplete()) {
                    Intent i = new Intent(context, Register5.class);
                    data.putAll(getData());
                    i.putExtra("data", data);
                    startActivity(i);
                }
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
        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCurrency(currency.getText().toString());
            }
        });

        mount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(!editFlag) {
                   data.put("pago", s.toString());
               }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    String str = s.toString();
                    if (str.contains(".")) {
                        editFlag = true;
                        int index = str.indexOf(".");
                        if (str.length() - index - 1 > 2) {
                            s.replace(index + 3, s.length(), "");
                        }
                    }
                }
                editFlag = false;
            }
        });


    }

    private void toggleCurrency(String curr){

      if(curr.equals("USD")){
          currency.setText("Bs");
          currency.setTextColor(getResources().getColor(R.color.bolivar));
          mount.setTextColor(getResources().getColor(R.color.bolivar));
      }else{
          currency.setText("USD");
          currency.setTextColor(getResources().getColor(R.color.dolar));
          mount.setTextColor(getResources().getColor(R.color.dolar));

      }

    }

    private void enableImputs(Boolean enabled){

        if(enabled){
            account.setVisibility(View.VISIBLE);
            date.setVisibility(View.VISIBLE);
            date.setText("Selecciona una fecha");
            titleB.setVisibility(View.VISIBLE);
            titleA.setVisibility(View.VISIBLE);
            toggleCurrency("USD");
        }else{
            account.setVisibility(View.INVISIBLE);
            date.setVisibility(View.INVISIBLE );
            titleB.setVisibility(View.INVISIBLE);
            titleA.setVisibility(View.INVISIBLE);
            toggleCurrency("Bs");
        }
        account.setText("");
    }



    private void fillInputs() {
        Intent intentData = getIntent();

        if (intentData.hasExtra("data")) {
            data = (HashMap<String, String>) intentData.getSerializableExtra("data");

            if(data.containsKey("cash")){
                if(data.get("cash").equals("1")){
                    cash.setChecked(true);
                }else{
                    bank.setChecked(true);
                }
            }
            if(data.containsKey("operation_number")){
                account.setText(data.get("operation_number"));
            }
            if(data.containsKey("date")){
                date.setText(data.get("date"));
            }
            if(data.containsKey("inscription")){
                mount.setText(String.valueOf(data.get("inscription")));
            }

            //esto se asegura de hacer la conversion a bolivares si se regresa a esta vista

            if(data.containsKey("currency")){
                String storedCurrency = data.get("currency");
                toggleCurrency( storedCurrency.equals("USD") ? "Bs" : "USD");

                if(storedCurrency.equals("Bs")){
                    double storedMount = Double.parseDouble(data.get("inscription"));
                    double storedTas = Double.parseDouble(listPrices.get("Dolar"));
                    double priceInBs = storedMount * storedTas;
                    mount.setText(String.valueOf(priceInBs));
                }

            }


        }
    }

    public HashMap<String, String> getData() {
        double dolarPrice = Double.parseDouble(listPrices.get("Dolar"));
        double pago = 0;

        //la condicional corrige el bug que ocuirre al regresar a la vista anterior.
        if(data.containsKey("pago")){
            pago = Double.parseDouble(data.get("pago"));
        }


        double pagoBolivares = pago * dolarPrice;

        //revisa si el pago es en bolivares, de hacerlo hace la conversion a dolares
        if(!currency.getText().toString().equals("USD")){
            pagoBolivares = pago;
            pago = pago /dolarPrice;
        }

        HashMap<String, String> data = new HashMap<>();


        data.put("cash", cash.isChecked() ? "1" : "2");
        data.put("operation_number",  account.getText().toString().trim());//numero de operación
        data.put("date", date.getText().toString());//fecha depósito
        data.put("inscription", String.valueOf(pago));//pago depósito
        data.put("currency", currency.getText().toString());//tipo de moneda
        //el pago expresado en Bolivares
        data.put("mountBS", String.valueOf(pagoBolivares));
        //agrega el precio del dolar al momento de hacer la inscripcion
        data.put("dolarPrice", String.valueOf(dolarPrice));
        //agrega el precio del mes al momento de hacer la transaccion
        data.put("monthlyPrice", String.valueOf(monthlyPrice));


        /****/
        // obtiene el pago y lo suma al abono guardado en la tabla, esto nos da la cantidad total de dinero del representante
        double abonoPlusPayment = dblAbono + pago;
        //se le resta el precio del mes y guardamos el resto en total
        double total = abonoPlusPayment - monthlyPrice;

        //si el total es positivo, se almacena en la tabla abono
        if(total >= 0){
            data.put("abono", String.valueOf(total));
            data.put("status", "true");

        }
        //si no es positivo, implica que no alcanza para pagar 1 mes, por lo tanto lo que queda es abonado
        else if(abonoPlusPayment > 0){
            data.put("abono", String.valueOf(abonoPlusPayment));
            data.put("status", "false");
        }

        return data;
    }

    private double getMonthlyPrice(){

        double monthPrice = Float.parseFloat(listPrices.get("Inscripción"));

        return monthPrice;
    }

    private void setAbonoText(){

        FindStudent findStudent = new FindStudent(context);
        String tutorCode = findStudent.findStudentTutor(data.get("tutor_ci"));
        if(tutorCode.equals("-1")) {
            lblAbono.setText("0");
            data.put("savedAbono", "0");
            dblAbono = 0;
        }else{

            String savedAbono = String.valueOf(new Abono(context).getAbono(tutorCode));
            lblAbono.setText(savedAbono);
            data.put("savedAbono", savedAbono);
            dblAbono = Double.parseDouble(savedAbono);
        }
    }

    public boolean isDataComplete(){

        if(mount.getText().toString().isEmpty()){
            Toast.makeText(context, "Debe indicar un monto", Toast.LENGTH_LONG).show();
            return false;
        }
        if(bank.isChecked() && account.getText().toString().isEmpty()){
            Toast.makeText(context, "Debe indicar un numero de operación", Toast.LENGTH_LONG).show();
            return false;
        }

        if(bank.isChecked() && date.getText().toString().equalsIgnoreCase("Selecciona una fecha")){
            Toast.makeText(context, "Debe indicar la fecha de la operacion", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}