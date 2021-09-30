package object;

import java.util.List;

public class ExamHistory {
    private Exam exam;
    private String score;
    List<Question> questionRight,questionWrong,questionNotChosen;
    List<List<String>> IncorrectChosen;
    public ExamHistory(Exam exam,String score,List<Question> questionRight,List<Question> questionWrong,List<List<String>> IncorrectChosen,List<Question> questionNotChosen){
        this.exam = exam;
        this.score = score;
        this.questionRight = questionRight;
        this.questionWrong = questionWrong;
        this.IncorrectChosen = IncorrectChosen;
        this.questionNotChosen = questionNotChosen;
    }

    public List<Question> getQuestionNotChosen() {
        return questionNotChosen;
    }

    public Exam getExam() {
        return exam;
    }

    public List<List<String>> getIncorrectChosen() {
        return IncorrectChosen;
    }

    public List<Question> getQuestionRight() {
        return questionRight;
    }

    public List<Question> getQuestionWrong() {
        return questionWrong;
    }

    public String getScore() {
        return score;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setIncorrectChosen(List<List<String>> incorrectChosen) {
        IncorrectChosen = incorrectChosen;
    }

    public void setQuestionRight(List<Question> questionRight) {
        this.questionRight = questionRight;
    }

    public void setQuestionWrong(List<Question> questionWrong) {
        this.questionWrong = questionWrong;
    }

    public void setQuestionNotChosen(List<Question> questionNotChosen) {
        this.questionNotChosen = questionNotChosen;
    }
}
