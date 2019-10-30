package xyz.inosurvey.inosurvey.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import xyz.inosurvey.inosurvey.SurveyActivity;
import xyz.inosurvey.inosurvey.R;

import static xyz.inosurvey.inosurvey.SurveyActivity.controlPosition;

public class SurveyFragment extends Fragment {

    public ImageView checkImageView1, checkImageView2, checkImageView3, checkImageView4, checkImageView5, checkImageView6;
    public CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
    public RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6;
    public RadioButton imageRadioButton1, imageRadioButton2, imageRadioButton3, imageRadioButton4, imageRadioButton5, imageRadioButton6;
    public RadioGroup radioGroup;
    public TextView titleTextView;
    public RatingBar ratingBar;
    public EditText editText, opinionEditText;
    public float getRatingBarScore;
    public ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
    public ArrayList<RadioButton> radioButtonArrayList = new ArrayList<>();
    public ArrayList<RadioButton> imageRadioButtonArrayList = new ArrayList<>();
    private ArrayList<ImageView> checkImageViewArrayList = new ArrayList<>();
    public String radioAnswer;
    private String questionTitle;
    private String questionItemContent;
    private String questionItemImage;
    private ArrayList<String> answerArray = new ArrayList<>();
    private ArrayList<String> checkBoxAnswerArray = new ArrayList<>();
    Drawable drawable;
    int radioButtonPosition = 0;

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
        checkImageView1 = v.findViewById(R.id.checkImageView1);
        checkImageView2 = v.findViewById(R.id.checkImageView2);
        checkImageView3 = v.findViewById(R.id.checkImageView3);
        checkImageView4 = v.findViewById(R.id.checkImageView4);
        checkImageView5 = v.findViewById(R.id.checkImageView5);
        checkImageView6 = v.findViewById(R.id.checkImageView6);
        imageRadioButton1 = v.findViewById(R.id.imageRadioButton1);
        imageRadioButton2 = v.findViewById(R.id.imageRadioButton2);
        imageRadioButton3 = v.findViewById(R.id.imageRadioButton3);
        imageRadioButton4 = v.findViewById(R.id.imageRadioButton4);
        imageRadioButton5 = v.findViewById(R.id.imageRadioButton5);
        imageRadioButton6 = v.findViewById(R.id.imageRadioButton6);
        titleTextView = (TextView) v.findViewById(R.id.titleTextVIew);
        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        ratingBar.bringToFront();
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

        imageRadioButtonArrayList.add(imageRadioButton1);
        imageRadioButtonArrayList.add(imageRadioButton2);
        imageRadioButtonArrayList.add(imageRadioButton3);
        imageRadioButtonArrayList.add(imageRadioButton4);
        imageRadioButtonArrayList.add(imageRadioButton5);
        imageRadioButtonArrayList.add(imageRadioButton6);

        checkImageViewArrayList.add(checkImageView1);
        checkImageViewArrayList.add(checkImageView2);
        checkImageViewArrayList.add(checkImageView3);
        checkImageViewArrayList.add(checkImageView4);
        checkImageViewArrayList.add(checkImageView5);
        checkImageViewArrayList.add(checkImageView6);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SurveyActivity sf= (SurveyActivity)getActivity();
                for(int i =0; i<checkImageViewArrayList.size(); i++){
                    checkImageViewArrayList.get(i).setVisibility(View.GONE);
                }
                if(checkedId == R.id.radioButton1){
                    radioAnswer = "1";
                    if(sf.questionTypeId == 7 && sf.questionFiltering == 1){
                        sf.filteringFinishButton();
                        sf.responsive_filtering = true;
                    }else if(sf.questionTypeId == 7 && sf.questionFiltering == 2){
                        sf.filteringNextButton();
                        sf.responsive_filtering = false;
                    }
                }else if(checkedId == R.id.radioButton2){
                    radioAnswer = "2";
                    if(sf.questionTypeId == 7 && sf.questionFiltering == 2){
                        sf.filteringFinishButton();
                        sf.responsive_filtering = true;
                    }else if(sf.questionTypeId == 7 && sf.questionFiltering == 1){
                        sf.filteringNextButton();
                        sf.responsive_filtering = false;
                    }
                }else if(checkedId == R.id.radioButton3){
                    radioAnswer = "3";
                }else if(checkedId == R.id.radioButton4){
                    radioAnswer = "4";
                }else if(checkedId == R.id.radioButton5){
                    radioAnswer = "5";
                }else if(checkedId == R.id.radioButton6){
                    radioAnswer = "6";
                }
                if(checkedId == R.id.imageRadioButton1){
                    checkImageViewArrayList.get(0).setVisibility(View.VISIBLE);
                    radioAnswer = "1";
                }else if(checkedId == R.id.imageRadioButton2){
                    checkImageViewArrayList.get(1).setVisibility(View.VISIBLE);
                    radioAnswer = "2";
                }else if(checkedId == R.id.imageRadioButton3){
                    checkImageViewArrayList.get(2).setVisibility(View.VISIBLE);
                    radioAnswer = "3";
                }else if(checkedId == R.id.imageRadioButton4){
                    checkImageViewArrayList.get(3).setVisibility(View.VISIBLE);
                    radioAnswer = "4";
                }else if(checkedId == R.id.imageRadioButton5){
                    checkImageViewArrayList.get(4).setVisibility(View.VISIBLE);
                    radioAnswer = "5";
                }else if(checkedId == R.id.imageRadioButton6){
                    checkImageViewArrayList.get(5).setVisibility(View.VISIBLE);
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

    public String getType7Answer() { return this.radioAnswer; }

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
                    titleTextView.setText(sf.questionNumber + ". " + ((SurveyActivity)getActivity()).questionTitle);
                    JSONObject questionItemsJSONObject = null;
                    String questionItemContent = null;
                    if (((SurveyActivity)getActivity()).questionTypeId == 1) {
                        for (int i = 0; i <= ((SurveyActivity)getActivity()).questionItemsJSONArray.length(); i++) {
                            questionItemsJSONObject = sf.questionItemsJSONArray.getJSONObject(i);
                            questionItemContent = questionItemsJSONObject.getString("content");
                            radioButtonArrayList.get(radioButtonPosition).setText(questionItemContent);
                            radioButtonArrayList.get(radioButtonPosition).setVisibility(View.VISIBLE);
                            radioButtonPosition++;
                        }
                        radioButtonPosition=0;
                    } else if (sf.questionTypeId == 2) {
                        editText.setVisibility(View.VISIBLE);
                    } else if (sf.questionTypeId == 3) {
                        int checkBoxPosition = 0;
                        for (int i = 0; i <= sf.questionItemsJSONArray.length(); i++) {
                            questionItemsJSONObject = sf.questionItemsJSONArray.getJSONObject(i);
                            questionItemContent = questionItemsJSONObject.getString("content");
                            checkBoxArrayList.get(checkBoxPosition).setText(questionItemContent);
                            checkBoxArrayList.get(checkBoxPosition).setVisibility(View.VISIBLE);
                            checkBoxPosition++;
                        }
                    } else if (sf.questionTypeId == 4) {
                        ratingBar.setVisibility(View.VISIBLE);
                    } else if (sf.questionTypeId == 5) {
                        opinionEditText.setVisibility(View.VISIBLE);
                    }else if(sf.questionTypeId == 6){
                        for (int i = 0; i <= sf.questionItemsJSONArray.length(); i++) {
                            questionItemsJSONObject = sf.questionItemsJSONArray.getJSONObject(i);
                            questionItemContent = questionItemsJSONObject.getString("content");
                            questionItemImage = questionItemsJSONObject.getString("content_image");
                            System.out.println(questionItemImage + " 이미지 url");
                            imageRadioButtonArrayList.get(radioButtonPosition).setText(questionItemContent);
                                Thread thread = new Thread(){
                                    @Override
                                    public void run(){
                                        try{
                                            URL url = new URL(questionItemImage);
                                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                                            con.setDoInput(true);
                                            con.connect();
                                            InputStream inputStream = (InputStream)url.getContent();
                                            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                                            Bitmap originalBitmap = BitmapFactory.decodeStream(bufferedInputStream);
                                            Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 1000, 800, false);
                                            drawable = new BitmapDrawable(getResources(), resizedBitmap);
                                            //radioButtonArrayList.get(radioButtonPosition).setCompoundDrawables(0, bmp, 0, 0);
                                            inputStream.close();
                                            bufferedInputStream.close();
                                        }catch(MalformedURLException e){
                                            e.printStackTrace();
                                        }catch(IOException e){
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                thread.start();
                                try{
                                    thread.join();
                                }catch(InterruptedException e){
                                    e.printStackTrace();
                                }
                            imageRadioButtonArrayList.get(radioButtonPosition).setButtonDrawable(drawable);
                            imageRadioButtonArrayList.get(radioButtonPosition).setVisibility(View.VISIBLE);
                            radioButtonPosition++;
                        }
                    }else if(((SurveyActivity)getActivity()).questionTypeId == 7){
                        for (int i = 0; i <= ((SurveyActivity)getActivity()).questionItemsJSONArray.length(); i++) {
                            questionItemsJSONObject = ((SurveyActivity)getActivity()).questionItemsJSONArray.getJSONObject(i);
                            questionItemContent = questionItemsJSONObject.getString("content");
                            radioButtonArrayList.get(radioButtonPosition).setText(questionItemContent);
                            radioButtonArrayList.get(radioButtonPosition).setVisibility(View.VISIBLE);
                            radioButtonPosition++;
                        }
                        radioButtonPosition=0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("errjson");
                }
        }
    }

}



