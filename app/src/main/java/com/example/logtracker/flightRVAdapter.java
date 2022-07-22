package com.example.logtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class flightRVAdapter extends RecyclerView.Adapter<flightRVAdapter.ViewHolder> {

   private ArrayList<ArrayList> flightResults;

   private OnDeleteListener mOnDeleteListener;


   //constructor
   public flightRVAdapter(ArrayList<ArrayList> flightres, OnDeleteListener onDeleteListener){

       flightResults = flightres;
       this.mOnDeleteListener = onDeleteListener;


   }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView date, fromLoc, toLoc, typeofaft, aftid, typeofflight, flightdur, lightCont, flightrules,dutyonBoard;

        OnDeleteListener onDeleteListener;

        public ViewHolder(@NonNull View itemView, OnDeleteListener onDeleteListener) {
            super(itemView);
            this.onDeleteListener=onDeleteListener;

            date = itemView.findViewById(R.id.typetextView);
            fromLoc = itemView.findViewById(R.id.fromLoctextView);
            toLoc = itemView.findViewById(R.id.toLoctextView);
            typeofaft = itemView.findViewById(R.id.aircftTypetextView);
            aftid = itemView.findViewById(R.id.totalHoursTextView);
            typeofflight = itemView.findViewById(R.id.typeOfFilghttextView);
            flightdur = itemView.findViewById(R.id.durationtextView);
            lightCont = itemView.findViewById(R.id.lightConttextView);
            flightrules = itemView.findViewById(R.id.flightRulestextView);
            dutyonBoard = itemView.findViewById(R.id.dutyOnBoardtextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onDeleteListener.onDeleteClick(getAdapterPosition());
        }
    }



    @NonNull
    @Override
    public flightRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_row_layout,parent,false);
        return new ViewHolder(v, mOnDeleteListener);
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

    public interface OnDeleteListener{
       void onDeleteClick(int position);
    }
}
