package com.fdrs.ttracker.database;

import com.fdrs.ttracker.database.table.CategoryTable;
import com.fdrs.ttracker.database.table.TrackTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	  private static final String DATABASE_NAME = "tracktable.db";
	  private static final int DATABASE_VERSION = 12;

	  public DatabaseHelper(Context context) {
		  super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  // Method is called during creation of the database
	  @Override
	  public void onCreate(SQLiteDatabase database) {
		  TrackTable.onCreate(database);
		  CategoryTable.onCreate(database);
	  }

	  // Method is called during an upgrade of the database,
	  // e.g. if you increase the database version
	  @Override
	  public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		  TrackTable.onUpgrade(database, oldVersion, newVersion);
		  CategoryTable.onUpgrade(database, oldVersion, newVersion);
	  }

}
