package com.example.compquizizz.Controller.service.Implementation;

import androidx.annotation.NonNull;

import com.example.compquizizz.Controller.service.userService;
import com.example.compquizizz.Model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class userServiceImpl implements userService {
    String FirstName, Email,Password,Country,LastName,UserName;
    int age;

    //add confirmation message
    String response= "FAILED";
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    @Override
    public String registerUser(user User) {
        //initialize firebase database and refer to specific part for reference
        UserName=User.getUserName();
        db = FirebaseDatabase.getInstance();

        //set which class will be added as table
        reference = db.getReference("user");

        reference.child(UserName).setValue(User).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               if (task.isSuccessful()){
                   response="SUCCESS";
               }
               else{
                   response="FAILED";
               }
            }
        });
        return response;
    }

    @Override
    public String updateUser(user User) {
        return null;
    }

    @Override
    public String deleteUser(user User) {
        return null;
    }

    @Override
    public String loginUser(String userName, String password) {
        mAuth = FirebaseAuth.getInstance();

        //probability error becasue not put the this argument
        mAuth.signInWithEmailAndPassword(userName,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()) {
                  response = "SUCCESS";
              }
              else {
                  response="FAILED";
              }
            }
        });

        return response;
    }

    @Override
    public List<user> getallUser() {
        return null;
    }

    @Override
    public user getuserByName(String uName) {
        return null;
    }
    //add implementation
}
