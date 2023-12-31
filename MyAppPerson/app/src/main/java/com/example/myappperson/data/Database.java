package com.example.myappperson.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1 ;
    public static final String DATABASE_NAME = "myBudget.db";
    public static final String TABLE_BUDGET = "budget";
    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String TABLE_BALANCE = "balance";
    public static final String TABLE_BUDGET_ANALYSIS = "budget_analysis";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_BUDGET +
                "(budgetId integer primary key autoincrement," +
                "amount real not null, " +
                "category text not null); "
        );
        db.execSQL("create table " + TABLE_TRANSACTIONS +
                "(id integer primary key autoincrement," +
                "description text not null," +
                "category text not null, " +
                "amount real not null, " +
                "date text not null);"
        );
        db.execSQL("create table " + TABLE_BALANCE +
                "(id integer primary key autoincrement," +
                "balance real not null, " +
                "income real not null); "
        );
        db.execSQL("create table " + TABLE_BUDGET_ANALYSIS +
                "(id integer primary key autoincrement," +
                "category text not null, " +
                "goalAmount real not null, " +
                "spentAmount real not null, " +
                "balanceAmount real not null, " +
                "progress real not null) ; "
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BUDGET);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TRANSACTIONS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BALANCE);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BUDGET_ANALYSIS);
        onCreate(db);
    }

}
