import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;


public class printReportController implements Initializable {
    private Printing printer = new Printing();;

    @FXML private TableView<test> table = new TableView<>();
    @FXML private TableColumn<test, String> colRefNo = new TableColumn<>();
    @FXML private TableColumn<test, String> colDate = new TableColumn<>();
    @FXML private TableColumn<test, String> colName = new TableColumn<>();
    @FXML private TableColumn<test, String> colTest = new TableColumn<>();
    @FXML private TableColumn<test, String> colMoneyCollected = new TableColumn<>();
    @FXML private ToggleButton btnApplyFilter;
    @FXML private DatePicker datePickerFilter;

    private AutoCompleteTextField<patient> patientAutoText;

    private boolean All = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setItems(Database.loadReportTable(false));

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setEditable(false);
        table.setOnKeyPressed(this::keyPressed);
        colRefNo.setCellValueFactory(new PropertyValueFactory<>("RefNo"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colTest.setCellValueFactory(new PropertyValueFactory<>("Test"));
        colMoneyCollected.setCellValueFactory(new PropertyValueFactory<>("MoneyCollected"));

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
                    btnApplyFilter.setSelected(false);
                    toggleButtonClicked();
                }
            });
        });

        AnchorPane pane = (AnchorPane) table.getParent();
        pane.getChildren().add(patientAutoText);
        AnchorPane.setTopAnchor(patientAutoText,29.0);
        AnchorPane.setLeftAnchor(patientAutoText,419.0);
        AnchorPane.setRightAnchor(patientAutoText,189.0);
        patientAutoText.getStyleClass().add("scalable");

        ScaleNodes.setScale(pane, "scalable");
    }

    private void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case DELETE:
                removeReport();
                break;
            case ENTER:
                try {
                    printSelectedItems();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @FXML
    private void updateMoneyStatus() {
        ObservableList<test> paidTestList;
        paidTestList = table.getSelectionModel().getSelectedItems();
        if (!paidTestList.isEmpty()) {
            Database.updateMoneyStatus(paidTestList);
        }
        table.setItems(Database.loadReportTable(All));
    }

    @FXML
    void loadAllRecords()  {
        All = true;
        table.setItems(Database.loadReportTable(true));
    }

    @FXML
    void loadNotPrintedRecords( ) {
        All = false;
        table.setItems(Database.loadReportTable(false));
    }

    @FXML
    void printPreview() {
        try {
            printer.printPreview(table.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            messageBox.showMessage("Error previewing Report","Error");
            e.printStackTrace();
        }
    }

    @FXML
    void removeReport() {
        if (!LoginController.isAdmin()) {
            messageBox.showMessage("Admin Logged Required ","Authenticator");
            return;
        }
        if (table.getSelectionModel().getSelectedItem() == null) return;
        if (messageBox.confirmAsk("Confirm Delete")) {
            Database.removeTest(table.getSelectionModel().getSelectedItem());
            table.setItems(Database.loadReportTable(All));
        }
    }

    @FXML
    void printSelectedItems() throws IOException {
        printer.printReport(table.getSelectionModel().getSelectedItems());
        table.setItems(Database.loadReportTable(All));
    }

    @FXML
    void printMergedReport() throws IOException {
        ObservableList<test> reportList = table.getSelectionModel().getSelectedItems();
        int reportCount = reportList.size() - 1;
        if (reportCount < 1) {
          messageBox.showMessage("At least select two Reports to merge","Illegal");
          return;
        }
        for (int i = 0; i < reportCount; i++) {
            if (!reportList.get(i).getName().equals(reportList.get(i+1).getName())) {
                messageBox.showMessage("Merging Different patient's\nReports is not Allowed","Illegal");
                return;
            } else if (!reportList.get(i).getDate().isEqual(reportList.get(i+1).getDate())) {
                messageBox.showMessage("Merging Different Reports with\nDifferent Dates is not Allowed","Illegal");
                return;
            }
        }
        printer.printMergedReport(reportList);
        table.getSelectionModel().clearSelection();
        table.setItems(Database.loadReportTable(All));
    }
    @FXML
    void reEditReport() {
        if (!LoginController.isAdmin()) {
            messageBox.showMessage("Admin Logged Required ","Authenticator");
            return;
        }
        ObservableList<test> reportList = table.getSelectionModel().getSelectedItems();
        if (reportList.isEmpty()) return;
        Database.reEdit(reportList);
        table.setItems(Database.loadReportTable(All));
    }
    @FXML
    void toggleButtonClicked() {
        if (btnApplyFilter.isSelected()) {
            ObservableList<test> filteredTestList = Database.loadReportTable(All);
            if (patientAutoText.getLastSelectedObject() != null) {
                filteredTestList = filteredTestList.filtered(test -> test.getName().toLowerCase().equals(patientAutoText.getLastSelectedObject().getName().toLowerCase()));
                table.setItems(filteredTestList);
            }
            if (datePickerFilter.getValue() != null) {
                filteredTestList = filteredTestList.filtered(test -> test.getDate().equals(datePickerFilter.getValue()));
                table.setItems(filteredTestList);
            }

        } else {
            table.setItems(Database.loadReportTable(All));
        }
    }
    @FXML
    void datePickerClicked() {
        if (btnApplyFilter.isSelected()) {
            btnApplyFilter.setSelected(false);
            toggleButtonClicked();
        }
    }
}
