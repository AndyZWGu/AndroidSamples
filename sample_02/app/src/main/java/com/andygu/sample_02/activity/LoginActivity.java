package com.andygu.sample_02.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.andygu.sample_02.R;

public class LoginActivity extends AppCompatActivity {

  private EditText edAccount;
  private EditText edPassword;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    findViews();
    //假設取得已經登入的資料,若存在就自動填入帳號密碼
    SharedPreferences pref = getSharedPreferences("userProfile",MODE_PRIVATE);
    edAccount.setText(pref.getString("user_account",""));
    edPassword.setText(pref.getString("user_password",""));
  }


  public void findViews(){
    edAccount = findViewById(R.id.ed_account);
    edPassword = findViewById(R.id.ed_password);
  }

  public void reset(View v){
    //清除帳戶資料
    SharedPreferences pref = getSharedPreferences("userProfile",MODE_PRIVATE);
    pref.edit().clear().apply();//apply沒返回值,效能較好
    new AlertDialog.Builder(this).setTitle("成功").setMessage("已清除暫存帳密,請重啟app查看!").setNegativeButton("確定",null).show();
  }

  public void login(View v){
    String userAccount = edAccount.getText().toString();
    String userPassword = edPassword.getText().toString();
    //unidyna 123
    if(userAccount.equals("unidyna") && userPassword.equals("123")){
      //20180626 保存到偏好設定(簡單資料等等)
      SharedPreferences userPref =  getSharedPreferences("userProfile",MODE_PRIVATE);
      userPref.edit().putString("user_account",userAccount).putString("user_password",userPassword).apply();//除commit()另外還有apply()

      Toast.makeText(this,"登入成功",Toast.LENGTH_LONG).show();
      //保存到Intent
      getIntent().putExtra("LOGIN_ACCOUNT",userAccount);
      getIntent().putExtra("LOGIN_PASSWORD",userPassword);
      setResult(RESULT_OK,getIntent());
      finish();
    }else{
      new AlertDialog.Builder(this).setTitle("錯誤").setMessage("登入失敗").setNegativeButton("確定",null).show();
    }
  }

  public void cancel(View v){

  }

}
