package com.example.chenz.actionbardemo;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            ViewConfiguration mconfig = ViewConfiguration.get(this);
            Field menuKeyField;
            menuKeyField = ViewConfiguration.class.getDeclaredField("HasPermanentMenuKey");
            if (menuKeyField != null){
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(mconfig, false);
            }
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_calendar:
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String string = format.format(curDate);
                Toast.makeText(this, string,Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_call:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
                break;

            case R.id.action_msm:
                Intent intent1 = new Intent();
                //系统默认的action,用来打开默认的短信界面
                intent1.setAction(Intent.ACTION_SENDTO);
                //需要发短信的号码
                intent1.setData(Uri.parse("smsto:"+10086));
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

}
