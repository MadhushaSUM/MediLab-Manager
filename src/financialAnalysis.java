

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class financialAnalysis implements Initializable {

    @FXML private LineChart<String, Integer> lineChart;
    @FXML private TableView<finAnalysistest> table;
    @FXML private DatePicker datePicker;
    @FXML private TableColumn<finAnalysistest, String> colTest = new TableColumn<>();
    @FXML private TableColumn<finAnalysistest, Integer> colNumber = new TableColumn<>();
    @FXML private TableColumn<finAnalysistest, Integer> colTotal = new TableColumn<>();

    private ArrayList<finAnalysistest> testList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colTest.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
    }

    @FXML
    void search() {
        testList = Database.getFinAnalysisTestList();
        testList = Database.loadFinancialAnalysisTable(datePicker.getEditor().getText(),testList);
        table.getItems().clear();
        table.getItems().addAll(testList);
        lineChart.getData().clear();
        lineChart.getData().add(Database.loadFinancialLineChart(datePicker.getValue()));
    }
}
