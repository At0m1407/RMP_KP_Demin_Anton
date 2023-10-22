package com.example.myappperson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myappperson.model.BudgetAnalysis;
import com.example.myappperson.view.AnalysisActivity;
import com.example.myappperson.view.BalanceActivity;
import com.example.myappperson.view.BudgetActivity;
import com.example.myappperson.view.BudgetAnalysisActivity;
import com.example.myappperson.view.OptionActivity;
import com.example.myappperson.view.TransactionHistory;
import com.example.myappperson.view.TransactionsActivity;

public class MainActivity extends AppCompatActivity {
    // Tay Pham Comment
    // Laura Comment
    // Ailin Comment//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btnBalance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BalanceActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.btnBudget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BudgetActivity.class);
                startActivity(intent);

            }
        });
        findViewById(R.id.btnAddTransaction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransactionsActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.btnAnalysis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnalysisActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.btnTransHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransactionHistory.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btnSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OptionActivity.class);
                startActivity(intent);
            }
        });
    }
}