package com.fjrh.victorypay.dataBases.strings;

public class CreatorString {

    //users
    private static final String CreateUserString = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT NOT NULL, password TEXT NOT NULL)";
    //studens
    private static final String CreateStudensString = "CREATE TABLE students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, lastName TEXT NOT NULL, ci TEXT NOT NULL, nation TEXT NOT NULL, seccion TEXT NOT NULL, grade TEXT NOT NULL, gender TEXT NOT NULL, code TEXT NOT NULL UNIQUE, birthdate TEXT NOT NULL UNIQUE, age TEXT NOT NULL UNIQUE, parent_id INTEGER NOT NULL, tutor_id INTEGER NOT NULL)";
    //address
    private static final String CreateAddressString = "CREATE TABLE address (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id TEXT NOT NULL UNIQUE, birth_country TEXT NOT NULL, birth_state TEXT NOT NULL, birth_municipio TEXT NOT NULL, birth_parroquia TEXT NOT NULL, live_state TEXT NOT NULL, live_municipio TEXT NOT NULL, live_parroquia TEXT NOT NULL, address TEXT NOT NULL, procedence_school TEXT NOT NULL)";
    //medical_info
    private static final String CreateMedicalInfoString = "CREATE TABLE medical_info (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER NOT NULL UNIQUE, diabetes TEXT NOT NULL, hipertension TEXT NOT NULL, dislexia TEXT NOT NULL, daltonismo TEXT NOT NULL, epilepsia TEXT NOT NULL, asma TEXT NOT NULL, alergias TEXT NOT NULL, TDAH TEXT NOT NULL, observations TEXT NOT NULL)";
    //parents
    private static final String CreateParentsString = "CREATE TABLE parents (id INTEGER PRIMARY KEY AUTOINCREMENT, mother_name TEXT NOT NULL, mother_ci TEXT NOT NULL,  mother_nation TEXT NOT NULL, mother_work TEXT NOT NULL, father_name TEXT NOT NULL, father_ci TEXT NOT NULL, father_nation TEXT NOT NULL, father_work TEXT NOT NULL)";
    //tutors
    private static final String CreateTutorsString = "CREATE TABLE tutors (id INTEGER PRIMARY KEY AUTOINCREMENT, tutor_name TEXT NOT NULL, tutor_ci TEXT NOT NULL, tutor_nation TEXT NOT NULL, tutor_link TEXT NOT NULL)";
    //contact_info
    private static final String CreateContactInfoString = "CREATE TABLE contact_info (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER NOT NULL UNIQUE, phone1 TEXT NOT NULL, phone2 TEXT NOT NULL, email TEXT NOT NULL, whatsapp1 TEXT NOT NULL, whatsapp2 TEXT NOT NULL)";
    //inscription_payment
    private static  final String CreateInscriptionPaymentString = "CREATE TABLE inscription_payment (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER NOT NULL UNIQUE, inscription TEXT NOT NULL, cash TEXT NOT NULL, operation_number TEXT NOT NULL, date TEXT NOT NULL)";
    //schools
    private static final String CreateSchoolsString = "CREATE TABLE schools (id INTEGER PRIMARY KEY AUTOINCREMENT, school TEXT NOT NULL UNIQUE)";



    public static String getCreateUserString(){
        return CreateUserString;
    }
    public static String getCreateStudensString(){
        return CreateStudensString;
    }
    public static String getCreateAddressString(){ return CreateAddressString;}
    public static String getCreateMedicalInfoString(){ return CreateMedicalInfoString;}
    public static String getCreateParentsString(){return CreateParentsString;}
    public static String getCreateTutorsString(){return CreateTutorsString;}
    public static String getCreateContactInfoString(){return CreateContactInfoString;}
    public static String getCreateInscriptionPaymentString(){ return CreateInscriptionPaymentString;}
    public static String getCreateSchoolsString(){ return CreateSchoolsString;}



}
