package com.andygu.sample_02.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.andygu.sample_02.R;
import com.andygu.sample_02.database.helper.MyDBHelper;

public class FinanceActivity extends AppCompatActivity {

  private ListView lvFinance;

  private MyDBHelper myDBHelper;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_finance);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        startActivity(new Intent(FinanceActivity.this,AddFinanceActivity.class));
      }
    });

    findViews();

    myDBHelper = new MyDBHelper(this,"expense.db",null,1);
    Cursor cursor = myDBHelper.getReadableDatabase().rawQuery("SELECT * FROM exp",null);
    //simple_expandable_list_item_2為一列的Layout,不超過兩欄,或是自行配置,flags代表資料異動是否刷新並重新查詢
    //SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_expandable_list_item_2
    //    ,cursor,new String[]{"info","amount"},new int[]{android.R.id.text1,android.R.id.text2},0);

    //改為自訂的一列Layout
    SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,R.layout.finance_row
        ,cursor,new String[]{"cdate","info","amount"},new int[]{R.id.item_cdate,R.id.item_info,R.id.item_amount},0);
    lvFinance.setAdapter(simpleCursorAdapter);
  }

  public void findViews(){
    lvFinance = findViewById(R.id.lv_finance);
  }
}
