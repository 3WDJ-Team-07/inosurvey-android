package xyz.inosurvey.inosurvey.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;
import xyz.inosurvey.inosurvey.ItemData.SurveyListData;
import xyz.inosurvey.inosurvey.SurveyActivity;
import xyz.inosurvey.inosurvey.R;

public class SurveyFragment extends Fragment {

    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5 ,checkBox6;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    TextView titleContentTextView, titleTextView;
    EditText editText;
    private ArrayList<CheckBox> cbArray = new ArrayList<>();
    private ArrayList<RadioButton> rbArray = new ArrayList<>();
    private SurveyFragment sf;

    public SurveyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_survey,container,false);
        editText = (EditText) v.findViewById(R.id.editText);
        checkBox6 = (CheckBox) v.findViewById(R.id.checkBox6);
        checkBox5 = (CheckBox) v.findViewById(R.id.checkBox5);
        checkBox4 = (CheckBox) v.findViewById(R.id.checkBox4);
        checkBox3 = (CheckBox) v.findViewById(R.id.checkBox3);
        checkBox2 = (CheckBox) v.findViewById(R.id.checkBox2);
        checkBox1 = (CheckBox) v.findViewById(R.id.checkBox1);
        radioButton5 = (RadioButton) v.findViewById(R.id.radioButton5);
        radioButton4 = (RadioButton) v.findViewById(R.id.radioButton4);
        radioButton3 = (RadioButton) v.findViewById(R.id.radioButton3);
        radioButton2 = (RadioButton) v.findViewById(R.id.radioButton2);
        radioButton1 = (RadioButton) v.findViewById(R.id.radioButton1);
        titleContentTextView = (TextView) v.findViewById(R.id.titleContentTextView);
        titleTextView = (TextView) v.findViewById(R.id.titleTextVIew);

        editText.setHorizontallyScrolling(false);
        editText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() ==R.id.editText) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        cbArray.add(checkBox1);
        cbArray.add(checkBox2);
        cbArray.add(checkBox3);
        cbArray.add(checkBox4);
        cbArray.add(checkBox5);
        cbArray.add(checkBox6);

        rbArray.add(radioButton1);
        rbArray.add(radioButton2);
        rbArray.add(radioButton3);
        rbArray.add(radioButton4);
        rbArray.add(radioButton5);

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser == true){
            if(SurveyActivity.exPo != 0)
                titleContentTextView.setVisibility(View.GONE);
            if(SurveyActivity.exPo == 1){
                titleTextView.setText("1번 질문");
                for (int i = 0; i <= 3; i++) {
                    cbArray.get(i).setVisibility(View.VISIBLE);
                }
            }else if(SurveyActivity.exPo == 2){
                titleTextView.setText("2번 질문");
                for(int i =0; i<=2; i++){
                    rbArray.get(i).setVisibility(View.VISIBLE);
                }
            }else if(SurveyActivity.exPo == 3){
                titleTextView.setText("3번 질문");
                editText.setVisibility(View.VISIBLE);
            }
        }else{

        }
    }
}
