package gre.gwindall.androidpersistfilter;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;

public class ListPersistFiltering extends ListActivity {
	private DatabaseHelper dbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_screen);
		dbHelper = new DatabaseHelper(this);
		Cursor results = dbHelper.getAllRecords();

		String[] columnNames = { DatabaseHelper.NAME_COL,
				DatabaseHelper.DATE_OF_BIRTH_COL, DatabaseHelper.EMAIL_COL };
		int[] displayNames = { R.id.TextName, R.id.TextDOB, R.id.TextEmail };

		final SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.list_record_row, results, columnNames, displayNames, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		setListAdapter(records);
		// displayData();

		records.setFilterQueryProvider(new FilterQueryProvider() {

			public Cursor runQuery(CharSequence constraint) {
				Log.d("xxxx", "runQuery constraint:" + constraint);
				// Query the database using the filter.
				// The constraint is the characters typed by the user
				Cursor cur = dbHelper.getFilteredRecords(constraint.toString());
				return cur; // now the adapter will have the new filtered
							// content
			}
		});

		EditText txtInput = (EditText) findViewById(R.id.editTextFilter);
		
		// Add a listener so whenever the user types text
		// we can re-query the database
		txtInput.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			// s is the characters typed by the user.  Use these
			// to filter the data. Calling filter(s) will cause
			// runQuery() to be executed.
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				records.getFilter().filter(s);
				records.notifyDataSetChanged();
			}
		});
	}

	/*
	 * private void displayData() { // TODO Auto-generated method stub Cursor
	 * results = dbHelper.getAllRecords();
	 * 
	 * String[] columnNames ={DatabaseHelper.NAME_COL,
	 * DatabaseHelper.DATE_OF_BIRTH_COL, DatabaseHelper.EMAIL_COL}; int[]
	 * displayNames={R.id.TextName, R.id.TextDOB, R.id.TextEmail};
	 * 
	 * SimpleCursorAdapter records = new SimpleCursorAdapter(this,
	 * R.layout.list_record_row, results, columnNames, displayNames);
	 * setListAdapter(records);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

	/* Display the menu. Note the menu is defined in list_menu.xml */
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_menu, menu);
		return true;
	}

	/* Process the user's selection from the menu */
	public boolean onOptionsItemSelected(MenuItem item) {
		// Find which menu item was selected
		switch (item.getItemId()) {
		case R.id.itemHome: // Home item was selected
			/*
			 * Easiest way to go back is to simulate the back button being
			 * pressed. See
			 * http://myandroidnote.blogspot.com/2011/04/go-back-to-
			 * previous-activity.html
			 */
			super.onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
