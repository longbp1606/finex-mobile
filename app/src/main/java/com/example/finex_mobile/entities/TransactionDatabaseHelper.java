package com.example.finex_mobile.entities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class TransactionDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "transactions.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COL_ID = "id";
    public static final String COL_BUDGET_ID = "budgetId";
    public static final String COL_CONTENT = "content";
    public static final String COL_CREATED_AT = "createdAt";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_TRANSACTIONS + " ("
            + COL_ID + " TEXT PRIMARY KEY, "
            + COL_BUDGET_ID + " TEXT, "
            + COL_CONTENT + " TEXT, "
            + COL_CREATED_AT + " TEXT"
            + ");";

    public TransactionDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    public long addTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, transaction.getId());
        values.put(COL_BUDGET_ID, transaction.getBudgetId());
        values.put(COL_CONTENT, transaction.getContent());
        values.put(COL_CREATED_AT, transaction.getCreatedAt());
        return db.insert(TABLE_TRANSACTIONS, null, values);
    }

    public int updateTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, transaction.getContent());
        values.put(COL_CREATED_AT, transaction.getCreatedAt());
        return db.update(TABLE_TRANSACTIONS, values, COL_ID + " = ?", new String[]{transaction.getId()});
    }

    public int deleteTransaction(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TRANSACTIONS, COL_ID + " = ?", new String[]{id});
    }

    public Transaction getTransactionById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRANSACTIONS, null, COL_ID + " = ?", new String[]{id}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Transaction transaction = new Transaction(
                cursor.getString(cursor.getColumnIndexOrThrow(COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_BUDGET_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTENT)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_CREATED_AT))
            );
            cursor.close();
            return transaction;
        }
        return null;
    }

    public List<Transaction> getTransactionsForBudget(String budgetId) {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRANSACTIONS, null, COL_BUDGET_ID + " = ?", new String[]{budgetId}, null, null, COL_CREATED_AT + " DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_BUDGET_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTENT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_CREATED_AT))
                );
                transactions.add(transaction);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return transactions;
    }

    public List<Transaction> searchTransactionsForBudget(String budgetId, String query) {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRANSACTIONS, null,
                COL_BUDGET_ID + " = ? AND " + COL_CONTENT + " LIKE ?",
                new String[]{budgetId, "%" + query + "%"}, null, null, COL_CREATED_AT + " DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_BUDGET_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTENT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_CREATED_AT))
                );
                transactions.add(transaction);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return transactions;
    }
} 