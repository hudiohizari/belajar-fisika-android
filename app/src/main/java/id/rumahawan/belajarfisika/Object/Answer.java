package id.rumahawan.belajarfisika.Object;

import java.util.ArrayList;

public class Answer {
    private String owner;
    private String lessonId;
    private ArrayList<String> correctAnswer;
    private ArrayList<String> selectedAnswer;

    public Answer() {
    }

    public Answer(String owner, String lessonId, ArrayList<String> correctAnswer, ArrayList<String> selectedAnswer) {
        this.owner = owner;
        this.lessonId = lessonId;
        this.correctAnswer = correctAnswer;
        this.selectedAnswer = selectedAnswer;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public ArrayList<String> getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(ArrayList<String> correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(ArrayList<String> selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
