package object;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private String question;
    private List<String> QuestionCorrect;
    private List<String> answer;



    public String getQuestion() {
        return question;
    }

    public List<String> getQuestionCorrect() {
        return QuestionCorrect;
    }

    public void setQuestion(String question) {
        question = question;
    }

    public void setQuestionCorrect(List<String> questionCorrect) {
        QuestionCorrect = questionCorrect;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public Question(String question, List<String> QuestionCorrect,List<String> answer){
        this.question = question;
        this.QuestionCorrect = QuestionCorrect;
        this.answer = answer;
    }
    public JSONObject toJSON(){
        JSONObject questionJSON = new JSONObject();
        questionJSON.put("question",getQuestion());
        JSONArray answerArray = new JSONArray();
        for(String answer : getAnswer()){
            answerArray.put(answer);
        }
        questionJSON.put("answer",answerArray);
        JSONArray correctArray = new JSONArray();
        for(String correct:getQuestionCorrect()){
            correctArray.put(correct);
        }
        questionJSON.put("questionCorrect",correctArray);
        return questionJSON;
    }
}
