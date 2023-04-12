package com.fjrh.victorypay.dataBases.register;

import android.content.Context;
import android.util.Log;

import com.fjrh.victorypay.Libraries.CodeGenerator;
import com.fjrh.victorypay.dataBases.abono.Abono;
import com.fjrh.victorypay.dataBases.students.FindStudent;
import com.fjrh.victorypay.dataBases.users.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CreateMonthPaymentRegister {


    private Context context;
    private HashMap<String, String> paymentData;
    private ArrayList <HashMap<String, String>> studentData;
    private String user;
    private String description;
    private String type;
    private String insertion_query;
    private String rollback_query;
    private JSONArray metadata;
    private String register_code, student_code, tutor_code, payment, monthly_price, cash, operation_number, operation_date, months, updatedAT;


    public CreateMonthPaymentRegister(Context context, HashMap<String, String> paymentData, ArrayList<HashMap<String, String>> studentData) {
        this.context = context;
        this.paymentData = paymentData;
        this.studentData = studentData;
        initFields();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        updatedAT = dateFormat.format(date);

        this.user = new Users(context).getUsers().get("ci");
        this.description = "Pago de mensualidad o abono de estudiante";
        this.type = "3";
        this.tutor_code = studentData.get(0).get("tutor_code");

        this.insertion_query = createInsertionQuery();
        this.rollback_query = createRollbackQuery();
        this.register_code = CodeGenerator.getNewCode('R');
        this.metadata = createMetadata();



    }

    private void initFields() {

    }

    private String createInsertionQuery() {

        return "XXX";

    }

    private String createRollbackQuery() {

        return "XXX";
    }

    private JSONArray createMetadata() {
        JSONArray data = new JSONArray();
        try {
            JSONObject student = new JSONObject();

            for (int i = 0; i < studentData.size(); i++) {
                student.put("register_code", register_code);
                student.put("student_code", studentData.get(i).get("code"));
                student.put("tutor_code", studentData.get(i).get("tutor_code"));
                student.put("payment", paymentData.get("mount"));
                student.put("monthly_price", paymentData.get("monthly_price"));
                student.put("cash", paymentData.get("type"));
                student.put("operation_number", paymentData.get("operation").isEmpty() ? "no suministrado" : paymentData.get("operation"));
                student.put("operation_date", paymentData.get("date").equalsIgnoreCase("Seleccione una fecha") ? updatedAT : paymentData.get("date"));
                student.put("months", studentData.get(i).get("months"));
                student.put("user", user);
                student.put("updatedAT", updatedAT);

                data.put(student);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Register getRegister() {
        Register student = new Register();
        student.setUser(user);
        student.setDescription(description);
        student.setType(type);
        student.setInsertion_query(insertion_query);
        student.setRollback_query(rollback_query);
        student.setMetadata(metadata);
        return student;
    }

}
