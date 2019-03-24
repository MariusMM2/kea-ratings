package uni.kea.marius.kearatings.models;

import android.os.Parcel;
import uni.kea.marius.kearatings.databases.TeacherRepo;

public class Teacher extends RateableItem {

    Teacher(Parcel in) {
        super(in);
    }

    public Teacher(String uuidString, String name) {
        super(uuidString, name);
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
