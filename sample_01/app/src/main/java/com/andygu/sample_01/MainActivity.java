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
