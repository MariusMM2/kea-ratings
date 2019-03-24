package uni.kea.marius.kearatings.databases.scores;

import android.database.Cursor;
import android.database.CursorWrapper;
import uni.kea.marius.kearatings.models.Score;

public class ScoreCursorWrapper extends CursorWrapper {

    public ScoreCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Score getScore() {
        String uuidString = getString(getColumnIndex(ScoreDbSchema.ScoreTable.Cols.ID));
        String userIdString = getString(getColumnIndex(ScoreDbSchema.ScoreTable.Cols.USER_ID));

        return new Score(uuidString, userIdString);
    }
}
