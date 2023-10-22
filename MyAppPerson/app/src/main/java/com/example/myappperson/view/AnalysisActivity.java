package com.example.myappperson.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myappperson.AnalysisListviewAdapter;
import com.example.myappperson.MainActivity;
import com.example.myappperson.R;
import com.example.myappperson.data.AnalysisDao;
import com.example.myappperson.model.BudgetAnalysis;

import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {
    AnalysisDao analysisDao;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        analysisDao = new AnalysisDao(this);

        ArrayList<BudgetAnalysis> budgetAnalysisList = new ArrayList<BudgetAnalysis>();

        budgetAnalysisList = analysisDao.getAllBudgetingData();

        ListView listView = (ListView) findViewById(R.id.lvAllBudgetsData);
        AnalysisListviewAdapter analysisListviewAdapter = new AnalysisListviewAdapter(this, R.layout.analysis_adapter_views_layout, budgetAnalysisList);
        listView.setAdapter(analysisListviewAdapter);
        findViewById(R.id.arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalysisActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalysisActivity.this, BudgetAnalysisActivity.class);
                startActivity(intent);

            }
        });
    }

}