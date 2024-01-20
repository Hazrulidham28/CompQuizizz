package com.example.compquizizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.compquizizz.Model.chapter;
import com.example.compquizizz.Model.history;
import com.example.compquizizz.Model.question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ChapterActivity extends AppCompatActivity {
    ArrayList <chapter> chapterList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    String chapternum="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        Intent intent = getIntent();
        chapterList = new ArrayList<>();
        if (intent!=null){
            chapternum = intent.getStringExtra("ChaptNum");


            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser!=null){

                getChapter(chapternum);
                //get bestscore for each chapter

            }}

        Button start = findViewById(R.id.start_btn);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(ChapterActivity.this, QuizActivity.class);
                    intent.putExtra("ChaptNum",chapternum);
                    startActivity(intent);
                }

        });
    }

    public void getChapter(String chpid){
        //retrieve semua dalam chapter table
        DatabaseReference questRef = database.child("chapter").child(chpid);

        questRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("ChapterData", "Snapshot: " + snapshot.getValue());
                if (snapshot.exists()) {

                        chapter chapter = snapshot.getValue(chapter.class);

                        if (chapter != null) {
                            String description = snapshot.child("chapter_description").getValue(String.class);
                            int passing = snapshot.child("passing_score").getValue(int.class);
                            String chpName = snapshot.child("chapter_name").getValue(String.class);

                            Log.d("ChapterData", "Description: " + description);
                            Log.d("ChapterData", "Passing Score: " + passing);
                            Log.d("ChapterData", "Chapter Name: " + chpName);

                            TextView desc = findViewById(R.id.description);
                            TextView passingscr = findViewById(R.id.passing_score);
                            TextView chptname = findViewById(R.id.chp_title);


                            desc.setText(description);
                            passingscr.setText(String.valueOf(passing));
                            chptname.setText(chpName);
                            bestChaptscore(chpid);
                        } else {
                            Toast.makeText(ChapterActivity.this, "No data", Toast.LENGTH_SHORT).show();
                        }




            }
               else Toast.makeText(ChapterActivity.this, "Not exist", Toast.LENGTH_SHORT).show();}


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void bestChaptscore(String chpat){

        ArrayList <history> history = new ArrayList<history>();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String current = currentUser.getDisplayName();

        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference().child("history");
            historyRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("Check value", "Snapshot: " + snapshot.getValue());
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                        history histories = dataSnapshot.getValue(history.class);
                        if (histories !=null){
                            if (histories.getUserName().equalsIgnoreCase(current) && histories.getChaptID().equalsIgnoreCase(chpat)){

                                history.add(histories);
                            }
                        }
                    }displayBestscore(history);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

    public void displayBestscore(List hist){

        int maxScore=0;

        for (int i = 0;i<hist.size();i++){

            history history = (history)hist.get(i);

            if (history.getScore()>maxScore){
                maxScore=history.getScore();

            }
        }
        TextView bestscore = findViewById(R.id.chp_bestscore);
        bestscore.setText(String.valueOf(maxScore));
    }

}