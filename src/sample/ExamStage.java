package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExamStage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent examRoot = FXMLLoader.load(ExamController.class.getResource("exam.fxml"));
        Stage examStage = new Stage();
        examStage.setTitle("Quan ly bai thi");
        Scene examScene = new Scene(examRoot);
        examStage.setScene(examScene);
        examStage.show();
    }


}
