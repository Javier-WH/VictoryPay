package com.fjrh.victorypay.dataBases.strings;

public class CreatorString {

    //users
    private static final String CreateUserString = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT NOT NULL, password TEXT NOT NULL UNIQUE,  name TEXT NOT NULL, ci TEXT NOT NULL UNIQUE, updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";
    //studens
    private static final String CreateStudensString = "CREATE TABLE students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, lastName TEXT NOT NULL, ci TEXT NOT NULL UNIQUE, nation TEXT NOT NULL, seccion TEXT NOT NULL, grade TEXT NOT NULL, gender TEXT NOT NULL, code TEXT NOT NULL UNIQUE, birthdate TEXT NOT NULL, age TEXT NOT NULL, parents_code TEXT NOT NULL, tutor_code TEXT NOT NULL, updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";
    //address
    private static final String CreateAddressString = "CREATE TABLE addresses (id INTEGER PRIMARY KEY AUTOINCREMENT, student_code TEXT NOT NULL UNIQUE, birth_country TEXT NOT NULL, birth_state TEXT NOT NULL, birth_municipio TEXT NOT NULL, birth_parroquia TEXT NOT NULL, live_state TEXT NOT NULL, live_municipio TEXT NOT NULL, live_parroquia TEXT NOT NULL, address TEXT NOT NULL, procedence_school TEXT NOT NULL,updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";
    //medical_info
    private static final String CreateMedicalInfoString = "CREATE TABLE medical_infos (id INTEGER PRIMARY KEY AUTOINCREMENT, student_code TEXT NOT NULL UNIQUE, diabetes TEXT NOT NULL, hipertension TEXT NOT NULL, dislexia TEXT NOT NULL, daltonismo TEXT NOT NULL, epilepsia TEXT NOT NULL, asma TEXT NOT NULL, alergias TEXT NOT NULL, TDAH TEXT NOT NULL, observations TEXT NOT NULL, updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";
    //parents
    private static final String CreateParentsString = "CREATE TABLE parents (id INTEGER PRIMARY KEY AUTOINCREMENT, parents_code TEXT NOT NULL UNIQUE, mother_name TEXT NOT NULL, mother_ci TEXT NOT NULL,  mother_nation TEXT NOT NULL, mother_work TEXT NOT NULL, father_name TEXT NOT NULL, father_ci TEXT NOT NULL, father_nation TEXT NOT NULL, father_work TEXT NOT NULL, updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";
    //tutors
    private static final String CreateTutorsString = "CREATE TABLE tutors (id INTEGER PRIMARY KEY AUTOINCREMENT, tutor_code TEXT NOT NULL UNIQUE, tutor_name TEXT NOT NULL, tutor_ci TEXT NOT NULL, tutor_nation TEXT NOT NULL, tutor_link TEXT NOT NULL, updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";
    //contact_info
    private static final String CreateContactInfoString = "CREATE TABLE contact_infos (id INTEGER PRIMARY KEY AUTOINCREMENT, student_code TEXT NOT NULL UNIQUE, phone1 TEXT NOT NULL, phone2 TEXT NOT NULL, email TEXT, whatsaap1 TEXT, whatsaap2 TEXT, updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";
    //inscription_payment
    private static  final String CreateInscriptionPaymentString = "CREATE TABLE inscription_payments (id INTEGER PRIMARY KEY AUTOINCREMENT, student_code TEXT NOT NULL UNIQUE, inscription TEXT NOT NULL, cash TEXT NOT NULL, operation_number TEXT NOT NULL, date TEXT NOT NULL, status TEXT NOT NULL, updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";
    //schools
    private static final String CreateSchoolsString = "CREATE TABLE schools (id INTEGER PRIMARY KEY AUTOINCREMENT, school TEXT NOT NULL UNIQUE, updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";
    //prices
    private static final String CreatePricesString = "CREATE TABLE prices (id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT NOT NULL UNIQUE, price TEXT NOT NULL, updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";
    //params
    private static final String CreateParamsString = "CREATE TABLE params (id INTEGER PRIMARY KEY AUTOINCREMENT, param TEXT NOT NULL UNIQUE, value TEXT NOT NULL )";
    //monthControl
    private static final String CreateMonthControlString = "CREATE TABLE monthControls (id INTEGER PRIMARY KEY AUTOINCREMENT, student_code TEXT NOT NULL UNIQUE, control TEXT NOT NULL, updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";
    //abono
    private static final String CreateAbonoString = " CREATE TABLE abonos (id INTEGER PRIMARY KEY AUTOINCREMENT, tutor_code TEXT NOT NULL UNIQUE, abono TEXT NOT NULL UNIQUE, updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";

    //registers
    private static final String CreateRegisterString = "CREATE TABLE registers (register_code TEXT NOT NULL UNIQUE, user TEXT NOT NULL, description TEXT NOT NULL, type TEXT NOT NULL, insertion_query TEXT NOT NULL, rollback_query TEXT NOT NULL, metadata TEXT NOT NULL, updatedAT TEXT DEFAULT (strftime('%m/%d/%Y %H:%M:%S', 'now', 'localtime')))";




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
    public static String getCreatePricesString(){ return CreatePricesString;}
    public static String getCreateParamsString(){return CreateParamsString;}
    public static String getCreateMonthControlString(){return CreateMonthControlString;}
    public static String getCreateAbonoString(){return  CreateAbonoString;}
    public static String getCreateRegisterString(){return  CreateRegisterString;}
}
