package xyz.inosurvey.inosurvey;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginButton;
    private SharedPreferences preferences;
    private String idText = null;
    private String pwText = null;
    private String TAG_MESSAGE = "message";
    private String TAG_HEADER = "access_token";
    public String myJSON;
    private boolean mResult;
    private String jwtResult = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        preferences = getSharedPreferences("jwt", MODE_PRIVATE);
        System.out.println(preferences + "pre");
        //String jwtHeader = preferences.getString("jwt", null);
        if(preferences !=null){
            ActivityCompat.finishAffinity(this);
        }
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        loginButton= findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        EditText idEditText, pwEditText;
        idEditText = findViewById(R.id.idEditText);
        pwEditText = findViewById(R.id.pwEditText);
        idText = idEditText.getText().toString();
        pwText = pwEditText.getText().toString();
        GetData g = new GetData();
        g.getJson("http://192.168.43.216:8000/api/user/login");
        //액티비티 destroy
        //ActivityCompat.finishAffinity(this);
    }

    private void parseJSON(){
        try {
            JSONObject jsonObject = new JSONObject(myJSON);
            mResult = jsonObject.getBoolean("message");
            System.out.println(mResult + "mResult");
            if(jsonObject.getJSONObject(TAG_HEADER).toString() != null) {
                jwtResult = jsonObject.getJSONObject("access_token").toString();
                System.out.println(jwtResult + "jwtResult");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class GetData {

        public void getJson(String url){

            class GetDataJson extends AsyncTask<String, Void, String> {
                @Override
                protected String doInBackground(String... params) {
                    String uri = params[0];

                    if (params == null || params.length < 1)
                        return null;

                    BufferedReader bufferedReader = null;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setRequestProperty("Accept","application/json");
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        con.setReadTimeout(100000); //10초동안 서버로부터 반응없으면 에러
                        con.setConnectTimeout(15000); //접속하는 커넥션 타임 15초동안 접속안되면 접속안되는 것으로 간주 (ms)
                        con.setDoInput(true);
                        con.setDoOutput(true);
                        con.setUseCaches(false);
                        con.setDefaultUseCaches(false);

                        //jwtCookie = con.getHeaderField(COOKIES_HEADER);
                        /*List<String> jwtCookies = con.getHeaderFields().get(COOKIES_HEADER);
                        if(jwtCookies != null){
                            for(String cookies : jwtCookies){
                                jwt = cookies.split(";\\s")[0];
                                System.out.println(cookies.split(";\\s")[0] + "abcd");
                            }
                        }*/

                        OutputStream os = con.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write("user_id="+idText +"&password="+pwText);
                        //writer.write("user_id="+idText+"&password="+pwText);
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
                    if(result !=null) {
                        myJSON = result;
                        parseJSON();
                        setHeader(jwtResult);
                        loginAlert(mResult);
                    }

                }
            }
            GetDataJson g = new GetDataJson();
            g.execute(url);
        }

        public void setHeader(String header){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("jwt", header);
            editor.commit();
        }
    }

    public void loginAlert(boolean getResult){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        System.out.println(mResult + "zxcv");
        builder.setTitle("알림");
        if(getResult == true) {
            builder.setMessage("로그인 되었습니다");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Intent intent = new Intent(LoginActivity.this.getActivity(), MainActivity.class);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            });
        }else{
            builder.setMessage("로그인 실패");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        builder.show();
    }
}
