package com.example.compquizizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.compquizizz.Model.question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    ArrayList <question> questionList;
    int [] score =new int[5];
    private int currentquestionindex=0;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Intent intent = getIntent();
        if (intent!=null){
            String chapternum = intent.getStringExtra("ChaptNum");


            FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
        Toast.makeText(QuizActivity.this, String.valueOf(chapternum), Toast.LENGTH_SHORT).show();
        questionList = new ArrayList<>();
        getQuestion(chapternum);

        }}
    }
    public void getQuestion(String chp){

        DatabaseReference questRef = database.child("question");

        questRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot questionSnapshot:snapshot.getChildren()){
                    question questions = questionSnapshot.getValue(question.class);
                       if (questions.getChapter_id().equalsIgnoreCase(chp)) {
                           questionList.add(questions);
                       }
                }
                if (!questionList.isEmpty()){
                    displayQuestion();
                    Toast.makeText(QuizActivity.this,"Has not empty", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(QuizActivity.this, "No questions available for this chapter", Toast.LENGTH_SHORT).show();
                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void displayQuestion(){

        if (!questionList.isEmpty() && currentquestionindex < questionList.size()){
            question currentQuest = questionList.get(currentquestionindex);
            TextView question = findViewById(R.id.question);
            TextView progNum = findViewById(R.id.current_progress);
            RadioButton ans1 = findViewById(R.id.answer1);
            RadioButton ans2 = findViewById(R.id.answer2);
            RadioButton ans3 = findViewById(R.id.answer3);
            Button nextbut = findViewById(R.id.next_question);

            //set view
            question.setText(currentQuest.getQuestion());
            progNum.setText(String.valueOf(currentquestionindex+1));
            ans1.setText(currentQuest.getChoice1());
            ans2.setText(currentQuest.getChoice2());
            ans3.setText(currentQuest.getChoice3());

            nextbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextQuestion();
                }
            });
        }
        else {
            //handle when all question is displayed
            Toast.makeText(QuizActivity.this, "Quiz Completed", Toast.LENGTH_SHORT).show();
        }
    }
    private void nextQuestion(){
        currentquestionindex++;
        displayQuestion();
    }
}
