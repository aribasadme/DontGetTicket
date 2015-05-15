package com.ara.dontgetticket;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by aRa on 30/4/15.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    String dateString;

    public interface OnCompleteListener {
        void onComplete(Object sender, String time);
    }

    private OnCompleteListener mListener;

    // make sure the Activity implemented it
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

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
        // Return selected Date in format DD/MM/YYY
        String formattedDay = "" + day;
        String formattedMonth = "" + month;

        if (day < 10)
            formattedDay = "0"+day;
        if (month < 10)
            formattedMonth = "0"+month;

        dateString = String.format("%s/%s/%d", formattedDay, formattedMonth, year);
        this.mListener.onComplete(this, dateString);
        System.out.println(dateString);
    }
}