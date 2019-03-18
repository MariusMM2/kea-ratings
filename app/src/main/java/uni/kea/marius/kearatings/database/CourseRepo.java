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

        Course course = new Course();
        course.setName("Fooster Barsten Longname");

        addAll(Arrays.asList(course,
                new Course(),
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