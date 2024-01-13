package com.example.compquizizz.Controller.service;

import com.example.compquizizz.Model.history;

import java.util.List;

public interface historyService {
    public String addHistory(history History);

    public List<history> getHistoryByUname(String uName);
}
