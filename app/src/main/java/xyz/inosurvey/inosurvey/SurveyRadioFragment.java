package xyz.inosurvey.inosurvey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class SurveyRadioFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    ArrayList<RadioButton> radioArray;
    public int radioPosition;

    public SurveyRadioFragment(){}

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_survey_radio,container,false);

            Bundle args = this.getArguments();
            if (args != null) {
                int position = args.getInt("position", 4);
                radioPosition = position;
            }else
                radioPosition = 1;


        radioGroup = v.findViewById(R.id.radioGroup);
        radioButton1 = v.findViewById(R.id.radioButton1);
        radioButton2 = v.findViewById(R.id.radioButton2);
        radioButton3 = v.findViewById(R.id.radioButton3);
        radioButton4 = v.findViewById(R.id.radioButton4);
        radioButton5 = v.findViewById(R.id.radioButton5);

        ArrayList<RadioButton> radioArray = new ArrayList<>();
        radioArray.add(radioButton1);
        radioArray.add(radioButton2);
        radioArray.add(radioButton3);
        radioArray.add(radioButton4);
        radioArray.add(radioButton5);


        for(int i = 0; i<=radioPosition; i++){
            radioArray.get(i).setVisibility(View.VISIBLE);
        }

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser == true){

        }else{

        }
    }
}
