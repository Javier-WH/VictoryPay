package com.fjrh.victorypay.payment;

import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.prices.Prices;

import java.util.ArrayList;
import java.util.HashMap;

public class Payment0 extends AppCompatActivity {
    private Context context;
    private Button addStudent;
    private Button next;
    private Button back;

    private TextView stdCount_text;

    private ConstraintLayout student1Layout;
    private ConstraintLayout student2Layout;
    private ConstraintLayout student3Layout;
    private ConstraintLayout student4Layout;
    private ConstraintLayout student5Layout;
    private ConstraintLayout student6Layout;
    private ConstraintLayout student7Layout;
    private ConstraintLayout student8Layout;
    private ConstraintLayout student9Layout;
    private ConstraintLayout student10Layout;

    private TextView name1;
    private TextView name2;
    private TextView name3;
    private TextView name4;
    private TextView name5;
    private TextView name6;
    private TextView name7;
    private TextView name8;
    private TextView name9;
    private TextView name10;

    private TextView code1;
    private TextView code2;
    private TextView code3;
    private TextView code4;
    private TextView code5;
    private TextView code6;
    private TextView code7;
    private TextView code8;
    private TextView code9;
    private TextView code10;

    private TextView ci1;
    private TextView ci2;
    private TextView ci3;
    private TextView ci4;
    private TextView ci5;
    private TextView ci6;
    private TextView ci7;
    private TextView ci8;
    private TextView ci9;
    private TextView ci10;

    private TextView seccion1;
    private TextView seccion2;
    private TextView seccion3;
    private TextView seccion4;
    private TextView seccion5;
    private TextView seccion6;
    private TextView seccion7;
    private TextView seccion8;
    private TextView seccion9;
    private TextView seccion10;

    private TextView grade1;
    private TextView grade2;
    private TextView grade3;
    private TextView grade4;
    private TextView grade5;
    private TextView grade6;
    private TextView grade7;
    private TextView grade8;
    private TextView grade9;
    private TextView grade10;

    private ImageView delete1;
    private ImageView delete2;
    private ImageView delete3;
    private ImageView delete4;
    private ImageView delete5;
    private ImageView delete6;
    private ImageView delete7;
    private ImageView delete8;
    private ImageView delete9;
    private ImageView delete10;

    private TextView less1, less2, less3, less4, less5, less6, less7, less8, less9, less10;
    private TextView plus1, plus2, plus3, plus4, plus5, plus6, plus7, plus8, plus9, plus10;
    private EditText payment1, payment2, payment3, payment4, payment5, payment6, payment7, payment8, payment9, payment10;
    private ArrayList<EditText> payments;

    private ArrayList<HashMap<String, String>> studentList;
    private HashMap<String, String> paymentData;
    private HashMap<String, String> prices;
    private int montPriceInt = 0;
    private double saldoInt = 0;
    private TextView montPrice;
    private TextView saldo;
    private TextView saldoBs;
    private TextView tasa;

    private ConstraintLayout[] containers;
    private TextView[] names;
    private TextView[] cis;
    private TextView[] codes;
    private TextView[] seccions;
    private TextView[] grades;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment0);
        context = this;
        initComponents();
        initEvents();
        addStudents();
        checkStudents();
        distrubutePayment();
    }

    private void checkStudents() {
        if (studentList.size() <= 0) {
            openAddStudent();
        }
    }

    //llena automaticamente la mensualidad de los almunos al iniciar
    private void distrubutePayment() {
        if (studentList.size() <= 0) {
            return;
        }
        int value = 1;
        double saldo = Double.parseDouble(this.saldo.getText().toString());

        while (saldo >= montPriceInt) {
            for (int i = 0; i < studentList.size(); i++) {
                if (saldo >= montPriceInt) {
                    studentList.get(i).put("payment", String.valueOf(value));
                }
                saldo -= montPriceInt;
            }
            value++;

        }
        refreshContainer();
    }

    private void openAddStudent() {
        Intent i = new Intent(context, Payment1.class);
        i.putExtra("studentList", studentList);
        i.putExtra("paymentData", paymentData);
        startActivity(i);
    }

    private void initComponents() {
        addStudent = findViewById(R.id.btbAddStudent_payment0);
        next = findViewById(R.id.btnNext_payment0);
        back = findViewById(R.id.btnBack_payment0);
        stdCount_text = findViewById(R.id.textStdNumber);
        student1Layout = findViewById(R.id.student1_payment0);
        student2Layout = findViewById(R.id.student2_payment0);
        student3Layout = findViewById(R.id.student3_payment0);
        student4Layout = findViewById(R.id.student4_payment0);
        student5Layout = findViewById(R.id.student5_payment0);
        student6Layout = findViewById(R.id.student6_payment0);
        student7Layout = findViewById(R.id.student7_payment0);
        student8Layout = findViewById(R.id.student8_payment0);
        student9Layout = findViewById(R.id.student9_payment0);
        student10Layout = findViewById(R.id.student10_payment0);


        name1 = findViewById(R.id.name1_payment0);
        name2 = findViewById(R.id.name2_payment0);
        name3 = findViewById(R.id.name3_payment0);
        name4 = findViewById(R.id.name4_payment0);
        name5 = findViewById(R.id.name5_payment0);
        name6 = findViewById(R.id.name6_payment0);
        name7 = findViewById(R.id.name7_payment0);
        name8 = findViewById(R.id.name8_payment0);
        name9 = findViewById(R.id.name9_payment0);
        name10 = findViewById(R.id.name10_payment0);
        code1 = findViewById(R.id.code1_payment0);
        code2 = findViewById(R.id.code2_payment0);
        code3 = findViewById(R.id.code3_payment0);
        code4 = findViewById(R.id.code4_payment0);
        code5 = findViewById(R.id.code5_payment0);
        code6 = findViewById(R.id.code6_payment0);
        code7 = findViewById(R.id.code7_payment0);
        code8 = findViewById(R.id.code8_payment0);
        code9 = findViewById(R.id.code9_payment0);
        code10 = findViewById(R.id.code10_payment0);
        ci1 = findViewById(R.id.ci1_payment0);
        ci2 = findViewById(R.id.ci2_payment0);
        ci3 = findViewById(R.id.ci3_payment0);
        ci4 = findViewById(R.id.ci4_payment0);
        ci5 = findViewById(R.id.ci5_payment0);
        ci6 = findViewById(R.id.ci6_payment0);
        ci7 = findViewById(R.id.ci7_payment0);
        ci8 = findViewById(R.id.ci8_payment0);
        ci9 = findViewById(R.id.ci9_payment0);
        ci10 = findViewById(R.id.ci10_payment0);
        seccion1 = findViewById(R.id.seccion1_payment0);
        seccion2 = findViewById(R.id.seccion2_payment0);
        seccion3 = findViewById(R.id.seccion3_payment0);
        seccion4 = findViewById(R.id.seccion4_payment0);
        seccion5 = findViewById(R.id.seccion5_payment0);
        seccion6 = findViewById(R.id.seccion6_payment0);
        seccion7 = findViewById(R.id.seccion7_payment0);
        seccion8 = findViewById(R.id.seccion8_payment0);
        seccion9 = findViewById(R.id.seccion9_payment0);
        seccion10 = findViewById(R.id.seccion10_payment0);
        grade1 = findViewById(R.id.grade1_payment0);
        grade2 = findViewById(R.id.grade2_payment0);
        grade3 = findViewById(R.id.grade3_payment0);
        grade4 = findViewById(R.id.grade4_payment0);
        grade5 = findViewById(R.id.grade5_payment0);
        grade6 = findViewById(R.id.grade6_payment0);
        grade7 = findViewById(R.id.grade7_payment0);
        grade8 = findViewById(R.id.grade8_payment0);
        grade9 = findViewById(R.id.grade9_payment0);
        grade10 = findViewById(R.id.grade10_payment0);
        delete1 = findViewById(R.id.remove1_payment0);
        delete2 = findViewById(R.id.remove2_payment0);
        delete3 = findViewById(R.id.remove3_payment0);
        delete4 = findViewById(R.id.remove4_payment0);
        delete5 = findViewById(R.id.remove5_payment0);
        delete6 = findViewById(R.id.remove6_payment0);
        delete7 = findViewById(R.id.remove7_payment0);
        delete8 = findViewById(R.id.remove8_payment0);
        delete9 = findViewById(R.id.remove9_payment0);
        delete10 = findViewById(R.id.remove10_payment0);
        less1 = findViewById(R.id.less1);
        less2 = findViewById(R.id.less2);
        less3 = findViewById(R.id.less3);
        less4 = findViewById(R.id.less4);
        less5 = findViewById(R.id.less5);
        less6 = findViewById(R.id.less6);
        less7 = findViewById(R.id.less7);
        less8 = findViewById(R.id.less8);
        less9 = findViewById(R.id.less9);
        less10 = findViewById(R.id.less10);

        plus1 = findViewById(R.id.plus1);
        plus2 = findViewById(R.id.plus2);
        plus3 = findViewById(R.id.plus3);
        plus4 = findViewById(R.id.plus4);
        plus5 = findViewById(R.id.plus5);
        plus6 = findViewById(R.id.plus6);
        plus7 = findViewById(R.id.plus7);
        plus8 = findViewById(R.id.plus8);
        plus9 = findViewById(R.id.plus9);
        plus10 = findViewById(R.id.plus10);

        payment1 = findViewById(R.id.payment1);
        payment2 = findViewById(R.id.payment2);
        payment3 = findViewById(R.id.payment3);
        payment4 = findViewById(R.id.payment4);
        payment5 = findViewById(R.id.payment5);
        payment6 = findViewById(R.id.payment6);
        payment7 = findViewById(R.id.payment7);
        payment8 = findViewById(R.id.payment8);
        payment9 = findViewById(R.id.payment9);
        payment10 = findViewById(R.id.payment10);
        payments = new ArrayList<EditText>() {{
            add(payment1);
            add(payment2);
            add(payment3);
            add(payment4);
            add(payment5);
            add(payment6);
            add(payment7);
            add(payment8);
            add(payment9);
            add(payment10);
        }};
        saldoBs = findViewById(R.id.saldoBs);
        tasa = findViewById(R.id.tasa);

        paymentData = (HashMap<String, String>) getIntent().getSerializableExtra("paymentData");
        prices = new Prices(context).getPrices();

        montPrice = findViewById(R.id.montPrice);
        saldo = findViewById(R.id.saldo);

        studentList = new ArrayList<>();

        saldoInt = getMount();
        saldo.setText( String.valueOf(saldoInt));


        montPriceInt = Integer.parseInt(prices.get("Mensualidad"));
        montPrice.setText(String.valueOf(montPriceInt));

        containers = new ConstraintLayout[]{student1Layout, student2Layout, student3Layout, student4Layout, student5Layout, student6Layout, student7Layout, student8Layout, student9Layout, student10Layout};
        names = new TextView[]{name1, name2, name3, name4, name5, name6, name7, name8, name9, name10};
        cis = new TextView[]{ci1, ci2, ci3, ci4, ci5, ci6, ci7, ci8, ci9, ci10};
        codes = new TextView[]{code1, code2, code3, code4, code5, code6, code7, code8, code9, code10};
        seccions = new TextView[]{seccion1, seccion2, seccion3, seccion4, seccion5, seccion6, seccion7, seccion8, seccion9, seccion10};
        grades = new TextView[]{grade1, grade2, grade3, grade4, grade5, grade6, grade7, grade8, grade9, grade10};


    }
    private double getMount(){
        String currency = paymentData.get("currency");
        double dolarPrice = Double.parseDouble(paymentData.get("dolarPrice"));
        double mount = Double.parseDouble(paymentData.get("mount"));

        tasa.setText(String.valueOf( Math.floor(dolarPrice * 100) / 100 ));

        if(currency.equals("USD")){

            //el precio en bolivares que se muestra al usuario
            saldoBs.setText(String.valueOf( Math.floor((mount * dolarPrice) * 100) / 100 ));
            return mount;
        }

        saldoBs.setText(String.valueOf(mount));
        //regresa siempre el precio en dolares
        return Math.floor((mount / dolarPrice) * 100) / 100;
    }

    private void initEvents() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentList.size() > 0) {

                } else {
                    Toast.makeText(context, "Debe agregar al menos un estudiante", Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Payment2.class);
                startActivity(i);
            }
        });

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddStudent();

            }

        });

        delete1.setOnClickListener(new hideStudent(1));
        delete2.setOnClickListener(new hideStudent(2));
        delete3.setOnClickListener(new hideStudent(3));
        delete4.setOnClickListener(new hideStudent(4));
        delete5.setOnClickListener(new hideStudent(5));
        delete6.setOnClickListener(new hideStudent(6));
        delete7.setOnClickListener(new hideStudent(7));
        delete8.setOnClickListener(new hideStudent(8));
        delete9.setOnClickListener(new hideStudent(9));
        delete10.setOnClickListener(new hideStudent(10));

        less1.setOnClickListener(new LessAdd(1, 1));
        less2.setOnClickListener(new LessAdd(2, 1));
        less3.setOnClickListener(new LessAdd(3, 1));
        less4.setOnClickListener(new LessAdd(4, 1));
        less5.setOnClickListener(new LessAdd(5, 1));
        less6.setOnClickListener(new LessAdd(6, 1));
        less7.setOnClickListener(new LessAdd(7, 1));
        less8.setOnClickListener(new LessAdd(8, 1));
        less9.setOnClickListener(new LessAdd(9, 1));
        less10.setOnClickListener(new LessAdd(10, 1));

        plus1.setOnClickListener(new LessAdd(1, 2));
        plus2.setOnClickListener(new LessAdd(2, 2));
        plus3.setOnClickListener(new LessAdd(3, 2));
        plus4.setOnClickListener(new LessAdd(4, 2));
        plus5.setOnClickListener(new LessAdd(5, 2));
        plus6.setOnClickListener(new LessAdd(6, 2));
        plus7.setOnClickListener(new LessAdd(7, 2));
        plus8.setOnClickListener(new LessAdd(8, 2));
        plus9.setOnClickListener(new LessAdd(9, 2));
        plus10.setOnClickListener(new LessAdd(10, 2));

        payment1.addTextChangedListener(new calculateEvent(1));
        payment2.addTextChangedListener(new calculateEvent(2));
        payment3.addTextChangedListener(new calculateEvent(3));
        payment4.addTextChangedListener(new calculateEvent(4));
        payment5.addTextChangedListener(new calculateEvent(5));
        payment6.addTextChangedListener(new calculateEvent(6));
        payment7.addTextChangedListener(new calculateEvent(7));
        payment8.addTextChangedListener(new calculateEvent(8));
        payment9.addTextChangedListener(new calculateEvent(9));
        payment10.addTextChangedListener(new calculateEvent(10));


    }

    private void addStudents() {

        Intent intentData = getIntent();

        if (intentData.hasExtra("studentList")) {
            studentList = (ArrayList<HashMap<String, String>>) intentData.getSerializableExtra("studentList");
        }
        refreshContainer();
    }

    private void hideAllStudents() {


        student1Layout.setVisibility(View.GONE);
        student2Layout.setVisibility(View.GONE);
        student3Layout.setVisibility(View.GONE);
        student4Layout.setVisibility(View.GONE);
        student5Layout.setVisibility(View.GONE);
        student6Layout.setVisibility(View.GONE);
        student7Layout.setVisibility(View.GONE);
        student8Layout.setVisibility(View.GONE);
        student9Layout.setVisibility(View.GONE);
        student10Layout.setVisibility(View.GONE);

    }

    private void refreshContainer() {
        hideAllStudents();
        stdCount_text.setText(String.valueOf(studentList.size()));

        for (int i = 0; i < studentList.size(); i++) {
            names[i].setText(studentList.get(i).get("name"));
            cis[i].setText("C.I. " + studentList.get(i).get("ci"));
            codes[i].setText(studentList.get(i).get("code"));
            seccions[i].setText(studentList.get(i).get("seccion"));
            grades[i].setText(studentList.get(i).get("grade"));
            payments.get(i).setText(studentList.get(i).get("payment"));
            containers[i].setVisibility(View.VISIBLE);
            calculateCost();
        }

        if (studentList.size() == 0) {
            calculateCost();
        }

    }

    private void calculateCost() {

        int months = 0;

        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).get("payment").isEmpty()) {
                continue;
            }
            months += Integer.parseInt(studentList.get(i).get("payment"));
        }

        Double total = (double) montPriceInt * months;

        saldo.setText(String.valueOf(saldoInt - total));


    }

    class hideStudent implements View.OnClickListener {

        private int stdNumer;

        public hideStudent(int stdNuimber) {
            this.stdNumer = stdNuimber - 1;
        }

        @Override
        public void onClick(View v) {
            studentList.remove(stdNumer);
            refreshContainer();

        }
    }//

    // acciones de los botones + y -
    class LessAdd implements View.OnClickListener {
        private int number;
        private int type;

        LessAdd(int number, int type) {
            this.number = number - 1;
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            int value = 0;
            double currentSaldo = Double.parseDouble(saldo.getText().toString());

            value = studentList.get(number).get("payment").isEmpty() ? 0 : Integer.parseInt(studentList.get(number).get("payment"));

            if (value > 0 && type == 1) {
                value--;
            } else if (value < 12 && type == 2 && (currentSaldo - montPriceInt) >= 0) {
                value++;
            }
            studentList.get(number).put("payment", String.valueOf(value));
            refreshContainer();

        }
    }//

    //clase de del evento para los payments de cada estudiante
    class calculateEvent implements TextWatcher {
        int index = 0;

        calculateEvent(int index) {
            this.index = index - 1;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            studentList.get(index).put("payment", s.toString());
            calculateCost();
            if (Double.parseDouble(saldo.getText().toString()) < 0) {
                payments.get(index).setText("0");
                payments.get(index).setSelection(payments.get(index).getText().length());
                Toast.makeText(context, "No tiene saldo para asignar esa cantidad de meses", Toast.LENGTH_LONG).show();
                //s.clear();
            }
        }
    }
}//