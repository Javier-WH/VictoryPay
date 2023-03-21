package com.fjrh.victorypay.dataBases.students;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.fjrh.victorypay.dataBases.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class FindStudent extends DbHelper {
    Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;


    public FindStudent(@Nullable Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean isCiRegistered(String ci) {

        String query = "SELECT * FROM students WHERE ci = ?";

        Cursor cursor = db.rawQuery(query, new String[]{ci});

        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public boolean isCodeRegistered(String code) {

        String query = "SELECT * FROM students WHERE code = ?";

        Cursor cursor = db.rawQuery(query, new String[]{code});

        if (cursor.moveToFirst()) {
            return true;
        }

        return false;
    }

    public long findStudentParents(String motherCi, String fatherCi) {

        String query = "SELECT * FROM parents WHERE mother_ci = ? AND father_ci = ?";

        Cursor cursor = db.rawQuery(query, new String[]{motherCi, fatherCi});

        if (cursor.moveToFirst()) {
            long id = cursor.getInt(0);
            return id;
        }

        return -1;
    }

    public long findStudentTutor(String tutorCi) {

        String query = "SELECT * FROM tutors WHERE tutor_ci = ?";

        Cursor cursor = db.rawQuery(query, new String[]{tutorCi});

        if (cursor.moveToFirst()) {
            long id = cursor.getInt(0);
            return id;
        }

        return -1;
    }

    ////

    public Cursor searchStudentLikeCi(String ci) {
        String query = "SELECT * FROM students WHERE ci LIKE '%" + ci + "%'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor searchStudentLikeCode(String code) {
        String query = "SELECT * FROM students WHERE code LIKE '%" + code + "%'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor searchStudentLikeName(String name, String lastName) {
        String query = "SELECT * FROM students WHERE name LIKE '%" + name + "%' OR lastName LIKE '%" + lastName + "%'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //
    public HashMap<String, String> findStudentByCode(String code) {
        HashMap<String, String> response = new HashMap<>();
        String query = "SELECT * FROM students WHERE code = ?";
        Cursor cursor = db.rawQuery(query, new String[]{code});
        if (cursor.moveToFirst()) {
            response.put("name", cursor.getString(1));
            response.put("lastName", cursor.getString(2));
            response.put("ci", cursor.getString(3));
            response.put("nation", cursor.getString(4));
            response.put("seccion", cursor.getString(5));
            response.put("grade", cursor.getString(6));
            response.put("gender", cursor.getString(7));
            response.put("code", cursor.getString(8));
            response.put("birthdate", cursor.getString(9));
            response.put("age", cursor.getString(10));
            response.put("parent_id", cursor.getString(11));
            response.put("tutor_id", cursor.getString(12));
        }
        return response;
    }

    public ArrayList<HashMap<String, String>> getBrothers(String tutor_id) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        String query = "SELECT * FROM students WHERE tutor_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tutor_id});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();
                student.put("name", cursor.getString(1));
                student.put("lastName", cursor.getString(2));
                student.put("ci", cursor.getString(3));
                student.put("nation", cursor.getString(4));
                student.put("seccion", cursor.getString(5));
                student.put("grade", cursor.getString(6));
                student.put("gender", cursor.getString(7));
                student.put("code", cursor.getString(8));
                student.put("birthdate", cursor.getString(9));
                student.put("age", cursor.getString(10));
                student.put("parent_id", cursor.getString(11));
                student.put("tutor_id", cursor.getString(12));

                list.add(student);
            } while (cursor.moveToNext());
        }


        return list;
    }

    public ArrayList<HashMap<String, String>> getOfflineStudentList() {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        String query = "SELECT students.*, tutors.tutor_name, tutors.tutor_ci, tutors.tutor_nation, tutor_link, tutors.updatedAT as tutorDate, " +
                "parents.mother_name, parents.mother_ci, parents.mother_nation, parents.mother_work, parents.father_name, parents.father_ci, parents.father_nation, parents.father_work, parents.updatedAT as parentsDate, " +
                "address.birth_country, address.birth_state, address.birth_municipio, address.birth_parroquia, address.live_state, address.live_municipio, address.live_parroquia, address.address, address.procedence_school, address.updatedAT as addressDate, " +
                "contact_info.phone1, contact_info.phone2, contact_info.email, contact_info.whatsapp1, contact_info.whatsapp2, contact_info.updatedAT as contactDate, " +
                "medical_info.diabetes, medical_info.hipertension, medical_info.dislexia, medical_info.daltonismo, medical_info.epilepsia, medical_info.asma, medical_info.alergias, medical_info.TDAH, medical_info.observations, medical_info.updatedAT as medicalDate, " +
                "inscription_payment.inscription, inscription_payment.cash, inscription_payment.operation_number, inscription_payment.date, inscription_payment.status, inscription_payment.updatedAT as paymentDate, " +
                "abono.abono, abono.updatedAT as abonoDate " +
                "FROM students " +
                "JOIN tutors ON tutors.id = students.tutor_id " +
                "JOIN parents ON parents.id = students.parent_id " +
                "JOIN address ON students.id = address.student_id " +
                "JOIN contact_info ON students.id = contact_info.student_id " +
                "JOIN medical_info ON students.id = medical_info.student_id " +
                "JOIN inscription_payment ON students.id = inscription_payment.student_id " +
                "JOIN abono ON students.tutor_id = abono.tutor_id";


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {
                HashMap<String, String> student = new HashMap<>();

                for(int i = 0 ; i < cursor.getColumnCount() ; i++){
                    student.put(cursor.getColumnName(i),cursor.getString(i));
                }
                list.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

}
