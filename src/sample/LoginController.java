package sample;

import api.APIClient;
import api.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    @FXML
    TextField NameField;
    @FXML
    PasswordField PassField;
    @FXML
    Button LoginButton;
    public void Login(ActionEvent event) throws IOException {
        if(NameField.getText().isEmpty()||PassField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Bạn có chỗ chưa điền");
            alert.showAndWait();
        }
        else{
        LoginButton.setText("Đang kết nối");
        HttpResponse response = APIClient.getInstance().login(NameField.getText(),PassField.getText());
            if(response.getStatusLine().getStatusCode()!=200){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Có lỗi xảy ra");
                alert.showAndWait();
                LoginButton.setText("Đăng nhập");
            }
            else{
                JSONObject accountResponse = new JSONObject(EntityUtils.toString(response.getEntity()));
                Account account = Account.getInstance();
                JSONArray roleArray =  accountResponse.getJSONArray("roles");
                List<String> roleList= new ArrayList<>();
                for(int i = 0;i<roleArray.length();i++){
                    roleList.add(roleArray.getString(i));
                }
                if(roleList.contains("ROLE_ADMIN")||roleList.contains("ROLE_MODERATOR")){
                    String accessToken = accountResponse.getString("accessToken");
                    String Id = accountResponse.getString("id");
                    account.setAccessToken(accessToken);
                    account.setId(Id);
                    Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                    AnchorPane root = (AnchorPane) loader.load();
                    currentStage.setScene(new Scene(root));
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Đây không phải tài khoản hợp lệ");
                    alert.showAndWait();
                    LoginButton.setText("Đăng nhập");
                }
            }

        }
    }

}
