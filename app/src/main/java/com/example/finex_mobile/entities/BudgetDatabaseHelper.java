package com.example.finex_mobile.entities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class BudgetDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "budgets.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_BUDGETS = "budgets";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_CURRENCY_UNIT = "currencyUnit";
    public static final String COL_LANGUAGE = "language";
    public static final String COL_CREATED_AT = "createdAt";
    public static final String COL_UPDATED_AT = "updatedAt";
    public static final String COL_IS_ANALYZED = "isAnalyzed";
    public static final String COL_IS_DELETED = "isDeleted";
    public static final String COL_MONEY = "money";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_BUDGETS + " ("
            + COL_ID + " TEXT PRIMARY KEY, "
            + COL_TITLE + " TEXT, "
            + COL_CURRENCY_UNIT + " TEXT, "
            + COL_LANGUAGE + " TEXT, "
            + COL_CREATED_AT + " TEXT, "
            + COL_UPDATED_AT + " TEXT, "
            + COL_IS_ANALYZED + " INTEGER, "
            + COL_IS_DELETED + " INTEGER, "
            + COL_MONEY + " REAL"
            + ");";

    public BudgetDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGETS);
        onCreate(db);
    }

    public long addBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, budget.getId());
        values.put(COL_TITLE, budget.getTitle());
        values.put(COL_CURRENCY_UNIT, budget.getCurrencyUnit());
        values.put(COL_LANGUAGE, budget.getLanguage());
        values.put(COL_CREATED_AT, budget.getCreatedAt());
        values.put(COL_UPDATED_AT, budget.getUpdatedAt());
        values.put(COL_IS_ANALYZED, budget.isAnalyzed() ? 1 : 0);
        values.put(COL_IS_DELETED, budget.isDeleted() ? 1 : 0);
        values.put(COL_MONEY, budget.getMoney());
        return db.insert(TABLE_BUDGETS, null, values);
    }

    public int updateBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, budget.getTitle());
        values.put(COL_CURRENCY_UNIT, budget.getCurrencyUnit());
        values.put(COL_LANGUAGE, budget.getLanguage());
        values.put(COL_CREATED_AT, budget.getCreatedAt());
        values.put(COL_UPDATED_AT, budget.getUpdatedAt());
        values.put(COL_IS_ANALYZED, budget.isAnalyzed() ? 1 : 0);
        values.put(COL_IS_DELETED, budget.isDeleted() ? 1 : 0);
        values.put(COL_MONEY, budget.getMoney());
        return db.update(TABLE_BUDGETS, values, COL_ID + " = ?", new String[]{budget.getId()});
    }

    public int deleteBudget(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BUDGETS, COL_ID + " = ?", new String[]{id});
    }

    public List<Budget> getAllBudgets() {
        List<Budget> budgets = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BUDGETS, null, null, null, null, null, COL_CREATED_AT + " DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Budget budget = new Budget(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CURRENCY_UNIT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_LANGUAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CREATED_AT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_UPDATED_AT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_IS_ANALYZED)) == 1,
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_IS_DELETED)) == 1,
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_MONEY))
                );
                budgets.add(budget);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return budgets;
    }
} 