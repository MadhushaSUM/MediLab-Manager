

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;

public class PrintReceiptController implements Initializable {

    @FXML private TableView<TestForRecipt> tableView;
    @FXML private TableColumn<TestForRecipt,String> colTest;
    @FXML private TableColumn<TestForRecipt,Boolean> colIsSelected;

    private HashSet<String> previousTest;
    private HashSet<String> newTests;
    private ObservableList<TestForRecipt> testForReceiptsList = FXCollections.observableArrayList();
    private patient selectedPatient;
    private int advance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedPatient = TestController.selectedPatient;
        advance = TestController.advance;
        getPreviousTests();
        ObservableList<tableTests> tableTestsList = Database.getTableTestsList();
        for (String newTest : newTests) {
            int cost = tableTestsList.filtered(test -> test.getTestCode().equalsIgnoreCase(newTest)).get(0).getPriceInt();
            TestForRecipt testForRecipt = new TestForRecipt(newTest,cost);
            testForRecipt.setSelected();
            testForReceiptsList.add(testForRecipt);
        }
        for (String preTest : previousTest) {
            int cost = tableTestsList.filtered(test -> test.getTestCode().equalsIgnoreCase(preTest)).get(0).getPriceInt();
            testForReceiptsList.add(new TestForRecipt(preTest,cost));
        }
        tableView.setItems(testForReceiptsList);
        colTest.setCellValueFactory(new PropertyValueFactory<>("Test"));
        colIsSelected.setCellValueFactory(param -> param.getValue().isSelected());
        colIsSelected.setCellFactory(CheckBoxTableCell.forTableColumn(colIsSelected));
        colIsSelected.setEditable(true);
        tableView.setEditable(true);
    }
    private void getPreviousTests() {
        previousTest = TestController.previousTests;
        newTests = TestController.selectedTests;
    }
    @FXML
    void printReceipt() {
        ReceiptBase receipt = new ReceiptBase(tableView.getItems().filtered(TestForRecipt::isSelectedBoolean),selectedPatient,advance, LocalDate.now());
        receipt.initialize();
    }
    @FXML
    void cancel() {

    }
}
