package com.slart.ttracker.database.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.slart.ttracker.database.DatabaseHelper;
import com.slart.ttracker.database.model.Category;
import com.slart.ttracker.database.table.CategoryTable;

public class CategoryDao {
	
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] projection;

	public CategoryDao(Context context) {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		projection = CategoryTable.getProjection();
	}

	public void close() {
		dbHelper.close();
	}

	public void insert(String category) {
		ContentValues values = new ContentValues();
		values.put(CategoryTable.COLUMN_NAME, category);
		database.insert(CategoryTable.TABLE_CATEGORY, null, values);
	}

	public void delete(Category category) {
		database.delete(CategoryTable.TABLE_CATEGORY, CategoryTable.COLUMN_ID + " = " + category.getId(), null);
	}

	public void deleteByName(String name) {
		database.delete(CategoryTable.TABLE_CATEGORY, CategoryTable.COLUMN_NAME + " = '" + name +"'", null);
	}
	
	public int count(String where) {
		Cursor cursor = database.query(CategoryTable.TABLE_CATEGORY, projection, where, null, null, null, null);
		return cursor.getCount();
	}
	
	
	public List<Category> query() {
		return query(null, null, null);
	}

	public List<Category> query(String selection, String[] selectionArgs, String sortOrder) {
		return query(projection, selection, selectionArgs, sortOrder);
	}

	public List<Category> query(String[] projection, String selection, String[] selectionArgs, String sortOrder) {

		List<Category> categories = new ArrayList<Category>();

		Cursor cursor = database.query(CategoryTable.TABLE_CATEGORY, projection, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Category category = cursorToCategory(cursor);
			categories.add(category);
			cursor.moveToNext();
		}
		cursor.close();

		return categories;

	}
	
	public List<String> queryAllNames() {
		List<Category> categories = query();
		List<String> names = new ArrayList<String>();
		for (Category c : categories) {
			names.add(c.getName());
		}
		return names;
	}
	
	private Category cursorToCategory(Cursor cursor) {
		Category category = new Category();
		category.setId(cursor.getLong(0));
		category.setName(cursor.getString(1));
		return category;
	}

}
