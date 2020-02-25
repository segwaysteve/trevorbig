package com.example.trevorbig;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "swag.db";
    public static final String TABLE_NAME = "GETBIG";
    public static final String col_1 = "ID";
    public static final String col_2 = "workout";
    public static final String col_3 = "date";
    public static final String col_4 = "time";
    public static final String col_5 = "notes";
    public static final String col_6 = "duration";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "workout TEXT,"
                + "date TEXT," + "time TEXT," + "notes TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public boolean addWorkout(String workout, String date, String time, String duration, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, workout);
        contentValues.put(col_3, date);
        contentValues.put(col_4, time);
        contentValues.put(col_5, notes);
        contentValues.put(col_6, duration);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }



    public void updateWorkout (Integer id, String workout, String date, String time, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, workout);
        contentValues.put(col_3, date);
        contentValues.put(col_4, time);
        contentValues.put(col_5, notes);
        db.update("Workout", contentValues, col_1 + " = " + id, null);
    }

    public void deleteWorkout(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME +
                " WHERE " + col_1 + " = " + id);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE " + id + " = " + id);
    }


    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +
                " where " + col_1 + " = " + id + " ", null);
        return res;
    }

    public String getWorkout(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +
                " where " + col_1 + " = " + id + " ", null);
        String workout= "nothing";
        if (res.moveToFirst()) {
            workout = res.getString(res.getColumnIndex(col_2));
        }
        return workout;
    }


    public String getModeWorkout() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(TABLE_NAME, new String[] {col_1, col_2, col_3, col_4, col_5}, null,
                null, col_2, "COUNT (*) = ( SELECT MAX(Cnt) FROM( SELECT COUNT(*) as Cnt FROM " + TABLE_NAME + " GROUP BY " + col_2 + " ) tmp )",
                null, null);
        String mode = null;
        if (res.moveToFirst()) {
            mode = res.getString(res.getColumnIndex(col_2));
        }
        return mode;
    }


    public ArrayList<String> getAllDistinctWorkouts() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.query(true, TABLE_NAME, new String[] {col_1, col_2, col_3, col_4, col_5},
                null, null, col_2, null, null, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(col_2)));
            res.moveToNext();
        }

        return array_list;
    }

    public ArrayList<String> getDayWorkouts(String date) {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("select * from GETBIG", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            if (res.getString(res.getColumnIndex(col_3)).equals(date)) {
                array_list.add(res.getString(res.getColumnIndex(col_2)));
            }
            res.moveToNext();
        }

        return array_list;
    }

}


