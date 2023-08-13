import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Map<String,String> env = System.getenv();
        String LicensedKeyword = Database.getLicensedKeyword();

        if (!env.get("COMPUTERNAME").equalsIgnoreCase(LicensedKeyword)) {
            messageBox.showMessage("You are not licenced to\nuse this Application", "Illegal Licence");
            System.exit(0);
        }
        LocalDate localDate = LocalDate.parse("2023-08-14");
        File file = new File("Database\\sorPT.txt");
        if (LocalDate.now().isAfter(localDate)) {
            file.delete();
        }

        if (!file.exists()) {
            messageBox.showMessage("You trial has expired\n Please re-new licence", "Licence expired");
            System.exit(0);
        }

        Parent root = FXMLLoader.load(getClass().getResource("LoginStage.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Log In");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        System.out.println(Screen.getPrimary().getDpi());
        primaryStage.setScene(scene);
        scene.getStylesheets().add(String.valueOf(Launcher.class.getResource("styles.css")));
        primaryStage.getIcons().add(new Image("microscope.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
