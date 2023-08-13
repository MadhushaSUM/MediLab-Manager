

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;


public class TestController implements Initializable {

    @FXML private TextField txtName;
    @FXML private TextField txtGender;
    @FXML private TextField txtAge;
    @FXML private TextField txtDOB;
    @FXML private TextField txtConNumber;
    @FXML private LineChart<String, Number> lineChart;
    @FXML private TextField txtReqDocSecondLine;
    @FXML private CheckBox CheckMoney;
    @FXML private TextField txtAdvance;
    @FXML private TextField txtRef;
    @FXML private CheckBox checkFBS;
    @FXML private CheckBox checkPPBSALL;
    @FXML private CheckBox checkCRP;
    @FXML private CheckBox checkUFR;
    @FXML private CheckBox checkFBC;
    @FXML private CheckBox checkLP;
    @FXML private CheckBox checkSCRE;
    @FXML private CheckBox checkGLYCO;
    @FXML private CheckBox checkRHF;
    @FXML private CheckBox checkLFT;
    @FXML private CheckBox checkBU;
    @FXML private CheckBox checkSELEC;
    @FXML private CheckBox checkUHCG;
    @FXML private CheckBox checkESR;
    @FXML private CheckBox checkSFR;
    @FXML private CheckBox checkDENGUE;
    @FXML private CheckBox checkFANTIBODY;
    @FXML private CheckBox checkGCT;
    @FXML private CheckBox checkHB;
    @FXML private CheckBox checkHIV;
    @FXML private CheckBox checkMALBU;
    @FXML private CheckBox checkOTPT;
    @FXML private CheckBox checkPTINR;
    @FXML private CheckBox checkSALK;
    @FXML private CheckBox checkSCHO;
    @FXML private CheckBox checkSICAL;
    @FXML private CheckBox checkSTCAL;
    @FXML private CheckBox checkSPRO;
    @FXML private CheckBox checkSUACID;
    @FXML private CheckBox checkUS;
    @FXML private CheckBox checkBILI;
    @FXML private CheckBox checkEGFR;
    @FXML private CheckBox checkCULTURE;
    @FXML private CheckBox checkVDRL;
    @FXML private CheckBox checkOralGlucose;
    @FXML private CheckBox checkRBS;
    @FXML private CheckBox checkWBCDC;
    @FXML private CheckBox checkGammaGT;
    @FXML private CheckBox checkBSP;
    @FXML private CheckBox checkBG;
    @FXML private CheckBox checkPPBS;

    public static HashSet<String> selectedTests;
    public static HashSet<String> previousTests;
    public static patient selectedPatient;
    private static LocalDate date;
    public static int advance;
    private int refNo;

    private AutoCompleteTextField<Doctor> txtReqDoc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedTests =  new HashSet<>();

        selectedPatient = AddSelectPatientController.selectedPatient;
        date = AddSelectPatientController.date;
        txtName.setText(selectedPatient.getName());
        txtAge.setText(String.valueOf(selectedPatient.getAge()));
        txtConNumber.setText(String.valueOf(selectedPatient.getContactNumber()));
        txtDOB.setText(selectedPatient.getDOB().toString());
        txtGender.setText(selectedPatient.getGender());


        SortedSet<Doctor> entries = new TreeSet<>(Comparator.comparing(Doctor::toString));

        entries.addAll(Database.getDoctors());
        txtReqDoc = new AutoCompleteTextField(entries);

        txtReqDoc.getEntryMenu().setOnAction(e ->
        {
            ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event ->
            {
                if (txtReqDoc.getLastSelectedObject() != null)
                {
                    txtReqDoc.setText(txtReqDoc.getLastSelectedObject().getFirstLine());
                    txtReqDocSecondLine.setText(txtReqDoc.getLastSelectedObject().getSecondLine());
                }
            });
        });

        AnchorPane pane = (AnchorPane) txtName.getParent();
        pane.getChildren().add(txtReqDoc);
        AnchorPane.setTopAnchor(txtReqDoc,501.0);
        AnchorPane.setLeftAnchor(txtReqDoc,74.0);
        AnchorPane.setRightAnchor(txtReqDoc,496.0);
        txtReqDoc.getStyleClass().add("scalable");

        txtRef.setText(String.valueOf(Database.getLastReference() + 1));

        ScaleNodes.setScale(pane, "scalable");
    }

    @FXML
    void selectFBS() {
        lineChart.setTitle("FBS");
        loadLineChart.loadFBS(lineChart,selectedPatient.getID());
        if (checkFBS.isSelected()) {
            selectedTests.add("FBS");
        } else {
            selectedTests.remove("FBS");
        }
    }
    @FXML
    void selectPPBSALL() {
        lineChart.setTitle("POST PRANDIAL BLOOD SUGAR - ALL");
        loadLineChart.loadPPBSALL(lineChart,selectedPatient.getID());
        if (checkPPBSALL.isSelected()) {
            selectedTests.add("PPBSALL");
        } else {
            selectedTests.remove("PPBSALL");
        }
    }
    @FXML
    void selectPPBS() {
        lineChart.setTitle("POST PRANDIAL BLOOD SUGAR");
        loadLineChart.loadPPBS(lineChart,selectedPatient.getID());
        if (checkPPBS.isSelected()) {
            selectedTests.add("PPBS");
        } else {
            selectedTests.remove("PPBS");
        }
    }
    @FXML
    void selectCRP() {
        lineChart.setTitle("CRP");
        loadLineChart.loadCRP(lineChart,selectedPatient.getID());
        if (checkCRP.isSelected()) {
            selectedTests.add("CRP");
        } else {
            selectedTests.remove("CRP");
        }
    }
    @FXML
    void selectBlood_Urea() {
        lineChart.setTitle("Blood Urea");
        //loadLineChart.loadFBS(lineChart,selectedPatient.getID());
        //Don't know enough to set up lineChart
        if (checkBU.isSelected()) {
            selectedTests.add("BLOOD_UREA");
        } else {
            selectedTests.remove("BLOOD_UREA");
        }
    }
    @FXML
    void selectDengue() {
        lineChart.setTitle("Dengue");
        if (checkDENGUE.isSelected()) {
            selectedTests.add("DENGUETEST");
        } else {
            selectedTests.remove("DENGUETEST");
        }
    }
    @FXML
    void selectFAntibody() {
        lineChart.setTitle("F. Antibody Test");
        //loadLineChart.loadFBS(lineChart,selectedPatient.getID());
        //Don't know enough to set up lineChart
        if (checkFANTIBODY.isSelected()) {
            selectedTests.add("FILARIALANTIBODYTEST");
        } else {
            selectedTests.remove("FILARIALANTIBODYTEST");
        }
    }
    @FXML
    void selectHB() {
        lineChart.setTitle("HB");
        loadLineChart.loadHB(lineChart,selectedPatient.getID());
        if (checkHB.isSelected()) {
            selectedTests.add("HB");
        } else {
            selectedTests.remove("HB");
        }
    }
    @FXML
    void selectGCT() {
        lineChart.setTitle("GCT");
        loadLineChart.loadGCT(lineChart,selectedPatient.getID());
        if (checkGCT.isSelected()) {
            selectedTests.add("GCT");
        } else {
            selectedTests.remove("GCT");
        }
    }
    @FXML
    void selectHIV() {
        lineChart.setTitle("HIV");
        if (checkHIV.isSelected()) {
            selectedTests.add("HIV");
        } else {
            selectedTests.remove("HIV");
        }
    }
    @FXML
    void selectMicroalbumin() {
        lineChart.setTitle("Microalbumin");
        loadLineChart.loadMicroAlbumin(lineChart,selectedPatient.getID());
        if (checkMALBU.isSelected()) {
            selectedTests.add("MICROALBUMIN");
        } else {
            selectedTests.remove("MICROALBUMIN");
        }
    }
    @FXML
    void selectOTPT() {
        lineChart.setTitle("OT /PT");
        loadLineChart.loadOTPT(lineChart,selectedPatient.getID());
        if (checkOTPT.isSelected()) {
            selectedTests.add("OTPT");
        } else {
            selectedTests.remove("OTPT");
        }
    }
    @FXML
    void selectPTINR() {
        lineChart.setTitle("PT / INR");
        //loadLineChart.loadFBS(lineChart,selectedPatient.getID());
        //Don't know enough to set up lineChart
        if (checkPTINR.isSelected()) {
            selectedTests.add("PTINR");
        } else {
            selectedTests.remove("PTINR");
        }
    }
    @FXML
    void selectSAlkPhos() {
        lineChart.setTitle("S. Alk Phosphatase");
        loadLineChart.loadSAlkPhos(lineChart,selectedPatient.getID());
        if (checkSALK.isSelected()) {
            selectedTests.add("SALKPHOSPHATASE");
        } else {
            selectedTests.remove("SALKPHOSPHATASE");
        }
    }
    @FXML
    void selectSCholesterol() {
        lineChart.setTitle("S. Cholesterol");
        loadLineChart.loadSChol(lineChart,selectedPatient.getID());
        if (checkSCHO.isSelected()) {
            selectedTests.add("SCHOLESTEROL");
        } else {
            selectedTests.remove("SCHOLESTEROL");
        }
    }
    @FXML
    void selectSIonizedCalcium() {
        lineChart.setTitle("S. Ionized Calcium");
        loadLineChart.loadSIC(lineChart,selectedPatient.getID());
        if (checkSICAL.isSelected()) {
            selectedTests.add("SIONIZED_CALCIUM");
        } else {
            selectedTests.remove("SIONIZED_CALCIUM");
        }
    }
    @FXML
    void selectSTotalCalcium() {
        lineChart.setTitle("S. Total Calcium");
        loadLineChart.loadSTC(lineChart,selectedPatient.getID());
        if (checkSTCAL.isSelected()) {
            selectedTests.add("STOTAL_CALCIUM");
        } else {
            selectedTests.remove("STOTAL_CALCIUM");
        }
    }
    @FXML
    void selectSProteins() {
        lineChart.setTitle("S. Proteins");
        loadLineChart.loadSProteins(lineChart,selectedPatient.getID());
        if (checkSPRO.isSelected()) {
            selectedTests.add("SPROTEINS");
        } else {
            selectedTests.remove("SPROTEINS");
        }
    }
    @FXML
    void selectSURICSPHOSPHORUS() {
        lineChart.setTitle("S. Uric Acid & S. Phosphatase");
        loadLineChart.loadSUSP(lineChart,selectedPatient.getID());
        if (checkSUACID.isSelected()) {
            selectedTests.add("SURICSPHOSPHORUS");
        } else {
            selectedTests.remove("SURICSPHOSPHORUS");
        }
    }
    @FXML
    void selectUrineSugar() {
        lineChart.setTitle("Urine Sugar");
        //loadLineChart.loadFBS(lineChart,selectedPatient.getID());
        //Don't know enough to set up lineChart
        if (checkUS.isSelected()) {
            selectedTests.add("URINESUGAR");
        } else {
            selectedTests.remove("URINESUGAR");
        }
    }
    @FXML
    void selectLFT() {
        lineChart.setTitle("LFT");
        loadLineChart.loadLFT(lineChart,selectedPatient.getID());
        if (checkLFT.isSelected()) {
            selectedTests.add("LFT");
        } else {
            selectedTests.remove("LFT");
        }
    }
    @FXML
    void selectESR() {
        lineChart.setTitle("ESR");
        loadLineChart.loadESR(lineChart,selectedPatient.getID());
        if (checkESR.isSelected()) {
            selectedTests.add("ESR");
        } else {
            selectedTests.remove("ESR");
        }
    }
    @FXML
    void selectFBC() {
        lineChart.setTitle("FBC");
        loadLineChart.loadFBC(lineChart,selectedPatient.getID());
        if (checkFBC.isSelected()) {
            selectedTests.add("FBC");
        } else {
            selectedTests.remove("FBC");
        }
    }
    @FXML
    void selectGlyHemo() {
        lineChart.setTitle("GLYCO_HEMO");
        loadLineChart.loadGlyHemo(lineChart,selectedPatient.getID());
        if (checkGLYCO.isSelected()) {
            selectedTests.add("GLYCO_HEMO");
        } else {
            selectedTests.remove("GLYCO_HEMO");
        }
    }
    @FXML
    void selectLP() {
        lineChart.setTitle("LIPID PROFILE");
        loadLineChart.loadLipid_Profile(lineChart,selectedPatient.getID());
        if (checkLP.isSelected()) {
            selectedTests.add("LIPID_PROFILE");
        } else {
            selectedTests.remove("LIPID_PROFILE");
        }
    }
    @FXML
    void selectRheFactors() {
        lineChart.setTitle("Rhe Factors");
        loadLineChart.loadRheFac(lineChart,selectedPatient.getID());
        if (checkRHF.isSelected()) {
            selectedTests.add("RHE_FACTORS");
        } else {
            selectedTests.remove("RHE_FACTORS");
        }
    }
    @FXML
    void selectSCre() {
        lineChart.setTitle("S. Creatinine");
        loadLineChart.loadSCre(lineChart,selectedPatient.getID());
        if (checkSCRE.isSelected()) {
            selectedTests.add("SCREATININE");
        } else {
            selectedTests.remove("SCREATININE");
        }
    }
    @FXML
    void selectSElectro() {
        lineChart.setTitle("S. Electrolytes");
        loadLineChart.loadSElectro(lineChart,selectedPatient.getID());
        if (checkSELEC.isSelected()) {
            selectedTests.add("SELECTROLYTES");
        } else {
            selectedTests.remove("SELECTROLYTES");
        }
    }
    @FXML
    void selectSFR() {
        lineChart.setTitle("SFR");
        if (checkSFR.isSelected()) {
            selectedTests.add("SFR");
        } else {
            selectedTests.remove("SFR");
        }
    }
    @FXML
    void selectUFR() {
        lineChart.setTitle("UFR");
        if (checkUFR.isSelected()) {
            selectedTests.add("UFR");
        } else {
            selectedTests.remove("UFR");
        }
    }
    @FXML
    void selectUrineHCG() {
        lineChart.setTitle("Urine H.C.G.");
        if (checkUHCG.isSelected()) {
            selectedTests.add("URINEHCG");
        } else {
            selectedTests.remove("URINEHCG");
        }
    }
    @FXML
    void selectBilirubin() {
        lineChart.setTitle("Bilirubin");
        loadLineChart.loadBilirubin(lineChart,selectedPatient.getID());
        if (checkBILI.isSelected()) {
            selectedTests.add("BILIRUBIN");
        } else {
            selectedTests.remove("BILIRUBIN");
        }
    }
    @FXML
    void selectEGFR() {
        lineChart.setTitle("EGFR");
        loadLineChart.loadEGFR(lineChart,selectedPatient.getID());
        if (checkEGFR.isSelected()) {
            selectedTests.add("EGFR");
        } else {
            selectedTests.remove("EGFR");
        }
    }
    @FXML
    void selectCulture() {
        lineChart.setTitle("Culture A.B.S. Test");
        if (checkCULTURE.isSelected()) {
            selectedTests.add("CULTUREABSTEST");
        } else {
            selectedTests.remove("CULTUREABSTEST");
        }
    }
    @FXML
    void selectVDRL() {
        lineChart.setTitle("V.D.R.L.");
        if (checkVDRL.isSelected()) {
            selectedTests.add("VDRL");
        } else {
            selectedTests.remove("VDRL");
        }
    }
    @FXML
    void selectOralGlucose() {
        lineChart.setTitle("Oral Glucose Tolerance - 75g");
        if (checkOralGlucose.isSelected()) {
            selectedTests.add("ORALGLUCOSE");
        } else {
            selectedTests.remove("ORALGLUCOSE");
        }
    }
    @FXML
    void selectRBS() {
        lineChart.setTitle("Random Blood Sugar");
        if (checkRBS.isSelected()) {
            selectedTests.add("RBS");
        } else {
            selectedTests.remove("RBS");
        }
    }
    @FXML
    void selectWBCDC() {
        lineChart.setTitle("WBC / DC");
        if (checkWBCDC.isSelected()) {
            selectedTests.add("WBCDC");
        } else {
            selectedTests.remove("WBCDC");
        }
    }
    @FXML
    void selectGammaGT() {
        lineChart.setTitle("GammaGT");
        loadLineChart.loadGammaGT(lineChart,selectedPatient.getID());
        if (checkGammaGT.isSelected()) {
            selectedTests.add("GAMMAGT");
        } else {
            selectedTests.remove("GAMMAGT");
        }
    }
    @FXML
    void selectBSP() {
        lineChart.setTitle("BSP");
        loadLineChart.loadBSP(lineChart,selectedPatient.getID());
        if (checkBSP.isSelected()) {
            selectedTests.add("BSP");
        } else {
            selectedTests.remove("BSP");
        }
    }
    @FXML
    void selectBG() {
        lineChart.setTitle("Blood Group");
        if (checkBG.isSelected()) {
            selectedTests.add("BG");
        } else {
            selectedTests.remove("BG");
        }
    }

    @FXML
    void addRecord() {
        advance = 0;
        refNo = 0;
        try {
            advance = Integer.parseInt(txtAdvance.getText());
            refNo = Integer.parseInt(txtRef.getText());
        } catch (NumberFormatException e) {
            messageBox.showMessage("Enter valid Advance\nor leave it empty","Invalid Input");
            return;
        }
        if (selectedTests.isEmpty()) return;
        for (String selectedTest : selectedTests) {
            Database.AddRecord(selectedPatient.getID(), selectedTest, CheckMoney.isSelected(), txtReqDoc.getText(), txtReqDocSecondLine.getText(),advance,refNo,date);
        }
    }
    @FXML
    void saveAndPrintReceipt() {
        advance = 0;
        refNo = 0;
        try {
            advance = Integer.parseInt(txtAdvance.getText());
            refNo = Integer.parseInt(txtRef.getText());
        } catch (NumberFormatException e) {
            messageBox.showMessage("Enter valid Advance\nor leave it empty","Invalid Input");
            return;
        }
        //LocalDate.now() has to be changed
        previousTests = Database.checkPreviousTestForReceipt(selectedPatient.getID(), LocalDate.now());
        for (String selectedTest : selectedTests) {
            Database.AddRecord(selectedPatient.getID(), selectedTest, CheckMoney.isSelected(), txtReqDoc.getText(), txtReqDocSecondLine.getText(),advance,refNo,date);
        }
        if (previousTests.isEmpty()) {
            //No Previous Tests Detected. Just save tests and print a receipt

            ObservableList<TestForRecipt> testForReceiptsList = FXCollections.observableArrayList();
            ObservableList<tableTests> tableTestsList = Database.getTableTestsList();
            for (String newTest : selectedTests) {
                int cost = tableTestsList.filtered(test -> test.getTestCode().equalsIgnoreCase(newTest)).get(0).getPriceInt();
                TestForRecipt testForRecipt = new TestForRecipt(newTest,cost);
                testForRecipt.setSelected();
                testForReceiptsList.add(testForRecipt);
            }

            ReceiptBase receipt = new ReceiptBase(testForReceiptsList,selectedPatient,advance, LocalDate.now());
            receipt.initialize();

        } else {
            // there are tests saved for this patient on this date. ask user to print receipt for these tests only
            // or for all tests. save reports and act accordingly
            try {
                Parent root = FXMLLoader.load(getClass().getResource("PrintReceiptFrame.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Previous Tests Detected");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.getIcons().add(new Image("microscope.png"));
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
