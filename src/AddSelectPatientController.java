
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;


public class AddSelectPatientController implements Initializable {

    private ObservableList<patient> patientList = FXCollections.observableArrayList();
    private ObservableList<patient> filteredPatientList = FXCollections.observableArrayList();
    public static patient selectedPatient = null;
    public static LocalDate date;

    @FXML private ChoiceBox<String> choiceGender;
    @FXML private TextField txtConNumber;
    @FXML private TextField txtAge;
    @FXML private Button btnAddSelect;
    @FXML private ChoiceBox<String> choiceAge;
    @FXML private CheckBox checkPreciseAge;
    @FXML private DatePicker datePreciseAge;
    @FXML private DatePicker regDate;

    private AutoCompleteTextField<patient> patientAutoText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceGender.getItems().addAll("Male","Female");
        choiceGender.setValue("Male");

        SortedSet<patient> entries = new TreeSet<>(Comparator.comparing(patient::toString));

        entries.addAll(Database.getPatientList());
        patientAutoText = new AutoCompleteTextField(entries);

        patientAutoText.getEntryMenu().setOnAction(e ->
        {
            ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event ->
            {
                if (patientAutoText.getLastSelectedObject() != null)
                {
                    patientAutoText.setText(patientAutoText.getLastSelectedObject().getName());
                    txtAge.setText(String.valueOf(patientAutoText.getLastSelectedObject().getAge()));
                    choiceGender.setValue(patientAutoText.getLastSelectedObject().getGender());
                    txtConNumber.setText(String.valueOf(patientAutoText.getLastSelectedObject().getContactNumber()));
                    btnAddSelect.setText("Select Patient");
                    selectedPatient = patientAutoText.getLastSelectedObject();

                    patientAutoText.textProperty().addListener(observable -> deselectPatient());
                }
            });
        });

        AnchorPane pane = (AnchorPane) choiceGender.getParent();
        pane.getChildren().add(patientAutoText);
        AnchorPane.setTopAnchor(patientAutoText,88.0);
        AnchorPane.setLeftAnchor(patientAutoText,150.0);
        AnchorPane.setRightAnchor(patientAutoText,15.0);
        patientAutoText.getStyleClass().add("scalable");

        patientList = Database.getPatientList();
        //patientListView.setItems(patientList);
        //patientListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> loadPatientData(newValue));
        btnAddSelect.setText("Add Patient");
        choiceAge.getItems().addAll("Years","Months");
        choiceAge.setValue("Years");
        datePreciseAge.setDisable(true);
        regDate.setValue(LocalDate.now());

        ScaleNodes.setScale(pane, "scalable");
    }

    private void deselectPatient() {
        selectedPatient = null;
        txtAge.setText("");
        txtConNumber.setText("");
        btnAddSelect.setText("Add Patient");
        patientAutoText.textProperty().removeListener(observable -> deselectPatient());
    }

//    private void loadPatientData(patient newValue) {
//        //comboName.setText(newValue.getName());
//        txtGender.setText(newValue.getGender());
//        txtAge.setText(String.valueOf(newValue.getAge()));
//        txtConNumber.setText(String.valueOf(newValue.getContactNumber()));
//        selectedPatient = newValue;
//        btnAddSelect.setText("Select Patient");
//
//    }

//    @FXML
//    void searchPatients() {
//        String typedName = comboName.getText();
//        btnAddSelect.setText("Add Patient");
//        selectedPatient = null;
//        if (!typedName.isEmpty()) {
//            filteredPatientList.clear();
//            for (patient patient1 : patientList) {
//                if (patient1.getName().contains(typedName)) {
//                    filteredPatientList.add(patient1);
//                }
//            }
//
//            patientListView.setItems(filteredPatientList);
//        } else {
//
//            patientListView.setItems(patientList);
//        }
//    }
    @FXML
    void selectTest() {
        if (!checkPreciseAge.isSelected()) {
            if (txtAge.getText().isEmpty()) {
                messageBox.showMessage("Age must be filled", "Invalid Input");
                return;
            }
        } else {
            if (datePreciseAge.getValue() == null) {
                messageBox.showMessage("Date of birth must be filled", "Invalid Input");
                return;
            }
        }

        if (patientAutoText.getText().isEmpty()){
            messageBox.showMessage("Name must be filled","Invalid Input");
        } else if (choiceGender.getValue().isEmpty()) {
            messageBox.showMessage("Gender must be filled", "Invalid Input");
        }else if (regDate.getValue() == null) {
            messageBox.showMessage("Set a proper Date","Invalid Input");
        } else {
            if (selectedPatient == null) {
                try {
                    LocalDate DOB;
                    if (!checkPreciseAge.isSelected()) {
                        //For the patients who use default age structure
                        int age = Integer.parseInt(txtAge.getText());
                        if (choiceAge.getValue().equals("Years")) {
                            if (age <= 0) {
                                messageBox.showMessage("Invalid Age", "Invalid Input");
                                return;
                            }
                            DOB = LocalDate.now().minusYears(Integer.parseInt(txtAge.getText())).withDayOfYear(1);
                        } else {
                            if (age <= 0 || age >= 12) {
                                messageBox.showMessage("Invalid Age \nSelect 'Years'", "Invalid Input");
                                return;
                            }
                            DOB = LocalDate.now().withDayOfMonth(1).minusMonths(Integer.parseInt(txtAge.getText()));
                        }
                    } else {
                        //For the patients who use Precise age structure
                        DOB = datePreciseAge.getValue();
                    }
                    if (txtConNumber.getText().isEmpty()) {
                        messageBox.showMessage("Patient added without Contact Number","Alert");
                        txtConNumber.setText("0");
                    }
                    int conNumber = Integer.parseInt(txtConNumber.getText());
                    int patientID = Database.addPatient(patientAutoText.getText(),DOB,choiceGender.getValue(),conNumber,checkPreciseAge.isSelected());
                    selectedPatient = new patient(patientID,patientAutoText.getText(),DOB,choiceGender.getValue(),conNumber);
                } catch (java.lang.NumberFormatException e) {
                    System.err.println(e);
                    messageBox.showMessage("Invalid Age","Invalid Input");
                }
            }
            date = regDate.getValue();
            if (selectedPatient != null) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("TestStage.fxml"));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Add Tests");
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(String.valueOf(Launcher.class.getResource("styles.css")));
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.getIcons().add(new Image("microscope.png"));
                    stage.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        selectedPatient = null;
    }
    @FXML
    void usePreciseAge() {
        if (checkPreciseAge.isSelected()) {
            txtAge.setDisable(true);
            choiceAge.setDisable(true);
            datePreciseAge.setDisable(false);
        } else {
            txtAge.setDisable(false);
            choiceAge.setDisable(false);
            datePreciseAge.setDisable(true);
        }
    }
}
