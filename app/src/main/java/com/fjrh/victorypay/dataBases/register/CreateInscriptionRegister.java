package com.fjrh.victorypay.dataBases.register;

import android.content.Context;

import com.fjrh.victorypay.Libraries.CodeGenerator;
import com.fjrh.victorypay.dataBases.users.Users;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CreateInscriptionRegister {

    private Context context;
    private HashMap<String, String> data;
    private String user;
    private String description;
    private String type;
    private String insertion_query;
    private String rollback_query;
    private JSONObject metadata;
    private String student_code ,parents_code, tutor_code, tutor_ci, inscription, cash, operation_number,
            monthlyPrice, date, status, updatedAT;

    private double monthPrice, mount, abono, inscriptionDBL;

    public CreateInscriptionRegister(Context context, HashMap<String, String> data) {
        this.context = context;
        this.data = data;
        initFields();
        this.user = new Users(context).getUsers().get("ci");
        this.description = "Pago de inscripción de estudiante";
        this.type = "2";
        this.insertion_query = createInsertionQuery();
        this.rollback_query = createRollbackQuery();
        this.metadata = createMetadata();
    }

    private void initFields() {
        tutor_code = CodeGenerator.getNewCode('T');
        parents_code = CodeGenerator.getNewCode('P');
        student_code = data.get("code");
        tutor_ci = data.get("birth_country");
        inscription = data.get("inscription");
        cash = data.get("cash");
        operation_number = data.get("operation_number");
        monthlyPrice = data.get("monthlyPrice");
        date = data.get("date");
        status = data.get("status");
        monthPrice = Double.parseDouble(monthlyPrice);
        mount = Double.parseDouble(inscription);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        updatedAT = dateFormat.format(date);

    }

    private String createInsertionQuery() {

        if(mount >= monthPrice){
            abono = mount - monthPrice;
            inscriptionDBL = mount - abono;
        }else if(mount < monthPrice){
            abono = mount;
            inscriptionDBL = 0;
        }

        return "REPLACE INTO inscription_payments (student_code, inscription, cash, operation_number, monthlyPrice, date, status, updatedAT) " +
                "VALUES ('"+student_code+"', '"+inscription+"', '"+cash+"', '"+operation_number+"', '"+monthlyPrice+"', '"+date+"', '"+status+"', '"+updatedAT+"' )";
/////////////////continiuar desde aqui, falta la consulta para el abono
    }

    private String createRollbackQuery(){

        return "";

    }

    private JSONObject createMetadata(){
        JSONObject data = new JSONObject();
        try {
            data.put("student_code", student_code);
            data.put("inscription", inscription);
            data.put("cash", cash);
            data.put("operation_number", operation_number);
            data.put("monthlyPrice", monthlyPrice);
            data.put("date", date);
            data.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  data;
    }

    public Register getRegister(){
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
