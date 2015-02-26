package com.slart.ttracker.dialog;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	
	private OnDateSetListener callback;
	private int year;
	private int month;
	private int day;
	
	public DatePickerFragment(OnDateSetListener callback) {
		this.callback = callback;
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
	}

	public DatePickerFragment(OnDateSetListener callback, int year, int month, int day) {
		this.callback = callback;
		this.year = year;
		this.month = month;
		this.day = day;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), callback, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {}

}