package com.example.compquizizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.compquizizz.Model.history;
import com.example.compquizizz.Model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button c1;
    Button c2;
    Button c3;

    String userName;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        c1=findViewById(R.id.button5);
        c2=findViewById(R.id.button6);
        c3=findViewById(R.id.button7);
        c2.setEnabled(false);
        c3.setEnabled(false);



        FirebaseUser currentUser = mAuth.getCurrentUser();


            if (currentUser!=null){

                String uID = currentUser.getUid();
                DatabaseReference userRef = database.child("user").child(uID);

                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            user users = snapshot.getValue(user.class);
                            if (users!= null){
                                String highestScore= String.valueOf(users.getTotScore());
                                 userName = users.getUserName();
                                TextView scorehigh=findViewById(R.id.textView13);
                                scorehigh.setText(highestScore);
                                //create method to calculate leader board
                            }

                        }setDisplayname(userName);
                        findLeaderboard();
                        chckscore();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }





        BottomNavigationView btnviw = findViewById(R.id.bottomNavigationView);
        String uID = currentUser.getUid();
        btnviw.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuItem = item.getItemId();
                if (menuItem ==  R.id.History) {
                    Intent intent = new Intent(HomeActivity.this,HistoryActivity.class);
                    startActivity(intent);
                    intent.putExtra("uID",uID);
                    return true;
                }
                else if (menuItem ==  R.id.profile) {
                    Intent intent = new Intent(HomeActivity.this,ProfileActivity.class);
                    startActivity(intent);
                    intent.putExtra("uID",uID);
                    return true;
                }else if(menuItem ==  R.id.home){
                    Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                    startActivity(intent);
                    intent.putExtra("uID",uID);
                    return true;
                }
                return false;
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, ChapterActivity.class);
                String chapter = "Chap 1";
                intent.putExtra("ChaptNum",chapter);
                startActivity(intent);

            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, ChapterActivity.class);
                String chapter = "Chap 2";
                intent.putExtra("ChaptNum",chapter);
                startActivity(intent);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ChapterActivity.class);
                String chapter = "Chap 3";
                intent.putExtra("ChaptNum",chapter);
                startActivity(intent);
            }
        });

    }



    public void setDisplayname(String username){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build();

            currentUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(HomeActivity.this, "Welcome !!! " + username, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void findLeaderboard(){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user");
        Query topScoreQuery = usersRef.orderByChild("totScore").limitToLast(3);
        List<user> top3user = new ArrayList<>();
            topScoreQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot userSnapshot: snapshot.getChildren()){
                        user users = userSnapshot.getValue(user.class);
                        top3user.add(users);
                    }
                    Collections.reverse(top3user);
                    displayLeaderboard(top3user);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    public void displayLeaderboard(List top3){
        user user1=(user)top3.get(0);
        user user2=(user)top3.get(1);
        user user3=(user)top3.get(2);

        TextView us1 = findViewById(R.id.topscore1);
        TextView us2 = findViewById(R.id.topscore2);
        TextView us3 = findViewById(R.id.topscore3);

        us1.setText("1. "+user1.getUserName());
        us2.setText("2. "+user2.getUserName());
        us3.setText("3. "+user3.getUserName());
    }

    public void chckscore(){



        ArrayList <history> history1 = new ArrayList<history>();
        ArrayList <history> history2 = new ArrayList<history>();
        ArrayList <history> history3 = new ArrayList<history>();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String current = currentUser.getDisplayName();

        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference().child("history");
        historyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Check value", "Snapshot: " + snapshot.getValue());
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    history histories = dataSnapshot.getValue(history.class);
                    if (histories !=null){
                        if (histories.getUserName().equalsIgnoreCase(current) && histories.getChaptID().equalsIgnoreCase("Chap 1")){
                            history1.add(histories);
                    } else if (histories.getUserName().equalsIgnoreCase(current) && histories.getChaptID().equalsIgnoreCase("Chap 2")) {
                            history2.add(histories);
                        }

                    }
                }validateScore(history1,history2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
    public void validateScore(List hist1,List hist2){
        int [] maxscoreChapter= new int[3];// 0 for chapt 1, 1 for 2 ...
        ArrayList <history> history1 = new ArrayList<history>();
        ArrayList <history> history2 = new ArrayList<history>();





        //find max for each chapter
        int maxScore1=0,maxScore2=0;
        //chapter 1

        for (int i = 0;i<hist1.size();i++){

            history history = (history)hist1.get(i);

            if (history.getScore()>maxScore1){
                maxScore1=history.getScore();

            }
        }
        for (int i = 0;i<hist2.size();i++){

            history history = (history)hist2.get(i);

            if (history.getScore()>maxScore2){
                maxScore2=history.getScore();

            }
        }


        Button chp1;
        Button chp2;
        Button chp3;

        chp1 = findViewById(R.id.button5);
        chp2 = findViewById(R.id.button6);
        chp3 = findViewById(R.id.button7);

        //chapter 2

        if (maxScore1>=90){
            chp2.setEnabled(true);
        }

        if (maxScore2>=80){
            chp3.setEnabled(true);
        }

    }



    public void showDisabledButtonDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Locked");
            builder.setMessage("You neeed to pass the previous chapter!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the OK button click event
                dialog.dismiss(); // Close the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}