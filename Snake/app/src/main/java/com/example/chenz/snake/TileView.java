package com.example.chenz.snake;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by chenz on 2017/5/4.
 * 此类包含了贪吃蛇游戏画面的各个设定参数，主要负责绘制游戏画面。
 */

public class TileView extends View{

    private static final String tag = "yao";
    protected static int mTileSize = 20;        //方格的边长
    protected static int mXTileCount;           //X轴上方格的个数
    protected static int mYTileCount;           //Y轴上方格的个数
    private static int mXOffset;                //绘图时X轴上的起始坐标，按pixel计算
    private static int mYOffset;                //绘图时Y轴上的起始坐标

    /**
     * 存储着不同种类的bitmap图。
     * 通过resetTile，loadTile，将游戏中的方块加载到这个数组。
     * 可以理解为砖块字典。
     */
    private Bitmap[] mTileArray;                //位图数组

    /**
     * 存储整个界面内每个tile位置应该绘制的tile。
     * 可看做是我们直接操作的画布。
     * 通过setTile、clearTile进行图形显示的修改操作。
     */
    private int[][] mTileGrid;                  //映射整个游戏画面的数组

    private final Paint mPaint = new Paint();   //画笔

    public TileView(Context context, AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        Log.i(tag,"TileView Constructor");
        Log.i(tag,"mTileSize="+mTileSize);

        //使用TypedArray，获取在attrs.xml中为TileView定义的新属性tileSize。
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.TileView);
        mTileSize = a.getInt(R.styleable.TileView_tileSize,40);
        a.recycle();
    }

    public TileView(Context context,AttributeSet attrs) {
        super(context,attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.TileView);
        mTileSize = a.getInt(R.styleable.TileView_tileSize,40);
        a.recycle();
    }

    //重置位图数组的长度
    public void resetTiles(int tilecount) {
        mTileArray = new Bitmap[tilecount];
    }

    //适应各种分辨率的屏幕，当改变屏幕大小时，同时修改tile的相关计数指标
    protected void onSizeChanged(int w,int h,int oldw,int oldh) {
        Log.i(tag,"onSizeChanged,"+"w="+w+"h="+h+"oldw="+oldw+"oldh="+oldh);
        mXTileCount = (int) Math.floor(w/mTileSize);
        mYTileCount = (int) Math.floor(h/mTileSize);
        Log.i(tag,"mXTileCount="+mXTileCount);
        Log.i(tag,"mYTileCount="+mYTileCount);
        mXOffset = ((w-(mTileSize*mXTileCount))/2);
        mYOffset = ((w-(mTileSize*mYTileCount))/2);
        Log.i(tag,"mXOffset="+mXOffset);
        Log.i(tag,"mYOffset="+mYOffset);
        mTileGrid = new int[mXTileCount][mYTileCount];
        clearTiles();
    }

    /**
     * 这里做了一个 Drawable 到 bitmap 的转换
     * 加载具体的砖块图片到砖块字典。
     * 即将对应的砖块的图片、对应的加载到mTileArray数组中。
     * @param key
     * @param tile
     */
    public void loadTile(int key, Drawable tile) {

        Bitmap bitmap = Bitmap.createBitmap(mTileSize,mTileSize,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        tile.setBounds(0,0,mTileSize,mTileSize);
        tile.draw(canvas);
        mTileArray[key] = bitmap;
    }

    //清空图形显示
    public void clearTiles() {
        Log.i(tag,"TileView.clearTiles");
        for (int x=0;x<mXTileCount;x++) {
            for (int y=0;y<mYTileCount;y++) {
                setTile(0,x,y);
            }
        }
    }

    //在相应的坐标位置绘制相应的砖块
    public void setTile(int tileindex,int x,int y) {
        mTileGrid[x][y] = tileindex;
    }

    //将直接操作的画布绘制到手机界面上
    public void onDraw(Canvas canvas) {
        Log.i(tag,"onDraw");
        super.onDraw(canvas);
        for(int x=0;x<mXTileCount;x++) {
            for (int y=0;y<mYTileCount;y++) {
                if (mTileGrid[x][y]>0) {
                    canvas.drawBitmap(mTileArray[mTileGrid[x][y]],
                            mXOffset+x*mTileSize,mYOffset+y*mTileSize,mPaint);
                }
            }
        }
    }
}
