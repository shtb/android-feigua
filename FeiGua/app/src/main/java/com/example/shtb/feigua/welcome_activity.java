package com.example.shtb.feigua;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class welcome_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);
        TextView weltext=(TextView)findViewById(R.id.textView2);
        weltext.setText("欢迎 " + global_data.name + " !");
        Button care=(Button)findViewById(R.id.button_care);
        care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welcome_activity.this, care.class);
                startActivity(intent);
            }
        });
        Button liuyan=(Button)findViewById(R.id.button_liuyan);
        liuyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(welcome_activity.this,activity_comment.class);
                startActivity(intent);
            }
        });
        Button title=(Button)findViewById(R.id.button_title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(welcome_activity.this,title.class);
                startActivity(intent);
            }
        });
        Button got=(Button)findViewById(R.id.button_lookclass);
        got.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(welcome_activity.this,activity_got.class);
                startActivity(intent);
            }
        });
        Button teac=(Button)findViewById(R.id.button);
        teac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(welcome_activity.this,uplesson.class);
                startActivity(intent);
            }
        });
        Button getc=(Button)findViewById(R.id.button_getclass);
        getc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(welcome_activity.this,activity_getclass.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
