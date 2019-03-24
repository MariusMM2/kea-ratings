package uni.kea.marius.kearatings.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.thedeanda.lorem.LoremIpsum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class RateableItem implements RepoItem, Parcelable {
    private Type mType;
    private UUID mId;
    private String mName;
    private List<Score> mScores;

    RateableItem() {
        mType = getType();
        mId = UUID.randomUUID();
        mName = LoremIpsum.getInstance().getName();
        mScores = new ArrayList<>();
    }

    protected abstract String[] getRatingTopics();

    public Score newScore(User user) {
        return new Score(getRatingTopics(), user);
    }

    @Override
    public UUID getId() {
        return mId;
    }

    public abstract Type getType();

    public String getName() {
        return mName;
    }

    public void addScore(Score score) {
        int scoreIndex = mScores.indexOf(score);
        if (scoreIndex < 0) {
            mScores.add(score);
        } else {
            mScores.set(scoreIndex, score);

        }
    }

    public float getRating() {
        return getOverallScore().getAverageRating();
    }

    public Score getOverallScore() {
        if (mScores.size() != 0) {
            return Score.average(mScores.toArray(new Score[]{}));
        } else {
            return new Score(getRatingTopics(), User.none());
        }
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isRatedBy(User currentUser) {
        return mScores.contains(new Score(new String[]{}, currentUser));
    }

    public Score getScore(User currentUser) {
        return mScores.get(mScores.indexOf(new Score(new String[]{}, currentUser)));
    }

    public enum Type {
        COURSE,
        TEACHER
    }

    @NonNull
    @Override
    public String toString() {
        return "RateableItem{" +
                "mType=" + mType +
                ", mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mScores=" + mScores.toString() +
                '}';
    }

    RateableItem(Parcel in) {
        mType = getType();

        long mostSigBits = in.readLong();
        long leastSigBits = in.readLong();

        mId = new UUID(mostSigBits, leastSigBits);
        mName = in.readString();
        if (in.readByte() == 0x01) {
            mScores = new ArrayList<>();
            in.readList(mScores, Score.class.getClassLoader());
        } else {
            mScores = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mType);
        dest.writeLong(mId.getMostSignificantBits());
        dest.writeLong(mId.getLeastSignificantBits());
        dest.writeString(mName);
        if (mScores == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mScores);
        }
    }

    public static final Parcelable.Creator<RateableItem> CREATOR = new Parcelable.Creator<RateableItem>() {
        @Override
        public RateableItem createFromParcel(Parcel in) {
            Type type = (Type) in.readValue(Type.class.getClassLoader());
            if (type == Type.COURSE) {
                return new Course(in);
            } else if (type == Type.TEACHER) {
                return new Teacher(in);
            } else {
                throw new IllegalStateException("RateableItem type is " + (type != null ? type.toString() : null));
            }
        }

        @Override
        public RateableItem[] newArray(int size) {
            return new RateableItem[size];
        }
    };
}
