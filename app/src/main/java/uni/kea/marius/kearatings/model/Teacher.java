package uni.kea.marius.kearatings.model;

import com.thedeanda.lorem.LoremIpsum;

import java.util.UUID;

public class Teacher implements RepoItem {
    private UUID mId;
    private String mName;
    private float mRating;

    public Teacher() {
        mId = UUID.randomUUID();
        mName = LoremIpsum.getInstance().getName();
        mRating = Course.sRandom.nextFloat();
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
