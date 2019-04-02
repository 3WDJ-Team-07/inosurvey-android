package xyz.inosurvey.inosurvey.ItemData;

//DataSet Class
public class DonationData {
    public String titleTextView;
    public String donationTextView;
    public String companyTextView;
    public int imageView;

    public DonationData(Integer imageView, String titleTextView, String companyText, String donationTextView){
        this.companyTextView = companyText;
        this.titleTextView = titleTextView;
        this.donationTextView = donationTextView;
        this.imageView = imageView;
    }
}