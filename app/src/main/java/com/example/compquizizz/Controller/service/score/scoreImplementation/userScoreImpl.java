package com.example.compquizizz.Controller.service.score.scoreImplementation;

import com.example.compquizizz.Controller.service.Implementation.historyServiceImpl;
import com.example.compquizizz.Controller.service.historyService;
import com.example.compquizizz.Controller.service.score.userScore;
import com.example.compquizizz.Model.history;

import java.util.List;

public class userScoreImpl implements userScore {
    List<history> historieslist=null;
    private historyService hServices = new historyServiceImpl();
    @Override
    public String getScoreByUname(String uName) {
        String totalScore= String.valueOf(0);

        //gethistory by uname in list
        //calculate the list
        //return the total
        //calculatee the total score for that username
         historieslist=hServices.getHistoryByUname(uName);
         //loop to calculate the total score
        if (historieslist != null) {
            for (history hist : historieslist) {
                totalScore += hist.getScore();
            }
        }
        return totalScore;
    }

    //get method for the highest three from user table
}
