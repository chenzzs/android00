package com.example.chenz.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by chenz on 2017/5/5.
 */

public class Game extends Activity{

    private SnakeView mSnakeView;
    private static String ICICLE_KEY = "snake-view";

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //隐藏系统自带标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game);

        mSnakeView = (SnakeView) findViewById(R.id.snake);
        mSnakeView.setTextView((TextView) findViewById(R.id.text));
        mSnakeView.setStartButton((Button) findViewById(R.id.play));
        mSnakeView.setControlButton((ImageButton) findViewById(R.id.left),(ImageButton) findViewById(R.id.right),
                (ImageButton) findViewById(R.id.up),(ImageButton) findViewById(R.id.down));

        if (savedInstanceState == null) {
            //新建一局游戏
            mSnakeView.setMode(mSnakeView.READY);
        } else {
            //打开已有游戏
            Bundle map = savedInstanceState.getBundle((ICICLE_KEY));
            if (map != null) {
                mSnakeView.restoreState(map);
            } else {
                mSnakeView.setMode(SnakeView.PAUSE);
            }
        }

        Button backButton = (Button) findViewById(R.id.back3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Game.this,Snake.class);
                startActivity(intent);
                Game.this.finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        /**
         * Pause the game along with the activity.
         * 在当前画面暂停游戏
         */
        mSnakeView.setMode(SnakeView.PAUSE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        /**
         * Store the game state.
         * 保存当前游戏
         */
        outState.putBundle(ICICLE_KEY, mSnakeView.saveState());
    }
}
