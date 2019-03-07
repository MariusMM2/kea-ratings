package uni.kea.marius.kearatings.model;

import com.thedeanda.lorem.LoremIpsum;

import java.util.Random;
import java.util.UUID;

public class Course implements RepoItem {
    private UUID mId;
    private String mName;
    private float mRating;
    static final Random sRandom = new Random();

    public Course() {
        mId = UUID.randomUUID();
        mName = LoremIpsum.getInstance().getName();
        mRating = sRandom.nextFloat();
    }

    @Override
    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public float getRating() {
        return mRating;
    }
}
