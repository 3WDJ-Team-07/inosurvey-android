package xyz.inosurvey.inosurvey.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import xyz.inosurvey.inosurvey.SurveyActivity;
import xyz.inosurvey.inosurvey.R;

import static xyz.inosurvey.inosurvey.SurveyActivity.controlPosition;

public class SurveyFragment extends Fragment {

    public CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
    public RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6;
    public RadioGroup radioGroup;
    public TextView titleTextView;
    public RatingBar ratingBar;
    public EditText editText, opinionEditText;
    public float getRatingBarScore;
    public ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
    public ArrayList<RadioButton> radioButtonArrayList = new ArrayList<>();
    public String radioAnswer;
    private String questionTitle = null;
    private String questionItemContent = null;
    private ArrayList<String> answerArray = new ArrayList<>();
    private ArrayList<String> checkBoxAnswerArray = new ArrayList<>();


    public SurveyFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_survey, container, false);

        editText = v.findViewById(R.id.editText);
        opinionEditText = (EditText) v.findViewById(R.id.opinionEditText);
        checkBox6 = (CheckBox) v.findViewById(R.id.checkBox6);
        checkBox5 = (CheckBox) v.findViewById(R.id.checkBox5);
        checkBox4 = (CheckBox) v.findViewById(R.id.checkBox4);
        checkBox3 = (CheckBox) v.findViewById(R.id.checkBox3);
        checkBox2 = (CheckBox) v.findViewById(R.id.checkBox2);
        checkBox1 = (CheckBox) v.findViewById(R.id.checkBox1);
        radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        radioButton6 = (RadioButton) v.findViewById(R.id.radioButton6);
        radioButton5 = (RadioButton) v.findViewById(R.id.radioButton5);
        radioButton4 = (RadioButton) v.findViewById(R.id.radioButton4);
        radioButton3 = (RadioButton) v.findViewById(R.id.radioButton3);
        radioButton2 = (RadioButton) v.findViewById(R.id.radioButton2);
        radioButton1 = (RadioButton) v.findViewById(R.id.radioButton1);
        titleTextView = (TextView) v.findViewById(R.id.titleTextVIew);
        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
;
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (ratingBar.getRating() < 1.0f) {
                    ratingBar.setRating(1);
                }
                getRatingBarScore = rating;
            }
        });
        //getSurveyJSON = getArguments().getString("surveyJSONData").trim();
        editText.setHorizontallyScrolling(false);
        editText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.editText) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        opinionEditText.setHorizontallyScrolling(false);
        opinionEditText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.opinionEditText) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        checkBoxArrayList.add(checkBox1);
        checkBoxArrayList.add(checkBox2);
        checkBoxArrayList.add(checkBox3);
        checkBoxArrayList.add(checkBox4);
        checkBoxArrayList.add(checkBox5);
        checkBoxArrayList.add(checkBox6);

        radioButtonArrayList.add(radioButton1);
        radioButtonArrayList.add(radioButton2);
        radioButtonArrayList.add(radioButton3);
        radioButtonArrayList.add(radioButton4);
        radioButtonArrayList.add(radioButton5);
        radioButtonArrayList.add(radioButton6);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton1){
                    radioAnswer = "1";
                }else if(checkedId == R.id.radioButton2){
                    radioAnswer = "2";
                }else if(checkedId == R.id.radioButton3){
                    radioAnswer = "3";
                }else if(checkedId == R.id.radioButton4){
                    radioAnswer = "4";
                }else if(checkedId == R.id.radioButton5){
                    radioAnswer = "5";
                }else if(checkedId == R.id.radioButton6){
                    radioAnswer = "6";
                }
            }
        });

        checkBox1.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkBoxAnswerArray.add("1");
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkBoxAnswerArray.add("2");
                }
            }
        });

        checkBox3.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkBoxAnswerArray.add("3");
                }
            }
        });

        checkBox4.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkBoxAnswerArray.add("4");
                }
            }
        });

        checkBox5.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkBoxAnswerArray.add("5");
                }
            }
        });

        checkBox6.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkBoxAnswerArray.add("6");
                }
            }
        });

        return v;
    }

    public String getRadioAnswer() {
        return this.radioAnswer;
    }

    public String getEditTextAnswer() { return editText.getText().toString(); }

    public String getCheckBoxAnswer() { return this.checkBoxAnswerArray.toString(); }

    public Float getRatingScoreAnswer() { return getRatingBarScore; }

    public String getOpinionTextAnswer() { return opinionEditText.getText().toString(); }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == true) {
            SurveyActivity sf= (SurveyActivity)getActivity();
                try {
                    if(controlPosition == -1){
                        return;
                    }
                    titleTextView.setVisibility(View.VISIBLE);
                    titleTextView.setText(((SurveyActivity)getActivity()).questionNumber + ". " + ((SurveyActivity)getActivity()).questionTitle);
                    JSONObject questionItemsJSONObject = null;
                    String questionItemContent = null;
                    if (((SurveyActivity)getActivity()).questionTypeId == 1) {
                        int radioButtonPosition = 0;
                        for (int i = 0; i <= ((SurveyActivity)getActivity()).questionItemsJSONArray.length(); i++) {
                            questionItemsJSONObject = ((SurveyActivity)getActivity()).questionItemsJSONArray.getJSONObject(i);
                            questionItemContent = questionItemsJSONObject.getString("content");
                            radioButtonArrayList.get(radioButtonPosition).setText(questionItemContent);
                            radioButtonArrayList.get(radioButtonPosition).setVisibility(View.VISIBLE);
                            radioButtonPosition++;
                        }
                    } else if (((SurveyActivity)getActivity()).questionTypeId == 2) {
                        editText.setVisibility(View.VISIBLE);

                    } else if (((SurveyActivity)getActivity()).questionTypeId == 3) {
                        int checkBoxPosition = 0;
                        for (int i = 0; i <= ((SurveyActivity)getActivity()).questionItemsJSONArray.length(); i++) {
                            questionItemsJSONObject = ((SurveyActivity)getActivity()).questionItemsJSONArray.getJSONObject(i);
                            questionItemContent = questionItemsJSONObject.getString("content");
                            checkBoxArrayList.get(checkBoxPosition).setText(questionItemContent);
                            checkBoxArrayList.get(checkBoxPosition).setVisibility(View.VISIBLE);
                        }
                    } else if (((SurveyActivity)getActivity()).questionTypeId == 4) {
                        ratingBar.setVisibility(View.VISIBLE);
                    } else if (((SurveyActivity)getActivity()).questionTypeId == 5) {
                        opinionEditText.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("errjson");
                }
        }
    }

}



