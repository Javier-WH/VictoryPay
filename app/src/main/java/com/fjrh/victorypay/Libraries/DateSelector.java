package com.fjrh.victorypay.Libraries;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.fjrh.victorypay.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// @param min and max date format expample 01/01/2020
public class DateSelector implements View.OnClickListener {

    Context context;
    TextView _view;
    Date minDate = new Date();
    Date maxDate = new Date();

    public DateSelector(Context context, TextView _view) {
        this.context = context;
        this._view = _view;
    }
    public DateSelector(Context context, TextView _view, String min) {
        this(context, _view);
        minDate = getDate(min);
    }

    public DateSelector(Context context, TextView _view, String min, String max) {
        this(context, _view, min);
        maxDate = getDate(max);

    }

    Calendar calendar = Calendar.getInstance();
    int _day = calendar.get(Calendar.DAY_OF_MONTH);
    int _month = calendar.get(Calendar.MONTH);
    int _year = calendar.get(Calendar.YEAR);

    @Override
    public void onClick(View v) {
        DatePickerDialog dpd = new DatePickerDialog(context, R.style.Theme_VictoryPay_datePicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                _view.setText(dayOfMonth + " / " + (month + 1) + " / " + year);
            }
        }, _day, _month, _year);

        //Establece la fecha maxima del calendario, que es la fecha actual
        calendar.setTime(maxDate);
        calendar.add(Calendar.DATE, 7);
        dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        //establece la fecha minima del calendario
        Calendar caMinDate = Calendar.getInstance();
        caMinDate.setTime(minDate);
        caMinDate.add(Calendar.DATE, 7);
        dpd.getDatePicker().setMinDate(caMinDate.getTimeInMillis());

        dpd.show();
    }

    private Date getDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
