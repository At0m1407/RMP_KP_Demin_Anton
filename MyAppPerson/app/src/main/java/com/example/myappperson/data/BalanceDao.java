package com.example.myappperson.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import com.example.myappperson.model.Balance;
public class BalanceDao {

    private Database database;

    public BalanceDao(Context context) {
        database = new Database(context);
    }

    public void insertIncome(double balance, double addIncome) {
        try (SQLiteDatabase db = database.getWritableDatabase()) {
            ContentValues content = new ContentValues();
            content.put("balance", balance);
            content.put("income", addIncome);
            db.insert(Database.TABLE_BALANCE, null, content);
        }
    }

    public boolean updateAfterTransaction(Balance b) {
        try (SQLiteDatabase db = database.getWritableDatabase()) {
            ContentValues content = new ContentValues();
            content.put("balance", b.getBalance());
            content.put("income", b.getIncome());
            int rowsUpdated = db.update(Database.TABLE_BALANCE, content, "id = ?", new String[]{"1"});
            return rowsUpdated > 0;
        }
    }

    public Balance requestBalance() {
        try (SQLiteDatabase db = database.getReadableDatabase()) {
            int id = 1;
            String sql = "SELECT * FROM " + Database.TABLE_BALANCE + " WHERE id = ?";
            Cursor result = db.rawQuery(sql, new String[]{String.valueOf(id)});

            if (result.moveToFirst()) {
                Balance b = new Balance(result.getDouble(result.getColumnIndex("balance")), result.getDouble(result.getColumnIndex("income")));
                result.close();
                return b;
            } else {
                result.close();
                return new Balance(0.0, 0.0);
            }
        }
    }
}
