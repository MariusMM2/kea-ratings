package uni.kea.marius.kearatings.database;

import android.content.Context;
import uni.kea.marius.kearatings.model.Course;

import java.util.Arrays;

public class CourseRepo extends AbstractRepo<Course> {
    private static CourseRepo instance;

    public static CourseRepo getInstance(Context context) {
        if (instance == null) {
            instance = new CourseRepo(context);
        }
        return instance;
    }

    private CourseRepo(Context context) {
        super(context);
        addAll(Arrays.asList(new Course(),
                new Course(),
                new Course(),
                new Course(),
                new Course(),
                new Course(),
                new Course(),
                new Course(),
                new Course(),
                new Course(),
                new Course()));
    }
}