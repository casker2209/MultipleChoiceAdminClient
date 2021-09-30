package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private Button Exam;
    @FXML
    private Button Bank;
    @FXML
    public void goExam(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("exam.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        currentStage.setScene(new Scene(root));
    }
    @FXML
    public void goBank(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bank.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        currentStage.setScene(new Scene(root));

    }
}
