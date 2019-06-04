package com.example.doacoes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "DB";
    private static final int DATABASE_VERSION = 1;
    private final String CREATE_TABLE = "CREATE TABLE Doacoes (ID INTEGER PRIMARY KEY AUTOINCREMENT, Titulo TEXT NOT NULL, Descricao TEXT, Regiao TEXT NOT NULL, Responsavel TEXT NOT NULL, Tipo TEXT NOT NULL);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
