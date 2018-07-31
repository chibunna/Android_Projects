/* 
 * EnterDataPersist3.java
 * 
 * Android application to demonstrate writing to and reading from 
 * a database.  Demonstrates creating a database with two tables
 * and inserting rows containing a foreign key.
 * 
 * The actual database handling is in the class DatabaseHelper
 */

package gre.gwindall.androidpersistfilter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class EnterDataPersistFiltering extends Activity {

	private DatabaseHelper dbHelper;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_data_screen);

		DatePicker dp = (DatePicker) this.findViewById(R.id.DatePickerDOB);
		dp.init(1980, 5, 1, null); // set to 1st June 1980 - note months start
									// at 0
		dp.setCalendarViewShown(false); // optional - suppress calendar view

		Button btnNext = (Button) findViewById(R.id.ButtonNext);
		/* Handle the event generated when the user clicks a button */
		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				displayNextAlert(); // call method defined later in the program
			}
		});

		// Get ready to access the database later
		dbHelper = new DatabaseHelper(this);
	}

	/* Display the menu. Note the menu is defined in menu.xml */
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	/* Process the user's selection from the menu */
	public boolean onOptionsItemSelected(MenuItem item) {
		// Find which menu item was selected
		switch (item.getItemId()) {
		case R.id.itemNext: // Next item was selected
			popupToast("You selected Next from the menu");
			displayNextAlert(); // call method defined later in the program
			return true;
		case R.id.itemHome: // Home item was selected
			/*
			 * Easiest way to go back is to simulate the back button being pressed.
			 * See http://myandroidnote.blogspot.com/2011/04/go-back-to-previous-activity.html
			 */
			super.onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * The following are "helper" methods. They are declared as private because
	 * they are only called from within the class.
	 */

	/* Utility method created to display a popup "toast" alert */
	private void popupToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	/*
	 * Method to display an Alert confirming the user's input. Makes sense to
	 * have this in a separate method as it is called when either the "Next"
	 * menu item or button is clicked.
	 */
	private void displayNextAlert() {
		// Get what the user entered
		EditText nameInput = (EditText) findViewById(R.id.EditTextName);
		DatePicker dobInput = (DatePicker) findViewById(R.id.DatePickerDOB);
		EditText emailInput = (EditText) findViewById(R.id.EditTextEmail);

		// final so we can reference them in the anonymous inner class below

		final String strName = nameInput.getText().toString();
		final String strDOB = dobInput.getDayOfMonth() + "/"
				+ (dobInput.getMonth() + 1) + "/" + dobInput.getYear();
		final String strEmail = emailInput.getText().toString();

		// Create and display the Alert dialog
		new AlertDialog.Builder(this)
				.setTitle("   Details entered")
				.setMessage(
						" Details entered:\n " + strName + "\n " + strDOB
								+ "\n " + strEmail)
				.setNeutralButton("Back",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int which) {
								// do nothing - it will just close when clicked
							}
						})
				.setPositiveButton("Save",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,	int which) {
								// save the details entered if "Save" button clicked
								saveDetails(strName, strDOB, strEmail);
							}
						}).show();
	}

	protected void saveDetails(String name, String dob, String email) {
		try {
			
		// insertDetails() in the DataBaseHelper class does the real work	
	    // Note the "hard coded" skills.  Really we would want the user to be
	    // able to enter these.
		dbHelper.insertDetails(name, dob, email, "reading", "writing");
		long recordCount = dbHelper.getNumberOfRecords();
		// confirm details saved ...
		new AlertDialog.Builder(EnterDataPersistFiltering.this)
				.setTitle("   ** Details saved **\n" +
                recordCount + " records have been stored")
				.setNeutralButton("Next",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// do nothing
							}
						}).show();
		}
		catch (SQLiteException sqle) {
			// ... or display error message
			android.util.Log.w(this.getClass().getName(), 
					"   Error saving to database");
			new AlertDialog.Builder(EnterDataPersistFiltering.this)
			.setTitle("Couldn't save details")
			.setNeutralButton("Next",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							// do nothing
						}
					}).show();
		}

	}
}