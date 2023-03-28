package com.fjrh.victorypay.dataBases.register;

import android.content.Context;
import android.util.Log;

import com.fjrh.victorypay.Libraries.CodeGenerator;
import com.fjrh.victorypay.dataBases.abono.Abono;
import com.fjrh.victorypay.dataBases.students.FindStudent;
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
    private String student_code, tutor_code, tutor_ci, inscription, cash, operation_number,
            monthlyPrice, date, status, updatedAT, _tutor_code, operator;

    private double monthPrice, mount, abono, storedAbono, plus;

    public CreateInscriptionRegister(Context context, HashMap<String, String> data, String tutor_code) {
        this.context = context;
        this.data = data;
        initFields();
        this.user = new Users(context).getUsers().get("ci");
        this.description = "Pago de inscripción de estudiante";
        this.type = "2";
        this.tutor_code = tutor_code;

        FindStudent findStudent = new FindStudent(context);
        _tutor_code = findStudent.findStudentTutor(tutor_ci);
        this.storedAbono = new Abono(context).getAbono(_tutor_code);

        this.insertion_query = createInsertionQuery();
        this.rollback_query = createRollbackQuery();
        this.metadata = createMetadata();

    }

    private void initFields() {
        student_code = data.get("code");
        tutor_ci = data.get("tutor_ci");
        inscription = data.get("inscription");
        cash = data.get("cash");
        operation_number = data.get("operation_number");
        monthlyPrice = data.get("monthlyPrice");
        date = data.get("date");
        status = data.get("status");
        monthPrice = Double.parseDouble(monthlyPrice);
        mount = Double.parseDouble(data.get("inscription"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        updatedAT = dateFormat.format(date);

    }

    private String createInsertionQuery() {

        //sumar el current Abono con el dinero suministrado

        double abonoPlusMount = storedAbono + mount;

        //restar a la suma del abono y dinero suministrado el precio del mes

        double total = abonoPlusMount - monthPrice;

        if (total >= 0) {
            abono = total;
            inscription = String.valueOf(monthPrice);
            status = "true";
        } else {
            abono = abonoPlusMount;
            inscription = "0";
            status = "false";
        }

        plus = total - storedAbono;
        operator = plus > 0 ? "+" : "-";


        if (_tutor_code.equals("-1")) {
            return "REPLACE INTO inscription_payments (student_code, inscription, cash, operation_number, monthlyPrice, date, status, updatedAT) " +
                    "VALUES ('" + student_code + "', '" + inscription + "', '" + cash + "', '" + operation_number + "', '" + monthlyPrice + "', '" + date + "', '" + status + "', '" + updatedAT + "' ); " +
                    "REPLACE INTO abonos (tutor_code, abono, updatedAT) VALUES ('" + _tutor_code + "', '" + abono + "' , '" + updatedAT + "');";

        } else {

            return "REPLACE INTO inscription_payments (student_code, inscription, cash, operation_number, monthlyPrice, date, status, updatedAT) " +
                    "VALUES ('" + student_code + "', '" + inscription + "', '" + cash + "', '" + operation_number + "', '" + monthlyPrice + "', '" + date + "', '" + status + "', '" + updatedAT + "' ); " +
                    "UPDATE abonos SET abono = abono " + operator + " " + Math.abs(plus) + "  WHERE tutor_code = '" + _tutor_code + "';";
        }


    }

    private String createRollbackQuery() {

        String opositeOperator = operator.equals("+") ? "-" : "+";

        return "DELETE FROM inscription_payments WHERE student_code = '" + student_code + "' ; " +
                "UPDATE abonos SET abono = abono " + opositeOperator + " " + Math.abs(plus) + "  WHERE tutor_code = '" + _tutor_code + "';";

    }

    private JSONObject createMetadata() {
        JSONObject data = new JSONObject();
        try {
            data.put("student_code", student_code);
            data.put("inscription", inscription);
            data.put("cash", cash);
            data.put("operation_number", operation_number);
            data.put("monthlyPrice", monthlyPrice);
            data.put("date", date);
            data.put("status", status);
            data.put("updatedAT", updatedAT);
            data.put("tutor_code", tutor_code);

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
