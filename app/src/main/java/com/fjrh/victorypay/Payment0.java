package com.fjrh.victorypay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Payment0 extends AppCompatActivity {
    private Context context;
    private Button addStudent;
    private Button next;

    private TextView stdCount_text;

    private ConstraintLayout student1Layout;
    private ConstraintLayout student2Layout;
    private ConstraintLayout student3Layout;
    private ConstraintLayout student4Layout;
    private ConstraintLayout student5Layout;

    private TextView name1;
    private TextView name2;
    private TextView name3;
    private TextView name4;
    private TextView name5;

    private TextView code1;
    private TextView code2;
    private TextView code3;
    private TextView code4;
    private TextView code5;

    private TextView ci1;
    private TextView ci2;
    private TextView ci3;
    private TextView ci4;
    private TextView ci5;

    private TextView seccion1;
    private TextView seccion2;
    private TextView seccion3;
    private TextView seccion4;
    private TextView seccion5;

    private TextView grade1;
    private TextView grade2;
    private TextView grade3;
    private TextView grade4;
    private TextView grade5;

    private ImageView delete1;
    private ImageView delete2;
    private ImageView delete3;
    private ImageView delete4;
    private ImageView delete5;

    private ArrayList<HashMap<String, String>> studentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment0);
        context = this;
        initComponents();
        initEvents();

    }

    private void initComponents(){
        addStudent = findViewById(R.id.btbAddStudent_payment0);
        next = findViewById(R.id.btnNext_payment0);
        stdCount_text = findViewById(R.id.textStdNumber);
        student1Layout = findViewById(R.id.student1_payment0);
        student2Layout = findViewById(R.id.student2_payment0);
        student3Layout = findViewById(R.id.student3_payment0);
        student4Layout = findViewById(R.id.student4_payment0);
        student5Layout = findViewById(R.id.student5_payment0);
        name1 = findViewById(R.id.name1_payment0);
        name2 = findViewById(R.id.name2_payment0);
        name3 = findViewById(R.id.name3_payment0);
        name4 = findViewById(R.id.name4_payment0);
        name5 = findViewById(R.id.name5_payment0);
        code1 = findViewById(R.id.code1_payment0);
        code2 = findViewById(R.id.code2_payment0);
        code3 = findViewById(R.id.code3_payment0);
        code4 = findViewById(R.id.code4_payment0);
        code5 = findViewById(R.id.code5_payment0);
        ci1 = findViewById(R.id.ci1_payment0);
        ci2 = findViewById(R.id.ci2_payment0);
        ci3 = findViewById(R.id.ci3_payment0);
        ci4 = findViewById(R.id.ci4_payment0);
        ci5 = findViewById(R.id.ci5_payment0);
        seccion1 = findViewById(R.id.seccion1_payment0);
        seccion2 = findViewById(R.id.seccion2_payment0);
        seccion3 = findViewById(R.id.seccion3_payment0);
        seccion4 = findViewById(R.id.seccion4_payment0);
        seccion5 = findViewById(R.id.seccion5_payment0);
        grade1 = findViewById(R.id.grade1_payment0);
        grade2 = findViewById(R.id.grade2_payment0);
        grade3 = findViewById(R.id.grade3_payment0);
        grade4 = findViewById(R.id.grade4_payment0);
        grade5 = findViewById(R.id.grade5_payment0);
        delete1 = findViewById(R.id.remove1_payment0);
        delete2 = findViewById(R.id.remove2_payment0);
        delete3 = findViewById(R.id.remove3_payment0);
        delete4 = findViewById(R.id.remove4_payment0);
        delete5 = findViewById(R.id.remove5_payment0);
        studentList = new ArrayList<>();
    }

    private void initEvents(){

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
                refreshContainer();

            }
        });

        delete1.setOnClickListener(new hideStudent(1));
        delete2.setOnClickListener(new hideStudent(2));
        delete3.setOnClickListener(new hideStudent(3));
        delete4.setOnClickListener(new hideStudent(4));
        delete5.setOnClickListener(new hideStudent(5));

    }

    private void addStudent(){
        HashMap<String, String> newStudent = new HashMap<>();
        if(studentList.size() < 5){
            studentList.add(newStudent);
        }
    }

    private void hideAllStudents(){
        student1Layout.setVisibility(View.GONE);
        student2Layout.setVisibility(View.GONE);
        student3Layout.setVisibility(View.GONE);
        student4Layout.setVisibility(View.GONE);
        student5Layout.setVisibility(View.GONE);
    }


    private void refreshContainer(){
        hideAllStudents();
        stdCount_text.setText(String.valueOf(studentList.size()));

        ConstraintLayout[] containers = {student1Layout, student2Layout, student3Layout, student4Layout, student5Layout};

        for(int i = 0 ; i < studentList.size() ;  i++){
            containers[i].setVisibility(View.VISIBLE);
        }

    }


    class hideStudent implements View.OnClickListener{

        private int stdNumer;

        public  hideStudent(int stdNuimber){
            this.stdNumer = stdNuimber;
        }
        @Override
        public void onClick(View v) {

            switch (stdNumer){
                case 1:
                    student1Layout.setVisibility(View.GONE);
                    break;
                case 2:
                    student2Layout.setVisibility(View.GONE);
                    break;
                case 3:
                    student3Layout.setVisibility(View.GONE);
                    break;
                case 4:
                    student4Layout.setVisibility(View.GONE);
                    break;
                case 5:
                    student5Layout.setVisibility(View.GONE);
                    break;
                default:
            }
            studentList.remove(stdNumer -1);
            refreshContainer();

        }
    }//


}//