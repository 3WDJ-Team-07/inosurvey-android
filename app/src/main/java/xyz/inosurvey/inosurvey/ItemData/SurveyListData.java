package xyz.inosurvey.inosurvey.ItemData;


//DataSet Class
public class SurveyListData {
    public String surveyTitleText,coinText,timeText, surveyCoinText, surveyTimeText, progressBarText;
    public int coinImageView, timeImageView, progressBar;

    public SurveyListData(String surveyTitleText, String coinText, String timeText, String surveyCoinText, String surveyTimeText,
                          String progressBarText, int progressBar,int coinImageView, int timeImageView){
        this.surveyTitleText = surveyTitleText;
        this.coinText = coinText;
        this.timeText = timeText;
        this.surveyCoinText = surveyCoinText;
        this.surveyTimeText = surveyTimeText;
        this.progressBarText = progressBarText;
        this.progressBar = progressBar;
        this.coinImageView = coinImageView;
        this.timeImageView = timeImageView;

    }
}
