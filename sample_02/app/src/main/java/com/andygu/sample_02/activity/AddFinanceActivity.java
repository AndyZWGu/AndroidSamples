package com.andygu.sample_02.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.andygu.sample_02.R;
import com.andygu.sample_02.database.helper.MyDBHelper;

public class AddFinanceActivity extends AppCompatActivity {

  private EditText edDate;
  private EditText edInfo;
  private EditText edAmount;
  private Button btnAddFinance;
  private Button btnReturn;

  private MyDBHelper myDBHelper;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_finance);
    findViews();
    myDBHelper = new MyDBHelper(this,"expense.db",null,1);
  }

  public void findViews(){
    edDate = findViewById(R.id.ed_date);
    edInfo = findViewById(R.id.ed_info);
    edAmount = findViewById(R.id.ed_amount);
    btnAddFinance = findViewById(R.id.btn_add_finance);
    btnReturn = findViewById(R.id.btn_return);
  }

  public void addFinance(View v){
    String cdate = edDate.getText().toString();
    String info = edInfo.getText().toString();
    int amount = Integer.parseInt(edAmount.getText().toString());
    ContentValues cv = new ContentValues();
    cv.put("cdate",cdate);
    cv.put("info",info);
    cv.put("amount",amount);
    //呼叫Help進行資料庫新增
    Long id = myDBHelper.getWritableDatabase().insert("exp",null,cv);
    Log.d("ADD","id="+id);
    Intent intent = new Intent(this,FinanceActivity.class);
    startActivity(intent);
  }

  public void returnMenu(View v){
    finish();
  }
}
