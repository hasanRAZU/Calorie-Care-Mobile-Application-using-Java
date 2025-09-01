package com.example.caloriecare.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyPlanDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dailyPlan.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME_STORE_DAILY_PLAN = "daily_plan";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_DATA = "data";

    private static final String CREATE_TABLE_ALL_UPDATE =
            "CREATE TABLE " + TABLE_NAME_STORE_DAILY_PLAN + " (" +
                    COLUMN_EMAIL + " VARCHAR(30) NOT NULL, " +
                    COLUMN_TIME + " VARCHAR(30) NOT NULL, " +
                    COLUMN_DATA + " VARCHAR(300) NOT NULL);";

    public DailyPlanDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALL_UPDATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STORE_DAILY_PLAN);
        onCreate(db);
    }

    public long storeAllData(String email, String info) {
        String time = String.valueOf(new Date());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_DATA, info);
        return db.insert(TABLE_NAME_STORE_DAILY_PLAN, null, values);
    }


    public Cursor fetchDataByEmail(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME_STORE_DAILY_PLAN, new String[]{COLUMN_DATA, COLUMN_TIME},
                COLUMN_EMAIL + "=?", new String[]{String.valueOf(userEmail)}, null, null, null);
    }
}
