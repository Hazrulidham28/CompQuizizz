package com.example.compquizizz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compquizizz.Adapter.HistoryListAdapter;
import com.example.compquizizz.Controller.service.Implementation.historyServiceImpl;
import com.example.compquizizz.Controller.service.Implementation.userServiceImpl;
import com.example.compquizizz.Controller.service.historyService;
import com.example.compquizizz.Controller.service.userService;
import com.example.compquizizz.Model.history;
import com.example.compquizizz.Model.user;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    //need to call current logged in username
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    ArrayList <history> history;


    //get list of histories based on the current logged in user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        String uID = getIntent().getStringExtra("uID");
        history = new ArrayList<history>();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //get user by id
        if (currentUser!=null){
            String userId = currentUser.getUid();
            DatabaseReference userRef = database.child("user").child(userId);

               userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            user users = snapshot.getValue(user.class);
                            if (users!=null){
                                String userName = users.getUserName();
                                String score = String.valueOf(users.getTotScore());
                                TextView username = findViewById(R.id.username_historypage);
                                TextView scores = findViewById(R.id.show_highscore);
                                username.setText(userName);
                                scores.setText(score);
                                fetchHistory(userName);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        }

        BottomNavigationView btnviw = findViewById(R.id.bottomNavigationView);

        btnviw.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuItem = item.getItemId();
                if (menuItem ==  R.id.History) {
                    Intent intent = new Intent(HistoryActivity.this,HistoryActivity.class);
                    startActivity(intent);
                    intent.putExtra("uID",uID);
                    return true;
                }
                else if (menuItem ==  R.id.profile) {
                    Intent intent = new Intent(HistoryActivity.this,ProfileActivity.class);
                    startActivity(intent);
                    intent.putExtra("uID",uID);
                    return true;
                }else if(menuItem ==  R.id.home){
                    Intent intent = new Intent(HistoryActivity.this,HomeActivity.class);
                    startActivity(intent);
                    intent.putExtra("uID",uID);
                    return true;
                }
                return false;
            }
        });

    }
    private void fetchHistory(String userName){
    DatabaseReference historyRef = database.child("history").child(userName);

        historyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        history histories =dataSnapshot.getValue(history.class);
                        history.add(histories);
                    }
                    runRecyclerView(history);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void runRecyclerView(ArrayList<history> fetchedHistories){
        //Set date,chapter,score to the history list
        recyclerView = (RecyclerView) findViewById(R.id.history_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        HistoryListAdapter adapter = new HistoryListAdapter(HistoryActivity.this,fetchedHistories);
        recyclerView.setAdapter(adapter);


    }

}
