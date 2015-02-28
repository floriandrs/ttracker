package com.fdrs.ttracker.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fdrs.ttracker.R;
import com.fdrs.ttracker.util.Util;

public class StatsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.stats, menu);
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
	
	public void onOpenActivityStatsSimple(View view) {
		Intent intent = new Intent(this, StatsSimpleActivity.class);
		startActivity(intent);
	}

	public void onOpenActivityStatsAverage(View view) {
		Intent intent = new Intent(this, StatsAverageActivity.class);
		intent.putExtra(StatsAverageActivity.START, Util.buildDateEpoch(2015, 1, 1));
		intent.putExtra(StatsAverageActivity.END, Util.buildDateEpoch(2016, 1, 1));
		startActivity(intent);
	}
}
