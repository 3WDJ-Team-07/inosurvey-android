package xyz.inosurvey.inosurvey.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DonationList implements Parcelable {
    private int id, targetAmount, currentAmount,donatorID;
    private String title, content, image, company, createdAt, closedAt;

    public DonationList(){}

    public DonationList(int id, String title, String content, String company , String image, int targetAmount, int currentAmount,
                      int donatorID, String createdAt, String closedAt){
        this.id = id;
        this.title = title;
        this.content = content;
        this.company = company;
        this.image = image;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.donatorID = donatorID;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
    }

    public DonationList(Parcel in){
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(company);
        dest.writeString(image);
        dest.writeInt(targetAmount);
        dest.writeInt(currentAmount);
        dest.writeInt(donatorID);
        dest.writeString(createdAt);
        dest.writeString(closedAt);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        company = in.readString();
        image = in.readString();
        targetAmount = in.readInt();
        currentAmount = in.readInt();
        donatorID = in.readInt();
        createdAt = in.readString();
        closedAt = in.readString();
    }

    public static final Creator<DonationList> CREATOR = new Creator<DonationList>() {
        @Override
        public DonationList createFromParcel(Parcel in) {
            return new DonationList(in);
        }

        @Override
        public DonationList[] newArray(int size) {
            return new DonationList[size];
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

    public int getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getDonatorID() {
        return donatorID;
    }

    public void setDonatorID(int donatorID) {
        this.donatorID = donatorID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }
}