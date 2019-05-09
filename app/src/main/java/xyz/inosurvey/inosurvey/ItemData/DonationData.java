package xyz.inosurvey.inosurvey.ItemData;

import android.graphics.drawable.Drawable;

//DataSet Class
public class DonationData {
    public String titleTextView;
    public String donationTextView;
    public String companyTextView;
    public Drawable imageView;

    public DonationData(Drawable imageView, String titleTextView, String companyText, String donationTextView){
        this.companyTextView = companyText;
        this.titleTextView = titleTextView;
        this.donationTextView = donationTextView;
        this.imageView = imageView;
    }
}