package com.example.chenz.logdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.util.Log;

public class  main extends AppCompatActivity {

    private static final String ACTIVITY_TAG="LogDemo";
    private Button bt;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = (Button)findViewById(R.id.myButton);
        bt.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.v(main.ACTIVITY_TAG, "This is Verbose.");
                Log.d(main.ACTIVITY_TAG, "This is Debug.");
                Log.i(main.ACTIVITY_TAG, "This is Information");
                Log.w(main.ACTIVITY_TAG, "This is Warnning.");
                Log.e(main.ACTIVITY_TAG, "This is Error.");
            }
        }); }
}
