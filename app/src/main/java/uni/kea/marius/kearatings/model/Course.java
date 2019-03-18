package uni.kea.marius.kearatings.model;

import android.os.Parcel;

public class Course extends RepoItem {
    private static final String[] RATING_TOPICS = {"Preparation", "Competence", "Overall Performance", "Quality of Examples"};

    public Course() {
        super();
    }

    public Course(Parcel in) {
        super(in);
    }

    @Override
    protected String[] getRatingTopics() {
        return RATING_TOPICS;
    }

    @Override
    public Type getType() {
        return Type.COURSE;
    }

}
