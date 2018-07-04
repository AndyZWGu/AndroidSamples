package com.andygu.sample_02.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.andygu.sample_02.R;

public class UserProfileActivity extends AppCompatActivity {

  private TextView tvType;
  private TextView tvPhone;
  private TextView tvName;
  private TextView tvEmail;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile);
    SharedPreferences userPref =  getSharedPreferences("userProfile",MODE_PRIVATE);
    findViews();
    String ucId = userPref.getString("uc_id","");
    String uPhone = userPref.getString("u_phone","");
    String uName = userPref.getString("u_name","");
    String uEmail = userPref.getString("u_email","");
    if(ucId.equals("1")){
      tvType.setText("權限 : "+"管理員");
    }
    tvPhone.setText("電話 : "+uPhone);
    tvName.setText("暱稱 : "+uName);
    tvEmail.setText("信箱 : "+uEmail);
  }

  public void findViews(){
    tvType = findViewById(R.id.tv_type);
    tvPhone = findViewById(R.id.tv_phone);
    tvName = findViewById(R.id.tv_name);
    tvEmail = findViewById(R.id.tv_email);
  }

  public void go1(View v){
    new Job1Task().execute();
  }
  public void go2(View v){
    new Job2Task().execute(3);
  }

  public void go3(View v){
    new Job3Task().execute(6);
  }

  /**
   * AsyncTask  5秒後TextView顯示Done
   */
  class Job1Task extends AsyncTask< Void, Void, Void >{

    @Override protected Void doInBackground(Void... voids) {
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return null;
    }
    @Override protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      TextView info = findViewById(R.id.info);
      info.setText("DONE1");
    }
  }

  /**
   * AsyncTask  特定秒數秒後TextView顯示Done
   */
  class Job2Task extends AsyncTask< Integer, Void, Void >{

    @Override protected Void doInBackground(Integer... Integers) {
      try {
        Thread.sleep(1000*Integers[0]);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return null;
    }
    @Override protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      TextView info = findViewById(R.id.info);
      info.setText("DONE2");
    }
  }

  /**
   * AsyncTask  5秒後TextView顯示Done
   */
  class Job3Task extends AsyncTask< Integer, Integer, Void >{

    @Override protected Void doInBackground(Integer... Integers) {
      for(int i=0;i<Integers[0];i++){
        publishProgress(i); //呼叫onProgressUpdate過程中更新
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      return null;
    }

    @Override protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
      TextView info = findViewById(R.id.info);
      info.setText(String.valueOf(values[0]));
    }

    @Override protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      TextView info = findViewById(R.id.info);
      info.setText("DONE3");
    }
  }

}
