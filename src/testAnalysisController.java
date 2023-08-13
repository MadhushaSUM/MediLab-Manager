

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class testAnalysisController {

    @FXML private LineChart<String, Integer> lineChart;
    @FXML private DatePicker datePicker;
    @FXML private PieChart pieChart;
    @FXML private TextArea txtDetailsArea;

    @FXML
    void search() {
        HashMap<String, Integer> testNumbers = Database.getTestAnalysisNumbers(datePicker.getEditor().getText());
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        txtDetailsArea.setText("");
        txtDetailsArea.appendText("Total Test Count : " + testNumbers.values().stream().reduce(0, Integer::sum)+"\n");
        for (Map.Entry<String, Integer> entry : testNumbers.entrySet()) {
            txtDetailsArea.appendText(entry.getKey() + "  :  " + entry.getValue() +"\n");
            if (entry.getValue() != null) {
                chartData.add(new PieChart.Data(entry.getKey(),entry.getValue()));
            }
        }
        pieChart.setData(chartData);
        lineChart.getData().clear();
        LocalDate date = datePicker.getValue();
        lineChart.getData().add(Database.setDailyLineChart(date));
    }
}
