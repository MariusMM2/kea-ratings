package uni.kea.marius.kearatings.databases.scores;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import uni.kea.marius.kearatings.databases.scores.ScoreDbSchema.ScoreTable;
import uni.kea.marius.kearatings.databases.scores.ScoreDbSchema.ScoreValuesTable;

public class ScoreBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "scoreBase.db";

    public ScoreBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ScoreTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ScoreTable.Cols.ID + ", " +
                ScoreTable.Cols.ITEM_ID + ", " +
                ScoreTable.Cols.USER_ID +
                ")"
        );
        db.execSQL("CREATE TABLE " + ScoreValuesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ScoreValuesTable.Cols.SCORE_ID + ", " +
                ScoreValuesTable.Cols.CRITERIA + ", " +
                ScoreValuesTable.Cols.VALUE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
