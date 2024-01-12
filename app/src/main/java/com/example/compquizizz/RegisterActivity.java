package com.example.compquizizz;

import androidx.appcompat.app.AppCompatActivity;

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
    String country,age;
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
                eMail = findViewById(R.id.editTextLastName);
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

                //Toast.makeText(RegisterActivity.this," age", Toast.LENGTH_SHORT).show();
                user thisUser = new user(fNames,eMails,Passs,country,lNames,ageValues,Unames);

                String message =  userServices.registerUser(thisUser);

               if (message.equalsIgnoreCase(responseCode)){
                    Toast.makeText(RegisterActivity.this, "Register succeeded", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}