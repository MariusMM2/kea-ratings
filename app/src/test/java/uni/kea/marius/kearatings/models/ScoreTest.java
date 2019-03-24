package uni.kea.marius.kearatings.models;

public class ScoreTest {
    private final int N_TOPICS = 5;
    private String[] TOPICS;

//    @Before
//    public void setUp() throws Exception {
////        TOPICS = new String[]{"competence", "relevance", "enthusiasm"};
//        TOPICS = new String[N_TOPICS];
//        for (int i = 0; i < N_TOPICS; i++) {
//            TOPICS[i] = String.valueOf(i);
//        }
//    }
//
//    @SuppressWarnings("Duplicates")
//    @Test
//    public void ratingValueStorage() {
//        Score s = new Score(TOPICS);
//
//        //default value
//        assertEquals(0f, s.get(TOPICS[0]), 0f);
//
//        // non fractional values
//        for (float f = -1; f <= MAX + 1; f++) {
//            s.put(TOPICS[0], f);
//            float expected = Math.max(0, Math.min(MAX, f));
//            float actual = s.get(TOPICS[0]);
//            assertEquals(expected, actual, 0f);
//        }
//
//        // fractional values, in step increments
//        for (float f = -1; f <= MAX + 1; f += STEP) {
//            s.put(TOPICS[0], f);
//            float expected = Math.max(0, Math.min(MAX, f));
//            float actual = s.get(TOPICS[0]);
//            assertEquals(expected, actual, 0f);
//        }
//
//        // fractional values, in non-step increments
//        for (float f = -1; f <= MAX + 1; f += .1f) {
//            s.put(TOPICS[0], f);
//            float expected = (int) ((Math.max(0, Math.min(MAX, f)) + (STEP / 2f)) / STEP) * STEP;
//            float actual = s.get(TOPICS[0]);
////            System.out.printf("before: %f, after: %f, actual: %f%n", f, expected, actual);
//            assertEquals(String.format("%.2f must be a multiple of %.2f", expected, STEP), expected, actual, 0f);
//        }
//    }
//
//    @Test
//    public void singleTopicAverage() {
//        Score s1 = new Score(TOPICS);
//        Score s2 = new Score(TOPICS);
//        Score s3 = new Score(TOPICS);
//
//        // unary average, one topic
//        for (float a = 0; a <= 5; a += STEP) {
//            s1.put(TOPICS[0], a);
//            Score avg = Score.average(s1);
//            float expected = a / 1f;
//            float actual = avg.get(TOPICS[0]);
//            String format = String.format("avg(%f) = exp:%f, act:%f", s1.get(TOPICS[0]), expected, actual);
////                    System.out.println(format);
//            assertEquals(format, expected, actual, 0f);
//
//        }
//
//        // binary average, one topic
//        for (float a = 0; a <= 5; a += STEP) {
//            s1.put(TOPICS[0], a);
//            for (float b = 0; b <= 5; b += STEP) {
//                s2.put(TOPICS[0], b);
//                Score avg = Score.average(s1, s2);
//                float expected = (a + b) / 2f;
//                float actual = avg.get(TOPICS[0]);
//                String format = String.format("avg(%f, %f) = exp:%f, act:%f", s1.get(TOPICS[0]), s2.get(TOPICS[0]), expected, actual);
////                    System.out.println(format);
//                assertEquals(format, expected, actual, 0f);
//
//            }
//        }
//
//        // ternary average, one topic
//        for (float a = 0; a <= 5; a += STEP) {
//            s1.put(TOPICS[0], a);
//            for (float b = 0; b <= 5; b += STEP) {
//                s2.put(TOPICS[0], b);
//                for (float c = 0; c <= 5; c += STEP) {
//                    s3.put(TOPICS[0], c);
//                    Score avg = Score.average(s1, s2, s3);
//                    float expected = (a + b + c) / 3f;
//                    float actual = avg.get(TOPICS[0]);
//                    String format = String.format("avg(%f, %f, %f) = exp:%f, act:%f", s1.get(TOPICS[0]), s2.get(TOPICS[0]), s3.get(TOPICS[0]), expected, actual);
////                    System.out.println(format);
//                    assertEquals(format, expected, actual, 0f);
//                }
//            }
//        }
//
//        // nary average, one topic
//        // assume it works
//        assertTrue(true);
//    }
//
//    @Test
//    public void multipleTopicsAverage() {
//        Score s1 = new Score(TOPICS);
//        Score s2 = new Score(TOPICS);
//        Score s3 = new Score(TOPICS);
//
//        // unary average, multiple topics
//        for (float a = 0; a <= 5; a += STEP) {
//            for (String topic : TOPICS) s1.put(topic, a);
//            Score avg = Score.average(s1);
//            float expected = a / 1f;
//            for (String topic : TOPICS) {
//                float actual = avg.get(topic);
//                String format = String.format("avg(%f) = exp:%f, act:%f", s1.get(topic), expected, actual);
////                    System.out.println(format);
//                assertEquals(format, expected, actual, 0f);
//            }
//
//        }
//
//        // binary average, multiple topics
//        for (float a = 0; a <= 5; a += STEP) {
//            for (String topic : TOPICS) s1.put(topic, a);
//            for (float b = 0; b <= 5; b += STEP) {
//                for (String topic : TOPICS) s2.put(topic, b);
//                Score avg = Score.average(s1, s2);
//                float expected = (a + b) / 2f;
//                for (String topic : TOPICS) {
//                    float actual = avg.get(topic);
//                    String format = String.format("avg(%f, %f) = exp:%f, act:%f", s1.get(topic), s2.get(topic), expected, actual);
////                    System.out.println(format);
//                    assertEquals(format, expected, actual, 0f);
//                }
//            }
//        }
//
//        // ternary average, multiple topics
//        for (float a = 0; a <= 5; a += STEP) {
//            for (String topic : TOPICS) s1.put(topic, a);
//            for (float b = 0; b <= 5; b += STEP) {
//                for (String topic : TOPICS) s2.put(topic, b);
//                for (float c = 0; c <= 5; c += STEP) {
//                    for (String topic : TOPICS) s3.put(topic, c);
//                    Score avg = Score.average(s1, s2, s3);
//                    float expected = (a + b + c) / 3f;
//                    for (String topic : TOPICS) {
//                        float actual = avg.get(topic);
//                        String format = String.format("avg(%f, %f, %f) = exp:%f, act:%f", s1.get(topic), s2.get(topic), s3.get(topic), expected, actual);
////                    System.out.println(format);
//                        assertEquals(format, expected, actual, 0f);
//                    }
//                }
//            }
//        }
//    }
}