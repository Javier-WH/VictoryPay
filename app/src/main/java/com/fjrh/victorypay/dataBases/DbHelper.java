package com.fjrh.victorypay.dataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.fjrh.victorypay.dataBases.strings.CreatorString;


public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 61;
    private static final String DATABASE_NAME = "batalla.db";




    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //crea la base de datos users
        db.execSQL(CreatorString.getCreateUserString());

        //crea la base de datos students
        db.execSQL(CreatorString.getCreateStudensString());

        //crea la base de datos parents
        db.execSQL(CreatorString.getCreateParentsString());

        //crea la base de datos tutors
        db.execSQL(CreatorString.getCreateTutorsString());

        //crea la base de datos contact_info
        db.execSQL(CreatorString.getCreateContactInfoString());

        //crea la base de datos medical_info
        db.execSQL(CreatorString.getCreateMedicalInfoString());

        //crea la base de datos address
        db.execSQL(CreatorString.getCreateAddressString());

        //crea la base de datos inscription_payment
        db.execSQL(CreatorString.getCreateInscriptionPaymentString());

        //crea la base de datos schools
        db.execSQL(CreatorString.getCreateSchoolsString());

        //crea la base de datos prices
        db.execSQL(CreatorString.getCreatePricesString());

        //crea la base de datos params
        db.execSQL(CreatorString.getCreateParamsString());

        //crea la base de datos monthControl
        db.execSQL(CreatorString.getCreateMonthControlString());

        //crea la base de datos abono
        db.execSQL(CreatorString.getCreateAbonoString());

        //crea la base de datos registers
        db.execSQL(CreatorString.getCreateRegisterString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS students");
        db.execSQL("DROP TABLE IF EXISTS parents");
        db.execSQL("DROP TABLE IF EXISTS tutors");
        db.execSQL("DROP TABLE IF EXISTS contact_infos");
        db.execSQL("DROP TABLE IF EXISTS medical_infos");
        db.execSQL("DROP TABLE IF EXISTS addresses");
        db.execSQL("DROP TABLE IF EXISTS inscription_payments");
        db.execSQL("DROP TABLE IF EXISTS schools");
        db.execSQL("DROP TABLE IF EXISTS prices");
        db.execSQL("DROP TABLE IF EXISTS params");
        db.execSQL("DROP TABLE IF EXISTS monthControls");
        db.execSQL("DROP TABLE IF EXISTS abonos");
        db.execSQL("DROP TABLE IF EXISTS abono");
        db.execSQL("DROP TABLE IF EXISTS registers");
        onCreate(db);

    }


}
