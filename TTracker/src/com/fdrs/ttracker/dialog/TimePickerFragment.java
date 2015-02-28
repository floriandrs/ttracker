package com.fdrs.ttracker.dialog;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
	
	private OnTimeSetListener callback;
	private int hour;
	private int minute;
	
	public TimePickerFragment(OnTimeSetListener callback) {
		this.callback = callback;
		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
	}
	
	public TimePickerFragment(OnTimeSetListener callback, int hour, int minute) {
		this.callback = callback;
		this.hour =	hour;
		this.minute = minute;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new TimePickerDialog(getActivity(), callback, hour, minute, DateFormat.is24HourFormat(getActivity()));
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {}

}