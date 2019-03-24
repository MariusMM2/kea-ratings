package uni.kea.marius.kearatings.database;

import android.content.Context;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Repos {
    private Repos() {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {COURSE_REPO, TEACHER_REPO, USER_REPO})
    private @interface RepoType {
    }

    public static final int COURSE_REPO = 0;
    public static final int TEACHER_REPO = 1;
    public static final int USER_REPO = 2;

    public static Repo get(@RepoType int repo, Context context) {
        switch (repo) {
            case COURSE_REPO:
                return CourseRepo.getInstance(context);
            case TEACHER_REPO:
                return TeacherRepo.getInstance(context);
            case USER_REPO:
                return UserRepo.getInstance(context);
            default:
                throw new IllegalArgumentException();
        }
    }
}
