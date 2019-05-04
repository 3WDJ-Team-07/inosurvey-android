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
                +"id int(10) NOT NULL Primary key, "
                +"title VARCHAR(30) NOT NULL, "
                +"description VARCHAR(100) NOT NULL, "
                +"coin int(10) NOT NULL, "
                +"started_at VARCHAR(30) NOT NULL, "
                +"closed_at VARCHAR(30) NOT NULL, "
                +"respondent_count int(10) NOT NULL, "
                +"respondent_number int(10) NOT NULL, "
                +"is_completed int(1) NOT NULL, "
                +"is_sale int(1) NOT NULL, "
                +"bg_color VARCHAR(20) NOT NULL, "
                +"is_done int(1) NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS survey_list");
        onCreate(db);
    }
}
