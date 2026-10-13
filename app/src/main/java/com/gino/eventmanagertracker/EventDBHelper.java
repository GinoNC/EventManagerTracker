package com.gino.eventmanagertracker;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
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
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select * From EventDB";
        Cursor cur = db.rawQuery(sql, null);

        ArrayList<EventModel> result = new ArrayList<>();
        if (cur.getCount() > 0) {
            do {
                EventModel evt = new EventModel();
                evt.id = cur.getInt(0);
                evt.eventName = cur.getString(1);
                evt.eventDate = cur.getString(2);
                evt.eventLocation = cur.getString(3);
                evt.eventOrganiser = cur.getString(4);
                evt.eventType = cur.getString(5);
                evt.eventStatus = cur.getString(6);
                result.add(evt);
            } while (cur.moveToNext());
        }

        return result;
    }

    public EventModel getSingle(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select * From EventDB Where id = " + String.valueOf(id);
        Cursor cur = db.rawQuery(sql, null);

        if (cur.getCount() > 0) {
            EventModel evt = new EventModel();
            evt.id = cur.getInt(0);
            evt.eventName = cur.getString(1);
            evt.eventDate = cur.getString(2);
            evt.eventLocation = cur.getString(3);
            evt.eventOrganiser = cur.getString(4);
            evt.eventType = cur.getString(5);
            evt.eventStatus = cur.getString(6);
            return evt;
        }
        else {
            return null;
        }
    }
    public EventModel getSingle(String eventName, Date eventDate) {
        return null;
    }

    public void updateRecord(EventModel data) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues val = new ContentValues();
        val.put("evName", data.eventName);
        val.put("evDate", data.eventDate);
        val.put("evLocation", data.eventLocation);
        val.put("evOrganiser", data.eventOrganiser);
        val.put("evType", data.eventType);
        val.put("evStatus", data.eventStatus);
        db.update("EventDB", val, "id = ?", new String[] { String.valueOf(data.id) });
        db.close();
    }

    public void deleteRecord(int id) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete("EventDB", "id = ?", new String[] { String.valueOf(id) });
        db.close();
    }
}
