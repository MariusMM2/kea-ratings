package uni.kea.marius.kearatings.database;

import android.content.Context;
import uni.kea.marius.kearatings.R;
import uni.kea.marius.kearatings.model.Teacher;

import java.util.Arrays;

public class TeacherRepo extends AbstractRepo {
    public static String[] sRatingTopics;
    private static TeacherRepo sInstance;

    static TeacherRepo getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TeacherRepo(context);
        }
        return sInstance;
    }

    private TeacherRepo(Context context) {
        super(context);
        sRatingTopics = context.getResources().getStringArray(R.array.teacher_rating_topics);

        addAll(Arrays.asList(new Teacher(),
                new Teacher(),
                new Teacher(),
                new Teacher(),
                new Teacher()));
    }
}
