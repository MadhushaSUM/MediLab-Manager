

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;


public class patientAnalysis implements Initializable {
    private ObservableList<patient> patientList = FXCollections.observableArrayList();
    private ObservableList<patient> filteredPatientList = FXCollections.observableArrayList();
    private patient selectedPatient;


    @FXML private LineChart<String, Number> lineChart;
    @FXML private TextArea txtDetails;
    @FXML private ListView<String> testList;

    private AutoCompleteTextField<patient> txtAutoPatient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patientList = Database.getPatientList();
        testList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> listItemSelected(newValue));

        SortedSet<patient> entries = new TreeSet<>(Comparator.comparing(patient::toString));

        entries.addAll(Database.getPatientList());
        txtAutoPatient = new AutoCompleteTextField(entries);

        txtAutoPatient.getEntryMenu().setOnAction(e ->
        {
            ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event ->
            {
                if (txtAutoPatient.getLastSelectedObject() != null)
                {
                    selectedPatient = txtAutoPatient.getLastSelectedObject();
                    patientSelected();
                }
            });
        });

        AnchorPane pane = (AnchorPane) txtDetails.getParent();
        pane.getChildren().add(txtAutoPatient);
        AnchorPane.setTopAnchor(txtAutoPatient,10.0);
        AnchorPane.setLeftAnchor(txtAutoPatient,10.0);
        AnchorPane.setRightAnchor(txtAutoPatient,510.0);
    }

    private void listItemSelected(String oldValue) {
        /*
        Each test must have their own case : statement
        */
        if (oldValue != null) {
            switch (oldValue) {
                case "FBS":
                    loadLineChart.loadFBS(lineChart, selectedPatient.getID());
                    break;
                case "FBS2":
                    loadLineChart.loadPPBSALL(lineChart, selectedPatient.getID());
                    break;
                case "FBC":
                    loadLineChart.loadFBC(lineChart, selectedPatient.getID());
                    break;
                case "LIPID_PROFILE":
                    loadLineChart.loadLipid_Profile(lineChart, selectedPatient.getID());
                    break;
                case "GLUCOSETOLERANCE":
                    loadLineChart.loadGlucoTolerance(lineChart, selectedPatient.getID());
                    break;
                case "ORALGLUCOSE":
                    loadLineChart.loadOGTT(lineChart, selectedPatient.getID());
                    break;
                case "GLYCO_HEMO":
                    loadLineChart.loadGlyHemo(lineChart, selectedPatient.getID());
                    break;
                case "RHE_FACTORS":
                    loadLineChart.loadRheFac(lineChart, selectedPatient.getID());
                    break;
                case "LFT":
                    loadLineChart.loadLFT(lineChart, selectedPatient.getID());
                    break;
//                case "OGTT":
//                    loadLineChart.loadOGTT(lineChart, selectedPatient.getID());
//                    break;
                case "SCREATININE":
                    loadLineChart.loadSCre(lineChart, selectedPatient.getID());
                    break;
                case "SELECTROLYTES":
                    loadLineChart.loadSElectro(lineChart, selectedPatient.getID());
                    break;
                case "ESR":
                    loadLineChart.loadESR(lineChart, selectedPatient.getID());
                    break;
                case "BILIRUBIN":
                    loadLineChart.loadBilirubin(lineChart, selectedPatient.getID());
                    break;
                case "BLOOD_UREA":
                    loadLineChart.loadBloodUrea(lineChart, selectedPatient.getID());
                    break;
                case "HB":
                    loadLineChart.loadHB(lineChart, selectedPatient.getID());
                    break;
                case "OTPT":
                    loadLineChart.loadOTPT(lineChart, selectedPatient.getID());
                    break;
                case "RBS":
                    loadLineChart.loadRBS(lineChart, selectedPatient.getID());
                    break;
                case "SALKPHOSPHATASE":
                    loadLineChart.loadSAlkPhos(lineChart, selectedPatient.getID());
                    break;
                case "SCHOLESTEROL":
                    loadLineChart.loadSChol(lineChart, selectedPatient.getID());
                    break;
                case "SIONIZED_CALCIUM":
                    loadLineChart.loadSIC(lineChart, selectedPatient.getID());
                    break;
                case "SPROTEINS":
                    loadLineChart.loadSProteins(lineChart, selectedPatient.getID());
                    break;
                case "STOTAL_CALCIUM":
                    loadLineChart.loadSTC(lineChart, selectedPatient.getID());
                    break;
                case "SURICSPHOSPHORUS":
                    loadLineChart.loadSUSP(lineChart, selectedPatient.getID());
                    break;
                case "URINESUGAR":
                    loadLineChart.loadUrineSugar(lineChart, selectedPatient.getID());
                    break;
                case "WBCDC":
                    loadLineChart.loadWBCDC(lineChart, selectedPatient.getID());
                    break;
                case "BSP":
                    loadLineChart.loadBSP(lineChart, selectedPatient.getID());
                    break;
            }
        }
    }

//    @FXML
//    void searchPatients() {
//        String typedName = comboName.getEditor().getText();
//        selectedPatient = null;
//        if (!typedName.isEmpty()) {
//            filteredPatientList.clear();
//            for (patient patient1 : patientList) {
//                if (patient1.getName().contains(typedName)) {
//                    filteredPatientList.add(patient1);
//                    if (typedName.contains(patient1.getName())) {
//                        selectedPatient = patient1;
//                        patientSelected();
//                        comboName.getEditor().setText("");
//                    }
//                }
//            }
//            comboName.setItems(filteredPatientList);
//        } else {
//            comboName.setItems(patientList);
//        }
//    }

    private void patientSelected() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Name  : "+selectedPatient.getName()+"\n");
        buffer.append("Gender : "+selectedPatient.getGender()+"\n");
        buffer.append("Age : "+selectedPatient.getAge()+"\n");
        buffer.append("Contact Number : "+selectedPatient.getContactNumber()+"\n");
        buffer.append("Number of Tests done : "+ Database.getTestNumberDone(selectedPatient));
        txtDetails.setText(buffer.toString());

        testList.setItems(Database.getTestsDone(selectedPatient));
    }
}
