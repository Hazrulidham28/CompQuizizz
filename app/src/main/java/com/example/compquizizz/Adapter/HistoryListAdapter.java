package com.example.compquizizz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.example.compquizizz.Model.history;
import com.example.compquizizz.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {
     ArrayList<history> historyItems;
     Context context;

    // Constructor
    public HistoryListAdapter(Context context, ArrayList<history> historyItems) {
        this.context = context;
        this.historyItems = historyItems;
    }

    // onCreateViewHolder remains the same

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item,parent,false);

        HistoryListAdapter.ViewHolder viewHolders = new HistoryListAdapter.ViewHolder(v);
        return  viewHolders;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.ViewHolder holder, int position) {
        history item = historyItems.get(position);
        holder.date.setText((String)item.getDate());
        holder.chapter.setText((String)item.getChapter());
        holder.score.setText(String.valueOf(item.getScore()));

    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, chapter, score;
        // Add other TextViews for new fields if needed

        public ViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date_1_history);
            chapter = (TextView)view.findViewById(R.id.chapter_1_history);
            score = (TextView)view.findViewById(R.id.score_1);

        }
    }
}


