package com.shemaroo.ratefetchapp.datepicker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import com.shemaroo.ratefetchapp.R;

import java.util.Calendar;

public class DatePickerTo extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        int year = 2018;
        int month_new = (month + 1);
//        int day = 16;


        DatePickerDialog dpd = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
        String stringData = "Choose a Date";
        dpd.setTitle(stringData);

        //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors which support Theme declaration
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
            Supported Themes are....
                THEME_DEVICE_DEFAULT_DARK
                THEME_DEVICE_DEFAULT_LIGHT
                THEME_HOLO_DARK
                THEME_HOLO_LIGHT
                THEME_TRADITIONAL

         */
        //*********** Just uncomment any one below line to apply another Theme ************
//        return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK,this, year, month, day);
//        return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
//        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, this, year, month, day);
//        return new DatePickerDialog(getActivity(), AlertDialog.THEME_TRADITIONAL, this, year, month, day);

        return dpd;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month_new, int day) {

        int month = month_new+1;
        EditText tv = (EditText) getActivity().findViewById(R.id.date_to);


        String stringOfDate = day + "/" + month + "/" + year;
        tv.setText(stringOfDate);

    }
}
