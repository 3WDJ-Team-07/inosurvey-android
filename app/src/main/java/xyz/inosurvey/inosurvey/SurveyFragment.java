package xyz.inosurvey.inosurvey;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class SurveyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_survey,container,false);
        EditText editText = (EditText) v.findViewById(R.id.editText);
        CheckBox checkBox6 = (CheckBox) v.findViewById(R.id.checkBox6);
        CheckBox checkBox5 = (CheckBox) v.findViewById(R.id.checkBox5);
        CheckBox checkBox4 = (CheckBox) v.findViewById(R.id.checkBox4);
        CheckBox checkBox3 = (CheckBox) v.findViewById(R.id.checkBox3);
        CheckBox checkBox2 = (CheckBox) v.findViewById(R.id.checkBox2);
        CheckBox checkBox1 = (CheckBox) v.findViewById(R.id.checkBox1);
        RadioButton radioButton5 = (RadioButton) v.findViewById(R.id.radioButton5);
        RadioButton radioButton4 = (RadioButton) v.findViewById(R.id.radioButton4);
        RadioButton radioButton3 = (RadioButton) v.findViewById(R.id.radioButton3);
        RadioButton radioButton2 = (RadioButton) v.findViewById(R.id.radioButton2);
        RadioButton radioButton1 = (RadioButton) v.findViewById(R.id.radioButton1);
        TextView titleContentTextView = (TextView) v.findViewById(R.id.titleContentTextView);
        TextView titleTextVIew = (TextView) v.findViewById(R.id.titleTextVIew);

        return v;
    }
}
