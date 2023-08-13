

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

public class ReceiptManagerController implements Initializable {

    @FXML private DatePicker datePicker;
    @FXML private TextArea patientDetails;
    @FXML private TableView<TestForRecipt> tableView;
    @FXML private TableColumn<TestForRecipt,String> colTest;
    @FXML private TableColumn<TestForRecipt,Boolean> colIsSelected;

    private AutoCompleteTextField<patient> patientAutoText;
    private patient selectedPatient;
    private int advance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
                    selectedPatient = patientAutoText.getLastSelectedObject();

                    patientAutoText.textProperty().addListener(observable -> deselectPatient());
                }
            });
        });

        AnchorPane pane = (AnchorPane) datePicker.getParent();
        pane.getChildren().add(patientAutoText);
        AnchorPane.setTopAnchor(patientAutoText,10.0);
        AnchorPane.setLeftAnchor(patientAutoText,14.0);
        AnchorPane.setRightAnchor(patientAutoText,260.0);


        colTest.setCellValueFactory(new PropertyValueFactory<>("Test"));
        colIsSelected.setCellValueFactory(param -> param.getValue().isSelected());
        colIsSelected.setCellFactory(CheckBoxTableCell.forTableColumn(colIsSelected));
        colIsSelected.setEditable(true);
        tableView.setEditable(true);
    }
    private void deselectPatient() {
        selectedPatient = null;
        patientAutoText.textProperty().removeListener(observable -> deselectPatient());
    }

    @FXML
    void search() {
        if (selectedPatient == null) {
            messageBox.showMessage("Select a Patient","Alert");
            return;
        }
        if (datePicker.getValue() == null) {
            messageBox.showMessage("Select a Date","Alert");
            return;
        }
        advance = Database.getAdvancePayment(selectedPatient.getID(),datePicker.getValue());
        if (advance == -1) {
            patientDetails.setText("No Data for the patient this day");
            return;
        }
        String advancePayment;
        if (advance != 0) {
            advancePayment = "Rs. "+advance;
        } else {
            advancePayment = "No Data";
        }
        patientDetails.setText("Name :"+selectedPatient.getName() +"\n"
                + "Age :"+selectedPatient.getAge() +"\n"
                + "Gender :"+selectedPatient.getGender() +"\n"
                + "Contact Number :"+selectedPatient.getContactNumber() +"\n"
                + "Paid Advance :"+advancePayment +"\n"
                + "Date :"+datePicker.getValue().toString());
        ObservableList<TestForRecipt> receipts = Database.getRegisteredTestsForReceipt(selectedPatient.getID(),datePicker.getValue());
        for (TestForRecipt receipt : receipts) {
            receipt.setSelected();
        }
        tableView.setItems(receipts);
    }
    @FXML
    void print() {
        ReceiptBase receipt = new ReceiptBase(tableView.getItems().filtered(TestForRecipt::isSelectedBoolean),selectedPatient,advance, datePicker.getValue());
        receipt.initialize();
    }
}
