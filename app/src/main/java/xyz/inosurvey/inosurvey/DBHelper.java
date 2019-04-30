package xyz.inosurvey.inosurvey;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+"survey_list"+" ("
                +"survey_id VARCHAR(20) NOT NULL, "
                +"survey_title VARCHAR(30) NOT NULL, "
                +"description VARCHAR(100) NOT NULL, "
                +"coin VARCHAR(20) NOT NULL, "
                +"closed_at VARCHAR(30) NOT NULL, "
                +"respondent_count VARCHAR(20) NOT NULL, "
                +"respondent_number VARCHAR(20) NOT NULL, "
                +"is_complate boolean NOT NULL, "
                +"is_sale boolean NOT NULL, "
                +"theme VARCHAR(20) NOT NULL, "
                +"is_done boolean);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(DBHelper helper, int id, String title, String description, int coin, String closedAt, int respondentCount, int respondentNumber,  boolean isComplate, boolean isSale, String theme){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO survey_list(survey_id, survey_title, description, coin, closed_at, respondent_count, respondent_number, is_complate, is_sale, theme, is_done) values(id, title, description, coin, closedAt, respondentCount, respondentNumber, isComplate, isSale, theme, false)");
    }

    public void update(DBHelper helper){
        SQLiteDatabase db = getWritableDatabase();
    }

    public void delete(DBHelper helper){
        SQLiteDatabase db = getWritableDatabase();
    }

    public void getListData(DBHelper helper){
        SQLiteDatabase db = getReadableDatabase();
    }
}
