package uni.kea.marius.kearatings.model;

import android.os.Parcel;
import uni.kea.marius.kearatings.database.CourseRepo;

public class Course extends RateableItem {

    public Course() {
        super();
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
