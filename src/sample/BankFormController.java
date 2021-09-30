package sample;

import api.APIClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import object.Bank;
import object.Question;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BankFormController implements Initializable {
    List<Question> questionList;
    ObservableList<String> observableList;
    Bank bank;
    @FXML
    private TextField NameField;
    @FXML
    private TextField DescriptionField;
    @FXML
    private ListView questionListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionList = new ArrayList<>();
        observableList = FXCollections.observableArrayList(new ArrayList<String>());

    }
    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public void setObservableList(ObservableList<String> observableList) {
        this.observableList = observableList;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void QuestiontoObservable(List<Question> questionList){
        this.questionList = questionList;
        observableList = FXCollections.observableArrayList(new ArrayList<String>());
        for(Question question: questionList){
            observableList.add(question.getQuestion());
            questionListView.setItems(observableList);
        }
    }
    public void insertQuestion(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("question.fxml"));
        Parent questionRoot = loader.load();
        /*  Parent questionRoot = FXMLLoader.load(QuestionController.class.getResource("question.fxml"));*/
        QuestionController questionController = loader.getController();
        questionController.setBankFormController(this);
        questionController.setQuestionList(questionList);
        Stage questionStage = new Stage();
        questionStage.setTitle("Tạo bài thi");
        Scene questionScene = new Scene(questionRoot);
        questionStage.setScene(questionScene);
        questionStage.show();
    }
    public void goBack(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bank.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        currentStage.setScene(new Scene(root));
    }
    public void insertBank(ActionEvent event) throws IOException {
        if(NameField.getText().isEmpty()||DescriptionField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Bạn có chỗ chưa điền");
            alert.showAndWait();
        }
        else if(questionList.size()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Bài thi chưa có câu hỏi");
            alert.showAndWait();
        }
        else{
            String name = NameField.getText();
            String description = DescriptionField.getText();
            if(bank!=null)
            {
                String id = bank.getId();
                bank = new Bank(id,name,description,questionList);
            }
            else{
                bank = new Bank(name,description,questionList);
            }
            APIClient.getInstance().addBank(bank);
        }
    }
    public void Delete(ActionEvent event){
        if(questionListView.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vui lòng chọn một câu hỏi để xóa");
            alert.showAndWait();
        }
        else{
            System.out.println(questionList.size());
            int index = questionListView.getSelectionModel().getSelectedIndex();
            questionList.remove(index);
            observableList.remove(index);
            questionListView.setItems(observableList);
        }
    }
    public void Edit(ActionEvent event) throws IOException {
        int index = questionListView.getSelectionModel().getSelectedIndex();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("question.fxml"));
        Parent questionRoot = loader.load();
        /*  Parent questionRoot = FXMLLoader.load(QuestionController.class.getResource("question.fxml"));*/
        QuestionController questionController = loader.getController();
        questionController.setBankFormController(this);
        questionController.setIndex(index);
        questionController.setQuestionList(questionList);
        Question question = questionList.get(index);
        Stage questionStage = new Stage();
        questionStage.setTitle("Tạo bài thi");
        Scene questionScene = new Scene(questionRoot);
        TextField TextA = (TextField) questionScene.lookup("#TextA");
        TextField TextB = (TextField) questionScene.lookup("#TextB");
        TextField TextC = (TextField) questionScene.lookup("#TextC");
        TextField TextD = (TextField) questionScene.lookup("#TextD");
        TextField TextName = (TextField) questionScene.lookup("#TextName");
        CheckBox BoxA = (CheckBox ) questionScene.lookup("#BoxA");
        CheckBox BoxC = (CheckBox ) questionScene.lookup("#BoxB");
        CheckBox BoxB = (CheckBox ) questionScene.lookup("#BoxC");
        CheckBox BoxD = (CheckBox ) questionScene.lookup("#BoxD");
        System.out.println(question.toJSON().toString());
        TextName.setText(question.getQuestion());
        String A = question.getAnswer().get(0);
        TextA.setText(A);
        if(question.getQuestionCorrect().contains(A)) BoxA.setSelected(true);
        String B = question.getAnswer().get(1);
        TextB.setText(B);
        if(question.getQuestionCorrect().contains(B)) BoxB.setSelected(true);
        String C = question.getAnswer().get(2);
        TextC.setText(C);
        if(question.getQuestionCorrect().contains(A)) BoxC.setSelected(true);
        String D = question.getAnswer().get(3);
        TextD.setText(D);
        if(question.getQuestionCorrect().contains(D)) BoxD.setSelected(true);
        questionStage.setScene(questionScene);
        questionStage.show();

    }
}
