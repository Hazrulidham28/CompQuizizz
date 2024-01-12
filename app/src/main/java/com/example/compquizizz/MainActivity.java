package com.example.compquizizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

            // check if the user is already signed in.. direct to the main menu

        FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null){
                // is mean that the user isn't logged out from last session

                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(MainActivity.this,StartupActivity.class);
                startActivity(intent);
            }
    }
}