

import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML private TextField txtUserName;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Button btnExit;

    private static boolean isAdmin = false;

    @FXML
    void login() {
        SequentialTransition sqTra = ButtonAnimation.transitionByFading(btnLogin);
        sqTra.play();
        try {
            if (txtUserName.getText().isEmpty()) return;
            if (txtPassword.getText().isEmpty()) return;
            int authorisation = Database.getAuthentication(txtUserName.getText(),txtPassword.getText());
            if ( authorisation == 1) {
                messageBox.showMessage("Logged in as Admin","Authorised");
                isAdmin = true;
            } else if (authorisation == 0) {
                messageBox.showMessage("Logged in as User","Authorised");
                isAdmin = false;
            } else {
                messageBox.showMessage("Username and Password mismatch","Authorisation Failed");
                return;
            }
            Stage primaryStage = new Stage();
            AnchorPane root = FXMLLoader.load(getClass().getResource("mainStage.fxml"));

            primaryStage.setMinHeight(739d);
            primaryStage.setMinWidth(1050d);
            Line line01 = (Line) root.lookup("#line01");
            Line line02 = (Line) root.lookup("#line02");
            root.layoutBoundsProperty().addListener(observable -> {
                line01.setEndX(root.getWidth() - 10d);
                line02.setEndY(root.getHeight() - 120d);
            });
            primaryStage.setTitle("Medi Lab Management System");
            primaryStage.setResizable(true);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            scene.getStylesheets().add(String.valueOf(Launcher.class.getResource("styles.css")));
            primaryStage.getIcons().add(new Image("microscope.png"));
            primaryStage.show();
            primaryStage.setMaximized(true);

            txtPassword.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void exitApp() {
        SequentialTransition sqTra = ButtonAnimation.transitionByFading(btnExit);
        sqTra.setOnFinished(event -> Platform.exit());
        sqTra.play();
    }

    void keyPressed(KeyEvent event) {
        System.out.println("A");
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    public static boolean isAdmin() { return isAdmin; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtPassword.setOnKeyPressed(this::keyPressed);

        //login();
    }
}
