package com.andygu.sample_02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  private boolean isLogin = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //沒登入時返回登入畫面
    if(!isLogin){
      Intent intent = new Intent(this,LoginActivity.class);
      startActivity(intent);
    }

  }
}
