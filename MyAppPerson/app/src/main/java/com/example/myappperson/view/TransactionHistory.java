package com.example.myappperson.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myappperson.MainActivity;
import com.example.myappperson.R;
import com.example.myappperson.data.TransactionDao;

import java.util.ArrayList;

public class TransactionHistory extends AppCompatActivity {
    TransactionDao tDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        tDao = new TransactionDao(this);

        printTransactionList();
        findViewById(R.id.arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionHistory.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void printTransactionList(){
        ArrayList<String> transArray= new ArrayList<>();


        transArray = tDao.getAllTransactions();

        String [] arrayBtoString = new String[transArray.size()];
        int i = 0;

        for (Object value : transArray){
            arrayBtoString[i] = (String) value;
            i++;
        }

        ListAdapter bgListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayBtoString);
        ListView foodsListView = (ListView) findViewById(R.id.lvTransaction);
        foodsListView.setAdapter(bgListAdapter);
    }
}