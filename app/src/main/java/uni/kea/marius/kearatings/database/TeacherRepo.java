package uni.kea.marius.kearatings.database;

import android.content.Context;
import uni.kea.marius.kearatings.model.Teacher;

import java.util.Arrays;

public class TeacherRepo extends AbstractRepo {
    private static TeacherRepo instance;

    public static TeacherRepo getInstance(Context context) {
        if (instance == null) {
            instance = new TeacherRepo(context);
        }
        return instance;
    }

    private TeacherRepo(Context context) {
        super(context);
        addAll(Arrays.asList(new Teacher(),
                new Teacher(),
                new Teacher(),
                new Teacher(),
                new Teacher()));
    }
}
