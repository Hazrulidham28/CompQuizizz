package com.example.compquizizz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compquizizz.Adapter.HistoryListAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> date = new ArrayList<>(Arrays.asList("20 October","19 September","31 Disember"));
    ArrayList<String> chapter = new ArrayList<>(Arrays.asList("Chapter 1", "Chapter 2", "Chapter 3"));
    ArrayList<String> score = new ArrayList<>(Arrays.asList("25","50","100"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //Set date,chapter,score to the history list
        recyclerView = (RecyclerView)  findViewById(R.id.history_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        HistoryListAdapter adapter = new HistoryListAdapter(HistoryActivity.this,date,chapter,score);
        recyclerView.setAdapter(adapter);
    }
}
