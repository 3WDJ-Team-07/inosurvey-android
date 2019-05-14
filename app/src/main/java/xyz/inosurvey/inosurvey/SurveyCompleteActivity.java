package xyz.inosurvey.inosurvey;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.inosurvey.inosurvey.fragment.SurveyListFragment;

public class SurveyCompleteActivity extends AppCompatActivity {

    private TextView completeTextView, coinTextView, saveTextView;
    private ImageView logoImageView, inoImageView;
    private Button completeButton;
    private String ino;
    private ActionBar actionBar;
    private SharedPreferences preferences;
    private int userIno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_complete);

        actionBar = getSupportActionBar();
        actionBar.setTitle("설문 완료");

        preferences = getSharedPreferences("jwt", MODE_PRIVATE);
        userIno = preferences.getInt("user_ino", -1);

        completeTextView = findViewById(R.id.completeTextView);
        coinTextView = findViewById(R.id.coinTextView);
        saveTextView = findViewById(R.id.saveTextView);
        logoImageView = findViewById(R.id.logoImageView);
        inoImageView = findViewById(R.id.inoImageView);
        completeButton = findViewById(R.id.completeButton);
        Intent intent = getIntent();
        ino = intent.getStringExtra("ino");

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("user_ino", userIno+Integer.parseInt(ino));
        editor.commit();

        coinTextView.setText(ino +"이노");

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
            }
        });
    }
}
