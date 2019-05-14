package xyz.inosurvey.inosurvey.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import xyz.inosurvey.inosurvey.InoActivity;
import xyz.inosurvey.inosurvey.LoginActivity;
import xyz.inosurvey.inosurvey.R;

public class ProfileFragment extends Fragment {
    private TextView emailTextView, nameTextView, modifyTextView, userInoTextView;
    private ImageView inoImageView;
    private int userId;
    private String userNickName, userEmail;
    private SharedPreferences preferences;
    private int userIno;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_profile,container,false);

        preferences = getContext().getSharedPreferences("jwt", Context.MODE_PRIVATE);
        getFindViewById(v);
        getUserFromPreferences();
        nameTextView.setText(userNickName);
        emailTextView.setText(userEmail);
        userInoTextView.setText(userIno + "");

        userInoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InoActivity.class);
                startActivity(intent);
            }
        });

        inoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InoActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void getFindViewById(View view){
        nameTextView = view.findViewById(R.id.nameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        modifyTextView = view.findViewById(R.id.modifiyTextView);
        userInoTextView = view.findViewById(R.id.userInoTextView);
        inoImageView = view.findViewById(R.id.inoImageView);
    }

    private void getUserFromPreferences(){
        userId = preferences.getInt("user_id", -1);
        userIno = preferences.getInt("user_ino", -1);
        userNickName = preferences.getString("user_nickname", "닉네임");
        userEmail = preferences.getString("user_email", "이메일");
    }

    public void postToken(String url, String method){

        class dataJson extends AsyncTask<String, Void, String> {
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
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    con.setReadTimeout(100000); //10초동안 서버로부터 반응없으면 에러
                    con.setConnectTimeout(15000); //접속하는 커넥션 타임 15초동안 접속안되면 접속안되는 것으로 간주 (ms)
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    OutputStream os = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("access_token="+LoginActivity.jwtToken);
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

            }
        }
        dataJson g = new dataJson();
        g.execute(url, method);
    }
}
