package com.geogie.qiushi_15_04_18.json;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Inte[i] IDEA.
 * User: renchaojun[FR]
 * Date:@date ${date}
 * Email:1063658094@qq.com
 */
public class Tool {
    /**
     * 获取Json的字符串
     * @param path 网址
     * @return String
     */
    public static String getJsonStr(String path){
        try {
            InputStream in = getInputStream(path);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[]arr = new byte[1024*8];
            int c=0;
            while ((c=in.read(arr))!=-1){
                out.write(arr,0,c);
            }
            String s = new String(out.toByteArray());
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提供这个类内部使用的，网络获取InputStream流
     * @param path 网址
     * @return InputStream
     * @throws IOException
     */
    private static InputStream getInputStream(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection coon = (HttpURLConnection) url.openConnection();
        coon.setRequestMethod("GET");
        coon.setConnectTimeout(5000);
        if (coon.getResponseCode()==200){
            InputStream in = coon.getInputStream();
            return in;
        }
        return null;
    }
    /**
     * 解析Json
     * @param jsonStr Json字符串
     * @return List<Map<String ,Object>>（图片和文字）
     */
    public static List<Map<String ,Object>>getJsonList(String jsonStr){
        List<Map<String ,Object>>list = new ArrayList<Map<String, Object>>();
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONArray array = obj.getJSONArray("items");
            for (int i = 0; i < array.length(); i++) {
                Map<String ,Object>map=new HashMap<String, Object>();
                JSONObject json = array.getJSONObject(i);
                String ss = json.getString("image");
                Bitmap bitmap = null;
                String medium="";
                if (ss!="null"){
                    String pa="http://pic.qiushibaike.com/system/pictures/"
                            +ss.substring(3,8)+"/"+ss.substring(3,ss.length()-4)+
                            "/small/"+ss;
                    Log.d("Tool------->","ss:"+ ss + "pa:"+pa);
                    medium = "http://pic.qiushibaike.com/system/pictures/"
                            +ss.substring(3,8)+"/"+ss.substring(3,ss.length()-4)+
                            "/medium/"+ss;
                    bitmap = Tool.getBitmap(pa);
                }
                map.put("medium",medium);
                map.put("image",bitmap);
                map.put("content",json.getString("content"));
                map.put("comments_count",
                        json.getString("comments_count"));
                JSONObject jsonObject_user = json.optJSONObject("user");
                if (jsonObject_user!=null){
                    map.put("login", jsonObject_user.getString("login"));
                }else{
                    map.put("login","佚名");
                }
                list.add(map);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Bitmap getBitmap(String path){
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getInputStream(path));
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
