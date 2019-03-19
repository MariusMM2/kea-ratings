package uni.kea.marius.kearatings.model;

import android.os.Parcel;

public class Course extends RepoItem {
    private static final String[] RATING_TOPICS = {"Practicality", "Job Opportunities", "Relevance", "Organisation"};

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
