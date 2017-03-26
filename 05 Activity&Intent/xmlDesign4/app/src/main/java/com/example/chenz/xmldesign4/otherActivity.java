package com.example.chenz.xmldesign4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.VectorEnabledTintResources;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by chenz on 2017/3/26.
 */

public class otherActivity extends AppCompatActivity{

    private Intent intent;
    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState){
        //TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        //取得Intent 中的Bundle 对象
        Bundle bundle = this.getIntent().getExtras();
        ///* 取得Bundle 对象中的数据 */
        String ans = bundle.getString("ans");

        String sexText = "";
        if(ans.equals("in"))
            sexText = "正确";
        else
            sexText = "错误";

        TextView textView = (TextView) findViewById(R.id.text1);
        textView.setText("您选择的答案是：" + sexText);

        //以findViewById()取得Button 对象，并添加onClickListener
        Button bt3_back = (Button) findViewById(R.id.button_back);
        bt3_back.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                // TODO Auto-generated method stub
			    //返回result 回上一个activity
                otherActivity.this.setResult(RESULT_OK, intent);
			    //结束这个activity
                otherActivity.this.finish();
            }
        });
    }
}
