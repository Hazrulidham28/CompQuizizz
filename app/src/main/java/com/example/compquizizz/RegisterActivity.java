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
    String selectedcountry;int selectedage;
    int ageValues,totscore=0;
    String responseCode="FAILED";

    TextView gotoLogin;

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
        gotoLogin=findViewById(R.id.textViewLogin);

        //call spinner adapter class
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterrs = ArrayAdapter.createFromResource(this, R.array.ages_array, android.R.layout.simple_spinner_item);
        adapterrs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapterrs);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ageValues= Integer.parseInt(ageSpinner.getSelectedItem().toString());
               country =countrySpinner.getSelectedItem().toString();
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



                Log.d("view country",""+ageValues);

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

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}