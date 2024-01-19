package com.example.compquizizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.compquizizz.Controller.service.Implementation.userServiceImpl;
import com.example.compquizizz.Controller.service.userService;
import com.example.compquizizz.Model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {
    String country,age,uID;
    int ageValues,totscore=0;
    String responseCode="FAILED";

    private EditText fName,lName,Uname,eMail,Pass;
    private Button regButton;

    //database
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth auth;
    private userService userServices = new userServiceImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //get references from spinneer
        Spinner countrySpinner = findViewById(R.id.spinnerCountry);
        Spinner ageSpinner = findViewById(R.id.spinnerAge);

        //get references from xml

        regButton=findViewById(R.id.buttonRegister);

        //call spinner adapter class




        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the value from xml file
                fName = findViewById(R.id.editTextFirstName);
                lName = findViewById(R.id.editTextLastName);
                Uname = findViewById(R.id.editTextuName);
                eMail = findViewById(R.id.editTextEmail);
                Pass = findViewById(R.id.editTextPassword);
                String fNames,eMails,Passs,countrys,lNames,Unames;
                int ages;

                //convert to string
                fNames=fName.getText().toString();
                eMails=eMail.getText().toString();
                Passs=Pass.getText().toString();
                lNames=lName.getText().toString();
                Unames=Uname.getText().toString();

                // need to set up the spinner
                //this is just dummy data! need to be changed
                ageValues=20;
                country="Malaysia";

                //need to add text field checker to ensure no null value inserted
                user thisUser = new user(fNames,eMails,Passs,country,lNames,ageValues,Unames,uID,totscore);


                //initialize firebase database and refer to specific part for reference
                db = FirebaseDatabase.getInstance();
                //create for authentication
                auth = FirebaseAuth.getInstance();
                //set which class will be added as table
                reference = db.getReference("user");

                auth.createUserWithEmailAndPassword(eMails,Passs).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            String userID="null";
                            userID= user.getUid();
                            thisUser.setuID(userID);
                            //add user data to realtime database
                            reference.child(userID).setValue(thisUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(RegisterActivity.this, "Register succeeded", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "Register succeeded", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }

                    }
                });



            }
        });



    }
}