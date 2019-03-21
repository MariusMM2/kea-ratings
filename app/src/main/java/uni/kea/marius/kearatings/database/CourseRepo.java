package uni.kea.marius.kearatings.database;

import android.content.Context;
import uni.kea.marius.kearatings.R;
import uni.kea.marius.kearatings.model.Course;

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

        Course course = new Course();
        course.setName("Fooster Barsten Langtnavn");

        addAll(Arrays.asList(course,
                new Course(),
                new Course(),
                new Course(),
                new Course()));
    }
}