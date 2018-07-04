package com.andygu.sample_02.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import com.andygu.sample_02.R;

//CursorLoader 資料庫更動時,非同步更新資料
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class PictureActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>,
    AdapterView.OnItemClickListener {

  private static final int REQUEST_READ_STORAGE = 3;
  private SimpleCursorAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_picture);
    int permission = ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
    if(permission != PackageManager.PERMISSION_GRANTED){
      //未取得權限,向使用者要求允許權限
      ActivityCompat.requestPermissions(this,new String[]{READ_EXTERNAL_STORAGE},REQUEST_READ_STORAGE);
    }else{
      //已有權限,可進行檔案存取
      readThumbnail();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode){
      case REQUEST_READ_STORAGE:{
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
          //取得讀取外部儲存權限,進行存取
          readThumbnail();
        } else {
          //使用者拒絕權限,顯示對話框提示
          new AlertDialog.Builder(this).setMessage("必須允許讀取外部儲存權限才能顯示圖檔").setPositiveButton("OK",null).show();
        }
        break;
      }default:{
        break;
      }
    }
  }

  private void readThumbnail() {
    GridView grid = findViewById(R.id.grid);
    String[] from = { MediaStore.Images.Thumbnails.DATA,MediaStore.Images.Media.DISPLAY_NAME};
    int[] to = new int[]{R.id.thumb_image,R.id.thumb_text};
    adapter = new SimpleCursorAdapter(getBaseContext(),R.layout.thumb_item,null,from,to,0);
    grid.setAdapter(adapter);
    getLoaderManager().initLoader(0,null,this);
    grid.setOnItemClickListener(this);
  }


  @Override public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
    //查詢的資料位置
    Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    return new android.content.CursorLoader(this,uri,null,null,null,null);
  }

  @Override public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
    adapter.swapCursor(data);
  }

  @Override public void onLoaderReset(android.content.Loader<Cursor> loader) {

  }

  @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Intent intent = new Intent(this,PictureDetailActivity.class);
    //將目前點擊的項目位置放入Intent中
    intent.putExtra("POSITION",position);
    startActivity(intent);
  }
}
