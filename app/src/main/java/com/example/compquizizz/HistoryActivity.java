package com.example.compquizizz;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compquizizz.Adapter.HistoryListAdapter;
import com.example.compquizizz.Controller.service.Implementation.historyServiceImpl;
import com.example.compquizizz.Controller.service.Implementation.userServiceImpl;
import com.example.compquizizz.Controller.service.historyService;
import com.example.compquizizz.Controller.service.userService;
import com.example.compquizizz.Model.history;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> date = new ArrayList<>(Arrays.asList("20 October","19 September","31 Disember"));
    ArrayList<String> chapter = new ArrayList<>(Arrays.asList("Chapter 1", "Chapter 2", "Chapter 3"));
    ArrayList<String> score = new ArrayList<>(Arrays.asList("25","50","100"));

    //initialize implementaiton method
    private historyService hService = new historyServiceImpl();

    //need to call current logged in username
    private FirebaseAuth mAuth;

    String currentusername="username";

    //get list of histories based on the current logged in user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mAuth = FirebaseAuth.getInstance();



        List<history> histories = hService.getHistoryByUname(currentusername);

        if(histories != null && !histories.isEmpty()){

            Log.d("hasHistory", "Loaded with history" );
            //assign the retrieved histories to array list or anything better
        }
        else{

            //if there are no values in the histories or not exist yet
            //create dummy data/ default value to display in recycler view
            //if not set any value, application would crash
        }
        //Set date,chapter,score to the history list
        recyclerView = (RecyclerView)  findViewById(R.id.history_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        HistoryListAdapter adapter = new HistoryListAdapter(HistoryActivity.this,date,chapter,score);
        recyclerView.setAdapter(adapter);
    }
}
