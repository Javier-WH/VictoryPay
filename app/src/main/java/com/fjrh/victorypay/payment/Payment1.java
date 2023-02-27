package com.fjrh.victorypay.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.students.FindStudent;

public class Payment1 extends AppCompatActivity {
    private EditText ci;
    private EditText code;
    private EditText name;
    private TextView seccion;
    private TextView grade;
    private TextView gender;
    private TextView lastPayment;
    private TextView paymentStatus;
    private Button registerPayment;
    private Button paymentList;
    private Button moreInfo;
    private Context context;
    private FindStudent studentsData;
    private LinearLayout ciHelper;
    private LinearLayout codeHelper;
    private LinearLayout nameHelper;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment1);
        context = this;
        initComponents();
        initEvents();
    }

    private void initComponents() {
        ci = findViewById(R.id.studentCi_search);
        code = findViewById(R.id.studentCode_search);
        name = findViewById(R.id.studentName_search);
        seccion = findViewById(R.id.studentSeccion_search);
        grade = findViewById(R.id.studentGrade_search);
        gender = findViewById(R.id.studentGender_search);
        lastPayment = findViewById(R.id.lastPayment_search);
        paymentStatus = findViewById(R.id.paymentStatus_search);
        registerPayment = findViewById(R.id.btnRegister_search);
        paymentList = findViewById(R.id.btnPaymientLis_search);
        moreInfo = findViewById(R.id.btnMoreInfo_search);
        studentsData = new FindStudent(context);
        ciHelper = findViewById(R.id.ciHelper);
        codeHelper = findViewById(R.id.codeHelper);
        nameHelper = findViewById(R.id.nameHelper);
        progressBar = findViewById(R.id.progressBar_payment1);
    }

    private void initEvents() {
        ci.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    cleanAllData();
                }
            }
        });
        code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    cleanAllData();
                }
            }
        });
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    cleanAllData();
                }
            }
        });
        //
        ci.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loading(true);
                String text = ci.getText().toString().trim();
                if (text.isEmpty()) {
                    loading(false);
                    cleanHelpers();
                    return;
                }

                Cursor student = studentsData.searchStudentLikeCi(text);
                addCiSubject(student, ciHelper);
            }
        });

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loading(true);
                String text = code.getText().toString().trim();
                if (text.isEmpty()) {
                    loading(false);
                    cleanHelpers();
                    return;
                }

                Cursor student = studentsData.searchStudentLikeCode(text);
                addCiSubject(student, codeHelper);
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loading(true);
                String text = name.getText().toString().trim();
                if (text.isEmpty()) {
                    loading(false);
                    cleanHelpers();
                    return;
                }

                Cursor student = studentsData.searchStudentLikeName(text, text);
                addCiSubject(student, nameHelper);
            }
        });

    }


    private void addCiSubject(Cursor student, LinearLayout helper) {
        cleanHelpers();

        if (student.moveToFirst()) {
            do {
                int nameIndex = student.getColumnIndex("name");
                int lastNameIndex = student.getColumnIndex("lastName");
                int studentCiIndex = student.getColumnIndex("ci");
                int codeIndex = student.getColumnIndex("code");
                int gradeIndex = student.getColumnIndex("grade");
                int genderIndex = student.getColumnIndex("gender");
                int seccionIndex = student.getColumnIndex("seccion");

                String _name = student.getString(nameIndex);
                String _lastName = student.getString(lastNameIndex);
                String _ci = student.getString(studentCiIndex);
                String _code = student.getString(codeIndex);
                String _grade = student.getString(gradeIndex);
                String _gender = student.getString(genderIndex);
                String _seccion = student.getString(seccionIndex);


                String text = _lastName + " " + _name + " ci: " + _ci;

                TextView suggest = new TextView(context);
                suggest.setText(text);
                suggest.setTextSize(15f);
                suggest.setTextColor(getResources().getColor(R.color.white));
                suggest.setPadding(10, 15, 10, 10);
                LinearLayout.LayoutParams suggestParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                suggestParams.setMargins(10, 10, 10, 10);
                suggest.setLayoutParams(suggestParams);
                helper.addView(suggest);
                suggest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fillData(_name, _lastName, _ci, _code, _seccion, _grade, _gender, "Marzo", "Al dia");
                    }
                });
            } while (student.moveToNext());
        }

    }

    private void fillData(String name, String lastName, String ci, String code, String seccion, String grade, String gender, String lastPayment, String paymentStatus) {
        this.name.setText(name + " " + lastName);
        this.ci.setText(ci);
        this.code.setText(code);
        this.seccion.setText(seccion);
        this.grade.setText(grade);
        this.gender.setText(gender);
        this.lastPayment.setText(lastPayment);
        this.paymentStatus.setText(paymentStatus);
        cleanHelpers();
        loading(false);
    }

    private void cleanAllData(){
        this.name.setText("");
        this.ci.setText("");
        this.code.setText("");
        this.seccion.setText("");
        this.grade.setText("");
        this.gender.setText("");
        this.lastPayment.setText("");
        this.paymentStatus.setText("");
    }

    private void cleanHelpers() {
        ciHelper.removeAllViews();
        codeHelper.removeAllViews();
        nameHelper.removeAllViews();
    }

    private void loading(boolean isLoadig) {
        progressBar.setVisibility(isLoadig ? View.VISIBLE : View.INVISIBLE);
        registerPayment.setEnabled(!isLoadig);
        paymentList.setEnabled(!isLoadig);
        moreInfo.setEnabled(!isLoadig);
    }


}