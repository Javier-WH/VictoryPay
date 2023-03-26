package com.fjrh.victorypay.dataBases.students;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fjrh.victorypay.dataBases.DbHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateStudentList extends DbHelper {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public UpdateStudentList(Context context) {
        super(context);
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean update(ArrayList<JSONObject> list){
        db.execSQL("DELETE FROM students");
        db.execSQL("DELETE FROM address");
        db.execSQL("DELETE FROM contact_info");
        db.execSQL("DELETE FROM inscription_payment");
        db.execSQL("DELETE FROM medical_info");
        db.execSQL("DELETE FROM parents");
        db.execSQL("DELETE FROM students");
        db.execSQL("DELETE FROM tutors");
        db.execSQL("DELETE FROM abono");
        db.execSQL("VACUUM");

            list.forEach(student -> {
                try {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("studentName", student.getString("name"));
                    data.put("studentLastName", student.getString("lastName"));
                    data.put("studentCi", student.getString("ci"));
                    data.put("studentNation", student.getString("nation"));
                    data.put("seccion", student.getString("seccion"));
                    data.put("grade", student.getString("grade"));
                    data.put("gender", student.getString("gender"));
                    data.put("code", student.getString("code"));
                    data.put("birthDate", student.getString("birthdate"));
                    data.put("age", student.getString("age"));
                    data.put("parent_id", student.getString("parent_id"));
                    data.put("tutor_id", student.getString("tutor_id"));
                    data.put("updatedAt", student.getString("updatedAt"));

                    data.put("motherName", student.getString("mother_name"));
                    data.put("motherCi", student.getString("mother_ci"));
                    data.put("motherNationality", student.getString("mother_nation"));
                    data.put("motherWork", student.getString("mother_work"));
                    data.put("fatherName", student.getString("father_name"));
                    data.put("fatherCi", student.getString("father_ci"));
                    data.put("fatherNationality", student.getString("father_nation"));
                    data.put("fatherWork", student.getString("father_work"));
                    data.put("parentsDate", student.getString("parentsDate"));

                    data.put("tutor_code", student.getString("tutor_code"));
                    data.put("tutorName", student.getString("tutor_name"));
                    data.put("tutorCi", student.getString("tutor_ci"));
                    data.put("tutorNationality", student.getString("tutor_nation"));
                    data.put("link3", student.getString("tutor_link"));
                    data.put("tutorDate", student.getString("tutorDate"));

                    data.put("phone1", student.getString("phone1"));
                    data.put("phone2", student.getString("phone2"));
                    data.put("email", student.getString("email"));
                    data.put("w1", student.getString("whatsaap1"));
                    data.put("w2", student.getString("whatsaap2"));
                    data.put("contactDate", student.getString("contactDate"));

                    data.put("birthCountry", student.getString("birth_country"));
                    data.put("birthEstado", student.getString("birth_state"));
                    data.put("birthMunicipio", student.getString("birth_municipio"));
                    data.put("birthParroquia", student.getString("birth_parroquia"));
                    data.put("liveEstate", student.getString("live_state"));
                    data.put("liveMunicipio", student.getString("live_municipio"));
                    data.put("liveParroquia", student.getString("live_parroquia"));
                    data.put("address", student.getString("address"));
                    data.put("procedence", student.getString("procedence_school"));
                    data.put("addressDate", student.getString("addressDate"));

                    data.put("diabetes", student.getString("diabetes"));
                    data.put("hipertension", student.getString("hipertension"));
                    data.put("dislexia", student.getString("dislexia"));
                    data.put("daltonismo", student.getString("daltonismo"));
                    data.put("epilepsia", student.getString("epilepsia"));
                    data.put("asma", student.getString("asma"));
                    data.put("alergia", student.getString("alergias"));
                    data.put("TDAH", student.getString("TDAH"));
                    data.put("observations1_4", student.getString("observations"));
                    data.put("medicalDate", student.getString("medicalDate"));

                    data.put("monthlyPrice", student.getString("monthlyPrice"));
                    data.put("payMethod", student.getString("cash"));
                    data.put("account", student.getString("operation_number"));
                    data.put("date", student.getString("date"));
                    data.put("payment_status", student.getString("status"));
                    data.put("paymentDate", student.getString("paymentDate"));

                    data.put("abono", student.getString("abono"));
                    data.put("abonoDate", student.getString("abonoDate"));

                    //new InsertStuden(context).insert(data);

                } catch (JSONException e) {
                    Log.e("XXX", "114 UpdateStudent error " + e.getMessage());
                    e.printStackTrace();
                }
            });

        return true;
    }



}
