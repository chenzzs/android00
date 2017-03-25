package com.example.chenz.xmldesign1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    RadioButton rg1 = null;
    RadioButton rg2 = null;
    RadioButton rg3 = null;
    RadioButton rg4 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rg1 = (RadioButton) findViewById(R.id.a);
        rg2 = (RadioButton) findViewById(R.id.b);
        rg3 = (RadioButton) findViewById(R.id.c);
        rg4 = (RadioButton) findViewById(R.id.d);
        rg1.setClickable(true);

        Button button1 = (Button)findViewById(R.id.sure);
        Button button2 = (Button)findViewById(R.id.cancel);
    }
}
