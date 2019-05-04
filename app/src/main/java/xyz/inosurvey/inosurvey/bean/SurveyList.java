package xyz.inosurvey.inosurvey.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class SurveyList implements Parcelable {
    private int id, respondentCount, respondentNumber, isCompleted, isSale;
    private String startedAt, closedAt, title, description, coin, backgroundColor;

    public SurveyList(){}

    public SurveyList(int id, String title, String description, String coin, String startedAt, String closedAt,
                      int respondentCount, int respondentNumber, int isCompleted, int isSale, String bgColor){
        this.id = id;
        this.title = title;
        this.description = description;
        this.coin = coin;
        this.startedAt = startedAt;
        this.closedAt = closedAt;
        this.respondentCount = respondentCount;
        this.respondentNumber = respondentNumber;
        this.isCompleted = isCompleted;
        this.isSale = isSale;
        this.backgroundColor = bgColor;
    }

    public SurveyList(Parcel in){
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(coin);
        dest.writeString(startedAt);
        dest.writeString(closedAt);
        dest.writeInt(respondentCount);
        dest.writeInt(respondentNumber);
        dest.writeInt(isCompleted);
        dest.writeInt(isSale);
        dest.writeString(backgroundColor);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        coin = in.readString();
        startedAt = in.readString();
        closedAt = in.readString();
        respondentCount = in.readInt();
        respondentNumber = in.readInt();
        isCompleted = in.readInt();
        isSale = in.readInt();
        backgroundColor = in.readString();
    }

    public static final Creator<SurveyList> CREATOR = new Creator<SurveyList>() {
        @Override
        public SurveyList createFromParcel(Parcel in) {
            return new SurveyList(in);
        }

        @Override
        public SurveyList[] newArray(int size) {
            return new SurveyList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRespondentCount() {
        return respondentCount;
    }

    public void setRespondentCount(int respondentCount) {
        this.respondentCount = respondentCount;
    }

    public int getRespondentNumber() {
        return respondentNumber;
    }

    public void setRespondentNumber(int respondentNumber) {
        this.respondentNumber = respondentNumber;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

    public int getIsSale() {
        return isSale;
    }

    public void setIsSale(int isSale) {
        this.isSale = isSale;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}