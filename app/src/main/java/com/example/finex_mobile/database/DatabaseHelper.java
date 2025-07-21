package com.example.finex_mobile.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "finex_db";
    private static final int DB_VER = 1;
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context ctx) {
        if (instance == null) {
            instance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORIES = "CREATE TABLE categories (" +
                "id TEXT PRIMARY KEY, " +
                "name TEXT, " +
                "language TEXT" +
                ");";
        db.execSQL(CREATE_CATEGORIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // handle migrations if needed
        db.execSQL("DROP TABLE IF EXISTS categories");
        onCreate(db);
    }
}

