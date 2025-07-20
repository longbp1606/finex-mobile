package com.example.finex_mobile.entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finex_mobile.entities.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryDAO {
    private final DatabaseHelper dbHelper;

    public CategoryDAO(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public long insertCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String id = category.getId();
        if (id == null) id = UUID.randomUUID().toString();
        cv.put("id", id);
        cv.put("name", category.getName());
        cv.put("language", category.getLanguage());
        long res = db.insert("categories", null, cv);
        db.close();
        return res;
    }

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("categories", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                String id = c.getString(c.getColumnIndexOrThrow("id"));
                String name = c.getString(c.getColumnIndexOrThrow("name"));
                String lang = c.getString(c.getColumnIndexOrThrow("language"));
                list.add(new Category(id, name, lang));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public int updateCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", category.getName());
        cv.put("language", category.getLanguage());
        int res = db.update("categories", cv, "id=?", new String[]{category.getId()});
        db.close();
        return res;
    }

    public int deleteCategory(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int res = db.delete("categories", "id=?", new String[]{id});
        db.close();
        return res;
    }
}