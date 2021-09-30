package object;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Bank {
    private String id;
    private String name;
    private String description;
    private List<Question> questionList;
    public void setName(String name) {
        name = name;
    }

    public void setId(String id) {
        id = id;
    }

    public void setDescription(String description) {
        description = description;
    }

    public void setQuestionList(List<Question> question) {
        this.questionList= question;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public String getId() {
        return id;
    }
    public Bank(String id,String name,String description,List<Question> questionList){
        this.id = id;
        this.description = description;
        this.name = name;
        this.questionList = questionList;
    }
    public Bank(String name,String description,List<Question> questionList){
        this.description = description;
        this.name = name;
        this.questionList = questionList;
    }
    public Bank(BankInfo bankInfo,List<Question> questionList){
        this.id = bankInfo.getId();
        this.description = bankInfo.getDescription();
        this.name = bankInfo.getName();
        this.questionList = questionList;
    }
    public JSONObject toJSON(){
        JSONObject bank = new JSONObject();
        bank.put("name",name);
        bank.put("description",description);
        JSONArray questionArray = new JSONArray();
        for(Question question: questionList){
            questionArray.put(question.toJSON());
        }
        bank.put("questionList",questionArray);
        if(id!=null){
            bank.put("id",id);
        }
        return bank;
    }
}
