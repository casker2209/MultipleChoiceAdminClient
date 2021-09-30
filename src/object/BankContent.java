package object;

import java.util.List;

public class BankContent {
    String id;
    List<Question> questionList;

    public String getId() {
        return id;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
    public BankContent(String id, List<Question> questionList){
        this.id = id;
        this.questionList = questionList;
    }
}
