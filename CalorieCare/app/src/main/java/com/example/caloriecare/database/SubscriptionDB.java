package com.example.caloriecare.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SubscriptionDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "subscriptions.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SUBSCRIPTIONS = "subscriptions";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_SUBSCRIPTION_STATUS = "subscription_status";

    public SubscriptionDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SUBSCRIPTIONS_TABLE = "CREATE TABLE " + TABLE_SUBSCRIPTIONS + "("
                + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
                + COLUMN_SUBSCRIPTION_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_SUBSCRIPTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBSCRIPTIONS);
        onCreate(db);
    }

    public void addSubscription(String email, String subscriptionStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_SUBSCRIPTION_STATUS, subscriptionStatus);

        db.insert(TABLE_SUBSCRIPTIONS, null, values);
        db.close();
    }

    public int updateSubscriptionStatus(String email, String subscriptionStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBSCRIPTION_STATUS, subscriptionStatus);
        String selection = COLUMN_EMAIL +" = ?";
        String[] selectionArg ={email};

        return db.update(TABLE_SUBSCRIPTIONS, values, selection, selectionArg);
    }

    @SuppressLint("Range")
    public String getSubscriptionStatus(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SUBSCRIPTIONS, new String[]{COLUMN_SUBSCRIPTION_STATUS},
                COLUMN_EMAIL + " = ?", new String[]{email}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String status = cursor.getString(cursor.getColumnIndex("subscription_status"));
            cursor.close();
            return status;
        } else {
            return null;
        }
    }


    // testing [get all subscribed user]
    public String fetchAllTheTableData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SUBSCRIPTIONS, null);

        StringBuilder result = new StringBuilder();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                @SuppressLint("Range")
                String subscriptionStatus = cursor.getString(cursor.getColumnIndex(COLUMN_SUBSCRIPTION_STATUS));

                result.append("Email: ").append(email)
                        .append(", Subscription Status: ").append(subscriptionStatus)
                        .append("\n");
            } while (cursor.moveToNext());
        }

        cursor.close();
        return result.toString();
    }

}
