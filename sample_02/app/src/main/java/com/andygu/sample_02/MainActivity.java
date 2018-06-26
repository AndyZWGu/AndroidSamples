package com.andygu.sample_02;

import android.content.Intent;
import android.content.SharedPreferences;
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
