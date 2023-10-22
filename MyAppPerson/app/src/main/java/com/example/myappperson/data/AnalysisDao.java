package com.example.myappperson.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myappperson.model.BudgetAnalysis;

import java.util.ArrayList;

public class AnalysisDao {
    private static Database database;

    public AnalysisDao(Context context) {
        database = new Database(context);
    }

    public void insertBudgetForAnalysis(String category,int amount, double spent,double balance,double progress) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("category", category);
        content.put("goalAmount", amount);
        content.put("spentAmount", spent);
        content.put("balanceAmount", balance);
        content.put("progress", progress);
        db.insert(Database.TABLE_BUDGET_ANALYSIS, null, content);
        db.close();
    }
    public static boolean updateAfterTransaction(BudgetAnalysis b) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues content = new ContentValues();
        //content.put("category", b.getBudgetCategory());
        //content.put("goalAmount", amount);
        content.put("spentAmount", b.getAmountSpent());
        content.put("balanceAmount", b.getBudgetBalance());
        content.put("progress", b.getProgress());
        db.update(Database.TABLE_BUDGET_ANALYSIS, content, "category = ? ", new String[] {b.getBudgetCategory()} );
        return true;
    }
    public static BudgetAnalysis selectBudget(String category) {
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "select * from " + Database.TABLE_BUDGET_ANALYSIS + " where category = '" + category + "';";
        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();
        BudgetAnalysis b = new BudgetAnalysis( result.getString(1),result.getInt(2), result.getDouble(3), result.getDouble(4), result.getDouble(5));
        result.close();
        db.close();
        return b;
    }

    public ArrayList<BudgetAnalysis> getAllBudgetingData() {
        ArrayList<BudgetAnalysis> budgetingData = new ArrayList<BudgetAnalysis>();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from budget_analysis", null);

        while (res.moveToNext()) {
            BudgetAnalysis b = new BudgetAnalysis(res.getString(res.getColumnIndex("category")),
                    res.getInt(res.getColumnIndex("goalAmount")),
                    res.getDouble(res.getColumnIndex("spentAmount")),
                    res.getDouble(res.getColumnIndex("balanceAmount")),
                    res.getDouble(res.getColumnIndex("progress")));
            budgetingData.add(b);
        }

        res.close();
        db.close();

        return budgetingData;
    }





}