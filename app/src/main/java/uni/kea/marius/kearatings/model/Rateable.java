package uni.kea.marius.kearatings.model;

public interface Rateable {

    void addScore(Score score);

    float getRating();

    Score getOverallScore();

    Score newScoreTemplate();
}
