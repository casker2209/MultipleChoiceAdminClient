package object;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
public class Exam implements Serializable {
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
    public Exam(String id,String name,String description,List<Question> questionList){
        this.id = id;
        this.description = description;
        this.name = name;
        this.questionList = questionList;
    }

    public Exam(String name,String description,List<Question> questionList){
        this.name = name;
        this.description = description;
        this.questionList = questionList;
    }
    public Exam(ExamInfo examInfo,List<Question> questionList){
        this.id = examInfo.getId();
        this.description = examInfo.getDescription();
        this.name = examInfo.getName();
        this.questionList = questionList;
    }
    public JSONObject toJSON(){
        JSONObject examObject = new JSONObject();
        examObject.put("id",getId());
        examObject.put("name",getName());
        examObject.put("description",getDescription());
        JSONArray questionList = new JSONArray();
        for(Question question: getQuestionList()){
            questionList.put(question.toJSON());
        }
        examObject.put("questionList",questionList);
        return examObject;
    }
}
