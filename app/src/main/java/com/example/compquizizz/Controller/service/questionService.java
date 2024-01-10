package com.example.compquizizz.Controller.service;

import com.example.compquizizz.Model.chapter;
import com.example.compquizizz.Model.question;

import java.util.List;

public interface questionService {
    public List<question> retrieveQuestionByChap(String chap);
    public List<chapter> retrieveChapterDesc();

}
