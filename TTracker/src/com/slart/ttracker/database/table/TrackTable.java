package com.slart.ttracker.database.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TrackTable {
	
	  // Database table
	  public static final String TABLE_TRACK = "track";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_CATEGORY = "category";
	  public static final String COLUMN_COMMENT = "comment";
	  public static final String COLUMN_START = "start";
	  public static final String COLUMN_END = "end";
	  public static final String COLUMN_LAT = "lat";
	  public static final String COLUMN_LON = "lon";
	  
	  // Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
			  + TABLE_TRACK
			  + "(" 
			  + COLUMN_ID + " integer primary key autoincrement, " 
			  + COLUMN_CATEGORY + " text not null, " 
			  + COLUMN_COMMENT + " text, " 
			  + COLUMN_START + " integer not null, " 
			  + COLUMN_END + " integer, " 
			  + COLUMN_LAT + " real, " 
			  + COLUMN_LON + " real" 
			  + ");";

	  public static void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
	    Log.w(TrackTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
	    database.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACK);
	    onCreate(database);
	  }
	  
	  public static String[] getProjection() {
		  String[] projection = { 
				  COLUMN_ID,
				  COLUMN_COMMENT,
				  COLUMN_CATEGORY,
				  COLUMN_START,
				  COLUMN_END,
				  COLUMN_LAT,
				  COLUMN_LON
		  };
		  return projection;
	  }
	  
}
