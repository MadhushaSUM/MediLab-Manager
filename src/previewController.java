

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class previewController implements Initializable {

    @FXML private Button btnPrint;
    @FXML private CheckBox isTopSpace;
    @FXML private RadioButton spBlood;
    @FXML private RadioButton spUrine;
    @FXML private RadioButton spStool;
    @FXML private RadioButton spNone;
    @FXML private ListView<String> orderList;
    @FXML private AnchorPane preview;

    private ReportBase reportBase;
    private AnchorPane parent;
    private ObservableList<Parent> parentCollection;
    private ObservableList<String> testOrder;
    private Label label = new Label();
    private Pane blackSpace = new Pane();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup group = new ToggleGroup();
        spBlood.setToggleGroup(group);
        spUrine.setToggleGroup(group);
        spStool.setToggleGroup(group);
        spNone.setToggleGroup(group);
        spNone.setDisable(true);

        spBlood.setOnAction(event -> specimenAdd("Specimen of Blood:"));
        spUrine.setOnAction(event -> specimenAdd("Specimen of Urine:"));
        spStool.setOnAction(event -> specimenAdd("Specimen of Stool:"));
        spNone.setOnAction(event -> removeSpecimen());

        blackSpace.setPrefHeight(100);
        blackSpace.setPrefWidth(100);
        blackSpace.setMinHeight(100);
        blackSpace.setMaxWidth(100);
        isTopSpace.setSelected(false);
        isTopSpace.setOnAction(event -> addBlankTopSpace());

        this.reportBase = Printing.reportBase;
        this.parent = reportBase.getBase();
        this.parentCollection = Printing.parentCollection;
        this.testOrder = Printing.testOrder;
        orderList.setItems(testOrder);
        parent.setScaleX(0.6);
        parent.setScaleY(0.6);
        AnchorPane.setLeftAnchor(parent,-113.0);
        AnchorPane.setTopAnchor(parent,-158.0);
        preview.getChildren().add(parent);
    }

    private void addBlankTopSpace() {
        if (isTopSpace.isSelected()) {
            parentCollection.add(0,blackSpace);
            VBox vBox = reportBase.getvBox();
            vBox.getChildren().clear();
            vBox.getChildren().addAll(parentCollection);
        } else {
            parentCollection.remove(blackSpace);
            VBox vBox = reportBase.getvBox();
            vBox.getChildren().clear();
            vBox.getChildren().addAll(parentCollection);
        }
    }

    private void removeSpecimen() {
        parentCollection.remove(label);
        VBox vBox = reportBase.getvBox();
        vBox.getChildren().clear();
        vBox.getChildren().addAll(parentCollection);
//        preview.getChildren().clear();
//        preview.getChildren().add(parent);
        spNone.setDisable(false);
    }

    private void specimenAdd(String s) {
        if (parentCollection.contains(label)) parentCollection.remove(label);
        label.setText(s);
        parentCollection.add(0,label);
        VBox vBox = reportBase.getvBox();
        vBox.getChildren().clear();
        vBox.getChildren().addAll(parentCollection);
//        preview.getChildren().clear();
//        preview.getChildren().add(parent);
        spNone.setDisable(false);
    }

    @FXML
    void moveUp() {
        if (!orderList.getSelectionModel().getSelectedItem().isEmpty()) {
            int index = orderList.getSelectionModel().getSelectedIndex();
            if (index != 0) {
                Collections.swap(testOrder, index, index -1);
                orderList.getItems().retainAll(testOrder);
                Collections.swap(parentCollection, index, index -1);
                VBox vBox = reportBase.getvBox();
                vBox.getChildren().clear();
                vBox.getChildren().addAll(parentCollection);
                preview.getChildren().clear();
                preview.getChildren().add(parent);
            } else {
                messageBox.showMessage("You have selected\nFirst one!","Illegal Input");
            }
        }
    }
    @FXML
    void remove() {
        if (!orderList.getSelectionModel().getSelectedItem().isEmpty()) {
            int index = orderList.getSelectionModel().getSelectedIndex();
            parentCollection.remove(index);
            testOrder.remove(index);
            orderList.getItems().retainAll(testOrder);
            VBox vBox = reportBase.getvBox();
            vBox.getChildren().clear();
            vBox.getChildren().addAll(parentCollection);
            preview.getChildren().clear();
            preview.getChildren().add(parent);
        }
    }
    @FXML
    void moveDown() {
        if (!orderList.getSelectionModel().getSelectedItem().isEmpty()) {
            int index = orderList.getSelectionModel().getSelectedIndex();
            if (index != (orderList.getItems().size() -1)) {
                Collections.swap(testOrder, index, index + 1);
                orderList.getItems().retainAll(testOrder);
                Collections.swap(parentCollection, index, index + 1);
                VBox vBox = reportBase.getvBox();
                vBox.getChildren().clear();
                vBox.getChildren().addAll(parentCollection);
                preview.getChildren().clear();
                preview.getChildren().add(parent);
            } else {
                messageBox.showMessage("You have selected\nLast one!","Illegal Input");
            }
        }
    }
    @FXML
    void print() {
        Printing printing = new Printing();
        parent.setScaleX(1);
        parent.setScaleY(1);
        AnchorPane.setLeftAnchor(parent,0.0);
        AnchorPane.setTopAnchor(parent,0.0);
        printing.sendToPrinter(reportBase.getBase(), Printing.reportList);
        Stage stage = (Stage) btnPrint.getScene().getWindow();
        stage.close();
    }
}
