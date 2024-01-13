package com.example.compquizizz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.compquizizz.Controller.service.Implementation.userServiceImpl;
import com.example.compquizizz.Controller.service.userService;

public class LoginActivity extends AppCompatActivity {

    private userService userServices = new userServiceImpl();
    String responseCode="FAILED";
    private Button loginButton;
    EditText Email,password;
    String email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login_page_button);

       loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = findViewById(R.id.email_input);
                password = findViewById(R.id.password_input);
                email =Email.getText().toString();
                pass = password.getText().toString();

                //implementation method
                String message = userServices.loginUser(email,pass);

                if (message.equalsIgnoreCase(responseCode)){
                    Toast.makeText(LoginActivity.this, "Login succeeded", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
