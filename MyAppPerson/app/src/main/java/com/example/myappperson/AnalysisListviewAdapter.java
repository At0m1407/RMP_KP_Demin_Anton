package com.example.myappperson;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.myappperson.model.BudgetAnalysis;

import java.util.List;

public class AnalysisListviewAdapter extends ArrayAdapter<BudgetAnalysis> {

    private int layoutResource;

    public AnalysisListviewAdapter(Context context, int layoutResource, List<BudgetAnalysis> budgetAnalysisList) {
        super(context, layoutResource, budgetAnalysisList);
        this.layoutResource = layoutResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        BudgetAnalysis budgetAnalysis = getItem(position);
        ProgressBar itemProgressBar = view.findViewById(R.id.progressBar2);
        ProgressBar itemProgressBar2 = view.findViewById(R.id.progressBar3);
        itemProgressBar.setMax(100);
        itemProgressBar2.setMax(100);
        itemProgressBar.setProgress((int) (100 - budgetAnalysis.getProgress() * 100));
        itemProgressBar2.setProgress((int) (budgetAnalysis.getProgress() * 100));
        itemProgressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#028E08")));
        itemProgressBar2.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#BF0000")));

        if (budgetAnalysis != null) {
            TextView title = view.findViewById(R.id.textView1);
            TextView content1 = view.findViewById(R.id.textView2);
            TextView content2 = view.findViewById(R.id.textView3);

            if (title != null) {
                title.setText(budgetAnalysis.getBudgetCategory() + ": $" + budgetAnalysis.getBudgetAmount());

            }


            if (content1 != null) {
                String spentText = getContext().getString(R.string.you_spent, budgetAnalysis.getAmountSpent());
                content1.setText(spentText + budgetAnalysis.getBudgetAmount());
            }

                if (content2 != null) {
                // Показать оставшийся баланс всегда
                String balanceText = getContext().getString(R.string.you_have, budgetAnalysis.getBudgetBalance(), budgetAnalysis.getBudgetCategory());
                content2.setText(balanceText+budgetAnalysis.getBudgetBalance());
            }
        }

        return view;
    }



}