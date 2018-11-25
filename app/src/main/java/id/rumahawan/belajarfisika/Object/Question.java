package id.rumahawan.belajarfisika.Object;

public class Question {
    private String id;
    private String questionUrl;
    private String lessonId;
    private String correctAnswer;
    private String wrongAnswer0;
    private String wrongAnswer1;
    private String wrongAnswer2;

    public Question() {}

    public Question(String id, String questionUrl, String lessonId, String correctAnswer, String wrongAnswer0, String wrongAnswer1, String wrongAnswer2) {
        this.id = id;
        this.questionUrl = questionUrl;
        this.lessonId = lessonId;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer0 = wrongAnswer0;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionUrl() {
        return questionUrl;
    }

    public void setQuestionUrl(String questionUrl) {
        this.questionUrl = questionUrl;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getWrongAnswer0() {
        return wrongAnswer0;
    }

    public void setWrongAnswer0(String wrongAnswer0) {
        this.wrongAnswer0 = wrongAnswer0;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }
}
