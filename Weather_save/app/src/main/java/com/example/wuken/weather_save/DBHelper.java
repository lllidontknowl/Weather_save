package com.example.wuken.weather_save;

import static com.example.wuken.weather_save.DbConstants.TABLE_NAME;
import static android.provider.BaseColumns._ID;
import static com.example.wuken.weather_save.DbConstants.WEATHER;
import static com.example.wuken.weather_save.DbConstants.TEMP;
import static com.example.wuken.weather_save.DbConstants.DATE;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "demo.db";
    private final static int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WEATHER + " CHAR, " +
                TEMP + " CHAR, " +
                DATE + " CHAR);";
        db.execSQL(INIT_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
