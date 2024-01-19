package com.example.compquizizz.Model;

public class history {
    String HistoryID,UserName,ChaptID,QuestionDone,Date,Chapter;
    int Score;

    public history(String historyID, String userName, String chaptID, String questionDone, String date, String chapter, int score) {
        HistoryID = historyID;
        UserName = userName;
        ChaptID = chaptID;
        QuestionDone = questionDone;
        Date = date;
        Chapter = chapter;
        this.Score = score;
    }

    public history(){}

    public String getHistoryID() {
        return HistoryID;
    }

    public void setHistoryID(String historyID) {
        HistoryID = historyID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getChaptID() {
        return ChaptID;
    }

    public void setChaptID(String chaptID) {
        ChaptID = chaptID;
    }

    public String getQuestionDone() {
        return QuestionDone;
    }

    public void setQuestionDone(String questionDone) {
        QuestionDone = questionDone;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getChapter() {
        return Chapter;
    }

    public void setChapter(String chapter) {
        Chapter = chapter;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        this.Score = score;
    }
}
