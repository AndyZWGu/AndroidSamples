package com.andygu.sample_02.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.andygu.sample_02.R;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import com.andygu.sample_02.utils.ApiUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import com.andygu.sample_02.pojo.UserProfileVO;

public class LoginActivity extends AppCompatActivity {

  private EditText edAccount;
  private EditText edPassword;
  UserProfileVO userProfileVO = null;

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

  public void auto(View v){
    //懶人帶入
    edAccount.setText("unidynaoffice@unidyna.com");
    edPassword.setText("1234567");
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
    //if(userAccount.equals("unidyna") && userPassword.equals("123")){
    //  //20180626 保存到偏好設定(簡單資料等等)
    //  SharedPreferences userPref =  getSharedPreferences("userProfile",MODE_PRIVATE);
    //  userPref.edit().putString("user_account",userAccount).putString("user_password",userPassword).apply();//除commit()另外還有apply()
    //
    //  Toast.makeText(this,"登入成功",Toast.LENGTH_LONG).show();
    //  //保存到Intent
    //  getIntent().putExtra("LOGIN_ACCOUNT",userAccount);
    //  getIntent().putExtra("LOGIN_PASSWORD",userPassword);
    //  setResult(RESULT_OK,getIntent());
    //  finish();
    //}else{
    //  new AlertDialog.Builder(this).setTitle("錯誤").setMessage("登入失敗").setNegativeButton("確定",null).show();
    //}

    //改為串Zen設計的Api by Andy 20180628
    LoginAsyncTask loginAsyncTask = new LoginAsyncTask();
    loginAsyncTask.execute("http://192.168.0.54/index.php","login",userAccount,userPassword);
  }

  public void cancel(View v){

  }

  class LoginAsyncTask extends AsyncTask<String, Void, UserProfileVO>{

    @Override protected UserProfileVO doInBackground(String... strings) {
      try {
        HashMap<String, String> userProfile = new HashMap<>();
        userProfile.put("act",strings[1]);
        userProfile.put("u_email",strings[2]);
        userProfile.put("u_password",strings[3]);
        //傳送帳密到API獲取回應
        JSONObject userProfileJson = ApiUtils.makeHttpRequest(strings[0],ApiUtils.HTTP_METHOD_POST,userProfile);
          //物件封裝
          //userProfileVO.setStatusCode(userProfileJson.getString("status_code").toString());
          String statusCode = userProfileJson.getString("status_code");
          if(statusCode.equals("0000")){
            String uId = new JSONObject(userProfileJson.getString("data")).getString("u_id");
            String ucId = new JSONObject(userProfileJson.getString("data")).getString("uc_id");
            String uPhone = new JSONObject(userProfileJson.getString("data")).getString("u_phone");
            String uName = new JSONObject(userProfileJson.getString("data")).getString("u_name");
            String uEmail = new JSONObject(userProfileJson.getString("data")).getString("u_email");
            String errorMsg = userProfileJson.getString("error_msg");
            userProfileVO = new UserProfileVO(statusCode,uId,ucId,uPhone,uName,uEmail,errorMsg);
          } else if(statusCode.equals("0001")){
            String errorMsg = userProfileJson.getString("error_msg");
            userProfileVO = new UserProfileVO(statusCode,"","","","","",errorMsg);
          }

      } catch (Exception e) {
        e.printStackTrace();
      }
      return userProfileVO;
    }

    @Override protected void onPostExecute(UserProfileVO userProfileVO) {
      super.onPostExecute(userProfileVO);
      ////狀態碼0000代表登入正常,登入成功
      if(userProfileVO.getStatusCode().equals("0000")){

          SharedPreferences userPref =  getSharedPreferences("userProfile",MODE_PRIVATE);
          userPref.edit().putString("uc_id",userProfileVO.getUcId())
              .putString("u_phone",userProfileVO.getuPhone())
              .putString("u_name",userProfileVO.getuName())
              .putString("u_email",userProfileVO.getuEmail())
              .apply();//除commit()另外還有apply()
          Toast.makeText(LoginActivity.this,"登入成功",Toast.LENGTH_LONG).show();
          setResult(RESULT_OK,getIntent());
          finish();
      }else if(userProfileVO.getStatusCode().equals("0001")){ //登入失敗
        new AlertDialog.Builder(LoginActivity.this).setTitle("登入失敗").setMessage("訊息:"+userProfileVO.getErrorMessage()).setNegativeButton("確定",null).show();
      }
    }

  }

}
