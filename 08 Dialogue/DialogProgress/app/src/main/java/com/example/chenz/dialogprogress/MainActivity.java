package com.example.chenz.dialogprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar1 = null;
    private ProgressBar progressBar2 = null;
    private Button button = null;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar1 = (ProgressBar) findViewById(R.id.firstBar);
        progressBar2 = (ProgressBar) findViewById(R.id.secondBar);
        button = (Button) findViewById(R.id.bt);
        button.setOnClickListener(new ButtonLitener());
    }

    protected class ButtonLitener implements OnClickListener {

        @Override
        public void onClick(View view){
            if(i==0) {
                progressBar1.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.VISIBLE);
                progressBar1.setMax(100);
            }else if(i<progressBar1.getMax()) {
                progressBar1.setProgress(i);
                progressBar1.setSecondaryProgress(i+10);
            }else {
                progressBar1.setVisibility(View.GONE);
                progressBar2.setVisibility(View.GONE);
            }
            i+=10;
        }
    }
}
