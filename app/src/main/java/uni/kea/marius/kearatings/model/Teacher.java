package uni.kea.marius.kearatings.model;

import android.os.Parcel;
import uni.kea.marius.kearatings.database.TeacherRepo;

public class Teacher extends RepoItem {

    public Teacher() {
        super();
    }

    Teacher(Parcel in) {
        super(in);
    }

    @Override
    protected String[] getRatingTopics() {
        return TeacherRepo.sRatingTopics;
    }

    @Override
    public Type getType() {
        return Type.TEACHER;
    }
}
