package xyz.inosurvey.inosurvey;

import android.icu.util.Output;
import android.os.AsyncTask;
import android.webkit.CookieManager;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetData {

    public String myJSON;

    static final String COOKIES_HEADER = "Set-Cookie";

    public void getJson(String url, String method){

        class GetDataJson extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                String method = params[1];

                if (params == null || params.length < 1)
                    return null;
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod(method);
                    con.setRequestProperty("Accept","application/json");
                    con.setDoInput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                System.out.println(result + "qwer");
                //showList();
            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(url,method);
        }


    //protected void showList(){
        //try{
            //JSONObject jsonObj = new JSONObject(myJSON);
            //System.out.println(jsonObj.toString());
//            menus = jsonObj.getJSONArray(TAG_RESULTS);
//
//            for(int i=0; i<menus.length(); i++){
//                JSONObject c = menus.getJSONObject(i);
//                String id = c.getString(TAG_ID);
//                String name = c.getString(TAG_NAME);
//                String email = c.getString(TAG_EMAIL);
//                String created = c.getString(TAG_CREATE);
//                String updated = c.getString(TAG_UPDATE);
//
//
//                String abc = id + name + email + created + updated;
//                System.out.println("adc = " + abc);
//                HashMap<String, String> menusArray = new HashMap<String,String>();
//                menusArray.put(TAG_ID, id);
//                menusArray.put(TAG_NAME, name);
//                menusArray.put(TAG_EMAIL, email);
//                menusArray.put(TAG_CREATE, created);
//                menusArray.put(TAG_UPDATE, updated);
//                menuList.add(menusArray);
//            }



        //}catch(JSONException e){
           //e.printStackTrace();
           // System.out.println("Error");
        //}
   // }
    }

