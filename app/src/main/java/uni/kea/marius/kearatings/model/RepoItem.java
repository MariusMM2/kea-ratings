package uni.kea.marius.kearatings.model;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.thedeanda.lorem.LoremIpsum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class RepoItem implements Parcelable {
    private Type mType;
    private UUID mId;
    private String mName;
    private List<Score> mScores;

    RepoItem() {
        mType = getType();
        mId = UUID.randomUUID();
        mName = LoremIpsum.getInstance().getName();
        mScores = new ArrayList<>();
    }

    protected abstract String[] getRatingTopics();

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

    public void addScore(Score score) {
        mScores.add(score);
    }

    public float getRating() {
        return getOverallScore().getAverageRating();
    }

    public Score getOverallScore() {
        if (mScores.size() != 0) {
            return Score.average(mScores.toArray(new Score[]{}));
        } else {
            return newScoreTemplate();
        }
    }

    public void setName(String name) {
        mName = name;
    }

    public enum Type {
        COURSE,
        TEACHER
    }

    @NonNull
    @Override
    public String toString() {
        return "RepoItem{" +
                "mType=" + mType +
                ", mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mScores=" + mScores.toString() +
                '}';
    }

    RepoItem(Parcel in) {
        mType = getType();
        ParcelUuid parcelUuid = in.readParcelable(ParcelUuid.class.getClassLoader());
        mId = parcelUuid.getUuid();
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
        ParcelUuid parcelUuid = new ParcelUuid(mId);
        parcelUuid.writeToParcel(dest, flags);
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
