package com.andygu.sample_01;

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
    Log.d("BMI is",String.valueOf(bmi));
    Toast.makeText(this, "BMI is"+String.valueOf(bmi), Toast.LENGTH_SHORT).show();
    new AlertDialog.Builder(this)
        .setTitle("Result")
        .setMessage("BMI is"+String.valueOf(bmi))
        .setNegativeButton("OK",null)
        .setNeutralButton("CANCEL",null)
        .show();
  }

}
