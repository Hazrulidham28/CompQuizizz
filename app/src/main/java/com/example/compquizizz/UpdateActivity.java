package com.example.compquizizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.compquizizz.Model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {
    Button buttonupdate;
    Button buttonback;
    String selectedcountry;int selectedage;

    EditText updateFname,updateLname;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateFname = findViewById(R.id.editFname);
        updateLname = findViewById(R.id.editLname);


        buttonupdate =findViewById(R.id.buttonUpdate);
        buttonback =findViewById(R.id.buttonback);
        Spinner countrySpinner = findViewById(R.id.spinnereditcountry);
        Spinner ageSpinner = findViewById(R.id.spinnereditage);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterrs = ArrayAdapter.createFromResource(this, R.array.ages_array, android.R.layout.simple_spinner_item);
        adapterrs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapterrs);

        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ageValues= Integer.parseInt(ageSpinner.getSelectedItem().toString());
                String country =countrySpinner.getSelectedItem().toString();
                String newLname,newFname;

                newFname = updateFname.getText().toString();
                newLname=updateLname.getText().toString();


                Log.d("update name",newFname);
                Log.d("update fname",country);

                updateAcc(newFname,newLname,country,ageValues);

                Intent intent = new Intent(UpdateActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this,HomeActivity.class);
            }
        });

        showUser();

    }
    public void showUser(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            String userId = currentUser.getUid();
            DatabaseReference userRef = database.child("user").child(userId);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("profileData on update", "Snapshot: " + snapshot.getValue());
                    //remove for
                    user users = snapshot.getValue(user.class);
                    if (users!=null){
                        String userName = users.getUserName();
                        String score = String.valueOf(users.getTotScore());
                        String fname = users.getFirstName();
                        String lname = users.getLastName();
                        String fullnm = fname+" "+lname;
                        String address = users.getCountry();

                        updateFname.setText(fname);
                        updateLname.setText(lname);
                        //addmoremethod
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    public void updateAcc(String fname,String lname, String country, int age){
        DatabaseReference reference;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uID= currentUser.getUid();

        reference=FirebaseDatabase.getInstance().getReference("user").child(uID);

        reference.child("firstName").setValue(fname);
        reference.child("lastName").setValue(lname);
        reference.child("country").setValue(country);
        reference.child("age").setValue(age);
        Toast.makeText(UpdateActivity.this, "Updated" , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdateActivity.this,HomeActivity.class);
        startActivity(intent);
    }

}