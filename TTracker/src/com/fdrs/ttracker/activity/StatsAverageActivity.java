package com.fdrs.ttracker.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fdrs.ttracker.R;
import com.fdrs.ttracker.database.DbUtil;
import com.fdrs.ttracker.database.dao.CategoryDao;
import com.fdrs.ttracker.database.table.TrackTable;
import com.fdrs.ttracker.util.Util;

public class StatsAverageActivity extends Activity {
	
	protected static final String START = "start";
	protected static final String END = "end";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats_average);
		
		Long start = null;
		Long end = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Long extraStart = extras.getLong(START);
			Long extraEnd = extras.getLong(END);
			if (extraStart != null && extraEnd != null) {
				start = extraStart;
				end = extraEnd;
			}
		}
		
		String display = "";
		String[] categories = new CategoryDao(getApplicationContext()).queryAllNames().toArray(new String[0]);
		for (String cat : categories) {
			Long result = avgCategory(DbUtil.fetchAllTracks(getContentResolver(), buildWhere(cat, start, end)));
			display += cat + ": " + Util.formatMillis(result) + "\n";
		}
		
		TextView resultView = (TextView) findViewById(R.id.stats_average_result);
		resultView.setText(display);
		
	}
	
	private Long avgCategory(Cursor c) {
		Long result = 0L;
		int i = 0;
		while (c.moveToNext()) {
			i++;
			Long start = c.getLong(c.getColumnIndexOrThrow(TrackTable.COLUMN_START));
			Long end = c.getLong(c.getColumnIndexOrThrow(TrackTable.COLUMN_END));
			result += end-start;
		}
		if (i>1) {
			result /= i;
		}
		return result;
	}
	
	private String buildWhere(String cat, Long start, Long end) {
		String result = TrackTable.COLUMN_CATEGORY + "='" + cat + "'"+" AND " + TrackTable.COLUMN_END + " IS NOT NULL";
		if (start != null && end != null) {
			result += " AND " + TrackTable.COLUMN_START + "<" + end + " AND " + TrackTable.COLUMN_END + ">" + start;
		}
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.stats_average, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
