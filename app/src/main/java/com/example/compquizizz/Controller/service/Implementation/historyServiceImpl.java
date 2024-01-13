package com.example.compquizizz.Controller.service.Implementation;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.compquizizz.Controller.service.historyService;
import com.example.compquizizz.Model.history;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class historyServiceImpl implements historyService {
    List <history> history = null;

    String HistoryID,UserName,ChaptID,QuestionDone,Date,Chapter;
    String response ="FAILED";
    int score;

    DatabaseReference database;
    FirebaseDatabase db;
    FirebaseAuth auth;
    @Override
    public String addHistory(history History) {

        db = FirebaseDatabase.getInstance();

        database = db.getReference("history");
        database.child(HistoryID).setValue(History).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    response ="SUCCESS";
                }
                else{
                    response="FAILED";
                }
            }
        });


        return response;
    }

    @Override
    public List<history> getHistoryByUname(String Uname) {

    database = FirebaseDatabase.getInstance().getReference("history").child(Uname);
    history = new ArrayList<history>();

    database.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()){

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    history histories =dataSnapshot.getValue(history.class);
                    history.add(histories);
                }

            }
            else {

                Log.d("NoHistory", "No history found for username: " + Uname);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });



        //make sure to check the history isn't empty before using
        return history;
    }
}
