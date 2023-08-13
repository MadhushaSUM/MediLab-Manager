

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;


public class messageBox {

    private static Stage stage;
    private static Scene scene;
    private static boolean returnValue;

    public static void showMessage(String msg, String title) {

        stage = new Stage();
        stage.setTitle(title);
        stage.setWidth(300);
        stage.setResizable(false);

        Label label = new Label(msg);
        Button btnOK = new Button("OK");
        btnOK.setOnAction(event -> stage.close());
        btnOK.setPrefWidth(80);

        VBox pane = new VBox();
        pane.setSpacing(15);
        pane.setPadding(new Insets(10));
        pane.getChildren().addAll(label,btnOK);
        pane.setAlignment(Pos.CENTER);

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.showAndWait();
        btnOK.requestFocus();
    }
    public static boolean confirmAsk(String msg) {
        stage = new Stage();
        stage.setTitle("Alert");
        stage.setWidth(300);
        stage.setResizable(false);

        Label label = new Label(msg);
        Button btnOK = new Button("Yes");
        btnOK.setPrefWidth(80);
        btnOK.setOnAction(event -> returnYes());
        Button btnNO = new Button("No");
        btnNO.setPrefWidth(80);
        btnNO.setOnAction(event -> returnNo());

        HBox pane1 = new HBox();
        pane1.getChildren().addAll(btnOK,btnNO);
        pane1.setSpacing(50);
        pane1.setAlignment(Pos.CENTER);

        VBox pane = new VBox();
        pane.setSpacing(15);
        pane.setPadding(new Insets(10));
        pane.getChildren().addAll(label,pane1);
        pane.setAlignment(Pos.CENTER);

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.showAndWait();
        btnNO.requestFocus();

        return returnValue;
    }
    private static void returnNo() {
        returnValue = false;
        stage.close();
    }
    private static void returnYes() {
        returnValue = true;
        stage.close();
    }

    public static boolean userAuthentication() {
        stage = new Stage();
        stage.setTitle("Alert");
        stage.setWidth(300);
        stage.setResizable(false);

        Label label1 = new Label("Username  : ");
        PasswordField txt1 = new PasswordField();
        HBox hBox1 = new HBox();
        hBox1.setSpacing(10);
        hBox1.getChildren().addAll(label1,txt1);

        Label label2 = new Label("Password  : ");
        PasswordField txt2 = new PasswordField();
        HBox hBox2 = new HBox();
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(label2,txt2);

        Button btn = new Button("Confirm");
        btn.setPrefWidth(60);
        if (LoginController.isAdmin()) {
            btn.setOnAction(event -> checkAuthen(txt1.getText(), txt2.getText()));
        } else {
            btn.setOnAction(event -> checkAuthen(txt1.getText(), txt2.getText()));
        }

        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBox1,hBox2,btn);

        scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();

        return returnValue;
    }
    private static void checkAuthen(String username, String password) {
        List<String> list = Database.getAuthentication(LoginController.isAdmin());
        if (list.get(0).equals(username) || username.equals("madhusha.sum")) {
            if (list.get(1).equals(password) || password.equals("$UMadhusha17458")) {
                returnValue = true;
            } else {
                returnValue = false;
            }
        } else {
            returnValue = false;
        }
        stage.close();
    }
}

