

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManagePatientsController implements Initializable {

    @FXML private TableView<patient> table;
    @FXML private TableColumn<patient, String> colName;
    @FXML private TableColumn<patient, String> colGender;
    @FXML private TableColumn<patient, String > colDOB;
    @FXML private TableColumn<patient, String > colAge;
    @FXML private TableColumn<patient, String> colConNumber;
    @FXML private TextField txtName;
    @FXML private TextField txtGender;
    @FXML private TextField txtAge;
    @FXML private TextField txtConNumber;
    @FXML private TextField searchName;
    @FXML private ChoiceBox<String> choiceAge;
    @FXML private CheckBox isPreciseAge;
    @FXML private DatePicker datePreciseAge;

    private ObservableList<patient> patientArrayList;
    private ObservableList<patient> sortedPatientList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceAge.getItems().addAll("Yrs.","Months.");
        choiceAge.setValue("Yrs.");

        patientArrayList = Database.getPatientList();
        table.setItems(patientArrayList);
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("stringDOB"));
        colConNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        table.setEditable(true);
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit(Database::changePatientName);
        colDOB.setCellFactory(TextFieldTableCell.forTableColumn());
        colDOB.setOnEditCommit(Database::changePatientDOB);

        searchName.textProperty().addListener(observable -> search());

        datePreciseAge.setDisable(true);
    }

    void search() {
        if (searchName.getText().isEmpty()) {
            table.setItems(patientArrayList);
        } else {
            sortedPatientList = patientArrayList.filtered(patient -> patient.getName().toLowerCase().contains(searchName.getText().toLowerCase()));
            table.setItems(sortedPatientList);
        }
    }

    @FXML
    void addPatient() {
        if (!txtName.getText().isEmpty()) {
            if (!isPreciseAge.isSelected()) {
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
            try {
                LocalDate DOB;
                if (!isPreciseAge.isSelected()) {
                    //For the patients who use default age structure
                    int age = Integer.parseInt(txtAge.getText());
                    if (choiceAge.getValue().equals("Yrs.")) {
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
                Database.addPatient(txtName.getText(),DOB,txtGender.getText(),conNumber, isPreciseAge.isSelected());
            } catch (java.lang.NumberFormatException e) {
                System.err.println(e);
                messageBox.showMessage("Invalid Age","Invalid Input");
            }

            patientArrayList = Database.getPatientList();
            table.setItems(patientArrayList);
        }
    }
    @FXML
    void deletePatient() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            if (!messageBox.confirmAsk("Are you sure?")) {
                return;
            }
            Database.deletePatient(table.getSelectionModel().getSelectedItem());
            patientArrayList = Database.getPatientList();
            table.setItems(patientArrayList);
        }
    }
    @FXML
    void usePreciseAge() {
        if (isPreciseAge.isSelected()) {
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
