package com.example.compquizizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
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

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    EditText fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseUser currentUser = mAuth.getCurrentUser();
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
                            String fname = users.getFirstName();
                            String lname = users.getLastName();
                            String fullnm = fname+" "+lname;

                            EditText fullname = findViewById(R.id.fullname);
                            TextView uname = findViewById(R.id.username);
                            EditText pass = findViewById(R.id.password);
                            EditText email = findViewById(R.id.EmailAddress);
                            uname.setText(users.getUserName());
                            pass.setText(users.getPassword());
                            email.setText(users.getEmail());
                            fullname.setText(fullnm);
                            //addmoremethod
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
                    Intent intent = new Intent(ProfileActivity.this,HistoryActivity.class);
                    startActivity(intent);
                    intent.putExtra("uID",uID);
                    return true;
                }
                else if (menuItem ==  R.id.profile) {
                    Intent intent = new Intent(ProfileActivity.this,ProfileActivity.class);
                    startActivity(intent);
                    intent.putExtra("uID",uID);
                    return true;
                }else if(menuItem ==  R.id.home){
                    Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                    startActivity(intent);
                    intent.putExtra("uID",uID);
                    return true;
                }
                return false;
            }
        });
    }
}