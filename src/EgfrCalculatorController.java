

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class EgfrCalculatorController implements Initializable {
    @FXML private TextField txtCreatinineValue;
    @FXML private TextField txtAgeValue;
    @FXML private TextField txtEgfrValue;
    @FXML private CheckBox isFemale;
    @FXML private CheckBox isBlack;

    @FXML
    private void calculate() {
        double creatinineValue = 0;
        double age = 0;
        try {
            creatinineValue = Double.parseDouble(txtCreatinineValue.getText());
        } catch (NumberFormatException e) {
            messageBox.showMessage("Enter valid Creatinine Value","Alert");
            return;
        }
        try {
            age = Double.parseDouble(txtAgeValue.getText());
        } catch (NumberFormatException e) {
            messageBox.showMessage("Enter valid Age Value in Years","Alert");
            return;
        }
        double egfrValue;
        egfrValue = 186d * (Math.pow((creatinineValue) , -1.154d)) * (Math.pow(age , -0.203d)) ;
        if (isFemale.isSelected()) {
            egfrValue = egfrValue * 0.742d;
        }
        if (isBlack.isSelected()) {
            egfrValue = egfrValue * 1.21d;
        }
        egfrValue = Math.round(egfrValue * 100d) / 100d;
        txtEgfrValue.setText(String.valueOf(egfrValue));
    }
    @FXML
    private void reset() {
        txtEgfrValue.setText("");
        txtAgeValue.setText("");
        txtCreatinineValue.setText("");
        isFemale.setSelected(false);
        isBlack.setSelected(false);
        txtCreatinineValue.requestFocus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScaleNodes.setScale(txtCreatinineValue.getParent(), "scalable");
    }
}
