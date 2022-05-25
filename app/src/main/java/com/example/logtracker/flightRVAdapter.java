package com.example.logtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class flightRVAdapter extends RecyclerView.Adapter<flightRVAdapter.ViewHolder> {

   private ArrayList<ArrayList> flightResults;

   public flightRVAdapter(ArrayList<ArrayList> flightres){
        flightResults = flightres;
   }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView date, fromLoc, toLoc, typeofaft, aftid, typeofflight, flightdur, lightCont, flightrules,dutyonBoard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.datetextView);
            fromLoc = itemView.findViewById(R.id.fromLoctextView);
            toLoc = itemView.findViewById(R.id.toLoctextView);
            typeofaft = itemView.findViewById(R.id.aircftTypetextView);
            aftid = itemView.findViewById(R.id.aircftIDTextView);
            typeofflight = itemView.findViewById(R.id.typeOfFilghttextView);
            flightdur = itemView.findViewById(R.id.durationtextView);
            lightCont = itemView.findViewById(R.id.lightConttextView);
            flightrules = itemView.findViewById(R.id.flightRulestextView);
            dutyonBoard = itemView.findViewById(R.id.dutyOnBoardtextView);
        }
    }




    @NonNull
    @Override
    public flightRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_row_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull flightRVAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(flightResults.get(position));

        holder.date.setText((String)flightResults.get(position).get(1));
        holder.fromLoc.setText("From:  "+(String)flightResults.get(position).get(4));
        holder.toLoc.setText("To:  "+(String)flightResults.get(position).get(5));
        holder.typeofaft.setText("Type:  "+(String)flightResults.get(position).get(2));
        holder.aftid.setText("ID:  "+(String)flightResults.get(position).get(3));
        holder.typeofflight.setText("Type of flight:  "+(String)flightResults.get(position).get(7));
        holder.flightdur.setText("Duration:  "+(String)flightResults.get(position).get(8));
        holder.lightCont.setText("Light Conditions:  "+(String)flightResults.get(position).get(9));
        holder.flightrules.setText("Flight Rules:  "+(String)flightResults.get(position).get(10));
        holder.dutyonBoard.setText("Duty on Board:  "+(String)flightResults.get(position).get(11));
    }

    @Override
    public int getItemCount() {
        return flightResults.size();
    }
}
