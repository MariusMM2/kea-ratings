package uni.kea.marius.kearatings.databases.scores;

public class ScoreDbSchema {
    public static final class ScoreTable {
        public static final String NAME = "scores";

        public static final class Cols {
            public static final String ID = "id";
            public static final String ITEM_ID = "item_id";
            public static final String USER_ID = "user_id";
        }
    }

    public static final class ScoreValuesTable {
        public static final String NAME = "scoreValues";

        public static final class Cols {
            public static final String SCORE_ID = "score_id";
            public static final String CRITERIA = "criteria";
            public static final String VALUE = "value";
        }
    }
}
