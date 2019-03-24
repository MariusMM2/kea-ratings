package uni.kea.marius.kearatings.databases.scores;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Map;

public class ScoreValueCursorWrapper extends CursorWrapper {

    public ScoreValueCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public void putValue(Map<String, Float> dest) {
        String key = getString(getColumnIndex(ScoreDbSchema.ScoreValuesTable.Cols.CRITERIA));
        float value = getFloat(getColumnIndex(ScoreDbSchema.ScoreValuesTable.Cols.VALUE));

        dest.put(key, value);
    }
}
