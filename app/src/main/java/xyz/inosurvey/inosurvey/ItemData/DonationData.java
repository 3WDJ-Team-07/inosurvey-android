package xyz.inosurvey.inosurvey.ItemData;

import android.graphics.drawable.Drawable;

//DataSet Class
public class DonationData {
    public String titleTextView;
    public String amountTextView;
    public Drawable imageView;
    public int progressBar;
    public String percentTextView;

    public DonationData(Drawable imageView, String titleTextView, String amountTextView, int progressBar, String percentTextView){
        this.titleTextView = titleTextView;
        this.amountTextView = amountTextView;
        this.imageView = imageView;
        this.progressBar = progressBar;
        this.percentTextView = percentTextView;
    }
}