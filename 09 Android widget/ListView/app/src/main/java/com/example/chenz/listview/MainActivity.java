package com.example.chenz.listview;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {

    String[] weekStrings = new String[]{"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,weekStrings);
        this.setListAdapter(arrayAdapter);
        this.getListView().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.setTitle(((TextView) view).getText());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MainActivity.this.setTitle("nothingSelected!");
            }
        });
    }
}
