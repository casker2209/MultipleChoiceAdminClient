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
import object.Bank;
import object.Question;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BankController implements Initializable {
    List<Bank> bankList;
    List<String> bankNameList;
    @FXML
    private Button Insert;
    @FXML
    private Button Delete;
    @FXML
    private Button Edit;
    @FXML
    private ListView BankList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getBank() throws IOException {
        bankList = new ArrayList<>();
        bankNameList = new ArrayList<>();
        bankList = APIClient.getInstance().getBank();
        for(Bank bank : bankList){
            bankNameList.add(bank.getName());
        }
    }

    public void updateList() throws IOException {
        getBank();
        BankList.setItems(FXCollections.observableArrayList(bankNameList));

    }
    public void deleteBank() throws IOException {
        int index = BankList.getSelectionModel().getSelectedIndex();
        Bank bank = bankList.get(index);
        APIClient.getInstance().deleteBank(bank);
        updateList();
    }
    public void refresh(ActionEvent event) throws IOException {
        updateList();
    }
    public void addBank(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bankform.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        currentStage.setScene(new Scene(root));
    }
    public void editBank(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bankform.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        BankFormController bankFormController = loader.getController();
        Bank bank = bankList.get(BankList.getSelectionModel().getSelectedIndex());
        bankFormController.setBank(bank);
        Scene scene = new Scene(root);
        TextField bankName = (TextField) scene.lookup("#NameField");
        bankName.setText(bank.getName());
        TextField bankDesc = (TextField) scene.lookup("#DescriptionField");
        bankDesc.setText(bank.getDescription());
        bankFormController.setQuestionList(bank.getQuestionList());
        ObservableList<String> observableList = FXCollections.observableArrayList(new ArrayList<String>());
        for(Question question: bank.getQuestionList()){
            observableList.add(question.getQuestion());
        }
        ListView bankListView = (ListView) scene.lookup("#questionListView");
        bankListView.setItems(observableList);
        currentStage.setScene(scene);
    }
    public void goBack(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        currentStage.setScene(new Scene(root));
    }
}
