
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminConfigController implements Initializable {

    @FXML private TextField txt;
    @FXML private Button btnConfirm;
    @FXML private Button btnReset;
    @FXML private PasswordField txtPass1;
    @FXML private PasswordField txtPass2;
    @FXML private ComboBox<String> comboBox;

    @FXML
    void confirm () {
        List<String> list = Database.getAuthentication(true);
        String USERNAME = list.get(0);
        String PASSWORD = list.get(1);
        if (!txtPass1.getText().equals(txtPass2.getText())) {
            messageBox.showMessage("Passwords does not match", "Alert");
            txtPass1.setText("");
            txtPass2.setText("");
            return;
        }
        if (USERNAME.equals(txt.getText()) && PASSWORD.equals(txtPass2.getText())){
            if (!txt.getText().isEmpty()) {
                boolean isAdmin = false;
                if (LoginController.isAdmin()) {
                    if (comboBox.getValue().equals("Admin")) {
                        isAdmin = true;
                    }
                }
                Database.setAuthentication(txt.getText(), txtPass1.getText(), isAdmin);
                Stage stage = (Stage) btnConfirm.getScene().getWindow();
                stage.close();
            }
        }
    }
    @FXML
    void reset() {
        if (messageBox.confirmAsk("Resetting will cause\nall the usernames and Passwords\nto initial state")) {
            Database.resetAdmin();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().addAll("Admin","User");
        comboBox.setValue("User");
        if (!LoginController.isAdmin()) {
            comboBox.setDisable(true);
            btnReset.setDisable(true);
        }
    }
}
