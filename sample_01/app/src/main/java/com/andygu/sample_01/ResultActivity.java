package com.andygu.sample_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

  private TextView tvResultBmi;
  private TextView tvHelp;
  private Float BMI;
  private String help;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_result);
    findViews();
    Bundle bag = getIntent().getExtras();
    BMI = bag.getFloat(getString(R.string.bmi_extra),0);
    help = bag.getString("help","");
    tvResultBmi.setText("BMI is "+String.valueOf(BMI));
    tvHelp.setText(help);
  }

  public void findViews(){
    tvResultBmi = findViewById(R.id.tv_result_bmi);
    tvHelp = findViewById(R.id.tv_help);
  }

}
