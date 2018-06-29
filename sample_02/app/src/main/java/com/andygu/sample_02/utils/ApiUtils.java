package com.andygu.sample_02.utils;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * API連線工具
 * Created by a556520 on 16/01/14.
 */
public class ApiUtils {

  private static Context Ctx;

  private static final String CHARSET = "UTF-8";

  public static final String HTTP_METHOD_POST = "POST";
  public static final String HTTP_METHOD_GET = "GET";


  public ApiUtils(Context context) {
    this.Ctx = context;
  }

  public static JSONObject makeHttpRequest(String url, String method, HashMap<String, String> params) {
    URL urlObj;
    HttpURLConnection conn = null;
    String json = null;
    JSONObject jObj = null;

    // Build url parameter string
    StringBuilder sbParams = new StringBuilder("");
    int i = 0;
    Log.d("params",params + " / ");
    for (String key : params.keySet()) {
      try {
        if (i != 0) {
          sbParams.append("&");
        }
        if (params.get(key) == null) {
          sbParams.append(key).append("=").append(URLDecoder.decode("null", CHARSET));
        } else {
          sbParams.append(key).append("=").append(URLDecoder.decode(params.get(key), CHARSET));
        }
      } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
      }
      i++;
    }

    //連線獲取字串
    try {
      urlObj = new URL(url);
      if(method.equals(HTTP_METHOD_GET)){
        conn = (HttpURLConnection) urlObj.openConnection();
      }
      else if(method.equals(HTTP_METHOD_POST)){
        conn = (HttpURLConnection) urlObj.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setDoInput(true); //允許輸入流，即允許下載
        conn.setDoOutput(true); //允許輸出流，即允許上傳
        conn.setUseCaches(false); //設置是否使用緩存

        if(conn != null){
          //post Request
          OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
          out.write(sbParams.toString());
          out.flush();
          out.close();

          //Get Response
          InputStream is = conn.getInputStream();
          BufferedReader reader = new BufferedReader(new InputStreamReader(is));
          StringBuffer response = new StringBuffer();
          String line;
          while ((line = reader.readLine()) != null) {
            response.append(line);
          }
          reader.close();
          json = response.toString();
        }
      }
    }catch (Exception e){

    }finally {
      if (conn != null) {
        conn.disconnect();
      }
    }

    //轉換JSON格式
    if (json != null) {
      try {
        jObj = new JSONObject(json);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return jObj;
  }

  public static String getJSONString(Map<String, String> params){
    JSONObject json = new JSONObject();
    for(String key:params.keySet()) {
      try {
        json.put(key, params.get(key));
      }catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return json.toString();
  }

}
