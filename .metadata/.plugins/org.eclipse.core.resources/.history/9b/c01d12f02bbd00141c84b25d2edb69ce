package com.slart.ttracker.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.fdrs.ttracker.R;
import com.slart.ttracker.database.dao.CategoryDao;
import com.slart.ttracker.database.table.CategoryTable;
import com.slart.ttracker.util.Util;

public class EditCategoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_category);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_category, menu);
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
	
	public void onCreateNewCategory(View view) {

		CategoryDao dao = new CategoryDao(this);
		EditText nameField = (EditText) findViewById(R.id.name);
		String name = nameField.getText().toString();
		
		int count = dao.count(CategoryTable.COLUMN_NAME + "='"+name+"'");
		if (count == 0) {
			dao.createCategory(name);
			Util.toast(getApplicationContext(), getString(R.string.created_category) + " " + name);
		}
		else {
			Util.toast(getApplicationContext(), getString(R.string.category_already_exists) + " " + name);
		}
		
		Intent intent = new Intent(this, ViewAllCategoriesActivity.class);
		startActivity(intent);

	}
}
