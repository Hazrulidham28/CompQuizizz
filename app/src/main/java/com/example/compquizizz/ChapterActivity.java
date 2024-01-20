package com.example.compquizizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.compquizizz.Model.chapter;
import com.example.compquizizz.Model.question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChapterActivity extends AppCompatActivity {
    ArrayList <chapter> chapterList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        Intent intent = getIntent();
        chapterList = new ArrayList<>();
        if (intent!=null){
            String chapternum = intent.getStringExtra("ChaptNum");


            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser!=null){

                getChapter(chapternum);

            }}

        Button start = findViewById(R.id.start_btn);
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
                            //viewtest(chapt);
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
    public void viewtest(chapter chpat){

        Toast.makeText(ChapterActivity.this, chpat.getChapterName(), Toast.LENGTH_SHORT).show();
    }

}