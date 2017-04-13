package com.example.chenz.dialogdatepicker;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.DateKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView showdate;
    private Button setdate;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showdate = (TextView) findViewById(R.id.showdate);
        setdate = (Button) findViewById(R.id.setdate);

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = new Date();
        calendar.setTime(date);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showdate.setText("Now:"+year+"-"+(month+1)+"-"+day);

        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, DateListener,year,month,day);
                datePickerDialog.show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener DateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker,int y,int m,int d){
            year = y;
            month = m;
            day = d;
            updateDate();
        }

        private void updateDate() {
            showdate.setText("Now:"+year+"-"+(month+1)+"-"+day);
        }
    };
}
