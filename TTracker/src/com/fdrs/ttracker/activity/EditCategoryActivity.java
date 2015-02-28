package com.fdrs.ttracker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.fdrs.ttracker.R;
import com.fdrs.ttracker.database.dao.CategoryDao;
import com.fdrs.ttracker.database.table.CategoryTable;
import com.fdrs.ttracker.util.Util;

public class EditCategoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_category);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_category, menu);
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
	
	public void onCreateNewCategory(View view) {

		CategoryDao dao = new CategoryDao(this);

		EditText nameField = (EditText) findViewById(R.id.name);
		String name = nameField.getText().toString();

		EditText commentField = (EditText) findViewById(R.id.comment);
		String comment = commentField.getText().toString();
		
		int count = dao.count(CategoryTable.COLUMN_NAME + "='"+name+"'");
		if (count == 0) {
			dao.insert(name, comment);
			Util.toast(getApplicationContext(), getString(R.string.created_category) + " " + name);
		}
		else {
			Util.toast(getApplicationContext(), getString(R.string.category_already_exists) + " " + name);
		}
		
		nameField.setText("");
		commentField.setText("");
		
		//Intent intent = new Intent(this, ViewAllCategoriesActivity.class);
		//startActivity(intent);

	}
}
