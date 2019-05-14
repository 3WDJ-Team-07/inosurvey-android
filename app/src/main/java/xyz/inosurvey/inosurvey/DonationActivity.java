package xyz.inosurvey.inosurvey;

import android.app.job.JobParameters;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import xyz.inosurvey.inosurvey.bean.DonationList;

public class DonationActivity  extends AppCompatActivity {

    ImageView imageView;
    TextView introTextView;
    Button donationButton;
    EditText editText;
    private int donateID;
    private Drawable imageDrawable;
    private String imageURL;
    private int giveINO;
    private int userINO;
    private SharedPreferences preferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        preferences = getSharedPreferences("jwt", MODE_PRIVATE);
        userINO = preferences.getInt("user_ino", -1);
        imageView = findViewById(R.id.imageView);
        introTextView = findViewById(R.id.introTextView);
        donationButton = findViewById(R.id.donationButton);
        editText = findViewById(R.id.editText);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("기부하기");
        ab.setDisplayHomeAsUpEnabled(true);
        getDonationInformation();

        donationButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAlert();
            }
        });
    }

    public void getDonationInformation(){
        Intent intent = getIntent();
        int listPosition = intent.getIntExtra("position", 1);
        System.out.println(listPosition + "listlist");
        ArrayList<DonationList> donationListArray;
        donationListArray = intent.getParcelableArrayListExtra("data");
        donateID = donationListArray.get(listPosition).getId();
        imageURL = donationListArray.get(listPosition).getImage();
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    URL url = new URL(imageURL);
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    con.setDoInput(true);
                    con.connect();
                    InputStream inputStream = (InputStream)url.getContent();
                    imageDrawable = Drawable.createFromStream(inputStream, "");
                    inputStream.close();
                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try{
            thread.join();
        }catch(InterruptedException e){
            System.out.println("mzmzmz");
            e.printStackTrace();
        }
        imageView.setImageDrawable(imageDrawable);
        introTextView.setText(donationListArray.get(listPosition).getContent());
    }

    public void finishAlert(){
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        int color = Color.parseColor("#2196f3");
        editText.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("기부하실 이노를 입력해주세요.\n" +"보유 이노 : " + userINO);
        builder.setView(editText);
        builder.setPositiveButton("입력", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                giveINO = Integer.parseInt(editText.getText().toString());
                postINO("http://54.180.121.254/api/donation/donate", "POST");
                Toast.makeText(getApplicationContext(), giveINO +" 이노 기부하셨습니다.", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("user_ino", userINO-giveINO);
                editor.commit();
                finish();
            }
        });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
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

    public void postINO(String url, String method) {

        class postJSON extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute(){

            }
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
                    con.setRequestProperty("Accept", "application/json");
                    con.setDoInput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    con.setDoOutput(true);
                    SharedPreferences preferences = getSharedPreferences("jwt", MODE_PRIVATE);
                    int userID = preferences.getInt("user_id", -1);
                    System.out.println(userID + "usus");
                    OutputStream os = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("user_id="+ userID +"&donation_id=" + donateID + "&ino=" + giveINO);
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
            protected void onPostExecute(String result) {
                String json = result;
                System.out.println(json + "json1234");
            }
        }
        postJSON g = new postJSON();
        g.execute(url, method);
    }

}
