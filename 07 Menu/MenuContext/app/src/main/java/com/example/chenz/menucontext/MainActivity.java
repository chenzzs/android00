package com.example.chenz.menucontext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = new TextView(this);
        textView.setText("上下文菜单的载体");
        registerForContextMenu(textView);
        setContentView(textView);
    }

    public boolean onContextItemSelected(MenuItem item){
        super.onContextItemSelected(item);
        switch(item.getItemId()){
            case 1:
                break;
            case 2:
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo contextMenuInfo){
        menu.add(0,1,1,R.string.newf);
        menu.add(0,2,2,R.string.open);
        super.onCreateContextMenu(menu,view,contextMenuInfo);
    }
}
