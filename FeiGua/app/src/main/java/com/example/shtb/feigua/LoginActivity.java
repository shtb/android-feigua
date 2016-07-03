package com.example.shtb.feigua;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Thread.*;


public class LoginActivity extends Activity{

    EditText user;
    EditText pas;
    String my_ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user=(EditText)findViewById(R.id.editText_name_r);
        pas=(EditText)findViewById(R.id.editText_password_r);
        SharedPreferences save=getSharedPreferences("data_login",MODE_PRIVATE);
        user.setText(save.getString("name",""));
        pas.setText(save.getString("password",""));
        Button regist=(Button)findViewById(R.id.button_getuser);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new Thread(new Runnable() {
                        public void run() {
                            global_data.my_net.init("121.42.31.135",32954);
                        }
                    }).start();
                Intent intent=new Intent(LoginActivity.this,activity_register.class);
                startActivity(intent);
                finish();
            }
        });
        Button entry=(Button)findViewById(R.id.button_login);
        entry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                global_data.name=user.getText().toString();
                global_data.password=pas.getText().toString();

                if(TextUtils.isEmpty(global_data.name))
                {
                    Toast.makeText(LoginActivity.this,"请输入用户名！",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(global_data.password))
                {
                    Toast.makeText(LoginActivity.this,"请输入密码！",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    CheckBox swit=(CheckBox)findViewById(R.id.switch1);
                    if(swit.isChecked())
                    {
                        SharedPreferences.Editor edit=getSharedPreferences("data_login",MODE_PRIVATE).edit();
                        edit.putString("name",global_data.name);
                        edit.putString("password",global_data.password);
                        edit.commit();
                    }
                    else
                    {
                        SharedPreferences.Editor edit=getSharedPreferences("data_login",MODE_PRIVATE).edit();
                        edit.putString("name","");
                        edit.putString("password","");
                        edit.commit();
                    }
                        global_data.my_net.got=false;
                        new Thread(new Runnable() {
                            public void run() {
                                global_data.my_net.init("121.42.31.135",32954);
                                global_data.my_net.sendMsg("b00000",global_data.name+"\n"+global_data.password);
                            }
                        }).start();
                        int wti=0;
                        Toast.makeText(LoginActivity.this,"--登录中--",Toast.LENGTH_SHORT).show();
                        while(!global_data.my_net.got&&wti<10)
                        {
                            wti++;
                            try {
                                sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if(global_data.my_net.message.equals("succeed"))
                        {
                            Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,welcome_activity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(global_data.my_net.message.equals("NULL"))
                        {
                            Toast.makeText(LoginActivity.this,"登录失败,请检查用户名，密码后重试！",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"登录失败,请检查网络后重试！",Toast.LENGTH_SHORT).show();
                            global_data.my_net.good_net=false;
                        }
                }
            }
        });
        //global_data.my_net=new internet("101.200.235.134",5555);
        //Toast.makeText(LoginActivity.this,global_data.my_net.message,Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
