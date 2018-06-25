package com.andygu.sample_02;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

  private EditText edAccount;
  private EditText edPassword;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    findViews();
  }


  public void findViews(){
    edAccount = findViewById(R.id.ed_account);
    edPassword = findViewById(R.id.ed_password);
  }

  public void login(View v){
    String user_account = edAccount.getText().toString();
    String user_password = edPassword.getText().toString();
    //unidynaoffice@unidyna.com  unidyna12976504
    if(user_account.equals("unidyna") && user_password.equals("123")){
      Toast.makeText(this,"登入成功",Toast.LENGTH_LONG).show();
      finish();
    }else{
      new AlertDialog.Builder(this).setTitle("錯誤").setMessage("登入失敗").setNegativeButton("確定",null).show();
    }
  }

  public void cancel(View v){

  }

}
