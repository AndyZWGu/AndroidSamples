package com.andygu.sample_02.activity;

import android.Manifest;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.andygu.sample_02.R;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;

import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import java.util.ArrayList;


import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_CONTACTS;

public class ContactsContentActivity extends AppCompatActivity {

  private ListView lvContacts;

  private static final int REQUEST_CONTACTS = 1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contacts_content);
    findViews();
    int premission = ActivityCompat.checkSelfPermission(this, READ_CONTACTS);
    if(premission != PackageManager.PERMISSION_GRANTED){
      //未取得權限,向使用者要求允許權限
      ActivityCompat.requestPermissions(this,new String[]{READ_CONTACTS,WRITE_CONTACTS},REQUEST_CONTACTS);
    } else {
      readContactsInit();
    }

  }

  public void findViews(){
    lvContacts = findViewById(R.id.lv_contacts);
  }

  /**
   * ActivityCompat.requestPermissions 獲取權限後
   * @param requestCode
   * @param permissions
   * @param grantResults
   */
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch(requestCode){
      case REQUEST_CONTACTS:{
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
          //取得聯絡人權限,進行存取
          readContactsInit();
        } else {
          new AlertDialog.Builder(this).setMessage("必須允許聯絡人權限,才能顯示資料").setPositiveButton("OK",null).show();
        }
        break;
      }
      default:{
        break;
      }
    }
  }

  private void readContactsInit() {
    //初始化顯示全部使用者
    ContentResolver resolver = getContentResolver();
    //顯示全部的使用者
    Cursor cursor = resolver.query(Contacts.CONTENT_URI,null,null,null,null);
    //填入Adapter
    SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor
        ,new String[]{Contacts.DISPLAY_NAME,Contacts.HAS_PHONE_NUMBER},new int[]{android.R.id.text1,android.R.id.text2},1){
      //追加複寫bindView客製化
      @Override public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView phone = view.findViewById(android.R.id.text2);
        if(cursor.getInt(cursor.getColumnIndex(Contacts.HAS_PHONE_NUMBER))==0){
          phone.setText("");
        } else {
          int id = cursor.getInt(cursor.getColumnIndex(Contacts._ID));
          Cursor pCursor = getContentResolver().query(Phone.CONTENT_URI,null,Phone.CONTACT_ID+"=?", new String[]{String.valueOf(id)},null);
          if(pCursor.moveToFirst()){
            String number = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
            phone.setText(number);
          }
        }
      }
    };
    lvContacts.setAdapter(simpleCursorAdapter);
  }

  public void readContactsAll(View v) {
    //初始化顯示全部使用者
    ContentResolver resolver = getContentResolver();
    //顯示全部的使用者
    Cursor cursor = resolver.query(Contacts.CONTENT_URI,null,null,null,null);
    //填入Adapter
    SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor
        ,new String[]{Contacts.DISPLAY_NAME,Contacts.HAS_PHONE_NUMBER},new int[]{android.R.id.text1,android.R.id.text2},1){
      //追加複寫bindView客製化
      @Override public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView phone = view.findViewById(android.R.id.text2);
        if(cursor.getInt(cursor.getColumnIndex(Contacts.HAS_PHONE_NUMBER))==0){
          phone.setText("");
        } else {
          int id = cursor.getInt(cursor.getColumnIndex(Contacts._ID));
          Cursor pCursor = getContentResolver().query(Phone.CONTENT_URI,null,Phone.CONTACT_ID+"=?", new String[]{String.valueOf(id)},null);
          if(pCursor.moveToFirst()){
            String number = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
            phone.setText(number);
          }
        }
      }
    };
    lvContacts.setAdapter(simpleCursorAdapter);
  }

  public void readContactsHavePhone(View v) {
    ContentResolver resolver = getContentResolver();
    String[] projection = {Contacts._ID,Contacts.DISPLAY_NAME,Phone.NUMBER};
    //不顯示無電話號碼的使用者,用Implicit Join隱性合併查詢
    Cursor cursor = resolver.query(Phone.CONTENT_URI,projection,null,null,null);
    //填入Adapter
    SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor
        ,new String[]{Contacts.DISPLAY_NAME,Phone.NUMBER},new int[]{android.R.id.text1,android.R.id.text2},1);
    lvContacts.setAdapter(simpleCursorAdapter);
  }

  /**
   * 新增聯絡人June
   * @param v
   */
  public void insertContact(View v){
    ArrayList ops = new ArrayList();
    int index = ops.size();
    ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
        .withValue(RawContacts.ACCOUNT_TYPE,null)
        .withValue(RawContacts.ACCOUNT_NAME,null).build());
    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
        .withValueBackReference(Data.RAW_CONTACT_ID,index)
        .withValue(Data.MIMETYPE,StructuredName.CONTENT_ITEM_TYPE)
        .withValue(StructuredName.DISPLAY_NAME,"June").build());
    ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
        .withValueBackReference(Data.RAW_CONTACT_ID,index)
        .withValue(Data.MIMETYPE,Phone.CONTENT_ITEM_TYPE)
        .withValue(Phone.NUMBER,"0900112233")
        .withValue(Phone.TYPE,Phone.TYPE_MOBILE).build());
    try{
      getContentResolver().applyBatch(ContactsContract.AUTHORITY,ops);
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (OperationApplicationException e) {
      e.printStackTrace();
    }
    Toast.makeText(this,"新增June",Toast.LENGTH_LONG).show();
    finish();
  }

  /**
   * 更新聯絡人June
   * @param v
   */
  public void updateContact(View v){
    String where = Phone.DISPLAY_NAME + " = ? AND " +Data.MIMETYPE + " = ?";
    String[] params = new String[]{"June",Phone.CONTENT_ITEM_TYPE};
    ArrayList ops = new ArrayList();
    ops.add(ContentProviderOperation.newUpdate(Data.CONTENT_URI)
      .withSelection(where,params)
      .withValue(Phone.NUMBER,"0966666666").build());
    try{
     getContentResolver().applyBatch(ContactsContract.AUTHORITY,ops);
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (OperationApplicationException e) {
      e.printStackTrace();
    } Toast.makeText(this,"更新June",Toast.LENGTH_LONG).show();
    finish();
  }

  /**
   * 刪除聯絡人June
   * @param v
   */
  public void deleteContact(View v){
    String where = Phone.DISPLAY_NAME + " = ? ";
    String[] params = new String[]{"June"};
    ArrayList ops = new ArrayList();
    ops.add(ContentProviderOperation.newDelete(RawContacts.CONTENT_URI).withSelection(where,params).build());
    try{
      getContentResolver().applyBatch(ContactsContract.AUTHORITY,ops);
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (OperationApplicationException e) {
      e.printStackTrace();
    } Toast.makeText(this,"刪除June",Toast.LENGTH_LONG).show();
    finish();
  }

}
