package com.andygu.sample_02.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.andygu.sample_02.R;
import com.andygu.sample_02.activity.*;
import com.andygu.sample_02.adapter.IconAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
    AdapterView.OnItemClickListener {

  public static final int RC_LOGIN = 1; //代表該功能的常數
  private boolean isLogin = false;
  String[] data = {"最新消息","餘額查詢","交易明細","離開"};
  String[] data2 = {"投資理財","個人帳戶","關於我們","聯絡資訊","版權聲明","離開"};
  int[] icons = {R.drawable.func_finance, R.drawable.func_account,R.drawable.func_about,R.drawable.func_contact,R.drawable.func_copy,R.drawable.func_exit };

  private ArrayAdapter lvAdapter;
  private ArrayAdapter gvAdapter;
  private ArrayAdapter<CharSequence>  spAdapter;
  private Spinner notify;
  private ListView lvFunc;
  private GridView gvFunc;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //Toolbar
    Toolbar mToolbarTb = (Toolbar) findViewById(R.id.tb_toolbar);
    setSupportActionBar(mToolbarTb);

    //ListView
    lvFunc = findViewById(R.id.lv_func);
    lvAdapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,data);
    lvFunc.setAdapter(lvAdapter);
    lvFunc.setOnItemClickListener(this);

    //GridView
    gvFunc = findViewById(R.id.gv_func);
    gvAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_2,data2);
    //------------------------------------
    //gvFunc.setAdapter(gvAdapter);
    IconAdapter iconAdapter = new IconAdapter(this,data2,icons);
    gvFunc.setAdapter(iconAdapter);//改成自定Adapter類別
    //------------------------------------
    gvFunc.setOnItemClickListener(this);

    //Spinner
    notify = findViewById(R.id.spinner);
    spAdapter = ArrayAdapter.createFromResource(this,R.array.notify_array,android.R.layout.simple_spinner_dropdown_item);
    notify.setAdapter(spAdapter);
    notify.setOnItemSelectedListener(this);

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
        //String loginAccount = data.getStringExtra("LOGIN_ACCOUNT");
        //String loginPassword = data.getStringExtra("LOGIN_PASSWORD");
        //Log.d("透過傳遞取得使用者登入的帳號密碼為","帳號 : "+loginAccount+" / "+"b密碼 :"+loginPassword);
        //SharedPreferences pref = getSharedPreferences("userProfile",MODE_PRIVATE);
        //String prefLoginAccount = pref.getString("user_account","");
        //String prefLoginPassword = pref.getString("user_password","");
        //Log.d("透過Pref取得使用者登入的帳號密碼為","帳號 : "+prefLoginAccount+" / "+"b 密碼 :"+prefLoginPassword);
      }else{
        finish();
      }
    }
  }

  //toolbar init
  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main,menu);
    return true;
  }

  //toolbar選擇時
  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if(id == R.id.action_setting){
      new AlertDialog.Builder(this).setTitle("事件").setMessage("你按下setting").setNegativeButton("OK",null).show();
      return true;
    }
    if(id == R.id.action_logout){
      SharedPreferences pref = getSharedPreferences("userProfile",MODE_PRIVATE);
      pref.edit().clear().apply();//apply沒返回值,效能較好
      Intent intent = new Intent(this,LoginActivity.class);
      startActivityForResult(intent,RC_LOGIN);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * spinner用
   * @param parent
   * @param view
   * @param position
   * @param id
   */
  @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    String clickText = spAdapter.getItem(position).toString();
    switch(position) {
      case 0: {
        break;
      }
      case 1: {
        Toast.makeText(MainActivity.this, "你選擇的Item為" + clickText, Toast.LENGTH_LONG).show();
        gvFunc.setVisibility(View.INVISIBLE);
        lvFunc.setVisibility(View.VISIBLE);
        break;
      }
      case 2: {
        Toast.makeText(MainActivity.this, "你選擇的Item為" + clickText, Toast.LENGTH_LONG).show();
        gvFunc.setVisibility(View.VISIBLE);
        lvFunc.setVisibility(View.INVISIBLE);
        break;
      }
      case 3: {
        break;
      }
      case 4: {
        break;
      }
      default: {
        break;
      }
    }
  }

  /**
   * spinner用
   * @param parent
   */
  @Override public void onNothingSelected(AdapterView<?> parent) {

  }

  /**
   *ListView,GridView,Click時用
   * @param parent
   * @param view
   * @param position
   * @param id
   */
  @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //用parent的型別,view的型別會是TextView
    if(parent instanceof ListView){
      String clickText = lvAdapter.getItem(position).toString();
      if(clickText=="離開"){
        System.exit(0);
        return;
      }
      new AlertDialog.Builder(MainActivity.this).setTitle("事件").setMessage("你按下ListView的Item為"+clickText).setNegativeButton("OK",null).show();
    }
    if(parent instanceof GridView){
      //GirdView利用Image的Id判別
      String clickText = gvAdapter.getItem(position).toString();
      switch ((int)id){
        case R.drawable.func_finance:
          //new AlertDialog.Builder(MainActivity.this).setTitle("事件").setMessage("你按下GistView的Item為"+clickText).setNegativeButton("OK",null).show();
          //投資理財功能
          startActivity(new Intent(MainActivity.this,FinanceActivity.class));
          break;
        case R.drawable.func_account:
          //個人帳戶功能
          //new AlertDialog.Builder(MainActivity.this).setTitle("事件").setMessage("你按下GistView的Item為"+clickText).setNegativeButton("OK",null).show();
          startActivity(new Intent(MainActivity.this,UserProfileActivity.class));
          break;
        case R.drawable.func_about:
          new AlertDialog.Builder(MainActivity.this).setTitle("事件").setMessage("你按下GistView的Item為"+clickText).setNegativeButton("OK",null).show();
          break;
        case R.drawable.func_contact:
          new AlertDialog.Builder(MainActivity.this).setTitle("事件").setMessage("你按下GistView的Item為"+clickText).setNegativeButton("OK",null).show();
          break;
        case R.drawable.func_copy:
          new AlertDialog.Builder(MainActivity.this).setTitle("事件").setMessage("你按下GistView的Item為"+clickText).setNegativeButton("OK",null).show();
          break;
        case R.drawable.func_exit:
          System.exit(0);
          break;
        default:
          break;
      }
    }
  }

}
