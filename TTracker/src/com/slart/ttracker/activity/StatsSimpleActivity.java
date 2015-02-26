package com.slart.ttracker.activity;

import java.util.HashMap;
import java.util.Map;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fdrs.ttracker.R;
import com.slart.ttracker.database.DbUtil;
import com.slart.ttracker.database.dao.CategoryDao;
import com.slart.ttracker.database.table.TrackTable;
import com.slart.ttracker.util.Util;

public class StatsSimpleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats_simple);
		
		CategoryDao cDao = new CategoryDao(getApplicationContext());
		
		Map<String, Long> values = new HashMap<String, Long>();
		String[] categories = cDao.getAllCategoryNames().toArray(new String[0]);
		Long start = 0L;
		Long end = System.currentTimeMillis();
		
		String display = "";
		for (String cat : categories) {
			Long result = sumCategory(DbUtil.fetchAllTracks(getContentResolver(), buildWhere(cat, start, end)));
			display += cat+": "+Util.formatMillis(result)+"\n";
			values.put(cat, result);
		}
		
		TextView resultView = (TextView) findViewById(R.id.stats_simple_result);
		resultView.setText(display);
		
	}
	
	private Long sumCategory(Cursor c) {
		Long result = 0L;
		while (c.moveToNext()) {
			Long start = c.getLong(c.getColumnIndexOrThrow(TrackTable.COLUMN_START));
			Long end = c.getLong(c.getColumnIndexOrThrow(TrackTable.COLUMN_END));
			result += end-start;
		}
		return result;
	}
	
	private String buildWhere(String cat, Long start, Long end) {
		return  TrackTable.COLUMN_CATEGORY + "='" + cat + "'";//+" AND " +
				//TrackTable.COLUMN_START + "<" + end + " AND " +
				//TrackTable.COLUMN_END + ">" + start;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.stats_simple, menu);
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
