package com.andygu.sample_02.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.andygu.sample_02.R;

public class AddFinanceActivity extends AppCompatActivity {

  private EditText edDate;
  private EditText edInfo;
  private EditText edAmount;
  private Button btnAddFinance;
  private Button btnReturn;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_finance);
    findViews();
  }

  public void findViews(){
    edDate = findViewById(R.id.ed_date);
    edInfo = findViewById(R.id.ed_info);
    edAmount = findViewById(R.id.ed_amount);
    btnAddFinance = findViewById(R.id.btn_add_finance);
    btnReturn = findViewById(R.id.btn_return);
  }

  public void returnMenu(View v){
    finish();
  }
}
