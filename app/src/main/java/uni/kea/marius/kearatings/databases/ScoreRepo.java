package uni.kea.marius.kearatings.databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import uni.kea.marius.kearatings.databases.scores.ScoreBaseHelper;
import uni.kea.marius.kearatings.databases.scores.ScoreCursorWrapper;
import uni.kea.marius.kearatings.databases.scores.ScoreDbSchema.ScoreTable;
import uni.kea.marius.kearatings.databases.scores.ScoreDbSchema.ScoreValuesTable;
import uni.kea.marius.kearatings.databases.scores.ScoreValueCursorWrapper;
import uni.kea.marius.kearatings.models.RateableItem;
import uni.kea.marius.kearatings.models.Score;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScoreRepo {
    private static final String TAG = "ScoreRepo";
    private static ScoreRepo sInstance;

    public synchronized static ScoreRepo getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ScoreRepo(context);
        }
        return sInstance;
    }

    private SQLiteDatabase mDatabase;

    private ScoreRepo(Context context) {
        mDatabase = new ScoreBaseHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public void addScores(RateableItem item) {
        //TODO: improve addition and updating efficiency
        deleteScores(item);
        Log.d(TAG, "saving scores for " + item.toString());

        for (Score score : item.getScores()) {
            Log.d(TAG, "    saving score " + score.toString());
            ContentValues values = getScoreContentValues(score, item);

            mDatabase.insert(ScoreTable.NAME, null, values);

            for (Map.Entry<String, Float> scoreValue : score.getRatings().entrySet()) {
                Log.d(TAG, "        saving entry " + scoreValue.toString());
                ContentValues values2 = getScoreValueContentValues(scoreValue, score);
                mDatabase.insert(ScoreValuesTable.NAME, null, values2);
            }
        }
    }

    public void getScores(RateableItem item) {
        String uuidString = item.getId().toString();
        Log.d(TAG, "loading scores for " + item.toString());

        try (ScoreCursorWrapper cursor = queryScores(
                ScoreTable.Cols.ITEM_ID + " = ?",
                new String[]{uuidString}
        )) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Score score = cursor.getScore();
                Map<String, Float> scoreValues = new LinkedHashMap<>();
                Log.d(TAG, "    loading score " + score.toString());

                try (ScoreValueCursorWrapper valuesCursor = queryScoreValues(
                        ScoreValuesTable.Cols.SCORE_ID + " = ?",
                        new String[]{score.getId().toString()}
                )) {
                    valuesCursor.moveToFirst();

                    while (!valuesCursor.isAfterLast()) {
                        valuesCursor.putValue(scoreValues);
                        valuesCursor.moveToNext();
                    }

                    for (Map.Entry<String, Float> scoreValue : scoreValues.entrySet()) {
                        Log.d(TAG, "        loaded entry " + scoreValue.getKey() + " : " + scoreValue.getValue());
                    }

                }

                score.setRatings(scoreValues);
                item.addScore(score);
                cursor.moveToNext();
            }
        }
    }

    public void deleteScores(RateableItem item) {
        String uuidString = item.getId().toString();
        String[] whereArgs = {uuidString};

        mDatabase.delete(ScoreTable.NAME,
                ScoreTable.Cols.ITEM_ID + " = ?",
                whereArgs);

        for (Score score : item.getScores()) {
            String[] whereArgs2 = {score.getId().toString()};
            mDatabase.delete(ScoreValuesTable.NAME,
                    ScoreValuesTable.Cols.SCORE_ID + " = ?",
                    whereArgs2);
        }
    }

    private static ContentValues getScoreContentValues(Score score, RateableItem item) {
        ContentValues values = new ContentValues();
        values.put(ScoreTable.Cols.ID, score.getId().toString());
        values.put(ScoreTable.Cols.ITEM_ID, item.getId().toString());
        values.put(ScoreTable.Cols.USER_ID, score.getUserId().toString());

        return values;
    }

    private static ContentValues getScoreValueContentValues(Map.Entry<String, Float> scoreValue, Score score) {
        ContentValues values = new ContentValues();
        values.put(ScoreValuesTable.Cols.SCORE_ID, score.getId().toString());
        values.put(ScoreValuesTable.Cols.CRITERIA, scoreValue.getKey());
        values.put(ScoreValuesTable.Cols.VALUE, scoreValue.getValue());

        return values;
    }

    private ScoreCursorWrapper queryScores(String whereClause, String[] whereArgs) {
        @SuppressLint("Recycle") Cursor cursor = mDatabase.query(
                ScoreTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ScoreCursorWrapper(cursor);
    }

    private ScoreValueCursorWrapper queryScoreValues(String whereClause, String[] whereArgs) {
        @SuppressLint("Recycle") Cursor cursor = mDatabase.query(
                ScoreValuesTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ScoreValueCursorWrapper(cursor);
    }
}
