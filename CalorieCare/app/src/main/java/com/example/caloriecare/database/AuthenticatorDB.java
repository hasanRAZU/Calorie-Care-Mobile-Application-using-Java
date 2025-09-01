package com.example.caloriecare.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.Date;

public class AuthenticatorDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "calorieCareDatabase.db";
    private static final int DATABASE_VERSION = 1;


    // Table and Column
    private static final String TABLE_NAME_REGISTRATION = "registration_info";
    private static final String COLUMN_SERIAL_NO = "serial_no";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_USERTYPE = "user_type";
    private static final String COLUMN_SECURITY_QUESTION = "security_question";
    private static final String COLUMN_SECURITY_ANSWER = "security_answer";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_TIME = "time";



    // Create Table
    /*CREATE TABLE table_name (
            column1 datatype,
            column2 datatype,
            column3 datatype
    );*/
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME_REGISTRATION + " ( " +
            COLUMN_SERIAL_NO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " VARCHAR(30) NOT NULL, " +
            COLUMN_EMAIL + " VARCHAR(40) NOT NULL, " +
            COLUMN_SECURITY_QUESTION + " VARCHAR(50) NOT NULL, " +
            COLUMN_SECURITY_ANSWER + " VARCHAR(20) NOT NULL, " +
            COLUMN_PASSWORD + " VARCHAR(20) NOT NULL, " +
            COLUMN_USERTYPE + " VARCHAR(12) NOT NULL, " +
            COLUMN_PHONE_NUMBER + " VARCHAR(11) NOT NULL, " +
            COLUMN_TIME + " VARCHAR(30) NOT NULL);";

    public AuthenticatorDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase db) {
        db .execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_REGISTRATION);
        onCreate(db);
    }



    // operation
    public long signUp(String name, String email, String phoneNumber, String password, String question, String answer, String userType){
        String time = String.valueOf(new Date());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_SECURITY_QUESTION, question);
        values.put(COLUMN_SECURITY_ANSWER, answer);
        values.put(COLUMN_USERTYPE, userType);
        values.put(COLUMN_TIME, time);

        return db.insert(TABLE_NAME_REGISTRATION, null, values);
    }

    public Cursor logIn(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_SERIAL_NO, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PHONE_NUMBER, COLUMN_PASSWORD, COLUMN_USERTYPE, COLUMN_SECURITY_QUESTION, COLUMN_SECURITY_ANSWER};
        String selections = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ? ";
        String[] selectionsArg = {email, password};

        return db.query(TABLE_NAME_REGISTRATION, columns, selections, selectionsArg, null, null, null);
    }


    public Cursor checkDuplicateAccount(String email, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_SERIAL_NO, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PHONE_NUMBER, COLUMN_PASSWORD, COLUMN_USERTYPE, COLUMN_SECURITY_QUESTION, COLUMN_SECURITY_ANSWER};
        String selections = COLUMN_EMAIL + " = ? OR " + COLUMN_PHONE_NUMBER + " = ? ";
        String[] selectionsArg = {email, phone};

        return db.query(TABLE_NAME_REGISTRATION, columns, selections, selectionsArg, null, null, null);
    }
}
