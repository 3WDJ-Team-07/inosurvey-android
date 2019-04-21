package xyz.inosurvey.inosurvey;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context,  String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+"survey_list"+" ("
                +"toekn VARCHAR(20), "
                +"survey_id VARCHAR(20) NOT NULL, "
                +"survey_title VARCHAR(30) NOT NULL, "
                +"description VARCHAR(30) NOT NULL, "
                +"coin VARCHAR(20) NOT NULL, "
                +"closed_at VARCHAR(30) NOT NULL, "
                +"respondent_count VARCHAR(20) NOT NULL, "
                +"respondent_number VARCHAR(20) NOT NULL, "
                +"is_complate boolean NOT NULL, "
                +"theme VARCHAR(20) NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(){
    }

    public void update(){}

    public void delete(){}

    public void getListData(){}
}
