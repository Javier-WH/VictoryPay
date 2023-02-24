package com.fjrh.victorypay.dataBases.students;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fjrh.victorypay.dataBases.DbHelper;
import com.fjrh.victorypay.dataBases.params.Params;


import java.util.HashMap;

public class InsertStuden extends DbHelper {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private FindStudent findStudent;
    private HashMap<String, String> params;


    public InsertStuden(Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        findStudent = new FindStudent(context);
        params = new Params(context).getParams();
    }


    private long insertStudent(HashMap<String, String> user, String parent_id, String tutor_id) {

        ContentValues values = new ContentValues();
        values.put("name", user.get("studentName"));
        values.put("lastName", user.get("studentLastName"));
        values.put("ci", user.get("studentCi"));
        values.put("nation", user.get("studentNation"));
        values.put("seccion", user.get("seccion"));
        values.put("grade", user.get("grade"));
        values.put("gender", user.get("gender"));
        values.put("code", user.get("code"));
        values.put("birthdate", user.get("birthDate"));
        values.put("age", user.get("age"));
        values.put("parent_id", parent_id);
        values.put("tutor_id", tutor_id);

        return db.insert("students", null, values);
    }

    //
    private long insertParent(HashMap<String, String> parent) throws SQLException {

        ContentValues values = new ContentValues();
        values.put("mother_name", parent.get("motherName"));
        values.put("mother_ci", parent.get("motherCi"));
        values.put("mother_nation", parent.get("motherNationality"));
        values.put("mother_work", parent.get("motherWork"));
        values.put("father_name", parent.get("fatherName"));
        values.put("father_ci", parent.get("fatherCi"));
        values.put("father_nation", parent.get("fatherNationality"));
        values.put("father_work", parent.get("fatherWork"));


        return db.insert("parents", null, values);
    }

    //
    private long insertTutor(HashMap<String, String> tutor) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("tutor_name", tutor.get("tutorName"));
        values.put("tutor_ci", tutor.get("tutorCi"));
        values.put("tutor_nation", tutor.get("tutorNationality"));
        values.put("tutor_link", tutor.get("link3"));


        return db.insert("tutors", null, values);
    }

    //
    private long insertContact(HashMap<String, String> contact, String stdId) throws SQLException {

        ContentValues values = new ContentValues();
        values.put("student_id", stdId);
        values.put("phone1", contact.get("phone1"));
        values.put("phone2", contact.get("phone2"));
        values.put("email", contact.get("email"));
        values.put("whatsapp1", contact.get("w1"));
        values.put("whatsapp2", contact.get("w2"));


        return db.insert("contact_info", null, values);
    }

    //
    private long insertAddress(HashMap<String, String> address, String stdId) throws SQLException {

        ContentValues values = new ContentValues();
        values.put("student_id", stdId);
        values.put("birth_country", address.get("birthCountry"));
        values.put("birth_state", address.get("birthEstado"));
        values.put("birth_municipio", address.get("birthMunicipio"));
        values.put("birth_parroquia", address.get("birthParroquia"));
        values.put("live_state", address.get("liveEstate"));
        values.put("live_municipio", address.get("liveMunicipio"));
        values.put("live_parroquia", address.get("liveParroquia"));
        values.put("address", address.get("address"));
        values.put("procedence_school", address.get("procedence"));

        return db.insert("address", null, values);
    }
    private long insertMedical(HashMap<String, String> medical, String stdId) throws SQLException {

        ContentValues values = new ContentValues();
        values.put("student_id", stdId);
        values.put("diabetes", medical.get("diabetes"));
        values.put("hipertension", medical.get("hipertension"));
        values.put("dislexia", medical.get("dislexia"));
        values.put("daltonismo", medical.get("daltonismo"));
        values.put("epilepsia", medical.get("epilepsia"));
        values.put("asma", medical.get("asma"));
        values.put("alergias", medical.get("alergia"));
        values.put("TDAH", medical.get("TDAH"));
        values.put("observations", medical.get("observations1_4"));

        return db.insert("medical_info", null, values);
    }
    private long insertPayment(HashMap<String, String> payment, String stdId) throws SQLException {


        ContentValues values = new ContentValues();
        values.put("student_id", stdId);
        values.put("inscription", payment.get("mount"));
        values.put("cash", payment.get("payMethod").equals("1") ? "true" : "false");
        values.put("operation_number", payment.get("account"));
        values.put("date", payment.get("date"));

        return db.insert("inscription_payment", null, values);
    }

    public boolean insert(HashMap<String, String> data) {



        //si ya está registrada la cédula del estudiante NO inscribe el estudiante
        if(findStudent.isCiRegistered(data.get("studentCi"))){
            return false;
        }
        //si ya está registrado el código del estudiante NO inscribe el estudiante
        if(findStudent.isCodeRegistered(data.get("code"))){
            return false;
        }

        try {
            //revisa que los padres no esten inscritos previamente, de estar inscritos, obtiene sus id
            long parentID = findStudent.findStudentParents(data.get("motherCi"), data.get("fatherCi"));
            if(parentID == -1){
                parentID = insertParent(data);
            }

            //revissa que el tutor no esté previamente inscrito, de esta inscrito obtiene su id
            long tutorID = findStudent.findStudentTutor(data.get("tutorCi"));
            if(tutorID == -1) {
                tutorID = insertTutor(data);
            }

            String studentID = String.valueOf(insertStudent(data, String.valueOf(parentID), String.valueOf(tutorID)));
            insertContact(data, studentID);
            insertAddress(data, studentID);
            insertMedical(data, studentID);
            insertPayment(data, studentID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
