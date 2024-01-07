package com.example.compquizizz.Model;

public class question {
    String QuestionID,ChaptID,QuestionText,choice1,choice2,choice3,correctAnsw;

    public question(String questionID, String chaptID, String questionText, String choice1, String choice2, String choice3, String correctAnsw) {
        QuestionID = questionID;
        ChaptID = chaptID;
        QuestionText = questionText;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.correctAnsw = correctAnsw;
    }

    public String getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(String questionID) {
        QuestionID = questionID;
    }

    public String getChaptID() {
        return ChaptID;
    }

    public void setChaptID(String chaptID) {
        ChaptID = chaptID;
    }

    public String getQuestionText() {
        return QuestionText;
    }

    public void setQuestionText(String questionText) {
        QuestionText = questionText;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getCorrectAnsw() {
        return correctAnsw;
    }

    public void setCorrectAnsw(String correctAnsw) {
        this.correctAnsw = correctAnsw;
    }
}
