package com.example.chenz.xmldesign3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    RadioButton rb1 = null;
    RadioButton rb2 = null;
    RadioButton rb3 = null;
    RadioButton rb4 = null;
    RadioButton currentGadioButton = null;
    RadioGroup radioGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获得单选按钮组和单选按钮
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rb1 = (RadioButton) findViewById(R.id.a);
        rb2 = (RadioButton) findViewById(R.id.b);
        rb3 = (RadioButton) findViewById(R.id.c);
        rb4 = (RadioButton) findViewById(R.id.d);
        rb1.setClickable(true);

        //监听单选按钮
        Button bt1_sure = (Button)findViewById(R.id.sure);
        Button bt2_cancel = (Button)findViewById(R.id.cancel);
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
            else if(rb3.isChecked())
                ans = "of";
            else if(rb4.isChecked())
                ans = "in";

            //new 一个Intent对象，并指定class
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,otherActivity.class);

            //new 一个 Bundle对象，并将要传递的数据导入
            Bundle bundle = new Bundle();
            bundle.putString("ans",ans);

            //将Bundle的对象assign给Intent
            intent.putExtras(bundle);

            //通过Intent对象启动另外一个activity
            MainActivity.this.startActivity(intent);
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
        super.onActivityResult(requestCode,requestCode,data);
        switch (resultCode){
            case RESULT_OK:
                //获取来自otherActivuty的数据，并显示在画面上
                Bundle bundle = data.getExtras();
                String ans = bundle.getString("ans");
                break;

            default:break;
        }
    }
}
