package com.example.chenz.handlermessageprogess;

import android.os.Handler;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button startButton = null;
    private Button endButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(new StartButtonListener());
        endButton = (Button) findViewById(R.id.end);
        endButton.setOnClickListener(new EndButtonListener());
    }

    Handler handler = new Handler();

    class StartButtonListener implements View.OnClickListener {
        public void onClick(View view){
            handler.post(updataThread);
        }
    }

    class EndButtonListener implements View.OnClickListener {
        public void onClick(View view) {
            handler.post(updataThread);
        }
    }

    Runnable updataThread = new Runnable() {
        @Override
        public void run() {
            System.out.println("UpdataThread");
            Log.v("tag","UpdateThread");
            handler.postDelayed(updataThread,2000);
        }
    };
}
