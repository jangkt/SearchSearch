package com.server1.addserver1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CategoryDBHelper extends SQLiteOpenHelper {
    public CategoryDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table if not exists setCategory(categoryname text not null, categoryinfo text not null)");
        db.execSQL("Create table if not exists getCategory(categoryname text not null, categoryinfo text not null)");
        db.execSQL("Create table if not exists serverName(server text not null, serverURL text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists category");
        onCreate(db);
    }
}
