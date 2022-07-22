package com.example.logtracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.example.logtracker.basicActivities.RegistrationActivity;
import com.example.logtracker.basicActivities.SearchActivity;

import java.util.ArrayList;
import java.util.Calendar;

public  class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public ArrayList<Integer> date = new ArrayList<Integer>();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }



    public void onDateSet(DatePicker view, int year, int month, int day) {
        //set the buttons
        date.add(0,day);
        date.add(1,(month + 1));
        date.add(2,year);
        if(this.getTag().equals("datePicker")) { //for registration fragment
            System.out.println("the date is " + "day: " + day + " month: " + (month + 1) + " of year: " + year);
            RegistrationActivity.setDateButton(date);
        }
        else if (this.getTag().equals("fromPicker")){ //for search fragment
            int mode =0; //for from date
            SearchActivity.setDateButton(date,mode);
            System.out.println("from picked");
        }
        else if(this.getTag().equals("untilPicker")){
            int mode =1; //for until date
            SearchActivity.setDateButton(date,mode);
            System.out.println("until picked");
        }
    }



    public ArrayList<Integer> getDates(){
        return date;
    }
}
