package com.slart.ttracker.activitiy;

import static com.slart.ttracker.util.Util.formatDate;
import static com.slart.ttracker.util.Util.formatTime;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.fdrs.ttracker.R;
import com.slart.ttracker.database.DbUtil;
import com.slart.ttracker.database.contentprovider.TrackContentProvider;
import com.slart.ttracker.database.dao.CategoryDao;
import com.slart.ttracker.database.table.TrackTable;
import com.slart.ttracker.dialog.DatePickerFragment;
import com.slart.ttracker.dialog.TimePickerFragment;
import com.slart.ttracker.util.Util;

public class EditTrackActivity extends Activity {
	
	private Spinner categoryField;
	private EditText commentField;
	private Button startDateField;
	private Button startTimeField;
	private Button endDateField;
	private Button endTimeField;
	private EditText latField;
	private EditText lonField;
	
	private ArrayAdapter<String> categoryAdapter;
	
	private Long startDateEpoch;
	private Long startTimeEpoch;
	private Long endDateEpoch;
	private Long endTimeEpoch;
	
	private double currentLat;
	private double currentLon;
	
	private Uri trackUri;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.activity_create_new_track);
		
		categoryField = (Spinner) findViewById(R.id.track);
		commentField = (EditText) findViewById(R.id.comment);
		startDateField = (Button) findViewById(R.id.startDate);
		startTimeField = (Button) findViewById(R.id.startTime);
		endDateField = (Button) findViewById(R.id.endDate);
		endTimeField = (Button) findViewById(R.id.endTime);
		latField = (EditText) findViewById(R.id.lat);
        lonField = (EditText) findViewById(R.id.lon);
        
        CategoryDao dao = new CategoryDao(getApplicationContext());
        List<String> categories = dao.getAllCategoryNames();
        categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categoryField.setAdapter(categoryAdapter);
		
		Bundle extras = getIntent().getExtras();
		
		if (extras == null) {

			startDateField.setText(getString(R.string.today));
			startTimeField.setText(getString(R.string.now));
			endDateField.setText(getString(R.string.empty));
			endTimeField.setText(getString(R.string.empty));

			startDateEpoch = System.currentTimeMillis(); 
			startTimeEpoch = null; 
			endDateEpoch = null;
			endTimeEpoch = null;

		}
		else {

			trackUri = extras.getParcelable(TrackContentProvider.CONTENT_ITEM_TYPE);
			fillData(trackUri);

			Button submitButton = (Button) findViewById(R.id.enter);
			submitButton.setText(getResources().getString(R.string.update));
			

		}
	
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		      // Called when a new location is found by the network location provider.
		    	currentLat = Math.floor(location.getLatitude()*100)/100;
		    	currentLon = Math.floor(location.getLongitude()*100)/100;
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };

		// Register the listener with the Location Manager to receive location updates
		  locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, locationListener);


		//Button btn = (Button) findViewById(R.id.start);
		//btn.setEnabled(false);
		
		//setResult(RESULT_OK);
        //finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_new_track, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void fillData(Uri uri) {

		Cursor trackCursor = DbUtil.fetchTrack(uri, getContentResolver());
		trackCursor.moveToFirst();

		commentField.setText(trackCursor.getString(trackCursor.getColumnIndexOrThrow(TrackTable.COLUMN_COMMENT)));
		latField.setText(String.valueOf(trackCursor.getDouble(trackCursor.getColumnIndexOrThrow(TrackTable.COLUMN_LAT))));
		lonField.setText(String.valueOf(trackCursor.getDouble(trackCursor.getColumnIndexOrThrow(TrackTable.COLUMN_LON))));
		categoryField.setSelection(categoryAdapter.getPosition(trackCursor.getString(trackCursor.getColumnIndexOrThrow(TrackTable.COLUMN_CATEGORY))));
			
		Long startEpoch = trackCursor.getLong(trackCursor.getColumnIndexOrThrow(TrackTable.COLUMN_START));
		Long endEpoch = trackCursor.getLong(trackCursor.getColumnIndexOrThrow(TrackTable.COLUMN_END));
			
		startTimeEpoch = startEpoch % 86400000L;
		startDateEpoch = startEpoch - startTimeEpoch;
		endTimeEpoch = endEpoch % 86400000L;
		endDateEpoch = endEpoch - endTimeEpoch;
		
		String startDateText = Util.formatDateFromEpoch(getApplicationContext(), startEpoch);
		String startTimeText = Util.formatTimeFromEpoch(getApplicationContext(), startEpoch);
		String endDateText = Util.formatDateFromEpoch(getApplicationContext(), endEpoch);
		String endTimeText = Util.formatTimeFromEpoch(getApplicationContext(), endEpoch);
		
		if (endEpoch==0L) {
			endDateText = getString(R.string.empty);
			endTimeText = getString(R.string.empty);
		}

		startDateField.setText(startDateText);
		startTimeField.setText(startTimeText);
		endDateField.setText(endDateText);
		endTimeField.setText(endTimeText);

		trackCursor.close();

	}
	
	public void onCreateNewTrack(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		saveState();
		startActivity(intent);
	}

	public void onLocationUpdateButtonClick(View view) {
		
		String lat = String.valueOf(currentLat);
		String lon = String.valueOf(currentLon);
		
		latField.setText(lat);
		lonField.setText(lon);
		
	}
	
	public void setStartDate(View view) {

		OnDateSetListener callback = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
				if (startDateField != null) {
					startDateField.setText(formatDate(getApplicationContext(), year, month, day));
					startDateEpoch = Util.buildDateEpoch(year, month, day);
				}
			}
		};
		
		int[] yearMonthDay = epochToYearMonthDayHourMinute(startDateEpoch);
	    DialogFragment newFragment = new DatePickerFragment(callback, yearMonthDay[0], yearMonthDay[1], yearMonthDay[2]);
	    newFragment.show(getFragmentManager(), "startDatePicker");

	}
	
	public void setEndDate(View view) {

		OnDateSetListener callback = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
				if (endDateField != null) {
					endDateField.setText(formatDate(getApplicationContext(), year, month, day));
					endDateEpoch = Util.buildDateEpoch(year, month, day);
				}
			}
		};
		
		int[] yearMonthDay = epochToYearMonthDayHourMinute(endDateEpoch);
	    DialogFragment newFragment = new DatePickerFragment(callback, yearMonthDay[0], yearMonthDay[1], yearMonthDay[2]);
	    newFragment.show(getFragmentManager(), "endDatePicker");

	}

	public void setStartTime(View view) {
		OnTimeSetListener callback = new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				if (startTimeField != null) {
					startTimeField.setText(formatTime(getApplicationContext(), hourOfDay, minute));
					startTimeEpoch = Util.buildTimeEpoch(hourOfDay, minute);
				}
			}
		};
		int[] timeParts = epochToYearMonthDayHourMinute(startTimeEpoch);
	    DialogFragment newFragment = new TimePickerFragment(callback, timeParts[3], timeParts[4]);
	    newFragment.show(getFragmentManager(), "startTimePicker");
	}

	public void setEndTime(View view) {
		OnTimeSetListener callback = new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				if (endTimeField != null) {
					endTimeField.setText(formatTime(getApplicationContext(), hourOfDay, minute));
					endTimeEpoch = Util.buildTimeEpoch(hourOfDay, minute);
				}
			}
		};
		int[] timeParts = epochToYearMonthDayHourMinute(endTimeEpoch);
	    DialogFragment newFragment = new TimePickerFragment(callback, timeParts[3], timeParts[4]);
	    newFragment.show(getFragmentManager(), "endTimePicker");
	}
	
	private int[] epochToYearMonthDayHourMinute(Long epoch) {

		GregorianCalendar cal = new GregorianCalendar();
		if (cal!=null && epoch!=null) {
			cal.setTimeInMillis(epoch);
		}
		
		return new int[] {
				cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH),
				cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE)
		};

	}
	
	private void saveState() {

		String categoryValue = categoryField.getSelectedItem().toString();
		String commentValue = commentField.getText().toString();
		
		String latString = latField.getText().toString();
		Double latValue  = latString.isEmpty() ? null : Double.parseDouble(latString);

		String lonString = lonField.getText().toString();
		Double lonValue  = lonString.isEmpty() ? null : Double.parseDouble(lonString);

		Long startEpoch = Util.addEpochs(startDateEpoch, startTimeEpoch);
		Long endEpoch = Util.addEpochs(endDateEpoch, endTimeEpoch);

		ContentValues values = new ContentValues();
		values.put(TrackTable.COLUMN_COMMENT, commentValue);
		values.put(TrackTable.COLUMN_CATEGORY, categoryValue);
		values.put(TrackTable.COLUMN_LAT, latValue);
		values.put(TrackTable.COLUMN_LON, lonValue);

		values.put(TrackTable.COLUMN_START, startEpoch);
		if (endEpoch != null) {
			values.put(TrackTable.COLUMN_END, endEpoch);
		}

		if (trackUri == null) {
			trackUri = getContentResolver().insert(TrackContentProvider.CONTENT_URI, values);
			toastOnSubmit(getString(R.string.started_new_track), categoryValue);
		} 
		else {
			getContentResolver().update(trackUri, values, null, null);
			toastOnSubmit(getString(R.string.updated_track), categoryValue);
		}
	}
	
	private void toastOnSubmit(String label, String cat) {
		Util.toast(getApplicationContext(), label+" "+cat);
	}
	
}
