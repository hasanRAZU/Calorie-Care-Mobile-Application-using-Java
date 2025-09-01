package com.example.caloriecare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientBioDataDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database_name.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME_STORE_ALL_UPDATE = "allUpdate";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_DATA = "data";

    private static final String CREATE_TABLE_ALL_UPDATE =
            "CREATE TABLE " + TABLE_NAME_STORE_ALL_UPDATE + " (" +
                    COLUMN_EMAIL + " VARCHAR(30) NOT NULL, " +
                    COLUMN_TIME + " VARCHAR(30) NOT NULL, " +
                    COLUMN_DATA + " VARCHAR(300) NOT NULL);";

    public ClientBioDataDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALL_UPDATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STORE_ALL_UPDATE);
        onCreate(db);
    }

    public long storeAllData(String email, String info) {
        String time = String.valueOf(new Date());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_DATA, info);
        return db.insert(TABLE_NAME_STORE_ALL_UPDATE, null, values);
    }


    public List<String> fetchAllData(String userEmail) {
        List<String> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_STORE_ALL_UPDATE,
                new String[]{COLUMN_DATA},
                COLUMN_EMAIL + "=?",
                new String[]{String.valueOf(userEmail)},
                null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                data.add(cursor.getString(cursor.getColumnIndexOrThrow("data")));
            }
            cursor.close();
        }
        return data;
    }
}
