package com.codetracker.logtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class totalHoursAdapter extends RecyclerView.Adapter<totalHoursAdapter.ViewHolder> {

    private ArrayList<ArrayList> hourResults;

    public totalHoursAdapter(ArrayList<ArrayList> flightres){
        hourResults = flightres;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView typeofaft, flightdur;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeofaft = itemView.findViewById(R.id.typetextView);
            flightdur = itemView.findViewById(R.id.totalHoursTextView);

        }
    }




    @NonNull
    @Override
    public totalHoursAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hours_row_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull totalHoursAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(hourResults.get(position));

        holder.typeofaft.setText((String)hourResults.get(position).get(0));
        holder.flightdur.setText((String)hourResults.get(position).get(1));
    }

    @Override
    public int getItemCount() {
        return hourResults.size();
    }
}