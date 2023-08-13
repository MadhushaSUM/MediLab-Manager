

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportBaseConfigController implements Initializable {

    private AnchorPane base;
    private Label label;
    @FXML private AnchorPane root;
    @FXML private TextField posTop;
    @FXML private TextField posLeft;
    @FXML private TextField posRight;
    @FXML private TextField posBottom;
    @FXML private Slider sliderHori;
    @FXML private Slider sliderVer;
    @FXML private CheckBox checkBold;
    @FXML private CheckBox checkItalic;
    @FXML private CheckBox checkUnderline;
    @FXML private CheckBox checkVisibility;
    @FXML private TextField labelText;
    @FXML private ChoiceBox<String> selectNode;
    @FXML private ChoiceBox<Pos> selectAlignment;
    @FXML private TextField txtFontName;
    @FXML private TextField txtFontSize;

    private ReportBase reportBase = new ReportBase();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        base = reportBase.getBase();
        base.setStyle("-fx-border-color : #ff0000; ");

        VBox vBox = reportBase.getvBox();
        vBox.setDisable(true);

        base.setScaleX(0.8);
        base.setScaleY(0.8);

        root.getChildren().add(base);
        AnchorPane.setTopAnchor(base,-75.0);
        AnchorPane.setLeftAnchor(base,-50.0);

        selectNode.getItems().addAll("headerLabel1","headerLabel2","headerLabel3","headerLabel4","headerLabel5","headerLabel6","headerLabel7","headerLabel8","headerLabel9",
                "headerDotLabel1","headerDotLabel2","headerDotLabel3","headerDotLabel4","headerDotLabel5","headerDotLabel6","headerDotLabel7", "footerLabel1","footerLabel2", "footerLabel3",
                "footerLabel4","mltLabel1","mltLabel2","addressLabel1","addressLabel2","nameLabel","ageLabel","dateLabel","timeLabel","reqDocLabel","refLabel","specimenLabel","timeLabel");

        selectAlignment.getItems().addAll(Pos.TOP_LEFT,Pos.CENTER);

        label = reportBase.getNode("headerLabel1");
        label.setOnMouseClicked(event -> labelClicked("headerLabel1"));
        label = reportBase.getNode("headerLabel2");
        label.setOnMouseClicked(event -> labelClicked("headerLabel2"));
        label = reportBase.getNode("headerLabel3");
        label.setOnMouseClicked(event -> labelClicked("headerLabel3"));
        label = reportBase.getNode("headerLabel4");
        label.setOnMouseClicked(event -> labelClicked("headerLabel4"));
        label = reportBase.getNode("headerLabel5");
        label.setOnMouseClicked(event -> labelClicked("headerLabel5"));
        label = reportBase.getNode("headerLabel6");
        label.setOnMouseClicked(event -> labelClicked("headerLabel6"));
        label = reportBase.getNode("headerLabel7");
        label.setOnMouseClicked(event -> labelClicked("headerLabel7"));
        label = reportBase.getNode("headerLabel8");
        label.setOnMouseClicked(event -> labelClicked("headerLabel8"));
        label = reportBase.getNode("headerLabel9");
        label.setOnMouseClicked(event -> labelClicked("headerLabel9"));
        label = reportBase.getNode("headerDotLabel1");
        label.setOnMouseClicked(event -> labelClicked("headerDotLabel1"));
        label = reportBase.getNode("headerDotLabel2");
        label.setOnMouseClicked(event -> labelClicked("headerDotLabel2"));
        label = reportBase.getNode("headerDotLabel3");
        label.setOnMouseClicked(event -> labelClicked("headerDotLabel3"));
        label = reportBase.getNode("headerDotLabel4");
        label.setOnMouseClicked(event -> labelClicked("headerDotLabel4"));
        label = reportBase.getNode("headerDotLabel5");
        label.setOnMouseClicked(event -> labelClicked("headerDotLabel5"));
        label = reportBase.getNode("headerDotLabel6");
        label.setOnMouseClicked(event -> labelClicked("headerDotLabel6"));
        label = reportBase.getNode("headerDotLabel7");
        label.setOnMouseClicked(event -> labelClicked("headerDotLabel7"));
        label = reportBase.getNode("footerLabel1");
        label.setOnMouseClicked(event -> labelClicked("footerLabel1"));
        label = reportBase.getNode("footerLabel2");
        label.setOnMouseClicked(event -> labelClicked("footerLabel2"));
        label = reportBase.getNode("footerLabel3");
        label.setOnMouseClicked(event -> labelClicked("footerLabel3"));
        label = reportBase.getNode("footerLabel4");
        label.setOnMouseClicked(event -> labelClicked("footerLabel4"));
        label = reportBase.getNode("mltLabel1");
        label.setOnMouseClicked(event -> labelClicked("mltLabel1"));
        label = reportBase.getNode("mltLabel2");
        label.setOnMouseClicked(event -> labelClicked("mltLabel2"));
        label = reportBase.getNode("addressLabel1");
        label.setOnMouseClicked(event -> labelClicked("addressLabel1"));
        label = reportBase.getNode("addressLabel2");
        label.setOnMouseClicked(event -> labelClicked("addressLabel2"));
        label = reportBase.getNode("nameLabel");
        label.setOnMouseClicked(event -> labelClicked("nameLabel"));
        label = reportBase.getNode("ageLabel");
        label.setOnMouseClicked(event -> labelClicked("ageLabel"));
        label = reportBase.getNode("dateLabel");
        label.setOnMouseClicked(event -> labelClicked("dateLabel"));
        label = reportBase.getNode("timeLabel");
        label.setOnMouseClicked(event -> labelClicked("timeLabel"));
        label = reportBase.getNode("reqDocLabel");
        label.setOnMouseClicked(event -> labelClicked("reqDocLabel"));
        label = reportBase.getNode("refLabel");
        label.setOnMouseClicked(event -> labelClicked("refLabel"));
        label = reportBase.getNode("specimenLabel");
        label.setOnMouseClicked(event -> labelClicked("specimenLabel"));
        label = reportBase.getNode("timeLabel");
        label.setOnMouseClicked(event -> labelClicked("timeLabel"));
        label = null;

        selectNode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> labelClicked(newValue));

        sliderHori.setDisable(true);
        sliderHori.valueProperty().addListener(observable -> setHorizontalPosotion());

        sliderVer.setDisable(true);
        sliderVer.valueProperty().addListener(observable -> setVerticalPosotion());

        checkBold.setDisable(true);
        checkBold.setOnAction(event -> setBold());
        checkItalic.setDisable(true);
        checkItalic.setOnAction(event -> setItalic());
        checkUnderline.setDisable(true);
        checkUnderline.setOnAction(event -> label.setUnderline(checkUnderline.isSelected()));

        checkVisibility.setDisable(true);
        checkVisibility.setOnAction(event -> label.setVisible(checkVisibility.isSelected()));

        selectAlignment.setDisable(true);
        selectAlignment.setOnAction(event -> label.setAlignment(selectAlignment.getValue()));

        txtFontName.setDisable(true);
        txtFontSize.setDisable(true);

        posLeft.setDisable(true);
        posTop.setDisable(true);
        posRight.setDisable(true);
        posBottom.setDisable(true);

        labelText.setDisable(true);
        labelText.setOnKeyReleased(event -> label.setText(labelText.getText()));
    }

    private void labelClicked(String labelID) {
        selectNode.setValue(labelID);
        label = reportBase.getNode(labelID);
        posLeft.setText(AnchorPane.getLeftAnchor(label).toString());
        posTop.setText(AnchorPane.getTopAnchor(label).toString());
        if (AnchorPane.getRightAnchor(label) != null) {
            posRight.setText(AnchorPane.getRightAnchor(label).toString());
        }
        if (AnchorPane.getBottomAnchor(label) != null) {
            posBottom.setText(AnchorPane.getBottomAnchor(label).toString());
        }
        label.setStyle(label.getStyle() + "-fx-background-color : #00ffff;");

        checkBold.setDisable(false);
        checkBold.setSelected(label.getStyle().contains("bold"));
        checkItalic.setDisable(false);
        checkItalic.setSelected(label.getStyle().contains("Italic"));
        checkUnderline.setDisable(false);
        checkUnderline.setSelected(label.isUnderline());

        checkVisibility.setDisable(false);
        checkVisibility.setSelected(label.isVisible());

        labelText.setDisable(false);
        labelText.setText(label.getText());

        selectAlignment.setDisable(false);
        selectAlignment.setValue(label.getAlignment());

        txtFontName.setDisable(false);
        txtFontName.setText(label.getFont().getName());
        txtFontSize.setDisable(false);
        txtFontSize.setText(String.valueOf(label.getFont().getSize()));

        posLeft.setDisable(false);
        posTop.setDisable(false);
        posRight.setDisable(false);
        posBottom.setDisable(false);

        sliderHori.setValue(AnchorPane.getLeftAnchor(label));
        sliderHori.setDisable(false);

        sliderVer.setValue( 820 - AnchorPane.getTopAnchor(label));
        sliderVer.setDisable(false);
    }

    @FXML
    void applyPosition() {
        if (label == null) {
            messageBox.showMessage("Select Label First!","Alert");
            return;
        }
        if (!posLeft.getText().isEmpty()) {
            AnchorPane.setLeftAnchor(label, Double.parseDouble(posLeft.getText()));
        }
        if (!posTop.getText().isEmpty()) {
            AnchorPane.setTopAnchor(label, Double.parseDouble(posTop.getText()));
        }
        if (!posRight.getText().isEmpty()) {
            AnchorPane.setRightAnchor(label, Double.parseDouble(posRight.getText()));
        }
        if (!posBottom.getText().isEmpty()) {
            AnchorPane.setBottomAnchor(label, Double.parseDouble(posBottom.getText()));
        }
        sliderHori.setValue(AnchorPane.getLeftAnchor(label));
        sliderVer.setValue( 820 - AnchorPane.getTopAnchor(label));
    }
    @FXML
    void applyFont() {
        if (label == null) {
            messageBox.showMessage("Select Label First!","Alert");
            return;
        }
        if (!txtFontName.getText().isEmpty() && !txtFontSize.getText().isEmpty()) {
            label.setStyle(label.getStyle() + " -fx-font-family : "+ txtFontName.getText() + " ; -fx-font-size : "+ Double.parseDouble(txtFontSize.getText()) +" ;");
        }
    }
    private void setHorizontalPosotion() {
        AnchorPane.setLeftAnchor(label, sliderHori.getValue());
        posLeft.setText(String.valueOf(sliderHori.getValue()));
    }
    private void setVerticalPosotion() {
        AnchorPane.setTopAnchor(label, 820 - sliderVer.getValue());
        posTop.setText(String.valueOf(820 - sliderVer.getValue()));
    }
    private void setBold() {
        if (checkBold.isSelected()) {
            label.setStyle(label.getStyle() + " -fx-font-weight : bold; ");
        } else {
            label.setStyle(label.getStyle().replace(" -fx-font-weight : bold; ",""));
        }
    }
    private void setItalic() {
        if (checkItalic.isSelected()) {
            label.setStyle(label.getStyle() + " -fx-font-style : Italic; ");
        } else {
            label.setStyle(label.getStyle().replace(" -fx-font-style : Italic; ",""));
        }
    }
    @FXML
    void save() {
        if (label == null) {
            messageBox.showMessage("Select Label First!","Alert");
            return;
        }
        if (posLeft.getText().isEmpty() || posTop.getText().isEmpty() || labelText.getText().isEmpty() || txtFontName.getText().isEmpty() || txtFontSize.getText().isEmpty()) {
            messageBox.showMessage("Fill essentials First!","Alert");
            return;
        }
        try {
            PrintStream printFile = new PrintStream("Configure\\"+label.getId()+".txt");
            printFile.println(posLeft.getText());
            printFile.println(posTop.getText());
            if (posRight.getText().isEmpty()) {
                printFile.println("null");
            } else {
                printFile.println(posRight.getText());
            }
            if (posBottom.getText().isEmpty()) {
                printFile.println("null");
            } else {
                printFile.println(posBottom.getText());
            }
            printFile.println(checkUnderline.isSelected());
            printFile.println(checkBold.isSelected());
            printFile.println(checkItalic.isSelected());
            printFile.println(txtFontName.getText());
            printFile.println(txtFontSize.getText());
            printFile.println(selectAlignment.getValue());
            if (!labelText.getText().isEmpty()) {
                printFile.println(labelText.getText());
            }
            printFile.println(checkVisibility.isSelected());
            printFile.close();
            messageBox.showMessage("Label Layout saved","Success");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void loadDefaults() {
        if (messageBox.confirmAsk("Proceeding will loose current layout\nand save Default Layout\nAre you sure?")) {
            reportBase.loadDefaults();
            reportBase.saveDefaults();
        }
    }
    @FXML
    void reset() {
        reportBase.initialize();
    }
}
