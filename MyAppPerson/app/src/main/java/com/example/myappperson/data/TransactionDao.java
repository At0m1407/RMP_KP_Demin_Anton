package com.example.myappperson.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myappperson.model.Balance;

import java.util.ArrayList;

public class TransactionDao {
    private Database database;

    public TransactionDao(Context context) {
        database = new Database(context);
    }

    public boolean insertTransaction(String description, String category, double amount, String date) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("description", description);
        contentValues.put("category", category);
        contentValues.put("amount", amount);
        contentValues.put("date", date);

        // Учитываем прирост и убыток в зависимости от знака суммы
        if (amount < 0) {
            // Расход
            contentValues.put("amount", amount);
        } else {
            // Доход
            contentValues.put("amount", -amount);
        }

        long result = db.insert(Database.TABLE_TRANSACTIONS, null, contentValues);
        db.close();

        return result != -1;
    }


    public ArrayList<String> getAllTransactions() {
        ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM transactions", null);
        while (res.moveToNext()) {
            array_list.add("Transaction Amount: $" + res.getString(res.getColumnIndex("amount")) + "\n" +
                    "Description: " + res.getString(res.getColumnIndex("description")) + "\n" +
                    "Category: " + res.getString(res.getColumnIndex("category")) + "\n" +
                    "Date purchased: " + res.getString(res.getColumnIndex("date")) + "\n");
        }
        res.close();
        return array_list;
    }

    public void updateBalance(BalanceDao balanceDao) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(amount) FROM transactions", null);
        if (res.moveToFirst()) {
            double totalAmount = res.getDouble(0);
            res.close();

            // Получите текущий баланс с использованием BalanceDao
            Balance currentBalance = balanceDao.requestBalance();

            // Вычислите новый баланс
            double newBalance = currentBalance.getBalance() + totalAmount;

            // Обновите баланс с использованием BalanceDao
            balanceDao.updateAfterTransaction(new Balance(newBalance, currentBalance.getIncome()));
        }
        db.close();
    }


}
