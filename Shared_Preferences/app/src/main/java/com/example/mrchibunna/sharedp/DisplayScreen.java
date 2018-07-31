package com.example.mrchibunna.sharedp;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class DisplayScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_info);

        SharedPreferences preferences = getSharedPreferences("missisohi", MODE_PRIVATE);
        String display = preferences.getString("display", "Sorry but we do not have any information at the moment\" + \"\\n\" + \"kindly check back in a few days. Thank you very much!");

        TextView displayInfo = (TextView) findViewById(R.id.textViewName);
        displayInfo.setText(display);

    }
}