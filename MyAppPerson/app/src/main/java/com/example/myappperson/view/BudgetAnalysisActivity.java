package com.example.myappperson.view;


import android.content.Intent;
import android.os.Bundle;

import com.example.myappperson.MainActivity;
import com.example.myappperson.R;
import com.example.myappperson.data.TransactionDao;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.utils.ColorTemplate;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class BudgetAnalysisActivity extends AppCompatActivity {
    private ImageView arrow;
    private TransactionDao transactionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_analysis);
        arrow = findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BudgetAnalysisActivity.this, AnalysisActivity.class));
            }
        });
        transactionDao = new TransactionDao(this);

        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<String> transactionData = transactionDao.getAllTransactions();
        HashMap<String, Double> categoryAmountMap = new HashMap<>();

        for (String transaction : transactionData) {
            String[] parts = transaction.split("\n");
            String category = parts[2].substring(parts[2].indexOf(": ") + 2);
            double amount = Double.parseDouble(parts[0].substring(parts[0].indexOf(": $") + 2).replace("$", ""));

            if (categoryAmountMap.containsKey(category)) {
                double currentAmount = categoryAmountMap.get(category);
                categoryAmountMap.put(category, currentAmount + amount);
            } else {
                categoryAmountMap.put(category, amount);
            }
        }

        ArrayList<PieEntry> entries = new ArrayList<>();

        for (Map.Entry<String, Double> entry : categoryAmountMap.entrySet()) {
            double amount = entry.getValue();
            String category = entry.getKey();
            int color;

            if (amount < 0) {
                color = Color.RED;  // Красный для отрицательных значений
            } else {
                color = Color.GREEN;  // Зеленый для положительных значений
            }

            entries.add(new PieEntry((float) Math.abs(amount), category));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Transaction Categories");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);

        pieChart.setData(data);
        pieChart.setCenterText("Transaction Analysis");
        pieChart.getDescription().setEnabled(false);

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf(value); // Отображение чисел из TransactionDao
            }
        };
        data.setValueFormatter(formatter);

        pieChart.animateY(1000);
        pieChart.invalidate();
    }
}
