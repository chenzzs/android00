package com.example.chenz.spinnerdome1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String[] countriesStr = new String[]{"惠州","珠海","广州"};
    private TextView textView;
    private Spinner spinner;
    private ArrayAdapter arrayAdapter;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);
        spinner = (Spinner)findViewById(R.id.spinner);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,countriesStr);
        arrayAdapter.setDropDownViewResource(R.layout.myspinner_downmenu);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int i,long l){
                textView.setText("选择的是"+countriesStr[i]);
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        animation = AnimationUtils.loadAnimation(this,R.anim.myanim);
        spinner.setOnTouchListener(new Spinner.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent me){
                view.startAnimation(animation);
                view.setVisibility(View.VISIBLE);
                return false;
            }
        });

        spinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view,boolean hsaFocus){

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spinner_demo1,menu);
        return true;
    }
}
