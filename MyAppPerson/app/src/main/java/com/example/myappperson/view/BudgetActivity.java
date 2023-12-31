package com.example.myappperson.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myappperson.MainActivity;
import com.example.myappperson.R;
import com.example.myappperson.data.BudgetDao;
import com.example.myappperson.data.Database;
import com.example.myappperson.model.BudgetAnalysis;
import com.example.myappperson.presenter.BudgetActivityPresenter;
import com.example.myappperson.presenter.BudgetInterface;

import java.util.ArrayList;

public class BudgetActivity extends AppCompatActivity implements BudgetInterface {

    private EditText etAmount;
    private Spinner spCategory;
    private Button btnClear, btnSave,btnDelete;
    private BudgetActivityPresenter bPresenter;
    private BudgetDao bDao;
    Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        db = new Database(this);
        bDao = new BudgetDao(this);

        etAmount = (EditText) findViewById(R.id.edtAmount);
        spCategory = (Spinner) findViewById(R.id.spCategoryB);
        bPresenter = new BudgetActivityPresenter(this, this.getApplicationContext() );
        CategorySpinner();

        btnClear = findViewById(R.id.btnClear);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        printBudgetList();

        findViewById(R.id.arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BudgetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAmount.setText(null);
                spCategory.setSelection(0);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonClicked(v);
                printBudgetList();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bPresenter.insertBudget()){
                    //bPresenter.insertBudgetBalance();
                    printBudgetList();
                }

            }
        });
    }
    public void printBudgetList(){
        ArrayList<String> budgetArray= new ArrayList<>();

        budgetArray = bDao.getAllBudget();

        String [] arrayBtoString = new String[budgetArray.size()];
        int i = 0;

        for (Object value : budgetArray){
            arrayBtoString[i] = (String) value;
            i++;
        }

        ListAdapter bgListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayBtoString);
        ListView foodsListView = (ListView) findViewById(R.id.bgListView);
        foodsListView.setAdapter(bgListAdapter);
    }

    public void deleteButtonClicked(View view){
        String inputText = spCategory.getSelectedItem().toString();
        bDao.deleteBudget(inputText);
        //printDatabase();
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


    @Override
    public String getBudgetAmount() {
        return etAmount.getText().toString();
    }

    @Override
    public String getBudgetCategory() {
        return spCategory.getSelectedItem().toString();
    }



}