package com.andygu.sample_02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  public static final int RC_LOGIN = 1; //代表該功能的常數
  private boolean isLogin = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //沒登入時返回登入畫面
    if(!isLogin){
      Intent intent = new Intent(this,LoginActivity.class);
      startActivityForResult(intent,RC_LOGIN);
      //startActivity(intent); //會有返回鍵無視登入的Bug,故用上面方法轉換頁面才對
    }

  }
}
