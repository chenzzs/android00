package com.example.chenz.xmldesign4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    RadioButton rb1 = null;
    RadioButton rb2 = null;
    RadioButton rb3 = null;
    RadioButton rb4 = null;
    RadioGroup radioGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rb1 = (RadioButton) findViewById(R.id.a);
        rb2 = (RadioButton) findViewById(R.id.b);
        rb3 = (RadioButton) findViewById(R.id.c);
        rb4 = (RadioButton) findViewById(R.id.d);
        rb1.setClickable(true);

        Button bt1_sure = (Button) findViewById(R.id.sure);
        Button bt2_cancel = (Button) findViewById(R.id.cancel);

        bt1_sure.setOnClickListener(new bt1_sure());
        bt2_cancel.setOnClickListener(new bt2_cancel());
    }

    class bt1_sure implements OnClickListener{

        @Override
        public void onClick(View view){
            String ans = "";
            if(rb1.isChecked())
                ans = "on";
            else if(rb2.isChecked())
                ans = "at";
            else if (rb3.isChecked())
                ans = "of";
            else if (rb4.isChecked())
                ans = "in";

            Intent intent = new Intent();
            intent.setClass(MainActivity.this,otherActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("ans",ans);

            intent.putExtras(bundle);
            //启动指定Activity并等待返回的结果，其中0是请求码，用于标识该请求
            startActivityForResult(intent,0);
        }
    }

    class bt2_cancel implements OnClickListener{

        @Override
        public void onClick(View view){
            // TODO Auto-generated method stub
            radioGroup.clearCheck();
            setTitle("");
        }
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode,resultCode,data);

        //当requestCode、resultCode同时为0，也就是处理特定的结果
        if(requestCode==0 && resultCode==0){
            //取得来自Activity2 的数据，并显示于画面上
            Bundle bundle = data.getExtras();
            String ans = bundle.getString("ans");
        }
    }
}
