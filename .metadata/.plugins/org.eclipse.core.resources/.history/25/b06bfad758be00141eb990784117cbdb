package com.slart.ttracker.database.contentprovider;

import com.slart.ttracker.database.DatabaseHelper;
import com.slart.ttracker.database.table.TrackTable;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class TrackContentProvider extends ContentProvider {
	
	private DatabaseHelper db;
	
	// used for the UriMacher
	private static final int TRACKS = 10;
	private static final int TRACK_ID = 20;

	private static final String AUTHORITY = "com.slart.ttracker.track.contentprovider";

	private static final String BASE_PATH = "tracks";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/tracks";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/track";

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, TRACKS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", TRACK_ID);
	}

	public TrackContentProvider() {
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = db.getWritableDatabase();
		int rowsDeleted = 0;

		switch (uriType) {
		case TRACKS:
			rowsDeleted = sqlDB.delete(TrackTable.TABLE_TRACK, selection, selectionArgs);
			break;
		case TRACK_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TrackTable.TABLE_TRACK, TrackTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TrackTable.TABLE_TRACK, TrackTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);

		return rowsDeleted;

	}

	@Override
	public String getType(Uri uri) {
		// TODO: Implement this to handle requests for the MIME type of the data
		// at the given URI.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = db.getWritableDatabase();
		long id = 0;
		switch (uriType) {
			case TRACKS:
				id = sqlDB.insert(TrackTable.TABLE_TRACK, null, values);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);

		return Uri.parse(BASE_PATH + "/" + id);
		
	}

	@Override
	public boolean onCreate() {
		db = new DatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		
		  // Using SQLiteQueryBuilder instead of query() method
	    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

	    // check if the caller has requested a column which does not exists
	    //checkColumns(projection);

	    // Set the table
	    queryBuilder.setTables(TrackTable.TABLE_TRACK);

	    int uriType = sURIMatcher.match(uri);
	    switch (uriType) {
	    case TRACKS:
	      break;
	    case TRACK_ID:
	      // adding the ID to the original query
	      queryBuilder.appendWhere(TrackTable.COLUMN_ID + "=" + uri.getLastPathSegment());
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }

	    SQLiteDatabase database = db.getWritableDatabase();
	    Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
	    // make sure that potential listeners are getting notified
	    cursor.setNotificationUri(getContext().getContentResolver(), uri);

	    return cursor;
		
	}
	
	public Cursor queryById(Long id) {
		 Uri uri = Uri.parse(CONTENT_URI + "/" + id);
		 return query(uri, null, null, null, null);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

	    int uriType = sURIMatcher.match(uri);
	    SQLiteDatabase sqlDB = db.getWritableDatabase();
	    int rowsUpdated = 0;

	    switch (uriType) {
	    case TRACKS:
	    	rowsUpdated = sqlDB.update(TrackTable.TABLE_TRACK, 
	    			values, 
	    			selection,
	    			selectionArgs);
	    	break;
	    case TRACK_ID:
	    	String id = uri.getLastPathSegment();
	    	if (TextUtils.isEmpty(selection)) {
	    		rowsUpdated = sqlDB.update(TrackTable.TABLE_TRACK, 
	    				values,
	    				TrackTable.COLUMN_ID + "=" + id, 
	    				null);
	    	} 
	    	else {
	    		rowsUpdated = sqlDB.update(TrackTable.TABLE_TRACK, 
	    				values,
	    				TrackTable.COLUMN_ID + "=" + id 
	    				+ " and " 
	    				+ selection,
	    				selectionArgs);
	    	}
	    	break;
	    default:
	    	throw new IllegalArgumentException("Unknown URI: " + uri);
	    }

	    getContext().getContentResolver().notifyChange(uri, null);
	    return rowsUpdated;

	}
}
