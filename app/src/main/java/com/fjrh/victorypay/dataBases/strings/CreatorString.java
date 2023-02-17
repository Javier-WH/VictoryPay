package com.fjrh.victorypay.dataBases.strings;

public class CreatorString {

    //users
    private static final String CreateUserString = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT NOT NULL, password TEXT NOT NULL)";
    //studens
    private static final String CreateStudensString = "CREATE TABLE students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, lastName TEXT NOT NULL, seccion TEXT NOT NULL, grade TEXT NOT NULL, gender TEXT NOT NULL, code TEXT NOT NULL UNIQUE, parent_id INTEGER NOT NULL, tutor_id INTEGER NOT NULL)";
    //parents
    private static final String CreateParentsString = "CREATE TABLE parents (id INTEGER PRIMARY KEY AUTOINCREMENT, mother_name TEXT NOT NULL, mother_ci TEXT NOT NULL,  mother_nation TEXT NOT NULL, father_name TEXT NOT NULL, father_ci TEXT NOT NULL, father_nation TEXT NOT NULL)";
    //tutors
    private static final String CreateTutorsString = "CREATE TABLE tutors (id INTEGER PRIMARY KEY AUTOINCREMENT, tutor_name TEXT NOT NULL, tutor_ci TEXT NOT NULL, tutor_nation TEXT NOT NULL)";
    //contact_info
    private static final String CreateContactInfoString = "CREATE TABLE contact_info (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER NOT NULL UNIQUE, phone1 TEXT NOT NULL, phone2 TEXT NOT NULL, email TEXT NOT NULL, whatsapp1 TEXT NOT NULL, whatsapp2 TEXT NOT NULL)";
    //schools
    private static final String CreateSchoolsString = "CREATE TABLE schools (id INTEGER PRIMARY KEY AUTOINCREMENT, school TEXT NOT NULL UNIQUE)";



    public static String getCreateUserString(){
        return CreateUserString;
    }
    public static String getCreateStudensString(){
        return CreateStudensString;
    }
    public static String getCreateParentsString(){return CreateParentsString;}
    public static String getCreateTutorsString(){return CreateTutorsString;}
    public static String getCreateContactInfoString(){return CreateContactInfoString;}
    public static String getCreateSchoolsString(){ return CreateSchoolsString;}
}
