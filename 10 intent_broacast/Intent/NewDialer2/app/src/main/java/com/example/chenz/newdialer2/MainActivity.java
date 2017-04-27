package com.example.chenz.newdialer2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button)findViewById(R.id.bt);
        final EditText editText = (EditText)findViewById(R.id.et);

        button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                String call = editText.getText().toString();
                if(PhoneNumberUtils.isGlobalPhoneNumber(call)){
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(call));
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this,"您输入的格式不对，请重新输入！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
