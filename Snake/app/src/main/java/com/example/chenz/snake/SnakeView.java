package com.example.chenz.snake;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by chenz on 2017/5/4.
 * SnakeView类定义了游戏运行时的画面改变及蛇体和果子的更新方法
 */

public class SnakeView extends TileView implements OnClickListener{

    private Button mPlay;
    private ImageButton mLeft;
    private ImageButton mRight;
    private ImageButton mUp;
    private ImageButton mDown;

    private static final String TAG = "SnakeView";

    //游戏的四种状态，初始时为预备开始的状态
    public int mMode = READY;
    public static final int PAUSE = 0;
    public static final int READY = 1;
    public static final int RUNNING = 2;
    public static final int LOSE = 3;

    //蛇体运动的方向标识
    public int mDirection = NORTH;
    public int mNextDirection = NORTH;
    public static final int NORTH = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
    public static final int WEST = 4;

    //游戏中仅有的三种砖块对应的数量
    public static final int RED_STAR = 1;
    public static final int YELLOW_STAR = 2;
    public static final int GREEN_STAR = 3;

    public long mScore = 0;         //记录获得的分数
    public long mMoveDelay = 600;  //每移动一步的延时，初始值为600ms，以后每吃一个果子*0.9

    /**
     * 记录上次移动的确切时间
     * 与mMoveDelay一同处理与用户的异步操作的协同问题
     */
    private long mLastMove;

    private TextView mStatusText;   //用来显示游戏状态的TextView

    /**
     * 两个链表，分别用来存储蛇体和果子的坐标
     * 每次蛇体的运动，蛇体的增长，产生新的苹果，被吃掉的苹果，都会在这里记录
     */
    private ArrayList<Coordinate> mSnakeTrail = new ArrayList<Coordinate>();
    private ArrayList<Coordinate> mAppleList = new ArrayList<Coordinate>();

    //随机数生成器：用来产生随机的苹果，在addRandomApple()中使用。
    private static final Random RNG = new Random();

    //用Handler机制实现定时刷新
    private RefreshHandler mRedreHandler = new RefreshHandler();
    class RefreshHandler extends Handler {
       //获取消息并处理
       @Override
        public void handleMessage(Message message) {
            SnakeView.this.update();
            SnakeView.this.invalidate();         //刷新view为基类的界面
            Log.i(TAG,"handleMessage|Thread Name="+Thread.currentThread().getName());
       }

       public void sleep(long delayMillis) {
           //清空消息队列，Handler进入对新消息的等待
           this.removeMessages(0);
           //定时发送新消息，激活Handler
           sendMessageDelayed(obtainMessage(0),delayMillis);
       }
    };

    public SnakeView(Context context, AttributeSet attrs) {
        super(context,attrs);
        initSnakeView();            //构造函数中，别忘了初始化游戏。
    }

    public SnakeView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        initSnakeView();
    }

    //初始化SnakeView类，注意，这跟初始化游戏是不一样的。
    private void initSnakeView() {
        setFocusable(true); // 设置焦点，由于在文字界面和游戏界面的跳转。这个focus是不可或缺的。

        //获取资源中的图片，加载到砖块字典中。
        Resources r = this.getContext().getResources();
        resetTiles(4);
        loadTile(RED_STAR,r.getDrawable(R.drawable.redstar));
        loadTile(YELLOW_STAR,r.getDrawable(R.drawable.yellowstar));
        loadTile(GREEN_STAR,r.getDrawable(R.drawable.greenstar));
    }

    //初始化游戏
    void initNewGame() {
        //清空保存蛇体和果子的数据结构
        mSnakeTrail.clear();
        mAppleList.clear();

        //设定初始状态的的蛇体位置
        mSnakeTrail.add(new Coordinate(7,25));
        mSnakeTrail.add(new Coordinate(6,25));
        mSnakeTrail.add(new Coordinate(5,25));
        mSnakeTrail.add(new Coordinate(4,25));
        mSnakeTrail.add(new Coordinate(3,25));
        mSnakeTrail.add(new Coordinate(2,25));
        mNextDirection = NORTH;

        //产生三个苹果
        addRandomApple();
        addRandomApple();
        addRandomApple();

        mMoveDelay = 600;
        mScore = 0;
    }

    /**
     * 在游戏暂停时，需要通过Bundle方式保存数据
     * Bundle支持简单的数组
     * 所以需要我们的部分数据结构，加蛇体和苹果位置的数组，转换成简单的序列化的int数组
     * @param cvec
     * @return
     */
    private int[] coordArrayListToArray(ArrayList<Coordinate>cvec) {
        int count = cvec.size();
        int[] rawArray = new int[count*2];
        for (int index=0;index<count;index++) {
            Coordinate c = cvec.get(index);
            rawArray[2*index] = c.x;
            rawArray[2*index+1] = c.y;
        }
        return rawArray;
    }

    //在意外情况下，暂时性保存游戏数据，在下次打开游戏时，可以继续游戏
    public Bundle saveState() {
        Bundle map = new Bundle();

        map.putIntArray("mAppleList",coordArrayListToArray(mAppleList));
        map.putInt("mDirection",Integer.valueOf(mDirection));
        map.putInt("mNextDirection",Integer.valueOf(mNextDirection));
        map.putLong("mMoveDelay",Long.valueOf(mMoveDelay));
        map.putLong("mScore",Long.valueOf(mScore));
        map.putIntArray("mSnakeTrail",coordArrayListToArray(mSnakeTrail));

        return map;
    }

    /**
     * 是coordArrayListToArray()的逆过程，用来读取保存在Bundle中的数据
     * @param rawArray
     * @return
     */
    private ArrayList<Coordinate>coordArrayToArrayList(int[] rawArray) {
        ArrayList<Coordinate>coordArrayList = new ArrayList<Coordinate>();

        int coordCount = rawArray.length;
        for (int index=0;index<coordCount;index+=2) {
            Coordinate c = new Coordinate(rawArray[index],rawArray[index+1]);
            coordArrayList.add(c);
        }
        return coordArrayList;
    }

    /**
     * 回复游戏数据，是saveState()的逆过程
     * @param icicle
     */
    public void restoreState(Bundle icicle) {
        setMode(PAUSE);

        mAppleList = coordArrayToArrayList(icicle.getIntArray("mAppleList"));
        mDirection = icicle.getInt("mDirection");
        mNextDirection = icicle.getInt("mNextDirection");
        mMoveDelay = icicle.getLong("mMoveDelay");
        mScore = icicle.getLong("mScore");
        mSnakeTrail = coordArrayToArrayList(icicle.getIntArray("mSnakeTrail"));
    }

    /**
     * 按键监听
     * 这里是游戏的基本逻辑
     * @param keyCode
     * @param msg
     * @return
     */
    public boolean onKeyDown(int keyCode,KeyEvent msg) {
        if (keyCode==KeyEvent.KEYCODE_DPAD_UP) {
            if (mMode==READY | mMode==LOSE) {
                /**
                 * At the beginning of the game,or the end of a previous one,
                 * we should start a new game.
                 */
                initNewGame();
                setMode(RUNNING);
                update();//实现对游戏数据的更新，是整个游戏的推动力
                return (true);
            }

            if (mMode==PAUSE) {
                /**
                 * If the game is merely paused,we should just continue where we left off.
                 */
                setMode(RUNNING);
                update();
                return (true);
            }

            if (mDirection!=SOUTH) {
                //如果按键的方向跟蛇本身的运动方向完全相反，则无法执行
                mNextDirection = NORTH;
            }
            return (true);
        }

        if (keyCode==KeyEvent.KEYCODE_DPAD_DOWN) {
            if (mDirection!=NORTH)
                mNextDirection=SOUTH;
            return (true);
        }

        if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT) {
            if (mDirection!=EAST)
                mNextDirection=WEST;
            return (true);
        }

        if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT) {
            if (mDirection!=WEST)
                mNextDirection=EAST;
            return (true);
        }

        return super.onKeyDown(keyCode,msg);
    }

    //Game类调用它，来绑定到相应的textview
    public void setTextView(TextView newView) {
        mStatusText = newView;
    }
    public void setStartButton(Button button) {
        mPlay = button;
        mPlay.setOnClickListener(this);
    }

    //更新游戏状态
    public void setMode(int newMode) {
        int oldMode = mMode;
        mMode = newMode;

        if(newMode==RUNNING & oldMode!=RUNNING) {
            //游戏开始后，将TextView的文字显示设置为不可见的。
            mStatusText.setVisibility(View.INVISIBLE);
            update();
            return;
        }

        Resources res = getContext().getResources();
        CharSequence str = "";
        if (newMode==PAUSE) {
            str = res.getText(R.string.mode_pause);
        }
        if (newMode==READY) {
            str = res.getText(R.string.mode_ready);
        }
        if (newMode==LOSE) {
            str = res.getString(R.string.mode_lose_prefix) + mScore + res.getString(R.string.mode_lose_suffix);
        }

        mStatusText.setText(str);
        mStatusText.setVisibility(VISIBLE);
        mPlay.setVisibility(VISIBLE);

        mLeft.setVisibility(VISIBLE);
        mRight.setVisibility(VISIBLE);
        mUp.setVisibility(VISIBLE);
        mDown.setVisibility(VISIBLE);
    }

    /**
     * 在地图上随机的增加果子
     * 产生的果子位置可能与另一个果子位置重合
     * 新产生的果子的坐标会增加到mApplist的数组上
     */
    private void addRandomApple() {
        Coordinate newCoord = null;
        boolean found = false;
        while (!found) {
            //果子不可产生在边框上
            int newX = 1 + RNG.nextInt(mXTileCount - 2);
            int newY = 9 + RNG.nextInt(mYTileCount - 2);
            newCoord = new Coordinate(newX,newY);

            //果子不在蛇体所在位置
            boolean collision = false;
            int snakelength = mSnakeTrail.size();
            for(int index=0;index<snakelength;index++) {
                if (mSnakeTrail.get(index).equals(newCoord)) {
                    collision = true;
                }
            }

            //如果这里并非是蛇体所在的位置，那么这里就是一个产生果子的好位置，否则进入循环，重新产生
            found = !collision;
        }

        if (newCoord == null) {
            Log.e(TAG,"Somehow ended up with a null newCoord!");
        }
        mAppleList.add(newCoord);
    }

    /**
     * 刷新游戏状态
     * 每次游戏画面的更新、游戏数据的更新，都是依靠这个update()来完成的
     */
    public void update() {
        if(mMode == RUNNING) {
            long now = System.currentTimeMillis();

            /**
             * 这里是对蛇体游戏刚开始时连续两个移动速率的控制
             * 主要作用应该是mMode变化时，对update()正确效果的保障
             */
            if(now-mLastMove > mMoveDelay) {
                clearTiles();       //清空界面画布
                updateWalls();      //重新绘制墙壁
                updateSnake();      //对蛇的游戏逻辑的处理以及绘制
                updateApples();      //对果子的游戏逻辑处理以及绘制
                mLastMove = now;
            }

            //利用Handler进行定时刷新的控制
            mRedreHandler.sleep(mMoveDelay);
        }
    }

    //用setTile绘制墙壁
    private void updateWalls() {
        for(int x=0;x<mXTileCount;x++) {
            setTile(GREEN_STAR,x,8);
            setTile(GREEN_STAR,x,mYTileCount-1);
        }
        for(int y=9;y<mYTileCount-1;y++) {
            setTile(GREEN_STAR,0,y);
            setTile(GREEN_STAR,mXTileCount-1,y);
        }
    }

    //用setTile绘制果子
    private void updateApples() {
        for(Coordinate c:mAppleList) {
            setTile(YELLOW_STAR,c.x,c.y);
        }
    }

    private void updateSnake() {
        boolean growSnake = false;  //吃过果子的蛇会长长，这个变量即为它的标记

        //头部很重要，只有头部可能碰到果子
        Coordinate head = mSnakeTrail.get(0);

        //蛇下一步一定会前移，也就是newHead，长长只会从尾部增加
        Coordinate newHead = new Coordinate(1,1);

        mDirection = mNextDirection;
        switch (mDirection) {
            case EAST:{
                newHead = new Coordinate(head.x+1,head.y);break;
            }
            case WEST:{
                newHead = new Coordinate(head.x-1,head.y);break;
            }
            case NORTH:{
                newHead = new Coordinate(head.x,head.y-1);break;
            }
            case SOUTH:{
                newHead = new Coordinate(head.x,head.y+1);break;
            }
        }

        //检测撞墙
        if((newHead.x<1) || (newHead.x>mXTileCount-2) || (newHead.y<9) || (newHead.y>mYTileCount-2)) {
            setMode(LOSE);
            return;
        }

        //检测撞自己
        int snakelength = mSnakeTrail.size();
        for (int snakeindex=0;snakeindex<snakelength;snakeindex++) {
            Coordinate c = (Coordinate) mSnakeTrail.get(snakeindex);
            if (c.equals(newHead)) {
                setMode(LOSE);
                return;
            }
        }

        //检测吃果子
        int applecount = mAppleList.size();
        for (int appleindex=0;appleindex<applecount;appleindex++) {
            Coordinate c = mAppleList.get(appleindex);
            if(c.equals(newHead)) {
                mAppleList.remove(c);
                addRandomApple();
                mScore++;
                mMoveDelay *= 0.9;
                growSnake = true;
            }
        }

        //前进
        mSnakeTrail.add(0,newHead);
        if (!growSnake) {
            mSnakeTrail.remove(mSnakeTrail.size()-1);
        }

        //绘制新的蛇体
        int index = 0;
        for(Coordinate c:mSnakeTrail) {
            if (index==0) {
                setTile(YELLOW_STAR,c.x,c.y);
            }else {
                setTile(RED_STAR,c.x,c.y);
            }
            index++;
        }
    }

    //坐标点的类，很简单的存储XY坐标
    private class Coordinate {
        public int x;
        public int y;

        public Coordinate(int newX,int newY) {
            x = newX;
            y = newY;
        }

        public boolean equals(Coordinate other) {
            if(x==other.x && y==other.y) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "Coordinate:["+x+","+y+"]";
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (mMode == READY | mMode == LOSE) {
                    initNewGame();
                    setMode(RUNNING);
                    update();
                    mPlay.setVisibility(View.GONE);
                    mLeft.setVisibility(View.VISIBLE);
                    mRight.setVisibility(View.VISIBLE);
                    mUp.setVisibility(View.VISIBLE);
                    mDown.setVisibility(View.VISIBLE);
                }

                if (mMode == PAUSE) {
                    setMode(RUNNING);
                    update();
                    mPlay.setVisibility(View.GONE);
                    mLeft.setVisibility(View.VISIBLE);
                    mRight.setVisibility(View.VISIBLE);
                    mUp.setVisibility(View.VISIBLE);
                    mDown.setVisibility(View.VISIBLE);
                    break;
                }
                break;

            case R.id.left:
                if (mDirection != EAST) {
                    mNextDirection = WEST;
                }
                break;

            case R.id.right:
                if (mDirection != WEST) {
                    mNextDirection = EAST;
                }
                break;

            case R.id.up:
                if (mDirection != SOUTH) {
                    mNextDirection = NORTH;
                }
                break;

            case R.id.down:
                if (mDirection != NORTH) {
                    mNextDirection = SOUTH;
                }
                break;

            default:
                break;

        }
    }

    public void setControlButton(ImageButton left, ImageButton right, ImageButton up, ImageButton down) {
        mLeft = left;
        mRight = right;
        mUp = up;
        mDown = down;
        mLeft.setOnClickListener(this);
        mRight.setOnClickListener(this);
        mUp.setOnClickListener(this);
        mDown.setOnClickListener(this);
    }
}
