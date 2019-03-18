package uni.kea.marius.kearatings.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Score implements Parcelable {
    static final float MAX = 5f;
    static final float STEP = .5f;

    private Map<String, Float> ratings;

    Score(String[] topics) {
        ratings = new LinkedHashMap<>(topics.length);
        for (String topic : topics) {
            ratings.put(topic, 0f);
        }
    }

    private Score() {
        ratings = new LinkedHashMap<>();
    }

    public void put(String key, float value) {
        float newValue = Math.max(0, Math.min(MAX, value));

        float newValueBoundToStep = (int) ((newValue + (STEP / 2f)) / STEP) * STEP;

        ratings.put(key, newValueBoundToStep);
//        ratings.put(key, Float.parseFloat(String.format("%.2f", newValueBoundToStep)));
    }

    @SuppressWarnings("ConstantConditions")
    public float get(String key) {
        return ratings.get(key);
    }

    public Map.Entry<String, Float> get(int position) {
        Set<Map.Entry<String, Float>> mapSet = ratings.entrySet();

        return mapSet.toArray(new Map.Entry[]{})[position];
    }

    public float getAverageRating() {
        float total = 0f;
        String[] topics = ratings.keySet().toArray(new String[]{});
        for (String topic : topics) {
            total += ratings.get(topic);
        }

        return total / topics.length;
    }

    public int getSize() {
        return ratings.size();
    }

    public static Score average(Score... scores) {
        Score result = new Score();

        int scoreCount = scores.length;
        if (scoreCount < 1) {
            return result;
        }

        String[] topics = scores[0].ratings.keySet().toArray(new String[]{});

        for (String topic : topics) {
            float rating = 0f;
            for (Score score : scores) {
                rating += score.get(topic);
            }

            //call put() of Map to bypass step bounding
            result.ratings.put(topic, rating / scoreCount);
        }

        return result;
    }

    @Override
    public String toString() {
        return "Score{" +
                "ratings=" + ratings.toString() +
                '}';
    }

    protected Score(Parcel in) {
        this();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            Float value = in.readFloat();
            ratings.put(key, value);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ratings.size());
        for (Map.Entry<String, Float> entry : ratings.entrySet()) {
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