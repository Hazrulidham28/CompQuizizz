package com.example.compquizizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class HomeActivity extends AppCompatActivity {
    Button logout;
    Button c1;
    Button c2;
    Button c3;
//temporary suppress

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logout=findViewById(R.id.logoutTemp);
        c1=findViewById(R.id.button5);
        c2=findViewById(R.id.button6);
        c3=findViewById(R.id.button7);



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

                                TextView scorehigh=findViewById(R.id.textView13);
                                scorehigh.setText(highestScore);

                                //create method to calculate leader board
                            }
                        }
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
                Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
                intent.putExtra("ChaptNum",1);
                startActivity(intent);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
                intent.putExtra("ChaptNum",2);
                startActivity(intent);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
                intent.putExtra("ChaptNum",3);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this,StartupActivity.class);
                startActivity(intent);

            }
        });
    }
}