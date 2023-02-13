package com.fjrh.victorypay.dataBases.students;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fjrh.victorypay.dataBases.DbHelper;

import java.util.HashMap;

public class InsertStuden extends DbHelper {
    Context context;

    public InsertStuden(Context context){
        super(context);
        this.context = context;
    }


    private long insertStudent(HashMap<String, String> user, String parent_id, String tutor_id){

        ContentValues values = new ContentValues();
        values.put("name", user.get("studentName"));
        values.put("lastName", user.get("studentLastName"));
        values.put("seccion", user.get("seccion"));
        values.put("grade", user.get("grade"));
        values.put("gender", user.get("gender"));
        values.put("code", user.get("code"));
        values.put("parent_id", parent_id);
        values.put("tutor_id", tutor_id);

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.insert("students", null, values);
    }
//
    private long insertParent(HashMap<String, String> parent) throws SQLException{

        ContentValues values = new ContentValues();
        values.put("mother_name", parent.get("motherName"));
        values.put("mother_ci", parent.get("motherCi"));
        values.put("mother_nation", parent.get("motherNationality"));
        values.put("father_name", parent.get("fatherName"));
        values.put("father_ci", parent.get("fatherCi"));
        values.put("father_nation", parent.get("fatherNationality"));

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.insert("parents", null, values);
    }
//
    private long insertTutor(HashMap<String, String> tutor) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("tutor_name", tutor.get("tutorName"));
        values.put("tutor_ci", tutor.get("tutorCi"));
        values.put("tutor_nation", tutor.get("tutorNationality"));

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.insert("tutors", null, values);
    }
//
    private long insertContact(HashMap<String, String> contact, String stdId) throws SQLException{

        ContentValues values = new ContentValues();
        values.put("student_id", stdId);
        values.put("phone1", contact.get("phone1"));
        values.put("phone2", contact.get("phone2"));
        values.put("email", contact.get("email"));
        values.put("whatsapp1", contact.get("w1"));
        values.put("whatsapp2", contact.get("w2"));

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.insert("contact_info", null, values);
    }

    public boolean insert(HashMap<String, String> data){

        try {
            long parentID = insertParent(data);
            long tutorID = insertTutor(data);
            long studentID = insertStudent(data, String.valueOf(parentID), String.valueOf(tutorID));
            insertContact(data, String.valueOf(studentID));

            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }



}
