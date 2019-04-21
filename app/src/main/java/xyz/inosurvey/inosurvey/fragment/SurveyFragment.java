package xyz.inosurvey.inosurvey.fragment;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import xyz.inosurvey.inosurvey.SurveyActivity;
import xyz.inosurvey.inosurvey.R;

public class SurveyFragment extends Fragment {

    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    private TextView titleTextView;
    private RatingBar ratingBar;
    private EditText editText, opinionEditText;
    private float getRaitngBarScore;
    private ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
    private ArrayList<RadioButton> radioButtonArrayList = new ArrayList<>();
    private SurveyFragment sf;
    private final String TAG = "SurveyFragment";

    private String getMessage = null;
    private String getSurveyJSON;
    private int questionNumber = 0;
    private int questionTypeId = 0;
    private String questionTitle = null;
    private String questionItemContent = null;

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
        radioButton5 = (RadioButton) v.findViewById(R.id.radioButton5);
        radioButton4 = (RadioButton) v.findViewById(R.id.radioButton4);
        radioButton3 = (RadioButton) v.findViewById(R.id.radioButton3);
        radioButton2 = (RadioButton) v.findViewById(R.id.radioButton2);
        radioButton1 = (RadioButton) v.findViewById(R.id.radioButton1);
        titleTextView = (TextView) v.findViewById(R.id.titleTextVIew);
        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        System.out.println("aaabbb");
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (ratingBar.getRating() < 1.0f) {
                    ratingBar.setRating(1);
                }
                getRaitngBarScore = rating;
                Log.d(TAG, "rating = " + getRaitngBarScore);
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

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        System.out.println("flag0");
        if (isVisibleToUser == true) {
            if (SurveyActivity.exPo == -1) {
                return;
            }else {
                try { //여기서 바로 받아야함 json 데이터를
                    getSurveyJSON = "{\n" +
                            "    \"message\": \"true\",\n" +
                            "    \"questionItem\": [\n" +
                            "        {\n" +
                            "            \"id\": 1,\n" +
                            "            \"question_title\": \"nostrum\",\n" +
                            "            \"question_number\": 1,\n" +
                            "            \"image\": \"imagesExample\",\n" +
                            "            \"form_id\": 1,\n" +
                            "            \"type_id\": 1,\n" +
                            "            \"question_items\": [\n" +
                            "                {\n" +
                            "                    \"id\": 5,\n" +
                            "                    \"content\": \"deserunt\",\n" +
                            "                    \"content_number\": 1,\n" +
                            "                    \"content_image\": \"exampleImages\",\n" +
                            "                    \"question_id\": 1\n" +
                            "                },\n" +
                            "                {\n" +
                            "                    \"id\": 2,\n" +
                            "                    \"content\": \"maxime\",\n" +
                            "                    \"content_number\": 2,\n" +
                            "                    \"content_image\": \"exampleImages\",\n" +
                            "                    \"question_id\": 1\n" +
                            "                }\n" +
                            "            ]\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"id\": 2,\n" +
                            "            \"question_title\": \"quam\",\n" +
                            "            \"question_number\": 2,\n" +
                            "            \"image\": \"imagesExample\",\n" +
                            "            \"form_id\": 1,\n" +
                            "            \"type_id\": 2,\n" +
                            "            \"question_items\": [\n" +
                            "                {\n" +
                            "                    \"id\": 4,\n" +
                            "                    \"content\": \"dolor\",\n" +
                            "                    \"content_number\": 1,\n" +
                            "                    \"content_image\": \"exampleImages\",\n" +
                            "                    \"question_id\": 2\n" +
                            "                }\n" +
                            "            ]\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"id\": 3,\n" +
                            "            \"question_title\": \"id\",\n" +
                            "            \"question_number\": 3,\n" +
                            "            \"image\": \"imagesExample\",\n" +
                            "            \"form_id\": 1,\n" +
                            "            \"type_id\": 3,\n" +
                            "            \"question_items\": [\n" +
                            "                {\n" +
                            "                    \"id\": 1,\n" +
                            "                    \"content\": \"natus\",\n" +
                            "                    \"content_number\": 1,\n" +
                            "                    \"content_image\": \"exampleImages\",\n" +
                            "                    \"question_id\": 3\n" +
                            "                }\n" +
                            "            ]\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"id\": 4,\n" +
                            "            \"question_title\": \"vel\",\n" +
                            "            \"question_number\": 4,\n" +
                            "            \"image\": \"imagesExample\",\n" +
                            "            \"form_id\": 1,\n" +
                            "            \"type_id\": 4,\n" +
                            "            \"question_items\": [\n" +
                            "                {\n" +
                            "                    \"id\": 3,\n" +
                            "                    \"content\": \"numquam\",\n" +
                            "                    \"content_number\": 1,\n" +
                            "                    \"content_image\": \"exampleImages\",\n" +
                            "                    \"question_id\": 4\n" +
                            "                }\n" +
                            "            ]\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}";
                    getSurveyJSON.trim();
                    JSONObject surveyJSONObject = new JSONObject(getSurveyJSON);
                    System.out.println("flag1");
                    String message = surveyJSONObject.getString("message");
                    System.out.println("flag2");
                    //System.out.println(message + "qwas");
                    JSONArray questionJSONArray = surveyJSONObject.getJSONArray("questionItem");
                    System.out.println("flag3");
                    JSONObject questionJSONObject = questionJSONArray.getJSONObject(SurveyActivity.exPo);
                    System.out.println("flag4");
                    questionTitle = questionJSONObject.getString("question_title");
                    System.out.println("flag5");
                    questionNumber = questionJSONObject.getInt("question_number");
                    titleTextView.setText(questionNumber + ". " + questionTitle);
                    titleTextView.setVisibility(View.VISIBLE);
                    questionTypeId = questionJSONObject.getInt("type_id");
                    System.out.println(questionNumber + "zxcv");
                    System.out.println(questionTypeId);
                    JSONArray questionItemsJSONArray = questionJSONObject.getJSONArray("question_items");
                    JSONObject questionItemsJSONObject = null;
                    if (questionTypeId == 1) {
                        int radioButtonPosition = 0;
                        for (int i = 0; i <= questionItemsJSONArray.length(); i++) {
                            questionItemsJSONObject = questionItemsJSONArray.getJSONObject(i);
                            questionItemContent = questionItemsJSONObject.getString("content");
                            radioButtonArrayList.get(radioButtonPosition).setText(questionItemContent);
                            radioButtonArrayList.get(radioButtonPosition).setVisibility(View.VISIBLE);
                            radioButtonPosition++;
                        }
                        radioButtonPosition = 0;
                    } else if (questionTypeId == 2) {
                        editText.setVisibility(View.VISIBLE);
                    } else if (questionTypeId == 3) {
                        int checkBoxPosition = 0;
                        for (int i = 0; i <= questionItemsJSONArray.length(); i++) {
                            questionItemsJSONObject = questionItemsJSONArray.getJSONObject(i);
                            questionItemContent = questionItemsJSONObject.getString("content");
                            checkBoxArrayList.get(checkBoxPosition).setText(questionItemContent);
                            checkBoxArrayList.get(checkBoxPosition).setVisibility(View.VISIBLE);
                        }
                        checkBoxPosition = 0;
                    } else if (questionTypeId == 4) {
                        ratingBar.setVisibility(View.VISIBLE);
                    } else if (questionTypeId == 5) {
                        opinionEditText.setVisibility(View.VISIBLE);
                    }/*
                if (SurveyActivity.exPo == 0) {
                    //titleTextView.setText(SurveyActivity.exPo +"번 질문");
                }
                if (SurveyActivity.exPo == 1) {
                    setText();
                    for (int i = 0; i <= 3; i++) {
                        checkBoxArrayList.get(i).setVisibility(View.VISIBLE);
                    }
                } else if (SurveyActivity.exPo == 2) {
                    setText();
                    for (int i = 0; i <= 2; i++) {
                        radioButtonArrayList.get(i).setVisibility(View.VISIBLE);
                    }
                } else if (SurveyActivity.exPo == 3) {
                    setText();
                    ratingBar.setVisibility(View.VISIBLE);
                } else if (SurveyActivity.exPo == 4) {
                    setText();
                    editText.setVisibility(View.VISIBLE);
                }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("errjson");
                }
            }
        }
    }
}

