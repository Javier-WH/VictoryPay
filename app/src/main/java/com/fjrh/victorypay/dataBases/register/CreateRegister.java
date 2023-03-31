package com.fjrh.victorypay.dataBases.register;

import android.content.Context;

import com.fjrh.victorypay.Libraries.CodeGenerator;
import com.fjrh.victorypay.dataBases.users.Users;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

//esta clase crea el registro de inscripción de un estuidiante (sin el pago de la inscripción)
public class CreateRegister {

    private Context context;
    private HashMap<String, String> data;
    private String user;
    private String description;
    private String type;
    private String insertion_query;
    private String rollback_query;
    private JSONObject metadata;
    private String name, lastName, ci, nation, seccion, grade, gender, code, birthdate, age, parents_code, tutor_code,
            student_code, birth_country,  birth_state, birth_municipio, birth_parroquia, live_state, live_municipio, live_parroquia, address, procedence_school,
            phone1, phone2, email, whatsaap1, whatsaap2, diabetes, hipertension, dislexia, daltonismo, epilepsia, asma, alergias, TDAH, observations,
            mother_name, mother_ci, mother_nation, mother_work, father_name, father_ci, father_nation, father_work,
            tutor_name, tutor_ci, tutor_nation, tutor_link, updatedAT;



    public CreateRegister(Context context, HashMap<String, String> data) {
        this.context = context;
        this.data = data;
        this.user = new Users(context).getUsers().get("ci");
        initFields();
        this.description = "Registro de nuevo estudiante";
        this.type = "1";
        this.insertion_query = createInsertionQuery();
        this.rollback_query = createRollbackQuery();
        this.metadata = createMetadata();


    }

    private void initFields() {
        tutor_code = CodeGenerator.getNewCode('T');
        parents_code = CodeGenerator.getNewCode('P');
        name = data.get("name");
        lastName = data.get("lastName");
        ci = data.get("ci");
        nation = data.get("nation");
        seccion = data.get("seccion");
        grade = data.get("grade");
        gender = data.get("gender");
        code = data.get("code");
        birthdate = data.get("birthdate");
        age = data.get("age");
        student_code = code;
        birth_country = data.get("birth_country");
        birth_state = data.get("birth_state");
        birth_municipio = data.get("birth_municipio");
        birth_parroquia = data.get("birth_parroquia");
        live_state = data.get("live_state");
        live_municipio = data.get("live_municipio");
        live_parroquia = data.get("live_parroquia");
        address = data.get("address");
        procedence_school = data.get("procedence_school");
        phone1 = data.get("phone1");
        phone2 = data.get("phone2");
        email = data.get("email");
        whatsaap1 = data.get("whatsaap1");
        whatsaap2 = data.get("whatsaap2");
        diabetes = data.get("diabetes");
        hipertension = data.get("hipertension");
        dislexia = data.get("dislexia");
        daltonismo = data.get("daltonismo");
        epilepsia = data.get("epilepsia");
        asma = data.get("asma");
        alergias = data.get("alergias");
        TDAH = data.get("TDAH");
        observations = data.get("observations");
        mother_name = data.get("mother_name");
        mother_ci = data.get("mother_ci");
        mother_nation = data.get("mother_nation");
        mother_work = data.get("mother_work");
        father_name = data.get("father_name");
        father_ci = data.get("father_ci");
        father_nation = data.get("father_nation");
        father_work = data.get("father_work");
        tutor_name = data.get("tutor_name");
        tutor_ci = data.get("tutor_ci");
        tutor_nation = data.get("tutor_nation");
        tutor_link = data.get("tutor_link");

        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        updatedAT= formato.format(fecha);

    }

    private String createInsertionQuery() {
        return "REPLACE INTO students (name, lastName, ci, nation, seccion, grade, gender, code, birthdate, age, parents_code, tutor_code, updatedAT) " +
                "VALUES ('"+name+"', '"+lastName+"', '"+ci+"', '"+nation+"', '"+seccion+"', '"+grade+"', '"+gender+"', '"+code+"', '"+birthdate+"', '"+age+"', '"+parents_code+"', '"+tutor_code+"', '"+updatedAT+"');  " +

                "REPLACE INTO addresses (student_code, birth_country, birth_state, birth_municipio, birth_parroquia, live_state, live_municipio, live_parroquia, address, procedence_school, updatedAT) " +
                "VALUES ('"+student_code+"', '"+birth_country+"', '"+birth_state+"', '"+birth_municipio+"', '"+birth_parroquia+"', '"+live_state+"', '"+live_municipio+"', '"+live_parroquia+"', '"+address+"', '"+procedence_school+"', '"+updatedAT+"'); " +

                "REPLACE INTO contact_infos (student_code, phone1, phone2, email, whatsaap1, whatsaap2, updatedAT) " +
                "VALUES ('"+student_code+"', '"+phone1+"', '"+phone2+"', '"+email+"', '"+whatsaap1+"', '"+whatsaap2+"', '"+updatedAT+"' ); " +

                "REPLACE INTO medical_infos (student_code, diabetes, hipertension, dislexia, daltonismo, epilepsia, asma, alergias, TDAH, observations, updatedAT) " +
                "VALUES ('"+student_code+"', '"+diabetes+"', '"+hipertension+"', '"+dislexia+"', '"+daltonismo+"', '"+epilepsia+"', '"+asma+"', '"+alergias+"', '"+TDAH+"', '"+observations+"', '"+updatedAT+"'); " +

                "REPLACE INTO parents (parents_code, mother_name, mother_ci, mother_nation, mother_work, father_name, father_ci, father_nation, father_work, updatedAT) " +
                "VALUES ('"+parents_code+"', '"+mother_name+"', '"+mother_ci+"', '"+mother_nation+"', '"+mother_work+"', '"+father_name+"', '"+father_ci+"', '"+father_nation+"', '"+father_work+"', '"+updatedAT+"'); " +

                "REPLACE INTO tutors (tutor_code, tutor_name, tutor_ci, tutor_nation, tutor_link, updatedAT) " +
                "VALUES ('"+tutor_code+"', '"+tutor_name+"', '"+tutor_ci+"', '"+tutor_nation+"', '"+tutor_link+"', '"+updatedAT+"');";

    }

    private String createRollbackQuery(){

        return "DELETE from students WHERE code = '"+code+"'; " +
                "DELETE from addresses WHERE student_code = '"+code+"'; " +
                "DELETE from contact_infos WHERE student_code = '"+code+"'; " +
                "DELETE from medical_infos WHERE student_code = '"+code+"';";

    }

    private JSONObject createMetadata(){
        JSONObject data = new JSONObject();
        try {
            data.put("name", name);
            data.put("lastname", lastName);
            data.put("ci", nation+ci);
            data.put("seccion", seccion);
            data.put("gender", gender);
            data.put("age", age);
            data.put("grade", grade);
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
        student.setTutor_code(tutor_code);
        return student;
    }

    public Register getInscriptionPaymentRegister(){
        Register inscriptionRegister = new CreateInscriptionRegister(context, data, tutor_code).getRegister();
        return inscriptionRegister;
    }

}
