package xyz.inosurvey.inosurvey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{

    Button loginButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton= findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //액티비티 destroy
        ActivityCompat.finishAffinity(this);
        //GetData g = new GetData();
        //g.getJson("http://172.26.3.14:8000/test2", "post");

    }
}