package com.example.shtb.feigua;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Thread.sleep;


public class activity_comment extends Activity {
    EditText COM;
    String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_comment);
        Button comm=(Button)findViewById(R.id.button_comment);
        COM=(EditText)findViewById(R.id.editText);
        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    global_data.my_net.got = false;
                    comment = COM.getText().toString();
                    new Thread(new Runnable() {
                        public void run() {
                            global_data.my_net.sendMsg("c00000" , global_data.name + "\n" + comment);
                        }
                    }).start();
                    int wti = 0;
                    Toast.makeText(activity_comment.this, "。。提交中。。", Toast.LENGTH_SHORT).show();
                    while (!global_data.my_net.got && wti < 10) {
                        wti++;
                        try {
                            sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (global_data.my_net.message.equals("succeed")) {
                        Toast.makeText(activity_comment.this, "提交成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (global_data.my_net.message.equals("NULL")) {
                        Toast.makeText(activity_comment.this, "提交失败,sorry！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity_comment.this, "提交失败,请检查网络后重试！", Toast.LENGTH_SHORT).show();
                        global_data.my_net.good_net = false;
                    }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_comment, menu);
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
