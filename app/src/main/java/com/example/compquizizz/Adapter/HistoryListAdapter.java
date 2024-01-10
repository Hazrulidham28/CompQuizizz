package com.example.compquizizz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.compquizizz.R;

import java.util.ArrayList;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {
    ArrayList<String> dateText, chapterText, scoreText;
    Context context;

    // Initialization Constructor
    public HistoryListAdapter(Context context, ArrayList<String> dateText, ArrayList<String> chapterText, ArrayList<String> scoreText) {
        this.context = context;
        this.dateText = dateText;
        this.chapterText = chapterText;
        this.scoreText = scoreText;
    }

    //Inflate the layout by instantiating history_list_item.xml
    @NonNull
    @Override
    public HistoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);

        //Pass view to ViewHolder
        HistoryListAdapter.ViewHolder viewHolder = new HistoryListAdapter.ViewHolder(view);
        return viewHolder;

    }

    //Binding the data into the specified position
    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.ViewHolder holder, int position) {
        // TypeCast Object to int type

        holder.date.setText((String) dateText.get(position));
        holder.chapter.setText((String) chapterText.get(position));
        holder.score.setText((String) scoreText.get(position));
    }

    @Override
    public int getItemCount() {
        return scoreText.size();
    }

    //Instantiate view in specified view
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, chapter, score;

        public ViewHolder(View view){
            super(view);
            date = (TextView) view.findViewById(R.id.date_1_history);
            chapter = (TextView) view.findViewById(R.id.chapter_1_history);
            score = (TextView) view.findViewById(R.id.score_1);
        }

    }
}

