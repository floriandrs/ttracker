package com.slart.ttracker.activitiy;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fdrs.ttracker.R;
import com.slart.ttracker.database.dao.CategoryDao;
import com.slart.ttracker.database.model.Category;
import com.slart.ttracker.util.Util;

public class ViewAllCategoriesActivity extends ListActivity {

	private CategoryDao dao;
	private ArrayAdapter<Category> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_all_categories);
		dao = new CategoryDao(this);
		ListView listView = getListView();
		registerForContextMenu(listView);
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshList();
	}
	
	protected void refreshList() {
		List<Category> values = dao.getAllCategories();
		adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, values); 
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_all_categories, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_add:
	            launchEditCategoryActivity();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.category_list_item, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.delete_category:
			deleteCategory(info.id);
			refreshList();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	private void deleteCategory(long id) {
		String category = adapter.getItem((int) id).getName();
		Util.toast(getApplicationContext(), String.valueOf(category));
		dao.deleteCategoryByName(category);
	}
	
	private void launchEditCategoryActivity() {
		Intent intent = new Intent(this, EditCategoryActivity.class);
		startActivity(intent);
	}
	


}
