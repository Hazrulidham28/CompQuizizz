package com.example.compquizizz.Model;

public class chapter {

    String ChaptID,ChapterName,ChapterDescription;
    int PassingScore;

    public chapter(String chaptID, String chapterName, String chapterDescription, int passingScore) {
        ChaptID = chaptID;
        ChapterName = chapterName;
        ChapterDescription = chapterDescription;
        PassingScore = passingScore;
    }

    public String getChaptID() {
        return ChaptID;
    }

    public void setChaptID(String chaptID) {
        ChaptID = chaptID;
    }

    public String getChapterName() {
        return ChapterName;
    }

    public void setChapterName(String chapterName) {
        ChapterName = chapterName;
    }

    public String getChapterDescription() {
        return ChapterDescription;
    }

    public void setChapterDescription(String chapterDescription) {
        ChapterDescription = chapterDescription;
    }

    public int getPassingScore() {
        return PassingScore;
    }

    public void setPassingScore(int passingScore) {
        PassingScore = passingScore;
    }
}
