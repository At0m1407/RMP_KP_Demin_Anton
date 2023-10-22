package com.example.myappperson.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myappperson.MainActivity;
import com.example.myappperson.R;
import com.example.myappperson.data.BalanceDao;
import com.example.myappperson.model.Balance;
import com.example.myappperson.presenter.BalanceInterface;
import com.example.myappperson.presenter.TransactionsListener;


public class BalanceActivity extends AppCompatActivity implements BalanceInterface {
    private EditText etAmount;
    private BalanceDao balanceDao;
    private TextView tvBalance;
    private TextView totalIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        etAmount = findViewById(R.id.edtAddIncome);
        balanceDao = new BalanceDao(this);
        tvBalance = findViewById(R.id.tvBalanceShow);
        totalIncome = findViewById(R.id.tvAmount);
        double updatedBalance = getIntent().getDoubleExtra("updated_balance", 0.0);
        printOutBalance();
// Обновляем tvBalanceShow с новым значением баланса
        tvBalance.setText(String.valueOf(updatedBalance));
        findViewById(R.id.arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BalanceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIncome();
            }
        });

        printOutBalance(); // Вызываем для инициализации информации при запуске активности
    }

    public void printOutBalance() {
        Balance b = balanceDao.requestBalance();
        tvBalance.setText(String.valueOf(b.getBalance()));
        totalIncome.setText(String.valueOf(b.getIncome()));
    }

    public void insertIncome() {
        double newIncome = Double.parseDouble(etAmount.getText().toString());
        double currentBalance = balanceDao.requestBalance().getBalance();
        double updatedBalance = currentBalance + newIncome;
        Balance updatedBalanceObj = new Balance(updatedBalance, newIncome);

        balanceDao.insertIncome(updatedBalance, newIncome);
        balanceDao.updateAfterTransaction(updatedBalanceObj);

        printOutBalance();
    }

    @Override
    public double getIncomeAmount() {
        return Double.parseDouble(etAmount.getText().toString());
    }
}
