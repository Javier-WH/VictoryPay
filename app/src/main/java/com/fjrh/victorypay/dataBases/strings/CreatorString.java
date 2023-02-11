package com.fjrh.victorypay.dataBases.strings;

public class CreatorString {

    private static final String CreateUserString = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT NOT NULL, password TEXT NOT NULL)";
    private static final String CreateStudensString = "CREATE TABLE students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, lastName TEXT NOT NULL, seccion TEXT NOT NULL, grade TEXT NOT NULL, code TEXT NOT NULL)";


    public static String getCreateUserString(){
        return CreateUserString;
    }
    public static String getCreateStudensString(){
        return CreateStudensString;
    }
}
