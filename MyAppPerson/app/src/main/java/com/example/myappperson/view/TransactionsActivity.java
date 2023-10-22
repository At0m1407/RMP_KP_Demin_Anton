package com.example.myappperson.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myappperson.MainActivity;
import com.example.myappperson.R;
import com.example.myappperson.data.BalanceDao;
import com.example.myappperson.data.Database;
import com.example.myappperson.data.TransactionDao;
import com.example.myappperson.model.Balance;
import com.example.myappperson.presenter.TransactionsInterface;
import com.example.myappperson.presenter.TransactionsListener;
import com.example.myappperson.presenter.TransactionsPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TransactionsActivity extends AppCompatActivity implements TransactionsInterface {
    private EditText description, amount, dateRecorded;
    private Spinner spCategory;
    private Button btnClear, btnSubmit;
    private TransactionDao tDao;
    private BalanceDao balanceDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        balanceDao = new BalanceDao(this);
        tDao = new TransactionDao(this);

        description = findViewById(R.id.editTextDescription);
        amount = findViewById(R.id.editTextAmount);
        dateRecorded = findViewById(R.id.editTextDate);
        spCategory = findViewById(R.id.spCategory);
        btnClear = findViewById(R.id.btnClear);
        btnSubmit = findViewById(R.id.btnSubmit);
        CategorySpinner();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy", Locale.getDefault());
        dateRecorded.setText(df.format(c));
        dateRecorded.setEnabled(true);
        findViewById(R.id.arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (insertTransaction()) {
                    updateBalance();
                }
            }
        });
    }

    public void clear() {
        description.requestFocus();
        description.setText(null);
        amount.setText(null);
        dateRecorded.setText(null);
        spCategory.setSelection(0);
    }

    public void CategorySpinner() {
        final String[] Category = {
                this.getResources().getString(R.string.bills),
                this.getResources().getString(R.string.education),
                this.getResources().getString(R.string.entertainment),
                this.getResources().getString(R.string.food_dining),
                this.getResources().getString(R.string.health_fitness),
                this.getResources().getString(R.string.other),
                this.getResources().getString(R.string.personal_care),
                this.getResources().getString(R.string.shopping),
                this.getResources().getString(R.string.transportation),
                this.getResources().getString(R.string.travel)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);
    }

    public boolean insertTransaction() {
        String transDescription = getTransDescription();
        String transCategory = getTransCategory();
        double transAmount = Double.parseDouble(getTransAmount());
        String transDate = getTransActualDate();

        // Insert the transaction into the database
        boolean inserted = tDao.insertTransaction(transDescription, transCategory, transAmount, transDate);

        if (inserted) {
            Toast.makeText(this, "Transaction inserted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to insert transaction", Toast.LENGTH_SHORT).show();
        }

        // Retrieve the updated balance from BalanceDao
        BalanceDao balanceDao = new BalanceDao(this);
        Balance balance = balanceDao.requestBalance();
        double updatedBalance = balance.getBalance();

        Intent intent = new Intent(this, BalanceActivity.class);
        intent.putExtra("updatedBalance", updatedBalance);
        startActivityForResult(intent, 1);

        return inserted;
    }

    public void updateBalance() {
        tDao.updateBalance(balanceDao);
    }

    @Override
    public String getTransDescription() {
        return description.getText().toString();
    }

    @Override
    public String getTransAmount() {
        // Amount is negative for expenses
        return "-" + amount.getText().toString();
    }

    @Override
    public String getTransActualDate() {
        return dateRecorded.getText().toString();
    }

    @Override
    public String getTransCategory() {
        return spCategory.getSelectedItem().toString();
    }
}
