package com.example.chenz.xmldesign3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

/**
 * Created by chenz on 2017/3/26.
 */

public class otherActivity extends AppCompatActivity {
    private Intent intent;
    private Bundle bundle;

    public void onClick(Bundle savedInstanceState){
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        //获取Intent中的Bundle对象
        Bundle bundle = this.getIntent().getExtras();

        //取得Bundle中的数据
        String ans = bundle.getString("ans");

        //判断所选答案
        String sexText = "";
        if(ans.equals("in"))
            sexText = "正确";
        else
            sexText = "错误";

        TextView textView = (TextView) findViewById(R.id.text1);
        textView.setText("您选择的答案是：" + sexText);

        //以findViewById()取得Button对象,并添加onClickListener
        Button bt3_back = (Button) findViewById(R.id.button_back);
        bt3_back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
			/* 返回result 回上一个activity */
                otherActivity.this.setResult(RESULT_OK, intent);
			/* 结束这个activity */
                otherActivity.this.finish();
            }
        });
    }
}
