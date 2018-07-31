package com.example.mrchibunna.hellolistview;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,R.layout.listitem, COUNTRIES));
        ListView lv = getListView();

        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),((TextView)view).getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });


}

    static final String[]COUNTRIES =new String[]{
            "South Africa", "Ghana", "Algeria",
            "Libya", "Jamaica", "Nigeria",
            "Kenya", "Liberia","Germany", "Japan",
            "Russia", "France", "USA",
            "Lebanon", "Japan", "San Marino",
            "Palestine", "Syria",
    };
}
