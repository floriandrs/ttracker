package com.slart.ttracker.database.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private String[] allColumns = { CategoryTable.COLUMN_ID, CategoryTable.COLUMN_NAME };

	public CategoryDao(Context context) {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Category createCategory(String category) {
		ContentValues values = new ContentValues();
		values.put(CategoryTable.COLUMN_NAME, category);
		long insertId = database.insert(CategoryTable.TABLE_CATEGORY, null, values);
		Cursor cursor = database.query(CategoryTable.TABLE_CATEGORY, allColumns, CategoryTable.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Category newCategory = cursorToCategory(cursor);
		cursor.close();
		return newCategory;
	}

	public void deleteCategory(Category category) {
		database.delete(CategoryTable.TABLE_CATEGORY, CategoryTable.COLUMN_ID + " = " + category.getId(), null);
	}

	public void deleteCategoryByName(String name) {
		database.delete(CategoryTable.TABLE_CATEGORY, CategoryTable.COLUMN_NAME + " = '" + name +"'", null);
	}
	
	public int count(String where) {
		Cursor cursor = database.query(CategoryTable.TABLE_CATEGORY, allColumns, where, null, null, null, null);
		return cursor.getCount();
	}

	public List<Category> getAllCategories() {

		List<Category> categories = new ArrayList<Category>();

		Cursor cursor = database.query(CategoryTable.TABLE_CATEGORY, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Category category = cursorToCategory(cursor);
			categories.add(category);
			cursor.moveToNext();
		}
		cursor.close();

		return categories;

	}
	
	public List<String> getAllCategoryNames() {
		List<Category> categories = getAllCategories();
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