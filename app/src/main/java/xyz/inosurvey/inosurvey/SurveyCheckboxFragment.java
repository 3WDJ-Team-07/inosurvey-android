package xyz.inosurvey.inosurvey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

public class SurveyCheckboxFragment extends Fragment {

    public int checkPosition;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
    ArrayList<CheckBox> checkArray;

    public SurveyCheckboxFragment(){}

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_survey_checkbox,container,false);

        Bundle args = this.getArguments();
        if (args != null) {
            int position = args.getInt("position");
            checkPosition = position;
        }else
            checkPosition = 1;

        checkBox1 = v.findViewById(R.id.checkBox1);
        checkBox2 = v.findViewById(R.id.checkBox2);
        checkBox3 = v.findViewById(R.id.checkBox3);
        checkBox4 = v.findViewById(R.id.checkBox4);
        checkBox5 = v.findViewById(R.id.checkBox5);
        checkBox6 = v.findViewById(R.id.checkBox6);

        ArrayList<CheckBox> checkArray = new ArrayList<>();
        checkArray.add(checkBox1);
        checkArray.add(checkBox2);
        checkArray.add(checkBox3);
        checkArray.add(checkBox4);
        checkArray.add(checkBox5);
        checkArray.add(checkBox6);

        for(int i = 0; i<checkPosition; i++){
            checkArray.get(i).setVisibility(View.VISIBLE);
        }

        return v;
    }
}
