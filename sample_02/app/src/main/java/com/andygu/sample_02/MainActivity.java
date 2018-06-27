package com.andygu.sample_02;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

  public static final int RC_LOGIN = 1; //代表該功能的常數
  private boolean isLogin = false;
  private ListView lvFunc;
  String[] func = {"餘額查詢","交易明細","最新消息","投資理財","離開"};

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //Toolbar
    Toolbar mToolbarTb = (Toolbar) findViewById(R.id.tb_toolbar);
    setSupportActionBar(mToolbarTb);
    //ListView
    lvFunc = findViewById(R.id.lv_func);
    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,func);
    lvFunc.setAdapter(adapter);
    //按鈕事件
    lvFunc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        new AlertDialog.Builder(MainActivity.this).setTitle("事件").setMessage("你按下ListView的Item").setNegativeButton("OK",null).show();
      }
    });
    //沒登入時返回登入畫面
    if(!isLogin){
      Intent intent = new Intent(this,LoginActivity.class);
      startActivityForResult(intent,RC_LOGIN);
      //startActivity(intent); //會有返回鍵無視登入的Bug,故用上面方法轉換頁面才對
    }
  }

  //下拉選單
  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main,menu);
    return true;
  }
  //下拉選單選擇時
  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if(id == R.id.action_setting){
      new AlertDialog.Builder(this).setTitle("事件").setMessage("你按下setting").setNegativeButton("OK",null).show();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  //請求返回
  @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == RC_LOGIN){
      if(resultCode == RESULT_OK){
        String loginAccount = data.getStringExtra("LOGIN_ACCOUNT");
        String loginPassword = data.getStringExtra("LOGIN_PASSWORD");
        Log.d("透過傳遞取得使用者登入的帳號密碼為","帳號 : "+loginAccount+" / "+"b密碼 :"+loginPassword);
        SharedPreferences pref = getSharedPreferences("userProfile",MODE_PRIVATE);
        String prefLoginAccount = pref.getString("user_account","");
        String prefLoginPassword = pref.getString("user_password","");
        Log.d("透過Pref取得使用者登入的帳號密碼為","帳號 : "+prefLoginAccount+" / "+"b 密碼 :"+prefLoginPassword);
      }else{
        finish();
      }
    }
  }
}
