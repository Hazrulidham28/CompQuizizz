package com.example.compquizizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.example.compquizizz.Model.history;
import com.example.compquizizz.Model.question;
import com.example.compquizizz.Model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class QuizActivity extends AppCompatActivity {
    ArrayList <question> questionList;
    int [] score =new int[5];
    String chaptID;
    private int currentquestionindex=0;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference reference;
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    FirebaseAuth auth;
    ProgressBar progressBar;
    String chapName="null";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        progressBar = (ProgressBar)findViewById(R.id.quiz_progress);
        Intent intent = getIntent();
        if (intent!=null){
            String chapternum = intent.getStringExtra("ChaptNum");
            chapName = intent.getStringExtra("Chapttitle");



            FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
        Toast.makeText(QuizActivity.this, String.valueOf(chapternum), Toast.LENGTH_SHORT).show();
        questionList = new ArrayList<>();
        getQuestion(chapternum);

        }}


    }
    public void getQuestion(String chp){
        //ambil semua data dalam table question
        DatabaseReference questRef = database.child("question");

        questRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot questionSnapshot:snapshot.getChildren()){

                    question questions = questionSnapshot.getValue(question.class);
                    Log.d("check database", "Question ID: " + questions.getCorrect_answer());
                        //add dalam qlist yang matchin chp id
                       if (questions.getChapter_id().equalsIgnoreCase(chp)) {
                           questionList.add(questions);
                       }
                }
                if (!questionList.isEmpty()){
                    displayQuestion();
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
             chaptID = currentQuest.getChapter_id();
            TextView question = findViewById(R.id.question);
            TextView progNum = findViewById(R.id.current_progress);
            TextView title = findViewById(R.id.quiz_title);
            RadioButton ans1 = findViewById(R.id.answer1);
            RadioButton ans2 = findViewById(R.id.answer2);
            RadioButton ans3 = findViewById(R.id.answer3);
            Button nextbut = findViewById(R.id.next_question);


            String correctansw = currentQuest.getCorrect_answer();

            //set view
            ans1.setChecked(false);
            ans2.setChecked(false);
            ans3.setChecked(false);
            question.setText(currentQuest.getQuestion());
            progNum.setText(String.valueOf(currentquestionindex+1));
            ans1.setText(currentQuest.getChoice1());
            ans2.setText(currentQuest.getChoice2());
            ans3.setText(currentQuest.getChoice3());
            title.setText(chapName);
            //Log.d("this is correct answer",correctansw);

            nextbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ans1.isChecked()) {
                        if ((ans1.getText().toString()).equals(correctansw)) {
                            // Correct answer
                            score[currentquestionindex] = 20;
                        } else {
                            // Incorrect answer
                            score[currentquestionindex] = 0; // You might want to award 0 points for incorrect answers
                        }
                        nextQuestion();

                    } else if (ans2.isChecked()) {
                        if ((ans2.getText().toString()).equals(correctansw)) {
                            // Correct answer
                            score[currentquestionindex] = 20;
                        } else {
                            // Incorrect answer
                            score[currentquestionindex] = 0;
                        }
                        nextQuestion();

                    } else if (ans3.isChecked()) {
                        if ((ans3.getText().toString()).equals(correctansw)) {
                            // Correct answer
                            score[currentquestionindex] = 20;
                        } else {
                            // Incorrect answer
                            score[currentquestionindex] = 0;
                        }
                        //Toast.makeText(QuizActivity.this, String.valueOf(score[currentquestionindex]), Toast.LENGTH_SHORT).show();
                        nextQuestion();

                    } else {
                        Toast.makeText(QuizActivity.this, "Select your answer", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
        else {
            //handle when all question is displayed
            //calculate total score
            int totalallq=0;

            for (int i=0;i<5;i++){

                totalallq=totalallq+score[i];
                //Log.d("total"+i,"sum"+totalallq);
            }

            Toast.makeText(QuizActivity.this,String.valueOf(totalallq), Toast.LENGTH_SHORT).show();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            String username = currentUser.getDisplayName();

            //do something to store history
            storeHistory(totalallq,currentquestionindex,username,chaptID);
        }
    }
    private void nextQuestion(){
        currentquestionindex++;
        displayQuestion();
        int currentProgress = progressBar.getProgress();
        int newProgress = currentProgress + 20;
        progressBar.setProgress(newProgress);

    }

    public void storeHistory(int totalsc,int index,String username,String chaptid){
        boolean pass=false;
        String chaptername,date,historyid;
        String done = String.valueOf(index);
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
         date = dateFormat.format(currentDate);
         historyid= UUID.randomUUID().toString();

        if (chaptid.equalsIgnoreCase("Chap 1")){
            chaptername = "Chapter 1";

            if (totalsc >= 90){
                pass=true;
            }

        } else if (chaptid.equalsIgnoreCase("Chap 2")) {
            chaptername = "Chapter 2";
            if (totalsc >= 80){
                pass=true;
            }
        }else {
            chaptername = "Chapter 3";
            if (totalsc >= 80){
                pass=true;
            }
        }
        popupresult(pass);
        history nhistory = new history(historyid,username,chaptid,done,date,chaptername,totalsc);


        addhistory(nhistory);
    }

    public void addhistory(history nhistory){
        reference = db.getReference("history");
        String hID = nhistory.getHistoryID();
        int score=nhistory.getScore();String uname = nhistory.getUserName();
        reference.child(hID).setValue(nhistory).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){


                    updatescore(score,uname);
                }
                else {
                    Toast.makeText(QuizActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void updatescore(int nscore,String uname){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uID = currentUser.getUid();
        DatabaseReference data = FirebaseDatabase.getInstance().getReference("user");
        DatabaseReference userRef = data.child(uID);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    user users = snapshot.getValue(user.class);

                    if (users!=null){
                        int newtotscore = users.getTotScore()+nscore;
                        //Toast.makeText(QuizActivity.this, String.valueOf(newtotscore), Toast.LENGTH_SHORT).show();
                        updateTotal(newtotscore,uID);
                    }
                    else {
                        Toast.makeText(QuizActivity.this, "no value", Toast.LENGTH_SHORT).show();
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void updateTotal(int tscore,String uname){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user").child(uname);

        //makesure put primary key
        reference.child("totScore").setValue(tscore).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //Toast.makeText(QuizActivity.this, "Succesfull update score", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void popupresult(boolean pass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (pass==true){
            builder.setTitle("Congratulations !");
            builder.setMessage("You have passed!");
        }else{
            builder.setTitle("Try again next time !");
            builder.setMessage("Don't give up!");
        }


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the OK button click event
                dialog.dismiss(); // Close the dialog
                Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
