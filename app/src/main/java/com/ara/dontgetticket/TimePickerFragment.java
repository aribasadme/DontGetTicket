package com.ara.dontgetticket;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by aRa on 30/4/15.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    String timeString="";
//    private Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public String GetTime(){
//        return timeString = hour + ":" + minute;
        return timeString;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        Toast.makeText(context, String.format("Selected hour: %d:%d", hourOfDay, minute), Toast.LENGTH_LONG).show();
        timeString = String.format("%d:%d", hourOfDay, minute);
        System.out.println(timeString);
    }
}