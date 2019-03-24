package uni.kea.marius.kearatings.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.*;

public class Score implements Parcelable {
    public static final float MAX = 5f;
    public static final float MIN = 0.5f;
    public static final float STEP = .5f;
    private static final float DEFAULT = -1;

    private UUID mId;
    private UUID mUserId;
    private Map<String, Float> mRatings;

    Score(String[] topics, User user) {
        mId = UUID.randomUUID();
        mUserId = user.getId();
        mRatings = new LinkedHashMap<>(topics.length);
        for (String topic : topics) {
            mRatings.put(topic, DEFAULT);
        }
    }

    public Score(String uuidString, String userUuidString) {
        this();
        mId = UUID.fromString(uuidString);
        mUserId = UUID.fromString(userUuidString);
    }

    private Score() {
        mRatings = new LinkedHashMap<>();
    }

    public void setRatings(Map<String, Float> ratings) {
        mRatings = ratings;
    }

    public Map.Entry<String, Float> get(int position) {
        Set<Map.Entry<String, Float>> mapSet = mRatings.entrySet();

        return mapSet.toArray(new Map.Entry[]{})[position];
    }

    float getAverageRating() {
        float total = 0f;
        String[] topics = mRatings.keySet().toArray(new String[]{});
        for (String topic : topics) {
            total += mRatings.get(topic);
        }

        return total / topics.length;
    }

    public int getSize() {
        return mRatings.size();
    }

    static Score average(Score... scores) {
        Score result = new Score();

        int scoreCount = scores.length;
        if (scoreCount < 1) {
            return result;
        }

        String[] topics = scores[0].mRatings.keySet().toArray(new String[]{});

        for (String topic : topics) {
            float rating = 0f;
            for (Score score : scores) {
                rating += score.mRatings.get(topic);
            }

            //call put() of Map to bypass step bounding
            result.mRatings.put(topic, rating / scoreCount);
        }

        return result;
    }

    public boolean isReadyToSubmit() {
        return !mRatings.values().contains(DEFAULT);
    }

    public UUID getId() {
        return mId;
    }

    public Object getUserId() {
        return mUserId;
    }

    public Map<String, Float> getRatings() {
        return mRatings;
    }

    @NonNull
    @Override
    public String toString() {
        return "Score{" +
                "mRatings=" + mRatings.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return Objects.equals(mUserId, score.mUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUserId);
    }

    private Score(Parcel in) {
        this();

        long mostSigBits = in.readLong();
        long leastSigBits = in.readLong();

        mId = new UUID(mostSigBits, leastSigBits);

        mostSigBits = in.readLong();
        leastSigBits = in.readLong();

        mUserId = new UUID(mostSigBits, leastSigBits);
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            Float value = in.readFloat();
            mRatings.put(key, value);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId.getMostSignificantBits());
        dest.writeLong(mId.getLeastSignificantBits());
        dest.writeLong(mUserId.getMostSignificantBits());
        dest.writeLong(mUserId.getLeastSignificantBits());
        dest.writeInt(mRatings.size());
        for (Map.Entry<String, Float> entry : mRatings.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeFloat(entry.getValue());
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Score> CREATOR = new Parcelable.Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };
}