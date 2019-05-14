package xyz.inosurvey.inosurvey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import xyz.inosurvey.inosurvey.ItemData.InoData;
import xyz.inosurvey.inosurvey.adapter.InoAdapter;

public class InoActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private int userId;
    private RecyclerView inoListView;
    private RecyclerView.LayoutManager inoLayoutManager;
    private RecyclerView.Adapter inoAdapter;
    private ArrayList<InoData> inoDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ino);
        actionBar = getSupportActionBar();
        actionBar.setTitle("상세 내역");
        actionBar.setDisplayHomeAsUpEnabled(true);

        getUserId();
        inoListView = findViewById(R.id.inoListView);
        inoListView.setHasFixedSize(true);
        inoLayoutManager = new LinearLayoutManager(this);
        inoListView.setLayoutManager(inoLayoutManager);
        inoDataSet = new ArrayList<>();

        getInoList("http://54.180.121.254/api/user/wallet/receipt/all", "POST");

    }

    public void getUserId(){
        SharedPreferences preferences = getSharedPreferences("jwt", MODE_PRIVATE);
        userId = preferences.getInt("user_id", -1);
        System.out.println(userId + "이노상세내역 userid");
    }

    public void addList(String getJSON){
        try {
            if(getJSON == null) {
                System.out.println("nullgetJSON");
            }
            JSONObject inoJSONObject = new JSONObject(getJSON);
            JSONArray listJSONArray = inoJSONObject.getJSONArray("list");
                for(int i=0; i<listJSONArray.length(); i++){
                    System.out.println("mnmn");
                    JSONObject listJSONObject = listJSONArray.getJSONObject(i);
                    String date = listJSONObject.getString("date");
                    String title = listJSONObject.getString("title");
                    String method = listJSONObject.getString("method");
                    String content = listJSONObject.getString("content");
                    String sign = listJSONObject.getString("sign");
                    System.out.println("sign : "+ sign);
                    int price = listJSONObject.getInt("price");
                        inoDataSet.add(new InoData("[" + title + "-" + method + "] " + content, date, R.drawable.minus, "" + sign + price));
                        //inoDataSet.add(new InoData("[" + title + "-" + method + "] " + content, date, R.drawable.plus, "" + sign + price));

                    }
            inoAdapter = new InoAdapter(inoDataSet);
            inoListView.setAdapter(inoAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getInoList(String url, String method){

        class GetInoList extends AsyncTask<String, Void, String> {
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
                    con.setDoOutput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    OutputStream os = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("id=" + userId);
                    writer.flush();
                    writer.close();
                    os.close();
                    con.connect();
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
                if(result != null) {
                    System.out.println("ino 상세내역 JSON 값 = " + result);
                    String getJSON = result;
                    addList(result);
                }
            }

        }
        GetInoList g = new GetInoList();
        g.execute(url,method);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this); 최상위 액티비티로 이동 후 아래에 있는 모든 activity destroy
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
