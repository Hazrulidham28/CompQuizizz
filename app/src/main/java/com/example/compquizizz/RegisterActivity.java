package com.example.compquizizz;

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

import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {
    String country,age,uID;
    int ageValues;
    String responseCode="FAILED";

    private EditText fName,lName,Uname,eMail,Pass;
    private Button regButton;

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
                ageValues=20;
                country="Malaysia";

                //need to add text field checker to ensure no null value inserted
                user thisUser = new user(fNames,eMails,Passs,country,lNames,ageValues,Unames,uID);

                String message =  userServices.registerUser(thisUser);

               if (message.equalsIgnoreCase(responseCode)){
                    Toast.makeText(RegisterActivity.this, "Register succeeded", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                   startActivity(intent);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}