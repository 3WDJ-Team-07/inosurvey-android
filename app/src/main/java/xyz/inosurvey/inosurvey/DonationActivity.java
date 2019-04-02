package xyz.inosurvey.inosurvey;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DonationActivity  extends AppCompatActivity {

    ImageView imageView;
    TextView IntroTextView;
    Button donationButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        imageView = findViewById(R.id.imageView);
        IntroTextView = findViewById(R.id.introTextView);
        donationButton = findViewById(R.id.donationButton);

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

}
