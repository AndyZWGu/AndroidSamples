package com.andygu.sample_01;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  private EditText edWeight;
  private EditText edHeight;
  private Button btnCalculateBmi;
  private Button btnHelp;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViews();
    btnHelp.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        new AlertDialog.Builder(MainActivity.this).setTitle("BMI說明").setMessage("體重(kg)/身高的平方(m)").setNegativeButton("OK",null).show();
      }
    });
    Toast.makeText(this,"onCreate",Toast.LENGTH_LONG).show();
  }

  private void findViews() {
    edWeight = findViewById(R.id.ed_weight);
    edHeight = findViewById(R.id.ed_height);
    btnCalculateBmi = findViewById(R.id.btn_calculate_bmi);
    btnHelp = findViewById(R.id.btn_help);
  }

  public void bmi(View v){
    String w = edWeight.getText().toString();
    String h = edHeight.getText().toString();
    if(w.length()==0 || h.length()==0){
      new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("請確認已輸入必要的身高與體重數值").setNegativeButton("OK",null).show();
      return;
    }
    float weight = Float.parseFloat(w);
    float height = Float.parseFloat(h);
    float bmi = weight/(height*height);
    Intent intent = new Intent(this,ResultActivity.class);
    Bundle bag = new Bundle();
    bag.putFloat(getString(R.string.bmi_extra),bmi);
    bag.putString("help","計算方法：體重(kg)/身高的平方(m)");
    intent.putExtras(bag);
    startActivity(intent);
    //Log.d("BMI is",String.valueOf(bmi));
    //Toast.makeText(this, "BMI is"+String.valueOf(bmi), Toast.LENGTH_SHORT).show();
    //new AlertDialog.Builder(this)
    //    .setTitle("Result")
    //    .setMessage("BMI is"+String.valueOf(bmi))
    //    .setNegativeButton("OK",null)
    //    .setNeutralButton("CANCEL",null)
    //    .show();
  }

  @Override protected void onStart() {
    super.onStart();
    Toast.makeText(this,"onStart",Toast.LENGTH_LONG).show();
  }

  @Override protected void onStop() {
    super.onStop();
    Toast.makeText(this,"onStop",Toast.LENGTH_LONG).show();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    Toast.makeText(this,"onDestroy",Toast.LENGTH_LONG).show();
  }

  @Override protected void onPause() {
    super.onPause();
    Toast.makeText(this,"onPause",Toast.LENGTH_LONG).show();
  }

  @Override protected void onResume() {
    super.onResume();
    Toast.makeText(this,"onResume",Toast.LENGTH_LONG).show();
  }

  @Override protected void onRestart() {
    super.onRestart();
    Toast.makeText(this,"onRestart",Toast.LENGTH_LONG).show();
  }
}
