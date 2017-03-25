package com.example.chenz.xmldesign2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    RadioButton rb1 = null;
    RadioButton rb2 = null;
    RadioButton rb3 = null;
    RadioButton rb4 = null;
    RadioButton currentRadioButton = null;
    RadioGroup radioGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取点选按钮组
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        //获取点选按钮
        rb1 = (RadioButton) findViewById(R.id.a);
        rb2 = (RadioButton) findViewById(R.id.b);
        rb3 = (RadioButton) findViewById(R.id.c);
        rb4 = (RadioButton) findViewById(R.id.d);
        rb1.setClickable(true);
        //监听单选按钮
        Button b1_sure = (Button)findViewById(R.id.sure);
        Button b2_cancel = (Button)findViewById(R.id.cancel);
        b1_sure.setOnClickListener(new b1_sure());
        b2_cancel.setOnClickListener(new b2_cancel());
    }

    class b1_sure implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (currentRadioButton.getText().equals("in")) {
                setTitle("你选择的答案是：是正确的！");
            } else {
                setTitle("你选择的答案是:是错误的！");
            }
        }
    }
    class b2_cancel implements OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            radioGroup.clearCheck();
            setTitle("");
        }
    }
}

