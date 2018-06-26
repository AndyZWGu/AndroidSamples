package com.andygu.sample_02;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

  @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == RC_LOGIN){
      if(resultCode == RESULT_OK){
        String login_account = data.getStringExtra("LOGIN_ACCOUNT");
        String login_password = data.getStringExtra("LOGIN_PASSWORD");
        Log.d("使用者登入的帳號密碼為","a : "+login_account+"\n"+"b :"+login_password);
      }else{
        finish();
      }
    }
  }
}
