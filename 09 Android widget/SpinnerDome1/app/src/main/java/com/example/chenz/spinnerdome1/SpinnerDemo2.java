package com.example.chenz.spinnerdome1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chenz on 2017/4/20.
 */

public class SpinnerDemo2 extends Activity{
    private static final String[] countriesStr = {"广州","惠州","珠海"};
    private TextView textView;
    private EditText editText;
    private Button button_add;
    private Button button_remove;
    private Spinner spinner;
    private ArrayAdapter adapter;
    private List allCountries;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i=0;i<countriesStr.length;i++) {
            allCountries.add(countriesStr[i]);
        }

        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,allCountries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.editText);
        button_add = (Button)findViewById(R.id.button_add);
        button_remove = (Button)findViewById(R.id.button_remove);
        spinner = (Spinner)findViewById(R.id.spinner);

        spinner.setAdapter(adapter);
        button_add.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                String newCountry = editText.getText().toString();

                for (int i=0;i<adapter.getCount();i++) {
                    if (newCountry.equals(adapter.getItem(i))) {
                        return;
                    }
                }

                if (!newCountry.equals("")) {
                    adapter.add(newCountry);
                    int position = adapter.getPosition(newCountry);
                    spinner.setSelection(position);
                    editText.setText("");
                }
            }
        });

        button_remove.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View arg0){
                if (spinner.getSelectedItem()!=null){
                    adapter.remove(spinner.getSelectedItem().toString());
                    editText.setText("");
                    if (adapter.getCount()==0) {
                        textView.setText("");
                    }
                }
            }
        });

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView arg0,View arg1,int arg2,long arg3){
                textView.setText(arg0.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView arg0) {

            }
        });
    }
}
