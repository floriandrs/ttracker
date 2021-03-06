package com.fdrs.ttracker.database.table;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CategoryTable {
	
	// Database table
	public static final String TABLE_CATEGORY = "category";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "category";
	public static final String COLUMN_COMMENT = "comment";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
			+ TABLE_CATEGORY
			+ "(" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_NAME + " text not null, " 
			+ COLUMN_COMMENT + " text" 
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(TrackTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		onCreate(database);
	}

	public static String[] getProjection() {
		String[] projection = { 
				COLUMN_ID,
				COLUMN_NAME,
				COLUMN_COMMENT
		};
		return projection;
    }

}
