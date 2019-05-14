package xyz.inosurvey.inosurvey.ItemData;

public class InoData {
    public String titleTextView, dateTextView, priceTextView;
    public int priceImageView;

    public InoData(String titleTextView, String dateTextView, int priceImageView, String priceTextView){
        this.titleTextView = titleTextView;
        this.dateTextView = dateTextView;
        this.priceImageView = priceImageView;
        this.priceTextView = priceTextView;
    }
}
