/* 
 * DatabaseHelper.java
 * 
 * For use with the Activity class EnterDataPersist3. 
 * 
 * This class does the actual database handling and is about as simple
 * as I can make it.  
 * 
 * Loosely based on pages 228/229 of "Beginning Android 2"
 * by Mark Murphy
 */

package gre.gwindall.androidpersistfilter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "Details_4";
	private static final String TABLE_NAME = "details";
	public static final String NAME_COL = "name";
	public static final String DATE_OF_BIRTH_COL = "dob";
	public static final String EMAIL_COL = "email";
	public static final String PERSON_ID_COL = "person_id";
	public static final String SKILL_COL = "skill";
	private SQLiteDatabase database;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1); // Note - 1 is a version number
												// for the database

		// get a reference to our database which we'll use later to insert
		database = getWritableDatabase();
	}

	@Override
	// Automatically called if the database needs creating
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE details (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, dob TEXT, email TEXT);");
		db.execSQL("CREATE TABLE skills (_id INTEGER PRIMARY KEY AUTOINCREMENT, person_id INTEGER, skill TEXT);");
	}

	@Override
	// Automatically called if database version number changes
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// bit mean to lose old data but at least we'll warn this is happening
		android.util.Log.w(this.getClass().getName(), DATABASE_NAME
				+ " database upgrade to version " + newVersion
				+ " old data lost");
		db.execSQL("DROP TABLE IF EXISTS details");
		db.execSQL("DROP TABLE IF EXISTS skills");
		onCreate(db);
	}

	// Called by EnterDataPersist3 to insert data entered by the user into
	// the database. We could use raw SQL (execSQL) but it is better to
	// use the purpose built insert method.
	public long insertDetails(String name, String dob, String email,
			String skill1, String skill2) {
		ContentValues rowValues = new ContentValues();

		// Assemble row of data in the ContentValues object
		rowValues.put(NAME_COL, name);
		rowValues.put(DATE_OF_BIRTH_COL, dob);
		rowValues.put(EMAIL_COL, email);

		// save the id of the person just inserted
		long personId = database.insertOrThrow("details", null, rowValues);

		// demonstrate adding two skills for this person

		rowValues.clear();

		rowValues.put(PERSON_ID_COL, personId);
		rowValues.put(SKILL_COL, skill1);

		database.insertOrThrow("skills", null, rowValues);

		rowValues.put(SKILL_COL, skill2);

		database.insertOrThrow("skills", null, rowValues);

		return personId;

	}

	// get the number of rows by doing a query - maybe a bit inefficient
	public long getNumberOfRecords() {
		Cursor c = database.query(TABLE_NAME, null, null, null, null, null,
				null);
		return c.getCount();
	}

	// get all the rows of the details table ordered by the name column
	public Cursor getAllRecords() {
		return database.query(TABLE_NAME, null, null, null, null, null,
				NAME_COL);

		// Alternative to demonstrate rawQuery()
		// return database.rawQuery("SELECT * FROM details ORDER BY name",
		// null);

		// Alternative demonstrating some of the other parameters
		// return database.query(TABLE_NAME, new String[] {"_id", NAME_COL,
		// DATE_OF_BIRTH_COL, EMAIL_COL},
		// "name like ?" , new String [] {"g%"}, null, null, NAME_COL);

	}

	// get all the rows of the details table ordered by the name column
	public Cursor getFilteredRecords(String filter) {
		// return database.query(TABLE_NAME, null, null, null, null, null,
		// NAME_COL);

		// Alternative to demonstrate rawQuery()
		// return database.rawQuery("SELECT * FROM details ORDER BY name",
		// null);

		// Alternative demonstrating some of the other parameters
		return database.query(TABLE_NAME, new String[] { "_id", NAME_COL,
				DATE_OF_BIRTH_COL, EMAIL_COL }, "name like ?",
				new String[] { filter + "%" }, null, null, NAME_COL);

	}

	// delete all the rows of the details table
	public void deleteAllRecords() {
		database.delete(TABLE_NAME, null, null);
	}
}
