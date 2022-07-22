package com.example.logtracker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.logtracker.basicActivities.RegistrationActivity;

import java.util.ArrayList;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public ArrayList<Integer> time = new ArrayList<Integer>();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour = 0;
        int minute = 0;

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        time.add(0,hourOfDay);
        time.add(1,minute);
        System.out.println("the flight time picked is : "+ hourOfDay+ " : "+minute);
        RegistrationActivity.setTimeButton(time);

    }

    public ArrayList<Integer> getTime(){return time;}
}
