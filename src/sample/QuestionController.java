package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import object.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionController {
    ExamFormController examFormController;
    BankFormController bankFormController;
    List<Question> questionList;
    Integer index;
    @FXML
    private TextField TextName,TextA,TextB,TextC,TextD;
    @FXML
    private Button Complete;
    @FXML
    private CheckBox BoxA,BoxB,BoxC,BoxD;

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public void setBankFormController(BankFormController bankFormController) {
        this.bankFormController = bankFormController;
    }

    @FXML
    public void complete(ActionEvent event){
        if(isNotNull()){
        List<String> questionCorrect = new ArrayList<String>();
        String name = TextName.getText();
        List<String> questionAnswer = new ArrayList<>();
        questionAnswer.add(TextA.getText());
        questionAnswer.add(TextB.getText());
        questionAnswer.add(TextC.getText());
        questionAnswer.add(TextD.getText());
        if(BoxA.isSelected()) questionCorrect.add(TextA.getText());
        if(BoxB.isSelected()) questionCorrect.add(TextB.getText());
        if(BoxC.isSelected()) questionCorrect.add(TextC.getText());
        if(BoxD.isSelected()) questionCorrect.add(TextD.getText());
        Question question = new Question(name,questionCorrect,questionAnswer);
        System.out.println(index);
        if(examFormController!=null) {
            if (index == null) questionList.add(question);
            else questionList.set(index, question);
            examFormController.QuestiontoObservable(questionList);
        }
        else {
            if (index == null) questionList.add(question);
            else questionList.set(index, question);
            bankFormController.QuestiontoObservable(questionList);
        }
        Scene scene = ((Scene) ((Button) event.getSource()).getScene());
        ((Stage)scene.getWindow()).close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Bạn có chỗ chưa điền");
            alert.showAndWait();
        }
    }
    public boolean isNotNull(){
        if(TextName.getText().isEmpty()||
                TextA.getText().isEmpty()||
                TextB.getText().isEmpty()||
                TextC.getText().isEmpty()||
                TextD.getText().isEmpty())
            return false;
        if(!(BoxA.isSelected()||BoxB.isSelected()||BoxC.isSelected()||BoxD.isSelected()))
            return false;
        return true;
    }

    public void setExamFormController(ExamFormController examFormController) {
        this.examFormController = examFormController;
    }
}
