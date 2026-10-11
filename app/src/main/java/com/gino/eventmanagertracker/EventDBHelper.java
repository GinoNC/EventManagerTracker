package com.gino.eventmanagertracker;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;


public class EventDBHelper extends SQLiteOpenHelper {
    public EventDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create Table EventDB(id integer primary key autoincrement, evName varchar(50), evDate varchar(10), evLocation varchar(100), " +
                "evOrganiser varchar(50), evType varchar(20), evStatus varchar(20))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addRecord(EventModel data) {
        ContentValues val = new ContentValues();
        val.put("evName", data.eventName);
        val.put("evDate", data.eventDate);
        val.put("evLocation", data.eventLocation);
        val.put("evOrganiser", data.eventOrganiser);
        val.put("evType", data.eventType);
        val.put("evStatus", data.eventStatus);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("EventDB", null, val);
        db.close();
    }

    public ArrayList<EventModel> getList() {
        ArrayList<EventModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select * from EventDB";
        //Cursor cursor = db.rawQuery(sql, null);
        return null;
    }

    public EventModel getSingle(int id) {
        return null;
    }
    public EventModel getSingle(String eventName, Date eventDate) {
        return null;
    }

    public void updateRecord(EventModel data) {

    }

    public void deleteRecord(int id) {

    }
}
