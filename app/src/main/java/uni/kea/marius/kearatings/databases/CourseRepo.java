package uni.kea.marius.kearatings.databases;

import android.content.Context;
import uni.kea.marius.kearatings.R;
import uni.kea.marius.kearatings.models.Course;

import java.util.Arrays;

public class CourseRepo extends AbstractRepo {
    public static String[] sRatingTopics;
    private static CourseRepo sInstance;

    static CourseRepo getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CourseRepo(context);
        }
        return sInstance;
    }

    private CourseRepo(Context context) {
        super(context);
        sRatingTopics = context.getResources().getStringArray(R.array.course_rating_topics);
        final String[] courses = context.getResources().getStringArray(R.array.courses);
        final String[] ids = context.getResources().getStringArray(R.array.course_ids);

        addAll(Arrays.asList(
                new Course(ids[0], courses[0]),
                new Course(ids[1], courses[1]),
                new Course(ids[2], courses[2]),
                new Course(ids[3], courses[3])));
    }
}