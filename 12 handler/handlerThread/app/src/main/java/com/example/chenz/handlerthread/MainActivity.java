package com.example.chenz.handlerthread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar = null;
    Button button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.bar);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new ButtonListener());
    }

    class ButtonListener implements OnClickListener {
        public void onClick(View view) {
            progressBar.setVisibility(View.VISIBLE);
            updateBarHandler.post(updateThread);
        }
    }

    Handler updateBarHandler = new Handler() {
        public void handleMessage(Message msg) {
            progressBar.setProgress(msg.arg1);
            updateBarHandler.post(updateThread);
        }
    };

    Runnable updateThread = new Runnable() {
        int i = 0;
        @Override
        public void run() {
            System.out.println("Begin Thread");
            i += 10;
            Message msg = updateBarHandler.obtainMessage();
            msg.arg1 = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            updateBarHandler.sendMessage(msg);
            if(i==100) {
                updateBarHandler.removeCallbacks(updateThread);
            }
        }
    };
}
