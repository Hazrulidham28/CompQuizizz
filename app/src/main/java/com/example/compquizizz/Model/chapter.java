package com.example.compquizizz.Model;

public class chapter {

    String chapter_id,chapter_name,chapter_description;
    int passing_score;

    public chapter(String chaptID, String chapterName, String chapterDescription, int passingScore) {
        this.chapter_id = chaptID;
        this.chapter_name = chapterName;
        this.chapter_description = chapterDescription;
        this.passing_score = passingScore;
    }
    public chapter(){}
    public String getChaptID() {
        return chapter_id;
    }

    public void setChaptID(String chaptID) {
        chapter_id = chaptID;
    }

    public String getChapterName() {
        return chapter_name;
    }

    public void setChapterName(String chapterName) {
        chapter_name = chapterName;
    }

    public String getChapterDescription() {
        return chapter_description;
    }

    public void setChapterDescription(String chapterDescription) {
        chapter_description = chapterDescription;
    }

    public int getPassingScore() {
        return passing_score;
    }

    public void setPassingScore(int passingScore) {
        passing_score = passingScore;
    }
}
