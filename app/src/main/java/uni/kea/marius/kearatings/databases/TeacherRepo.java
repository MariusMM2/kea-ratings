package uni.kea.marius.kearatings.databases;

import android.content.Context;
import uni.kea.marius.kearatings.R;
import uni.kea.marius.kearatings.models.Teacher;

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
        final String[] teachers = context.getResources().getStringArray(R.array.teachers);
        final String[] ids = context.getResources().getStringArray(R.array.teacher_ids);

        addAll(Arrays.asList(
                new Teacher(ids[0], teachers[0]),
                new Teacher(ids[1], teachers[1]),
                new Teacher(ids[2], teachers[2]),
                new Teacher(ids[3], teachers[3]),
                new Teacher(ids[4], teachers[4]),
                new Teacher(ids[5], teachers[5]),
                new Teacher(ids[6], teachers[6]),
                new Teacher(ids[7], teachers[7])));
    }
}
