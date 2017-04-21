package com.example.chenz.gridviewdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("网格视图");
        GridView gridView = (GridView)findViewById(R.id.grid_view);
        gridView.setAdapter(new ImageAdapter(this));
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        public ImageAdapter(Context c) {
            context = c;
        }
        private Integer[] mThumbIds = {
                R.drawable.grid_view_01,R.drawable.grid_view_02,R.drawable.grid_view_03,
                R.drawable.grid_view_04,R.drawable.grid_view_05,R.drawable.grid_view_06,
                R.drawable.grid_view_07,R.drawable.grid_view_08,R.drawable.grid_view_09,
                R.drawable.grid_view_10,R.drawable.grid_view_11,R.drawable.grid_view_12,
                R.drawable.grid_view_13,R.drawable.grid_view_14,R.drawable.grid_view_15,
                R.drawable.sample_0,R.drawable.sample_1,R.drawable.sample_2,R.drawable.sample_3,
                R.drawable.sample_4,R.drawable.sample_5,R.drawable.sample_6,R.drawable.sample_7
        };

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView==null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(85,85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8,8,8,8);
            }else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }
    }
}
