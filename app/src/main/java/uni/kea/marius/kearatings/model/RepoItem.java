package uni.kea.marius.kearatings.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.thedeanda.lorem.LoremIpsum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class RepoItem implements Rateable, Parcelable {
    private static final Random sRandom = new Random();
    private Type mType;
    private UUID mId;
    private String mName;
    private List<Score> mScores;

    RepoItem() {
        mType = getType();
        mId = UUID.randomUUID();
        mName = LoremIpsum.getInstance().getName();
        mScores = new ArrayList<>();
        //mRating = sRandom.nextInt(10) / 2f;

        int scoreCount = sRandom.nextInt(10);

        for (int i = 0; i < scoreCount; i++) {
            Score score = new Score(getRatingTopics());
            for (String topic : getRatingTopics()) {
                score.put(topic, sRandom.nextInt(10) / 2f);
            }

            mScores.add(score);
        }
    }

    protected RepoItem(Parcel in) {
        mType = getType();
        mId = (UUID) in.readValue(UUID.class.getClassLoader());
        mName = in.readString();
        if (in.readByte() == 0x01) {
            mScores = new ArrayList<>();
            in.readList(mScores, Score.class.getClassLoader());
        } else {
            mScores = null;
        }
    }

    protected abstract String[] getRatingTopics();

    @Override
    public Score newScoreTemplate() {
        return new Score(getRatingTopics());
    }

    public UUID getId() {
        return mId;
    }

    public abstract Type getType();

    public String getName() {
        return mName;
    }

    @Override
    public void addScore(Score score) {
        mScores.add(score);
    }

    @Override
    public float getRating() {
        return getOverallScore().getAverageRating();
    }

    @Override
    public Score getOverallScore() {
        return Score.average(mScores.toArray(new Score[]{}));
    }

    public void setName(String name) {
        mName = name;
    }

    public enum Type {
        COURSE,
        TEACHER
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mType);
        dest.writeValue(mId);
        dest.writeString(mName);
        if (mScores == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mScores);
        }
    }

    public static final Parcelable.Creator<RepoItem> CREATOR = new Parcelable.Creator<RepoItem>() {
        @Override
        public RepoItem createFromParcel(Parcel in) {
            Type type = (Type) in.readValue(Type.class.getClassLoader());
            if (type == Type.COURSE) {
                return new Course(in);
            } else if (type == Type.TEACHER) {
                return new Teacher(in);
            } else {
                throw new IllegalStateException("RepoItem type is " + (type != null ? type.toString() : null));
            }
        }

        @Override
        public RepoItem[] newArray(int size) {
            return new RepoItem[size];
        }
    };
}
