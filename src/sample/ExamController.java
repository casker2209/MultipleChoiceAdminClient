package sample;

import api.APIClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import object.Exam;
import object.Question;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExamController implements Initializable {
    List<Exam> examList;
    List<String> examNameList;
    @FXML
    private Button Insert;
    @FXML
    private Button Delete;
    @FXML
    private Button Edit;
    @FXML
    private ListView ExamList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getExam() throws IOException {
        examList = new ArrayList<>();
        examNameList = new ArrayList<>();
        examList = APIClient.getInstance().getExam();
        for(Exam exam : examList){
            examNameList.add(exam.getName());
        }
    }
    public void updateList() throws IOException {
        getExam();
        ExamList.setItems(FXCollections.observableArrayList(examNameList));

    }
    public void addExam(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("examform.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        currentStage.setScene(new Scene(root));
    }
    public void deleteExam(ActionEvent event) throws IOException {
        int index = ExamList.getSelectionModel().getSelectedIndex();
        Exam exam = examList.get(index);
        APIClient.getInstance().deleteExam(exam);
        updateList();
    }
    public void editExam(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("examform.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        ExamFormController examFormController = loader.getController();
        Exam exam = examList.get(ExamList.getSelectionModel().getSelectedIndex());
        examFormController.setExam(exam);
        Scene scene = new Scene(root);
        TextField examName = (TextField) scene.lookup("#NameField");
        examName.setText(exam.getName());
        TextField examDesc = (TextField) scene.lookup("#DescriptionField");
        examDesc.setText(exam.getDescription());
        examFormController.setQuestionList(exam.getQuestionList());
        ObservableList<String> observableList = FXCollections.observableArrayList(new ArrayList<String>());
        for(Question question: exam.getQuestionList()){
            observableList.add(question.getQuestion());
        }
        ListView examListView = (ListView) scene.lookup("#questionListView");
        examListView.setItems(observableList);
        currentStage.setScene(scene);
    }
    public void refresh(ActionEvent event) throws IOException {
        updateList();
    }
    public void goBack(ActionEvent event) throws IOException{
        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        currentStage.setScene(new Scene(root));
    }
}
