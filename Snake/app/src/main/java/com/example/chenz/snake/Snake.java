package com.example.chenz.snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Snake extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initStart();
    }

    public void initStart() {
        Button startButton = (Button) findViewById(R.id.start);
        Button authorButton = (Button) findViewById(R.id.author);
        Button helpButton = (Button) findViewById(R.id.help);
        Button exitButton = (Button) findViewById(R.id.exit);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Snake.this,Game.class);
                startActivity(intent);          //跳转到Game界面
                Snake.this.finish();            //销毁跳转之前的界面
            }
        });

        authorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Snake.this,AuthorView.class);
                startActivity(intent);
                Snake.this.finish();
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Snake.this,Help.class);
                startActivity(intent);
                Snake.this.finish();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snake.this.finish();
            }
        });
    }
}
