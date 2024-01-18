package com.example.compquizizz.Controller.service.Implementation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.compquizizz.Controller.service.historyService;
import com.example.compquizizz.Controller.service.score.scoreImplementation.userScoreImpl;
import com.example.compquizizz.Controller.service.score.userScore;
import com.example.compquizizz.Controller.service.userService;
import com.example.compquizizz.Model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.concurrent.Executor;

public class userServiceImpl implements userService {
    private userScore sServices = new userScoreImpl();
    String FirstName, Email,Password,Country,LastName,UserName;
    int age;

    //add confirmation message
    String response= "FAILED",chck1="FAILED",chck2="FAILED";
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseAuth auth;
    @Override
    public String registerUser(user User) {
        //initialize firebase database and refer to specific part for reference
        UserName=User.getUserName();
        db = FirebaseDatabase.getInstance();
        //create for authentication
        Email=User.getEmail();
        Password=User.getPassword();
        auth = FirebaseAuth.getInstance();
        //set which class will be added as table
        reference = db.getReference("user");

        auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    String userID="null";
                    userID= user.getUid();
                    User.setuID(userID);
                    //add user data to realtime database
                    reference.child(userID).setValue(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                chck1="SUCCESS";
                            }
                            else{
                                chck1="FAILED";
                            }
                        }
                    });
                    chck2="SUCCESS";
                }
                else{
                    chck2="FAILED";
                }
            }
        });

            if ("SUCCESS".equals(chck1) && "SUCCESS".equals(chck2)){
                response="SUCCESS";
            }
            else {
                response="FAILED";
            }
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
    public String loginUser(String email, String password) {
        mAuth = FirebaseAuth.getInstance();


        //probability error becasue not put the this argument
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
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

    @Override
    public user getuserUID(String uID) {
        return null;
    }

    @Override
    public String updateScorebyUname(String uName) {
            //this method should be implement everytime the quiz end
            //and after saving the history into the database in order to be f4nctional
            //implement userScoreImpl to get the score of the user
            int score = sServices.getScoreByUname(uName);
            //code for updating to database
             reference = FirebaseDatabase.getInstance().getReference("user").child(uName).child("totScore");
             reference.setValue(score).addOnSuccessListener(new OnSuccessListener<Void>() {

                 @Override
                 public void onSuccess(Void unused) {
                     response="SUCCESS";
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                    response="FAILED";
                 }
             });

             //set the message to success if update success
        return response;
    }
    //add implementation
}
