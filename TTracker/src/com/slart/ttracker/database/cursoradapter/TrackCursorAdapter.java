package com.slart.ttracker.database.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.fdrs.ttracker.R;
import com.slart.ttracker.database.table.TrackTable;

public class TrackCursorAdapter extends CursorAdapter {
	
	LayoutInflater inflater;

	public TrackCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

	       TextView categoryField = (TextView) view.findViewById(R.id.rowCategory);
	       String categoryString = cursor.getString(cursor.getColumnIndex(TrackTable.COLUMN_CATEGORY)); 
	       categoryField.setText(categoryString);

	       long startDate = cursor.getLong(cursor.getColumnIndex(TrackTable.COLUMN_START));
	       long endDate = cursor.getLong(cursor.getColumnIndex(TrackTable.COLUMN_END));
	       
	       String startString = DateUtils.formatDateTime(context, startDate, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);
	       String endString = DateUtils.formatDateTime(context, endDate, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);
	       
	       //String rangeString  = DateUtils.formatDateRange(context, startDate, endDate, DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);
	       
	       String myRangeString = startString;
	       if (endDate != 0L) {
	    	   myRangeString += " — " + endString; 
	       }
	       else {
	    	   myRangeString = context.getString(R.string.since) + " " + myRangeString;
	       }
	       

	       TextView dateField = (TextView) view.findViewById(R.id.rowDates);
	       //dateField.setText(startString+" - "+endString);
	       dateField.setText(myRangeString);

    }

	@Override
	public View newView(Context arg0, Cursor cursor, ViewGroup parent) {
		return inflater.inflate(R.layout.track_row, parent, false);
	}

}
