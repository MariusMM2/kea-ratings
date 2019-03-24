package uni.kea.marius.kearatings.models;

import android.os.Parcel;
import uni.kea.marius.kearatings.databases.CourseRepo;

public class Course extends RateableItem {

    public Course(String uuidString, String name) {
        super(uuidString, name);
    }

    Course(Parcel in) {
        super(in);
    }

    @Override
    protected String[] getRatingTopics() {
        return CourseRepo.sRatingTopics;
    }

    @Override
    public Type getType() {
        return Type.COURSE;
    }

}
