package com.example.chenz.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by chenz on 2017/5/5.
 */

public class Help extends Activity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        Button backButton = (Button) findViewById(R.id.back2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Help.this,Snake.class);
                startActivity(intent);
                Help.this.finish();
            }
        });
    }
}
