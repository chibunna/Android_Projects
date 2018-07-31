package gre.gwindall.androidpersistfilter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WelcomePersistFiltering extends Activity {
	private DatabaseHelper dbHelper;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_screen);

		Button btnEnter = (Button) findViewById(R.id.buttonEnter);
		Button btnList = (Button) findViewById(R.id.buttonList);
		Button btnDelete = (Button) findViewById(R.id.buttonDelete);
		/*
		 * Handle the event generated when the user clicks the "Enter data"
		 * button
		 */
		btnEnter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				displayEnterDataScreen(); // call method defined later in the
											// program
			}
		});

		/*
		 * Handle the event generated when the user clicks the "List records"
		 * button
		 */
		
		btnList.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				displayListScreen(); // call method defined later in the program
			}
		});

		/*
		 * Handle the event generated when the user clicks the "Delete all Records"
		 * button
		 */
		
		btnDelete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				deleteRecords(); // call method defined later in the program
			}
		});
		
		// Get ready to access the database later if we want to delete records
		dbHelper = new DatabaseHelper(this);
	}

	private void displayEnterDataScreen() {
		startActivity(new Intent(this, EnterDataPersistFiltering.class));
	}

	private void displayListScreen() {
		startActivity(new Intent(this, ListPersistFiltering.class));
	}
	
	private void deleteRecords() {
		// Create and display the Alert dialog
		new AlertDialog.Builder(this)
				.setMessage(
						"Do you really want to delete all records?")
				.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int which) {
								// do nothing - it will just close when clicked
							}
						})
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,	int which) {
								// save the details entered if "Save" button clicked
								dbHelper.deleteAllRecords();
								popupToast("All records deleted");
							}
						}).show();
		
	}	
	/* Utility method created to display a popup "toast" alert */
	private void popupToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

}
