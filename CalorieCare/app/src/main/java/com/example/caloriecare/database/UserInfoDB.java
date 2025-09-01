package com.example.caloriecare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserInfoDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userInfo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME_USER_INFO = "userInfo";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_OCCUPATION = "occupation";
    private static final String COLUMN_GENDER_TYPE = "gender_type";
    private static final String COLUMN_DIABETIC_STATUS = "diabetic_status";
    private static final String COLUMN_ALLERGY_STATUS = "allergy_status";
    private static final String COLUMN_DIET_STATUS = "diet_status";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_UPDATED_BY_NUTRITIONIST = "updated_by_nutritionist_status";
    private static final String COLUMN_SUBSCRIPTION_STATUS = "subscription_status";



    private static final String CREATE_TABLE_USER_INFO =
            "CREATE TABLE " + TABLE_NAME_USER_INFO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " VARCHAR(30) NOT NULL, " +
                    COLUMN_EMAIL + " VARCHAR(30) NOT NULL, " +
                    COLUMN_AGE + " INTEGER NOT NULL, " +
                    COLUMN_HEIGHT + " INTEGER NOT NULL, " +
                    COLUMN_WEIGHT + " INTEGER NOT NULL, " +
                    COLUMN_OCCUPATION + " VARCHAR(20) NOT NULL, " +
                    COLUMN_GENDER_TYPE + " VARCHAR(10) NOT NULL, " +
                    COLUMN_DIABETIC_STATUS + " VARCHAR(5) NOT NULL, " +
                    COLUMN_ALLERGY_STATUS + " VARCHAR(5) NOT NULL, " +
                    COLUMN_TIME + " VARCHAR(30) NOT NULL, " +
                    COLUMN_UPDATED_BY_NUTRITIONIST + " VARCHAR(10) NOT NULL, " +
                    COLUMN_SUBSCRIPTION_STATUS + " VARCHAR(10) NOT NULL, " +
                    COLUMN_DIET_STATUS + " VARCHAR(10) NOT NULL);";

    public UserInfoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER_INFO);
        onCreate(db);
    }

    // Method to store user info
    public void storeUserInfo(String name, String email, String age, String height, String weight, String occupation, String genderType, String diabeticStatus, String allergyStatus, String dietStatus, String updatedByNutritionistStatus, String subscriptionStatus) {
        String time =String.valueOf(new Date());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_OCCUPATION, occupation);
        values.put(COLUMN_GENDER_TYPE, genderType);
        values.put(COLUMN_DIABETIC_STATUS, diabeticStatus);
        values.put(COLUMN_ALLERGY_STATUS, allergyStatus);
        values.put(COLUMN_DIET_STATUS, dietStatus);
        values.put(COLUMN_UPDATED_BY_NUTRITIONIST, updatedByNutritionistStatus);
        values.put(COLUMN_SUBSCRIPTION_STATUS, subscriptionStatus);
        values.put(COLUMN_TIME, time);

        db.insert(TABLE_NAME_USER_INFO, null, values);
    }

    // Method to fetch all data based on age, height, and weight (in UploadPrescriptionClass)
    public Cursor fetchAllData(String userName, String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_AGE, COLUMN_HEIGHT, COLUMN_WEIGHT, COLUMN_OCCUPATION, COLUMN_GENDER_TYPE, COLUMN_DIABETIC_STATUS, COLUMN_ALLERGY_STATUS, COLUMN_DIET_STATUS, COLUMN_UPDATED_BY_NUTRITIONIST, COLUMN_SUBSCRIPTION_STATUS, COLUMN_TIME};
        String selection = COLUMN_NAME + " = ? AND " + COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {userName, userEmail};

        return db.query(TABLE_NAME_USER_INFO, columns, selection, selectionArgs, null, null, null);
    }

    // Method to update user info based on name and email
    public int updateUserInfo(String name, String email, String age, String height, String weight, String occupation, String genderType, String diabeticStatus, String allergyStatus, String dietStatus, String updatedByNutritionist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_AGE, age);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_OCCUPATION, occupation);
        values.put(COLUMN_GENDER_TYPE, genderType);
        values.put(COLUMN_DIABETIC_STATUS, diabeticStatus);
        values.put(COLUMN_ALLERGY_STATUS, allergyStatus);
        values.put(COLUMN_DIET_STATUS, dietStatus);
        values.put(COLUMN_UPDATED_BY_NUTRITIONIST, updatedByNutritionist);

        String selection = COLUMN_NAME + " = ? AND " + COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {name, email};

        return db.update(TABLE_NAME_USER_INFO, values, selection, selectionArgs);
    }




    // Fetch All Data by Nutritionist

    public List<String> fetchAllUserInfo() {
        List<String> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_AGE, COLUMN_HEIGHT, COLUMN_WEIGHT, COLUMN_OCCUPATION, COLUMN_GENDER_TYPE, COLUMN_DIABETIC_STATUS, COLUMN_ALLERGY_STATUS, COLUMN_DIET_STATUS, COLUMN_UPDATED_BY_NUTRITIONIST, COLUMN_TIME};

        Cursor cursor = db.query(TABLE_NAME_USER_INFO, columns, null,null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String userData = "ID: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)) + "\n" +
                        "Name: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)) + "\n" +
                        "Email: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)) + "\n" +
                        "Age: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AGE)) + "\n" +
                        "Height: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HEIGHT)) + "\n" +
                        "Weight: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT)) + "\n" +
                        "Occupation: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OCCUPATION)) + "\n" +
                        "Gender: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER_TYPE)) + "\n" +
                        "Diabetic Status: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIABETIC_STATUS)) + "\n" +
                        "Allergy Status: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ALLERGY_STATUS)) + "\n" +
                        "Diet Status: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIET_STATUS)) + "\n" +
                        "Updated By Nutritionist: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UPDATED_BY_NUTRITIONIST)) + "\n" +
                        "Time: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)) + "\n\n";

                data.add(userData);
            }
            cursor.close();
        }
        return data;
    }

    public Cursor getEmailById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_EMAIL};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return db.query(TABLE_NAME_USER_INFO, columns, selection, selectionArgs, null, null, null);
    }

    public int updateNutritionistStatus(String email, String nutritionistStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_UPDATED_BY_NUTRITIONIST, nutritionistStatus);

        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        return db.update(TABLE_NAME_USER_INFO, values, selection, selectionArgs);
    }

}
