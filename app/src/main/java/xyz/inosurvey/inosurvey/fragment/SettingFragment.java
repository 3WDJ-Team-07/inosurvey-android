package xyz.inosurvey.inosurvey.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import xyz.inosurvey.inosurvey.DBHelper;
import xyz.inosurvey.inosurvey.LoginActivity;
import xyz.inosurvey.inosurvey.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends Fragment {

    SharedPreferences preferences;
    private Button logoutButton, deleteButton;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_setting,container,false);

        logoutButton = v.findViewById(R.id.logoutButton);
        deleteButton = v.findViewById(R.id.deleteButton);

        logoutButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getContext().getSharedPreferences("jwt", MODE_PRIVATE);
                String jwt = preferences.getString("jwt", "");
                int ino = preferences.getInt("user_ino", -1);
                int id = preferences.getInt("user_id", -1);
                System.out.println(jwt +"    " +  ino + "     " + "        "+id + "qpqpqp");
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                LoginActivity.jwtToken = null;
                System.out.println(jwt + ino + id + "qpqpqp");
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        deleteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper helper = new DBHelper(v.getContext(), "survey_list", null, 1);
                SQLiteDatabase db = helper.getWritableDatabase();
                String sql = "delete from survey_list";
                db.execSQL(sql);
            }
        });

        return v;
    }
}
