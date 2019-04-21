package xyz.inosurvey.inosurvey;

import android.app.job.JobParameters;
import android.content.DialogInterface;
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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DonationActivity  extends AppCompatActivity {

    ImageView imageView;
    TextView IntroTextView;
    Button donationButton;
    EditText editText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        imageView = findViewById(R.id.imageView);
        IntroTextView = findViewById(R.id.introTextView);
        donationButton = findViewById(R.id.donationButton);
        editText = findViewById(R.id.editText);
        System.out.println("flag10");
        //getJson("http://192.168.43.239:8000/test","POST");
        ActionBar ab = getSupportActionBar();
        ab.setTitle("기부하기");
        ab.setDisplayHomeAsUpEnabled(true);

        donationButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAlert();
            }
        });
    }

    public void finishAlert(){
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("기부하실 이노를 입력해주세요.");
        builder.setView(editText);
        builder.setPositiveButton("입력", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String money = editText.getText().toString();
                Toast.makeText(getApplicationContext(), money +"원 기부하셨습니다.", Toast.LENGTH_SHORT).show();
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

    public void getJson(String url, String method) {

        class GetDataJson extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute(){

            }
            @Override
            protected String doInBackground(String... params) {
                System.out.println("flag20");
                String uri = params[0];
                System.out.println("flag30");
                String method = params[1];
                System.out.println("flag40");
                if (params == null || params.length < 1)
                    return null;
                BufferedReader bufferedReader = null;
                try {
                    System.out.println("flag50");
                    URL url = new URL(uri);
                    System.out.println("flag60");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    System.out.println("flag2");
                    con.setRequestMethod(method);
                    System.out.println("flag3");
                    con.setRequestProperty("Accept", "application/json");
                    System.out.println("flag4");
                    con.setDoInput(true);
                    System.out.println("flag5");
                    con.setUseCaches(false);
                    System.out.println("flag6");
                    con.setDefaultUseCaches(false);
                    System.out.println("flag7");
                    con.setDoOutput(true);
                    System.out.println("flag8");
                    OutputStream os = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("donation_id=1");
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
        GetDataJson g = new GetDataJson();
        g.execute(url, method);
    }

}
