package com.example.compquizizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.compquizizz.Model.history;
import com.example.compquizizz.Model.user;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    EditText fullname;
    ArrayList <history> historiarray= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button updateac=findViewById(R.id.updateAc);
        Button logout = findViewById(R.id.logoutbtn);
        Button Delete = findViewById(R.id.Deleteac);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this,StartupActivity.class);
                startActivity(intent);

            }
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            String userId = currentUser.getUid();
            DatabaseReference userRef = database.child("user").child(userId);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("profileData", "Snapshot: " + snapshot.getValue());
                    //remove for
                    user users = snapshot.getValue(user.class);
                    if (users!=null){
                        String userName = users.getUserName();
                        String score = String.valueOf(users.getTotScore());
                        String fname = users.getFirstName();
                        String lname = users.getLastName();
                        String fullnm = fname+" "+lname;
                        String address = users.getCountry();

                        fullname = findViewById(R.id.fullname);
                        TextView uname = findViewById(R.id.username);
                        EditText pass = findViewById(R.id.password);
                        EditText email = findViewById(R.id.EmailAddress);
                        EditText location = findViewById(R.id.PostalAddress);
                        uname.setText(users.getUserName());
                        pass.setText(users.getPassword());
                        email.setText(users.getEmail());
                        fullname.setText(fullnm);
                        location.setText(address);
                        //addmoremethod
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

        updateac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,UpdateActivity.class);
                startActivity(intent);
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the title and message for the AlertDialog
        builder.setTitle("Delete Account ?");
        builder.setMessage("This will delete all detail related to this account.");

        // Set a positive button and its listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform the action when the OK button is clicked
                // You can leave this empty or add your own logic here
                deleteAcc();
            }
        });

        // Set a negative button and its listener
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform the action when the Cancel button is clicked
                // You can leave this empty or add your own logic here
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void deleteAcc(){
        //delete data from user and history table
        DatabaseReference referenceuser,referencehistory;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uname= currentUser.getDisplayName();
        //1st get the history id for
        DatabaseReference historyRef = database.child("history");
        Log.d("Check value for history 0", "Username : " + uname);
        historyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        history histories =dataSnapshot.getValue(history.class);
                        Log.d("Check value for history 0", "Username : " + histories.getUserName());
                        if (histories.getUserName().equalsIgnoreCase(uname)){
                           historiarray.add(histories);
                            //Log.d("Check value for history 0", "Snapshot: " + history.get(0));
                        }
                        rundeletehistory(historiarray);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    public void rundeletehistory(ArrayList<history>hist){
        DatabaseReference referencehistory;
        ArrayList<String> hIDtodelete = new ArrayList<>();

        for (int i = 0; i < hist.size(); i++) {
            // Copy data to string
            history histories = hist.get(i);
            hIDtodelete.add(histories.getHistoryID());
        }

        for (int i = 0; i < hIDtodelete.size(); i++) {
            try {
                referencehistory = FirebaseDatabase.getInstance().getReference().child("history").child(hIDtodelete.get(i));
                referencehistory.removeValue();
            } catch (DatabaseException e) {
                // Handle the exception (e.g., log an error)
                e.printStackTrace();
            }
        }

        deletePROFILE();

    }

    public void deletePROFILE(){

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uID= currentUser.getUid();
        DatabaseReference userRef = database.child("user").child(uID);

        userRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("check logout", " Successful DELETED" );
            }
        });
        Intent intent = new Intent(ProfileActivity.this,StartupActivity.class);
        startActivity(intent);
    }
}