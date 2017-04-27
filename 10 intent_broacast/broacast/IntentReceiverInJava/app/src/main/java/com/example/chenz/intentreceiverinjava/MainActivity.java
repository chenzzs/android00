package com.example.chenz.intentreceiverinjava;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button boundButton = null;
    private Button relieveButton = null;
    private SMSReceiver smsReceiver = null;

    private static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boundButton = (Button) findViewById(R.id.bt1);
        boundButton.setOnClickListener(new RegisterReceiverListener());
        relieveButton = (Button) findViewById(R.id.bt2);
        relieveButton.setOnClickListener(new UnRegisterReceiverListener());
    }

    class RegisterReceiverListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            smsReceiver = new SMSReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(SMS_ACTION);
            MainActivity.this.registerReceiver(smsReceiver,filter);
        }

    }

    class UnRegisterReceiverListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            MainActivity.this.unregisterReceiver(smsReceiver);
        }

    }
}
