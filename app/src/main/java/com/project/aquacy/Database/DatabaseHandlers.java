package com.project.aquacy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.project.aquacy.CommonFile.Factory;

public class DatabaseHandlers extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MainInfo.db";

    public DatabaseHandlers(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);

    }

    public DatabaseHandlers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static final String TABLE_LOGIN_SETTING = "tableSetting";
    public static final String TABLE_PLANTMASTER = "palnttable";
    public static final String TABLE_CLIENT_DATALIST = "ClientDataList";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Factory.CREATE_TABLE_LOGIN_SETTING);
        db.execSQL(Factory.CREATE_TABLE_PLANTMASTER);
        db.execSQL(Factory.CREATE_CLIENT_DATALIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN_SETTING);
        db.execSQL(Factory.CREATE_TABLE_LOGIN_SETTING);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTMASTER);
        db.execSQL(Factory.CREATE_TABLE_PLANTMASTER);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT_DATALIST);
        db.execSQL(Factory.CREATE_CLIENT_DATALIST);



    }


}
