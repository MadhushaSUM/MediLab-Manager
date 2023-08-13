import javafx.animation.SequentialTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;


public class ControllerMain implements Initializable {
    @FXML private ListView<test> testList;
    @FXML private ScrollPane editPane;
    @FXML private CheckMenuItem menuCheckYears;
    @FXML private CheckMenuItem menuCheckMonths;
    @FXML private CheckMenuItem menuCheckDays;
    @FXML private Button btnNewTest;
    @FXML private Button btnLoadPrintReport;
    @FXML private Button btnInvoiceManager;
    @FXML private Button btnEGFR;
    @FXML private Label lblNewTest;
    @FXML private Label lblLoadPrintReport;
    @FXML private Label lblInvoiceManager;
    @FXML private Label lblEGFR;

    StackPane stackPane = new StackPane();

    private TextField[] txtArray;
    private TextArea[] textArea;
    private ComboBox<String>[] comboBoxes;
    private test selectedTest;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Label[] btnLabels = {lblNewTest, lblLoadPrintReport, lblInvoiceManager, lblEGFR};

        loadAgeingPreferences();
        menuCheckYears.setOnAction(event -> setAgeingPreferences());
        menuCheckMonths.setOnAction(event -> setAgeingPreferences());
        menuCheckDays.setOnAction(event -> setAgeingPreferences());
        refreshTestList();
        testList.getSelectionModel().selectedItemProperty().addListener(observable -> listItemSelect(testList.getSelectionModel().getSelectedItem()));
        
        editPane.setContent(stackPane);
        stackPane.minWidthProperty().bind(Bindings.createDoubleBinding(() -> editPane.getViewportBounds().getWidth(), editPane.viewportBoundsProperty()));

        for (Label label : btnLabels) {
            label.setScaleX(ScaleNodes.scalingFactor);
            label.setScaleY(ScaleNodes.scalingFactor);
        }

    }


    private void setAgeingPreferences() {
        try {
            PrintStream saveFile = new PrintStream("Configure\\AgeingPreferences.txt");
            if (menuCheckYears.isSelected()) {
                saveFile.println("true");
            } else {
                saveFile.println("false");
            }
            if (menuCheckMonths.isSelected()) {
                saveFile.println("true");
            } else {
                saveFile.println("false");
            }
            if (menuCheckDays.isSelected()) {
                saveFile.println("true");
            } else {
                saveFile.println("false");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            messageBox.showMessage("Setting Ageing preferences\nprocess failed","Error");
        }
    }
    private void loadAgeingPreferences() {
        try {
            Scanner fileScanner = new Scanner(new File("Configure\\AgeingPreferences.txt"));
            if (fileScanner.nextBoolean()) menuCheckYears.setSelected(true);
            if (fileScanner.nextBoolean()) menuCheckMonths.setSelected(true);
            if (fileScanner.nextBoolean()) menuCheckDays.setSelected(true);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            messageBox.showMessage("AgeingPreferences.txt not found\nDefault Ageing Preferences will be loaded","Alert");
            saveDefaultAgeingPreferences();
            loadAgeingPreferences();
        }

    }
    private void saveDefaultAgeingPreferences() {
        try {
            PrintStream saveFile = new PrintStream("Configure\\AgeingPreferences.txt");
            saveFile.println("true");
            saveFile.println("false");
            saveFile.println("false");
            saveFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            messageBox.showMessage("Setting Default Ageing\npreferences process failed","Error");
        }
    }

    @FXML
    private void refreshTestList() {
        testList.setItems(Database.getTestList());
    }

    private void listItemSelect(test selectedTest) {
        if (selectedTest != null) {
            try {
                this.selectedTest = selectedTest;
                stackPane.getChildren().clear();
                String test = selectedTest.getTest();
                int columnCount = 0;
                Parent root = null;
                Button btn;
                switch (test) {
                    case "BLOOD_UREA":
                        root = FXMLLoader.load(getClass().getResource("blood_ureaEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateBU(root);
                        return;
                    case "CRP":
                        root = FXMLLoader.load(getClass().getResource("crpEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateCRP(root);
                        return;
                    case "DENGUETEST":
                        txtArray = new TextField[1];
                        columnCount = 1;
                        textArea = new TextArea[1];
                        root = FXMLLoader.load(getClass().getResource("denguetestEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        break;
                    case "FILARIALANTIBODYTEST":
                        txtArray = new TextField[2];
                        columnCount = 2;
                        root = FXMLLoader.load(getClass().getResource("filarialantibodytestEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        textArea = new TextArea[1];
                        break;
                    case "GCT":
                        root = FXMLLoader.load(getClass().getResource("gctEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateGCT(root);
                        return;
                    case "FBS":
                        root = FXMLLoader.load(getClass().getResource("fbsEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateFBS1(root);
                        return;
                    case "PPBSALL":
                        root = FXMLLoader.load(getClass().getResource("ppbsallEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiatePPBSALL(root);
                        return;
                    case "PPBS":
                        root = FXMLLoader.load(getClass().getResource("ppbsEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiatePPBS(root);
                        return;
                    case "FBC":
                        root = FXMLLoader.load(getClass().getResource("fbcEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateFBC(root);
                        return;
                    case "LIPID_PROFILE":
                        root = FXMLLoader.load(getClass().getResource("lipid_profileEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateLP(root);
                        return;
                    case "GLYCO_HEMO":
                        txtArray = new TextField[2];
                        columnCount = 2;
                        root = FXMLLoader.load(getClass().getResource("glyco_hemoEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        textArea = new TextArea[1];
                        break;
                    case "UFR":
                        root = FXMLLoader.load(getClass().getResource("ufrEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateUFR(root);
                        return;
                    case "SFR":
                        root = FXMLLoader.load(getClass().getResource("sfrEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateSFR(root);
                        return;
                    case "RHE_FACTORS":
                        root = FXMLLoader.load(getClass().getResource("rhe_factorsEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateRHF(root);
                        return;
                    case "VDRL":
                        txtArray = new TextField[2];
                        columnCount = 2;
                        root = FXMLLoader.load(getClass().getResource("vdrlEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        textArea = new TextArea[1];
                        break;
                    case "LFT":
                        root = FXMLLoader.load(getClass().getResource("lftEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateLFT(root);
                        return;
//                    case "OGTT":
//                        txtArray = new TextField[3];
//                        columnCount = 3;
//                        root = FXMLLoader.load(getClass().getResource("ogttEdit.fxml"));
//                    ScaleNode.setScale(root, "scalable");
//                        break;
                    case "SCREATININE":
                        root = FXMLLoader.load(getClass().getResource("screatinineEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateSCRE(root);
                        return;
                    case "GAMMAGT":
                        root = FXMLLoader.load(getClass().getResource("gammagtEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateGammaGT(root);
                        return;
                    case "SELECTROLYTES":
                        root = FXMLLoader.load(getClass().getResource("selectrolytesEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateSELECTRO(root);
                        return;
                    case "URINEHCG":
                        root = FXMLLoader.load(getClass().getResource("urinehcgEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateUHCG(root);
                        return;
                    case "ESR":
                        root = FXMLLoader.load(getClass().getResource("esrEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateESR(root);
                        return;
                    case "HB":
                        root = FXMLLoader.load(getClass().getResource("hbEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateHB(root);
                        return;
                    case "HIV":
                        root = FXMLLoader.load(getClass().getResource("hivEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateHIV(root);
                        return;
                    case "MICROALBUMIN":
                        txtArray = new TextField[3];
                        columnCount = 3;
                        root = FXMLLoader.load(getClass().getResource("microalbuminEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        textArea = new TextArea[1];
                        break;
                    case "OTPT":
                        root = FXMLLoader.load(getClass().getResource("otptEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateOTPT(root);
                        return;
                    case "PTINR":
                        txtArray = new TextField[5];
                        columnCount = 5;
                        root = FXMLLoader.load(getClass().getResource("ptinrEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        textArea = new TextArea[1];
                        break;
                    case "SALKPHOSPHATASE":
                        root = FXMLLoader.load(getClass().getResource("salkphosphataseEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateSALK(root);
                        return;
                    case "SCHOLESTEROL":
                        root = FXMLLoader.load(getClass().getResource("scholesterolEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateSCHO(root);
                        return;
                    case "SIONIZED_CALCIUM":
                        txtArray = new TextField[1];
                        columnCount = 1;
                        root = FXMLLoader.load(getClass().getResource("sionized_calciumEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        textArea = new TextArea[1];
                        break;
                    case "SPROTEINS":
                        root = FXMLLoader.load(getClass().getResource("sproteinsEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateSPROTEINS(root);
                        return;
                    case "STOTAL_CALCIUM":
                        root = FXMLLoader.load(getClass().getResource("stotal_calciumEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateTOTALCALCIUM(root);
                        return;
                    case "SURICSPHOSPHORUS":
                        txtArray = new TextField[9];
                        columnCount = 9;
                        root = FXMLLoader.load(getClass().getResource("suricsphosphorusEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        textArea = new TextArea[1];
                        break;
                    case "URINESUGAR":
                        txtArray = new TextField[1];
                        columnCount = 1;
                        root = FXMLLoader.load(getClass().getResource("urinesugarEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        textArea = new TextArea[1];
                        break;
                    case "BILIRUBIN":
                        root = FXMLLoader.load(getClass().getResource("bilirubinEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateBilirubin(root);
                        return;
                    case "EGFR":
                        txtArray = new TextField[1];
                        columnCount = 1;
                        root = FXMLLoader.load(getClass().getResource("egfrEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        textArea = new TextArea[1];
                        break;
                    case "CULTUREABSTEST":
                        txtArray = new TextField[50];
                        columnCount = 50;
                        root = FXMLLoader.load(getClass().getResource("cultureabstestEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        textArea = new TextArea[1];
                        break;
                    case "ORALGLUCOSE":
                        root = FXMLLoader.load(getClass().getResource("oralglucoseEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateORALGLUCOSE(root);
                        return;
                    case "RBS":
                        root = FXMLLoader.load(getClass().getResource("rbsEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateRBS(root);
                        return;
                    case "WBCDC":
                        root = FXMLLoader.load(getClass().getResource("wbcdcEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateWBCDC(root);
                        return;
                    case "BSP":
                        root = FXMLLoader.load(getClass().getResource("bspEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateBSP(root);
                        return;
                    case "BG":
                        root = FXMLLoader.load(getClass().getResource("bgEdit.fxml"));
                        ScaleNodes.setScale(root, "scalable");
                        initiateBG(root);
                        return;
                }
                stackPane.getChildren().add(root);

                txtArray[0] = (TextField) root.lookup("#txtName");
                txtArray[0].setText(selectedTest.getName());

                txtArray[0] = (TextField) root.lookup("#txtDate");
                txtArray[0].setText(selectedTest.getDate().toString());

                for (int i = 0; i < columnCount; i++) {
                    txtArray[i] = (TextField) root.lookup("#val" + i);
                }
                if (textArea != null) {
                    for (int i = 0; i < textArea.length; i++) {
                        textArea[i] = (TextArea) root.lookup("#vala" + i);
                    }
                }
                ArrayList<Object> arrayList = Database.retrieveSavedReportValues(selectedTest, txtArray, textArea);
                txtArray = (TextField[]) arrayList.get(0);
                textArea = (TextArea[]) arrayList.get(1);
                if (test.equals("FBC")) {
                    txtArray[9].setOnKeyReleased(event -> calculateFBC());
                }
                btn = (Button) root.lookup("#btnSave");
                btn.setOnAction(event -> btnSaveClicked());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initiateTOTALCALCIUM(Parent root) {
        txtArray = new TextField[2];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[2];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 2; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
        }
        for (int i = 0; i < 2; i++) {
            comboBoxes[i] = (ComboBox) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH", "LOW");
        }
        setEventHandlersNoGender(0, 2.2, 2.6);
        setEventHandlersNoGender(1, 1.1, 1.3);

        textArea[0] = (TextArea) root.lookup("#vala0");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateLFT(Parent root) {
        txtArray = new TextField[11];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[10];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 11; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
        }
        for (int i = 0; i < 10; i++) {
            comboBoxes[i] = (ComboBox) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH", "LOW");
        }
        setEventHandlersNoGender(0, 7, 40);
        setEventHandlersNoGender(1, 7, 35);
        setEventHandlersNoGender(2, 40, 120);
        setEventHandlersNoGender(3, 0.2, 1.2);
        setEventHandlersNoGender(4, 0.1, 0.5);
        setEventHandlersNoGender(5, 0.1, 0.6);
        setEventHandlersNoGender(6, 6.2, 8.5);
        setEventHandlersNoGender(7, 10, 50);

        txtArray[8].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[8].getText());
                double A = Double.parseDouble(txtArray[7].getText());
                A = A / val;
                A = Math.round(A * 10d) / 10d;
                txtArray[9].setText(String.valueOf(A));
                if (val > 4) {
                    comboBoxes[8].setValue("HIGH");
                } else if (val < 2.5) {
                    comboBoxes[8].setValue("LOW");
                } else {
                    comboBoxes[8].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("ParsingError");
            }
        });
        txtArray[10].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[10].getText());
                if (val > 50) {
                    comboBoxes[9].setValue("HIGH");
                } else if (val < 10) {
                    comboBoxes[9].setValue("LOW");
                } else {
                    comboBoxes[9].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("ParsingError");
            }
        });

        textArea[0] = (TextArea) root.lookup("#vala0");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateSPROTEINS(Parent root) {
        txtArray = new TextField[4];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[3];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 4; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
        }
        for (int i = 0; i < 3; i++) {
            comboBoxes[i] = (ComboBox) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH", "LOW");
        }
        setEventHandlersNoGender(0, 6.2, 8.5);
        setEventHandlersNoGender(1, 2.5, 5.3);

        txtArray[2].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[2].getText());
                double A = Double.parseDouble(txtArray[1].getText());
                A = A / val;
                A = Math.round(A * 10d) / 10d;
                txtArray[3].setText(String.valueOf(A));
                if (val > 3.5) {
                    comboBoxes[2].setValue("HIGH");
                } else if (val < 2) {
                    comboBoxes[2].setValue("LOW");
                } else {
                    comboBoxes[2].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("ParsingError");
            }
        });

        textArea[0] = (TextArea) root.lookup("#vala0");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateBU(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[1];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());


        txtArray[0] = (TextField) root.lookup("#val0");
        comboBoxes[0] = (ComboBox) root.lookup("#valc0");
        setEventHandlersNoGender(0, 15, 45);

        textArea[0] = (TextArea) root.lookup("#vala0");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateLP(Parent root) {
        txtArray = new TextField[6];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[7];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 6; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
            comboBoxes[i] = (ComboBox) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH","LOW","");
        }
        comboBoxes[6] = (ComboBox) root.lookup("#valc6");
        comboBoxes[6].getItems().addAll("10","12","14");
        comboBoxes[6].setValue("12");

        setEventHandlersNoGender(0, 0, 200);
        setEventHandlersNoGender(1, 58, 159);
        txtArray[2].setOnKeyReleased(event -> calculateLP());

        textArea[0] = (TextArea) root.lookup("#vala0");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateBSP(Parent root) {
        txtArray = new TextField[6];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[6];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 6; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
            comboBoxes[i] = (ComboBox) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH","LOW","");
        }

        setEventHandlersNoGender(0, 65, 110);
        setEventHandlersNoGender(1, 0, 140);
        setEventHandlersNoGender(3, 0, 140);
        setEventHandlersNoGender(5, 0, 140);

        textArea[0] = (TextArea) root.lookup("#vala0");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateBG(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[1];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());
        txtArray = null;

        comboBoxes[0] = (ComboBox) root.lookup("#valc0");
        comboBoxes[0].getItems().addAll("O Positive", "O Negative", "A Positive", "A Negative", "B Positive", "B Negative", "AB Positive", "AB Negative");

        textArea[0] = (TextArea) root.lookup("#vala0");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void calculateLP() {

        try {
            double val = Double.parseDouble(txtArray[2].getText());
            if (selectedTest.getPatient().getGender().equalsIgnoreCase("male")) {
                if (val > 75) {
                    System.out.println("HIGH");
                    comboBoxes[2].setValue("HIGH");
                } else if (val < 27) {
                    System.out.println("LOW");
                    comboBoxes[2].setValue("LOW");
                } else {
                    comboBoxes[2].setValue("");
                }
            } else {
                if (val > 96) {
                    System.out.println("HIGH");
                    comboBoxes[2].setValue("HIGH");
                } else if (val < 33) {
                    System.out.println("LOW");
                    comboBoxes[2].setValue("LOW");
                } else {
                    comboBoxes[2].setValue("");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Parsing Error");
        }

        if (!txtArray[2].getText().isEmpty()) {
            if (txtArray[0].getText().isEmpty() || txtArray[1].getText().isEmpty()) {
                messageBox.showMessage("To calculate Enter Total Cholesterol\nand Triglycerides  first", "Attention");
                return;
            }
            double total;
            double hdl;
            double tryg;
            try {
                total = Double.parseDouble(txtArray[0].getText());
                tryg = Double.parseDouble(txtArray[1].getText());
                hdl = Double.parseDouble(txtArray[2].getText());
            } catch (Exception e) {
                messageBox.showMessage(e.getMessage(), "Error");
                return;
            }
            double ldl;
            double vldl;
            double last;

            ldl = total - (hdl + tryg / 5);
            vldl = tryg / 5;
            last = total / hdl;

            ldl = (Math.round(ldl * 10d)) / 10d;
            vldl = (Math.round(vldl * 10d)) / 10d;
            last = (Math.round(last * 10d)) / 10d;

            txtArray[3].setText(String.valueOf(ldl));
            txtArray[4].setText(String.valueOf(vldl));
            txtArray[5].setText(String.valueOf(last));
            if (ldl > 130) {
                System.out.println("HIGH");
                comboBoxes[3].setValue("HIGH");
            } else {
                comboBoxes[3].setValue("");
            }
            if (vldl > 40) {
                System.out.println("HIGH");
                comboBoxes[4].setValue("HIGH");
            } else if (vldl < 10) {
                System.out.println("LOW");
                comboBoxes[4].setValue("LOW");
            } else {
                comboBoxes[4].setValue("");
            }
            if (last > 5) {
                System.out.println("HIGH");
                comboBoxes[5].setValue("HIGH");
            } else if (last < 2) {
                System.out.println("LOW");
                comboBoxes[5].setValue("LOW");
            } else {
                comboBoxes[5].setValue("");
            }
        } else {
            txtArray[3].setText(null);
            txtArray[4].setText(null);
            txtArray[5].setText(null);
        }
    }

    private void initiateBilirubin(Parent root) {
        txtArray = new TextField[3];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[3];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 3; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
            comboBoxes[i] = (ComboBox) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH","LOW","");
        }
        setEventHandlersNoGender(0,0.2,1.2);
        setEventHandlersNoGender(1,0.0,0.5);

        textArea[0] = (TextArea) root.lookup("#vala0");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateCRP(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[1];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());
        txtArray[0] = (TextField) root.lookup("#val0");

        textArea[0] = (TextArea) root.lookup("#vala0");

        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        comboBoxes[0].getItems().addAll("Negative", "Positive");
        comboBoxes[0].setValue("Negative");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateSCHO(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[2];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());
        txtArray[0] = (TextField) root.lookup("#val0");

        textArea[0] = (TextArea) root.lookup("#vala0");

        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        comboBoxes[0].getItems().addAll("HIGH", "LOW", "");
        comboBoxes[1] = (ComboBox<String>) root.lookup("#valc1");
        comboBoxes[1].getItems().addAll("10", "12", "14");
        comboBoxes[1].setValue("12");

        setEventHandlersNoGender(0, 140, 239);

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateUHCG(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[1];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());
        txtArray = null;

        textArea[0] = (TextArea) root.lookup("#vala0");

        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        comboBoxes[0].getItems().addAll("Positive", "Negative");
        comboBoxes[0].setValue("Negative");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateSCRE(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[1];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 1; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
            comboBoxes[i] = (ComboBox<String>) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH", "LOW", "");
        }
        setEventHandlersNoGender(0, 0.6, 1.6);

        textArea[0] = (TextArea) root.lookup("#vala0");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateGammaGT(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[1];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 1; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
            comboBoxes[i] = (ComboBox<String>) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH", "LOW", "");
        }
        setEventHandlersNoGender(0, 10, 50);

        textArea[0] = (TextArea) root.lookup("#vala0");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateHB(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[1];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());
        txtArray[0] = (TextField) root.lookup("#val0");

        textArea[0] = (TextArea) root.lookup("#vala0");

        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        setEventHandlersWithGender(0, 12, 16.5, 11, 15);

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateRHF(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[1];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());
        txtArray[0] = (TextField) root.lookup("#val0");

        textArea[0] = (TextArea) root.lookup("#vala0");

        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        comboBoxes[0].getItems().addAll("Positive", "Negative");
        comboBoxes[0].setValue("Negative");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateRBS(Parent root) {
        txtArray = new TextField[2];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[1];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());
        txtArray[0] = (TextField) root.lookup("#val0");
        txtArray[1] = (TextField) root.lookup("#val1");

        textArea[0] = (TextArea) root.lookup("#vala0");

        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        setEventHandlersNoGender(0, 0, 140);

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateOTPT(Parent root) {
        txtArray = new TextField[2];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[2];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());
        txtArray[0] = (TextField) root.lookup("#val0");
        txtArray[1] = (TextField) root.lookup("#val1");

        textArea[0] = (TextArea) root.lookup("#vala0");

        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        setEventHandlersNoGender(0, 10, 35);
        comboBoxes[1] = (ComboBox<String>) root.lookup("#valc1");
        setEventHandlersNoGender(1, 10, 40);

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateHIV(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[2];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());
        txtArray = null;

        textArea[0] = (TextArea) root.lookup("#vala0");

        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        comboBoxes[0].getItems().addAll("POSITIVE", "NEGATIVE");
        comboBoxes[0].setValue("NEGATIVE");
        comboBoxes[1] = (ComboBox<String>) root.lookup("#valc1");
        comboBoxes[1].getItems().addAll("POSITIVE", "NEGATIVE");
        comboBoxes[1].setValue("NEGATIVE");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateGCT(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[1];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        txtArray[0] = (TextField) root.lookup("#val0");
        textArea[0] = (TextArea) root.lookup("#vala0");

        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        comboBoxes[0].getItems().addAll("HIGH", "LOW");
        setEventHandlersNoGender(0, 0, 140);

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateESR(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[1];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        txtArray[0] = (TextField) root.lookup("#val0");
        textArea[0] = (TextArea) root.lookup("#vala0");

        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        comboBoxes[0].getItems().addAll("HIGH", "LOW","");
        /*
        Use to Flag according to age

        txtArray[0].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[0].getText());
                int years = Period.between(selectedTest.getPatient().getDOB(), selectedTest.getDate()).getYears() - 17;
                boolean isMale = true;
                if (selectedTest.getPatient().getGender().equalsIgnoreCase("female")) {
                    isMale = false;
                }
                if (years <= 33) {
                    setEventHandlersWithGender(0, 0, 10, 0, 12);
                } else if (years <= 43) {
                    setEventHandlersWithGender(0, 0, 12, 0, 19);
                } else if (years <= 53) {
                    setEventHandlersWithGender(0, 0, 14, 0, 20);
                } else {
                    setEventHandlersWithGender(0, 0, 30, 0, 35);
                }
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");

            }
        });
         */

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateSALK(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[1];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        txtArray[0] = (TextField) root.lookup("#val0");

        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        comboBoxes[0].getItems().addAll("HIGH", "LOW");

        textArea[0] = (TextArea) root.lookup("#vala0");
        txtArray[0].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[0].getText());
                int days = Period.between(selectedTest.getPatient().getDOB(), selectedTest.getDate()).getYears() * 365 + Period.between(selectedTest.getPatient().getDOB(), selectedTest.getDate()).getDays();
                boolean isMale = true;
                if (selectedTest.getPatient().getGender().equalsIgnoreCase("female")) {
                    isMale = false;
                }
                if (days <= 30) {
                    setEventHandlersWithGender(0, 48, 406, 75, 316);
                } else if (days <= 365) {
                    setEventHandlersWithGender(0, 124, 341, 82, 383);
                } else if (days <= 1095) {
                    setEventHandlersWithGender(0, 108, 317, 104, 345);
                } else if (days <= 2190) {
                    setEventHandlersWithGender(0, 98, 297, 93, 309);
                } else if (days <= 3285) {
                    setEventHandlersWithGender(0, 69, 325, 86, 315);
                } else if (days <= 4380) {
                    setEventHandlersWithGender(0, 51, 332, 42, 362);
                } else if (days <= 5475) {
                    setEventHandlersWithGender(0, 50, 162, 74, 390);
                } else if (days <= 6570) {
                    setEventHandlersNoGender(0, 47, 119);
                } else {
                    setEventHandlersNoGender(0, 30, 120);
                }
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");

            }
        });

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateSELECTRO(Parent root) {
        txtArray = new TextField[2];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[2];

        stackPane.getChildren().add(root);
        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 2; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
        }
        for (int i = 0; i < 2; i++) {
            comboBoxes[i] = (ComboBox<String>) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH", "LOW");
        }
        textArea[0] = (TextArea) root.lookup("#vala0");
        setEventHandlersNoGender(0, 135, 145);
        setEventHandlersNoGender(1, 3.5, 5);
        //setEventHandlersNoGender(2,98,108);

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateORALGLUCOSE(Parent root) {
        txtArray = new TextField[6];
        textArea = new TextArea[2];
        comboBoxes = new ComboBox[4];

        stackPane.getChildren().add(root);

        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 6; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
        }
        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        comboBoxes[0].getItems().addAll("75", "50");
        comboBoxes[0].setValue("75");
        for (int i = 1; i <= 3; i++) {
            comboBoxes[i] = (ComboBox<String>) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH", "LOW");
        }
        textArea[0] = (TextArea) root.lookup("#vala0");
        textArea[1] = (TextArea) root.lookup("#vala1");

        txtArray[0].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[0].getText());
                double mmolL = val * 0.055d;
                mmolL = Math.round(mmolL * 10d) / 10d;
                txtArray[1].setText(String.valueOf(mmolL));
                if (val > 100) {
                    comboBoxes[1].setValue("HIGH");
                } else if (val < 70) {
                    comboBoxes[1].setValue("LOW");
                } else {
                    comboBoxes[1].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("ParsingError");
            }
        });
        txtArray[2].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[2].getText());
                double mmolL = val * 0.055d;
                mmolL = Math.round(mmolL * 10d) / 10d;
                txtArray[3].setText(String.valueOf(mmolL));
                if (val > 180) {
                    comboBoxes[2].setValue("HIGH");
                } else {
                    comboBoxes[2].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("ParsingError");
            }
        });
        txtArray[4].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[4].getText());
                double mmolL = val * 0.055d;
                mmolL = Math.round(mmolL * 10d) / 10d;
                txtArray[5].setText(String.valueOf(mmolL));
                if (val > 140) {
                    comboBoxes[3].setValue("HIGH");
                } else {
                    comboBoxes[3].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("ParsingError");
            }
        });
        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiatePPBS(Parent root) {
        txtArray = new TextField[2];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[2];

        stackPane.getChildren().add(root);

        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 2; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
        }

        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        comboBoxes[0].getItems().addAll("HIGH", "LOW","");

        comboBoxes[1] = (ComboBox<String>) root.lookup("#valc1");
        comboBoxes[1].getItems().addAll("Breakfast", "Lunch","Dinner");

        textArea[0] = (TextArea) root.lookup("#vala0");

        txtArray[0].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[0].getText());
                double mmolL = val * 0.055d;
                mmolL = Math.round(mmolL * 10d) / 10d;
                txtArray[1].setText(String.valueOf(mmolL));
                if (val > 140) {
                    comboBoxes[0].setValue("HIGH");
                } else {
                    comboBoxes[0].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("ParsingError");
            }
        });

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiatePPBSALL(Parent root) {
        txtArray = new TextField[6];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[3];

        stackPane.getChildren().add(root);

        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 6; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
        }
        for (int i = 0; i < 3; i++) {
            comboBoxes[i] = (ComboBox<String>) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH", "LOW","");
        }
        textArea[0] = (TextArea) root.lookup("#vala0");

        txtArray[0].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[0].getText());
                double mmolL = val * 0.055d;
                mmolL = Math.round(mmolL * 10d) / 10d;
                txtArray[1].setText(String.valueOf(mmolL));
                if (val > 140) {
                    comboBoxes[0].setValue("HIGH");
                } else {
                    comboBoxes[0].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("ParsingError");
            }
        });
        txtArray[2].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[2].getText());
                double mmolL = val * 0.055d;
                mmolL = Math.round(mmolL * 10d) / 10d;
                txtArray[3].setText(String.valueOf(mmolL));
                if (val > 140) {
                    comboBoxes[1].setValue("HIGH");
                } else {
                    comboBoxes[1].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("ParsingError");
            }
        });
        txtArray[4].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[4].getText());
                double mmolL = val * 0.055d;
                mmolL = Math.round(mmolL * 10d) / 10d;
                txtArray[5].setText(String.valueOf(mmolL));
                if (val > 140) {
                    comboBoxes[2].setValue("HIGH");
                } else {
                    comboBoxes[2].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("ParsingError");
            }
        });
        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateFBS1(Parent root) {
        txtArray = new TextField[2];
        textArea = new TextArea[2];
        comboBoxes = new ComboBox[2];

        stackPane.getChildren().add(root);

        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        txtArray[0] = (TextField) root.lookup("#val0");
        txtArray[1] = (TextField) root.lookup("#val1");
        textArea[0] = (TextArea) root.lookup("#vala0");
        textArea[1] = (TextArea) root.lookup("#vala1");
        comboBoxes[0] = (ComboBox<String>) root.lookup("#valc0");
        comboBoxes[0].getItems().addAll("HIGH", "LOW");
        comboBoxes[1] = (ComboBox<String>) root.lookup("#valc1");
        comboBoxes[1].getItems().addAll("10","12","14");
        comboBoxes[1].setValue("12");
        txtArray[0].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[0].getText());
                double mmolL = val * 0.055d;
                mmolL = Math.round(mmolL * 10d) / 10d;
                txtArray[1].setText(String.valueOf(mmolL));
                if (val > 125) {
                    comboBoxes[0].setValue("HIGH");
                } else if (val < 70) {
                    comboBoxes[0].setValue("LOW");
                } else {
                    comboBoxes[0].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("ParsingError");
            }
        });
        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateUFR(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[16];

        stackPane.getChildren().add(root);

        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        txtArray[0] = (TextField) editPane.lookup("#val0");

        for (int i = 0; i < 16; i++) {
            comboBoxes[i] = (ComboBox<String>) root.lookup("#valc" + i);
        }

        comboBoxes[0].getItems().addAll("Pale Yellow", "Yellow", "Dark Yellow", "Slightly Blood Stained", "Grossly Blood Stained");
        comboBoxes[0].setValue("Pale Yellow");
        comboBoxes[1].getItems().addAll("Clear", "Slightly Turbid", "Turbid");
        comboBoxes[1].setValue("Clear");
        comboBoxes[2].getItems().addAll("Acidic", "Alkaline");
        comboBoxes[2].setValue("Acidic");
        comboBoxes[3].getItems().addAll("Nil", "Faint Trace", "Trace", "+", "++", "+++");
        comboBoxes[3].setValue("Nil");
        comboBoxes[4].getItems().addAll("Nil", "Green(0.5g%)", "Yellow(1g%)", "Orange(1.5g%)", "Brick Red(>2g%)");
        comboBoxes[4].setValue("Nil");
        comboBoxes[5].getItems().addAll("Nil", "Positive");
        comboBoxes[5].setValue("Nil");
        comboBoxes[6].getItems().addAll("Nil", "Positive");
        comboBoxes[6].setValue("Nil");
        comboBoxes[7].getItems().addAll("Normal Amounts", "Increased");
        comboBoxes[7].setValue("Normal Amounts");
        comboBoxes[8].getItems().addAll("Occasional", "1 - 3", "3 - 5", "Mod. Field Full", "Field Full");
        comboBoxes[8].setValue("Occasional");
        comboBoxes[9].getItems().addAll("Nil", "Occasional", "1 - 3", "3 - 5", "Mod. Field Full", "Field Full");
        comboBoxes[9].setValue("Nil");
        comboBoxes[10].getItems().addAll("+", "++", "+++");
        comboBoxes[10].setValue("+");
        comboBoxes[11].getItems().addAll("Nil", "Positive");
        comboBoxes[11].setValue("Nil");
        comboBoxes[12].getItems().addAll("Not Seen", "Granular", "Red cell casts", "Pus cell casts");
        comboBoxes[12].setValue("Not Seen");
        comboBoxes[13].getItems().addAll("Not Seen", "Calcium oxalate", "Calcium carbonate", "Triple phosphate");
        comboBoxes[13].setValue("Not Seen");
        comboBoxes[14].getItems().addAll("Not Seen", "Yeast cells");
        comboBoxes[14].setValue("Not Seen");
        comboBoxes[15].getItems().addAll("Nil", "Positive");
        comboBoxes[15].setValue("Nil");

        textArea[0] = (TextArea) root.lookup("#vala0");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateSFR(Parent root) {
        txtArray = new TextField[1];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[11];

        stackPane.getChildren().add(root);

        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        txtArray = null;

        for (int i = 0; i < 11; i++) {
            comboBoxes[i] = (ComboBox<String>) root.lookup("#valc" + i);
        }

        comboBoxes[0].getItems().addAll("Greenish Yellow", "Yellow", "Pale Yellow", "Pale Brown", "Black", "Pale Black");
        comboBoxes[0].setValue("Greenish Yellow");
        comboBoxes[1].getItems().addAll("Watery", "Solid", "Semi Solid");
        comboBoxes[1].setValue("Semi Solid");
        comboBoxes[2].getItems().addAll("Nil", "Green(0.5g%)", "Yellow(1g%)", "Orange(1.5g%)", "Brick Red(>2g%)");
        comboBoxes[2].setValue("Nil");
        comboBoxes[3].getItems().addAll("Not Detected", "Detected");
        comboBoxes[3].setValue("Not Detected");
        comboBoxes[4].getItems().addAll("Nil", "Occasional", "1 - 3", "3 - 5", "Mod. Field Full", "Field Full");
        comboBoxes[4].setValue("Nil");
        comboBoxes[5].getItems().addAll("Nil", "Occasional", "1 - 3", "3 - 5", "Mod. Field Full", "Field Full");
        comboBoxes[5].setValue("Nil");
        comboBoxes[6].getItems().addAll("Nil","+", "++", "+++");
        comboBoxes[6].setValue("Nil");
        comboBoxes[7].getItems().addAll("Nil", "+");
        comboBoxes[7].setValue("Nil");
        comboBoxes[8].getItems().addAll("Nil", "+");
        comboBoxes[8].setValue("Nil");
        comboBoxes[9].getItems().addAll("Nil", "+");
        comboBoxes[9].setValue("Nil");
        comboBoxes[10].getItems().addAll("Nil", "+");
        comboBoxes[10].setValue("Nil");

        textArea[0] = (TextArea) root.lookup("#vala0");

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateFBC(Parent root) {
        txtArray = new TextField[18];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[13];

        stackPane.getChildren().add(root);

        txtArray[0] = (TextField) root.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) root.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 18; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
            if (i > 12) continue;
            comboBoxes[i] = (ComboBox<String>) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH", "LOW","");
        }
        textArea[0] = (TextArea) root.lookup("#vala0");
        /*
        This code is used to differ reference values depending on patient's age

        int ageY = Period.between(selectedTest.getPatient().getDOB(),selectedTest.getDate()).getYears();
        int ageM = Period.between(selectedTest.getPatient().getDOB(),selectedTest.getDate()).getMonths();
        int ageD = Period.between(selectedTest.getPatient().getDOB(),selectedTest.getDate()).getDays();

        if (ageY ==0 && ageM ==0 && ageD <= 28) {
            setEventHandlersNoGender(0,4,20);
        } else if (ageY <= 13) {
            setEventHandlersNoGender(0, 4, 12);
        } else {
            setEventHandlersNoGender(0, 4, 10);
        }

         */

        setEventHandlersNoGender(0, 4, 11);
        txtArray[1].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[1].getText());
                double wbc = Double.parseDouble(txtArray[0].getText());

                if (val > 75) {
                    System.out.println("HIGH");
                    comboBoxes[1].setValue("HIGH");
                } else if (val < 20) {
                    System.out.println("LOW");
                    comboBoxes[1].setValue("LOW");
                } else {
                    comboBoxes[1].setValue("");
                }

                double ab = Math.round(wbc * val) / 100d;
                txtArray[13].setText(String.valueOf(ab));
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");
            }
        });
        txtArray[2].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[2].getText());
                double wbc = Double.parseDouble(txtArray[0].getText());

                if (val > 45) {
                    System.out.println("HIGH");
                    comboBoxes[2].setValue("HIGH");
                } else if (val < 15) {
                    System.out.println("LOW");
                    comboBoxes[2].setValue("LOW");
                } else {
                    comboBoxes[2].setValue("");
                }

                double ab = Math.round(wbc * val) / 100d;
                txtArray[14].setText(String.valueOf(ab));
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");
            }
        });
        txtArray[3].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[3].getText());
                double wbc = Double.parseDouble(txtArray[0].getText());
                if (val > 4) {
                    System.out.println("HIGH");
                    comboBoxes[3].setValue("HIGH");
                } else if (val < 0) {
                    System.out.println("LOW");
                    comboBoxes[3].setValue("LOW");
                } else {
                    comboBoxes[3].setValue("");
                }

                double ab = Math.round(wbc * val) / 100d;
                txtArray[15].setText(String.valueOf(ab));
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");
            }
        });
        txtArray[4].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[4].getText());
                double wbc = Double.parseDouble(txtArray[0].getText());
                if (val > 8) {
                    System.out.println("HIGH");
                    comboBoxes[4].setValue("HIGH");
                } else if (val < 2) {
                    System.out.println("LOW");
                    comboBoxes[4].setValue("LOW");
                } else {
                    comboBoxes[4].setValue("");
                }

                double ab = Math.round(wbc * val) / 100d;
                txtArray[16].setText(String.valueOf(ab));
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");
            }
        });
        txtArray[5].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[5].getText());
                double wbc = Double.parseDouble(txtArray[0].getText());
                if (val > 1) {
                    System.out.println("HIGH");
                    comboBoxes[5].setValue("HIGH");
                } else if (val < 0) {
                    System.out.println("LOW");
                    comboBoxes[5].setValue("LOW");
                } else {
                    comboBoxes[5].setValue("");
                }

                double ab = Math.round(wbc * val) / 100d;
                txtArray[17].setText(String.valueOf(ab));
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");
            }
        });
        setEventHandlersWithGender(6, 13, 17.5, 11.5, 16);
        setEventHandlersWithGender(7, 40, 52,37,47);

        txtArray[8].setOnKeyReleased(event -> {
            if (txtArray[6].getText().isEmpty() || txtArray[7].getText().isEmpty()) {
                messageBox.showMessage("To calculate Enter Hb\nand P.C.V. first", "Attention");
                return;
            }
            if (!txtArray[8].getText().isEmpty()) {
                double hb;
                double pcv;
                double rbc;
                try {
                    hb = Double.parseDouble(txtArray[6].getText());
                    pcv = Double.parseDouble(txtArray[7].getText());
                    rbc = Double.parseDouble(txtArray[8].getText());
                } catch (Exception e) {
                    messageBox.showMessage(e.getMessage(), "Error");
                    return;
                }

                if (selectedTest.getPatient().getGender().equalsIgnoreCase("male")) {
                    if (rbc > 6.5) {
                        System.out.println("HIGH");
                        comboBoxes[8].setValue("HIGH");
                    } else if (rbc < 4.5) {
                        System.out.println("LOW");
                        comboBoxes[8].setValue("LOW");
                    } else {
                        comboBoxes[8].setValue("");
                    }
                } else {
                    if (rbc > 5.8) {
                        System.out.println("HIGH");
                        comboBoxes[8].setValue("HIGH");
                    } else if (rbc < 3.8) {
                        System.out.println("LOW");
                        comboBoxes[8].setValue("LOW");
                    } else {
                        comboBoxes[8].setValue("");
                    }
                }



                double mcv = ((pcv / rbc) * 10);
                double mch = (hb / rbc) * 10;
                double mchc = (hb / pcv) * 100;

                mcv = Math.round(mcv * 10d) / 10d;
                mch = Math.round(mch * 10d) / 10d;
                mchc = Math.round(mchc * 10d) / 10d;

                txtArray[11].setText(String.valueOf(mcv));
                txtArray[10].setText(String.valueOf(mch));
                txtArray[9].setText(String.valueOf(mchc));

                if (mcv > 100) {
                    System.out.println("HIGH");
                    comboBoxes[11].setValue("HIGH");
                } else if (mcv < 80) {
                    System.out.println("LOW");
                    comboBoxes[11].setValue("LOW");
                } else {
                    comboBoxes[11].setValue("");
                }

                if (mch > 32) {
                    System.out.println("HIGH");
                    comboBoxes[10].setValue("HIGH");
                } else if (mch < 27) {
                    System.out.println("LOW");
                    comboBoxes[10].setValue("LOW");
                } else {
                    comboBoxes[10].setValue("");
                }

                if (mchc > 36) {
                    System.out.println("HIGH");
                    comboBoxes[9].setValue("HIGH");
                } else if (mchc < 32) {
                    System.out.println("LOW");
                    comboBoxes[9].setValue("LOW");
                } else {
                    comboBoxes[9].setValue("");
                }
            } else {
                txtArray[9].setText(null);
                txtArray[10].setText(null);
                txtArray[11].setText(null);

                comboBoxes[9].setValue("");
                comboBoxes[10].setValue("");
                comboBoxes[11].setValue("");
            }
        });
        setEventHandlersNoGender(12,150,450);

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }

    private void initiateWBCDC(Parent root) {
        txtArray = new TextField[11];
        textArea = new TextArea[1];
        comboBoxes = new ComboBox[6];

        stackPane.getChildren().add(root);

        txtArray[0] = (TextField) editPane.lookup("#txtName");
        txtArray[0].setText(selectedTest.getName());

        txtArray[0] = (TextField) editPane.lookup("#txtDate");
        txtArray[0].setText(selectedTest.getDate().toString());

        for (int i = 0; i < 11; i++) {
            txtArray[i] = (TextField) root.lookup("#val" + i);
            if (i > 5) continue;
            comboBoxes[i] = (ComboBox<String>) root.lookup("#valc" + i);
            comboBoxes[i].getItems().addAll("HIGH", "LOW");
        }
        textArea[0] = (TextArea) root.lookup("#vala0");

        setEventHandlersNoGender(0, 4, 10);


        txtArray[1].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[1].getText());
                double wbc = Double.parseDouble(txtArray[0].getText());
                if (val > 70) {
                    System.out.println("HIGH");
                    comboBoxes[1].setValue("HIGH");
                } else if (val < 50) {
                    System.out.println("LOW");
                    comboBoxes[1].setValue("LOW");
                } else {
                    comboBoxes[1].setValue("");
                }

                double ab = Math.round(wbc * val) / 100d;
                txtArray[5].setText(String.valueOf(ab));
                if (ab > 7) {
                    System.out.println("HIGH");
                    comboBoxes[5].setValue("HIGH");
                } else if (ab < 2) {
                    System.out.println("LOW");
                    comboBoxes[5].setValue("LOW");
                } else {
                    comboBoxes[5].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");
            }
        });
        txtArray[2].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[2].getText());
                double wbc = Double.parseDouble(txtArray[0].getText());
                if (val > 45) {
                    System.out.println("HIGH");
                    comboBoxes[2].setValue("HIGH");
                } else if (val < 15) {
                    System.out.println("LOW");
                    comboBoxes[2].setValue("LOW");
                } else {
                    comboBoxes[2].setValue("");
                }

                double ab = Math.round(wbc * val) / 100d;
                txtArray[6].setText(String.valueOf(ab));
                if (ab > 4) {
                    System.out.println("HIGH");
                    comboBoxes[6].setValue("HIGH");
                } else if (ab < 0.8) {
                    System.out.println("LOW");
                    comboBoxes[6].setValue("LOW");
                } else {
                    comboBoxes[6].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");
            }
        });
        txtArray[3].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[3].getText());
                double wbc = Double.parseDouble(txtArray[0].getText());
                if (val > 5) {
                    System.out.println("HIGH");
                    comboBoxes[3].setValue("HIGH");
                } else if (val < 0.5) {
                    System.out.println("LOW");
                    comboBoxes[3].setValue("LOW");
                } else {
                    comboBoxes[3].setValue("");
                }

                double ab = Math.round(wbc * val) / 100d;
                txtArray[7].setText(String.valueOf(ab));
                if (ab > 0.5) {
                    System.out.println("HIGH");
                    comboBoxes[7].setValue("HIGH");
                } else if (ab < 0.02) {
                    System.out.println("LOW");
                    comboBoxes[7].setValue("LOW");
                } else {
                    comboBoxes[7].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");
            }
        });
        txtArray[4].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[4].getText());
                double wbc = Double.parseDouble(txtArray[0].getText());
                if (val > 12) {
                    System.out.println("HIGH");
                    comboBoxes[4].setValue("HIGH");
                } else if (val < 3) {
                    System.out.println("LOW");
                    comboBoxes[4].setValue("LOW");
                } else {
                    comboBoxes[4].setValue("");
                }

                double ab = Math.round(wbc * val) / 100d;
                txtArray[8].setText(String.valueOf(ab));
                if (ab > 1.2) {
                    System.out.println("HIGH");
                    comboBoxes[8].setValue("HIGH");
                } else if (ab < 0.12) {
                    System.out.println("LOW");
                    comboBoxes[8].setValue("LOW");
                } else {
                    comboBoxes[8].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");
            }
        });

        ((Button) root.lookup("#btnSave")).setOnAction(event -> btnSaveClickedNew());
    }



    private void setEventHandlersNoGender(int id, double lowValue, double highValue) {
        txtArray[id].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[id].getText());
                if (val > highValue) {
                    System.out.println("HIGH");
                    comboBoxes[id].setValue("HIGH");
                } else if (val < lowValue) {
                    System.out.println("LOW");
                    comboBoxes[id].setValue("LOW");
                } else {
                    comboBoxes[id].setValue("");
                }
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");
            }
        });
    }
    private void setEventHandlersWithGender(int id, double lowValueM, double highValueM, double lowValueF, double highValueF) {
        txtArray[id].setOnKeyReleased(event -> {
            try {
                double val = Double.parseDouble(txtArray[id].getText());
                if (selectedTest.getPatient().getGender().equalsIgnoreCase("male")) {
                    if (val > highValueM) {
                        System.out.println("HIGH");
                        comboBoxes[id].setValue("HIGH");
                    } else if (val < lowValueM) {
                        System.out.println("LOW");
                        comboBoxes[id].setValue("LOW");
                    } else {
                        comboBoxes[id].setValue("");
                    }
                } else {
                    if (val > highValueF) {
                        System.out.println("HIGH");
                        comboBoxes[id].setValue("HIGH");
                    } else if (val < lowValueF) {
                        System.out.println("LOW");
                        comboBoxes[id].setValue("LOW");
                    } else {
                        comboBoxes[id].setValue("");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Parsing Error");
            }
        });
    }

    @FXML
    void loadNewTest() {
        SequentialTransition sqTra = ButtonAnimation.transitionByFading(btnNewTest);
        sqTra.play();

        Stage pane = (Stage) testList.getScene().getWindow();
        pane.focusedProperty().addListener(observable -> refreshTestList());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AddSelectPatientFrame.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("New Test");
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
    @FXML
    void loadPrintReport() {
        SequentialTransition sqTra = ButtonAnimation.transitionByFading(btnLoadPrintReport);
        sqTra.play();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("printReport.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Print Report");
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
    @FXML
    void loadDevSupport() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("contactDeveloperStage.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("SUM Software Solutions");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(String.valueOf(Launcher.class.getResource("styles.css")));
            stage.setScene(scene);            stage.setResizable(false);
            stage.getIcons().add(new Image("microscope.png"));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void loadAdminCinfig() {
        if (messageBox.userAuthentication()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("AdminConfigStage.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Administrator Configuration");
                Scene scene = new Scene(root);
                scene.getStylesheets().add(String.valueOf(Launcher.class.getResource("styles.css")));
                stage.setScene(scene);                stage.setResizable(false);
                stage.getIcons().add(new Image("microscope.png"));
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            messageBox.showMessage("Username or Password mismatch", "Authenticator");
        }
    }
    @FXML
    void loadTestPrice() {
        if (LoginController.isAdmin()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("testPriceStage.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Test Prices");
                Scene scene = new Scene(root);
                scene.getStylesheets().add(String.valueOf(Launcher.class.getResource("styles.css")));
                stage.setScene(scene);
                stage.setResizable(false);
                stage.getIcons().add(new Image("microscope.png"));
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            messageBox.showMessage("Admin Login Required", "Authenticator");
        }
    }
    @FXML
    void loadTestSpecimen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("testSpecimenStage.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Test Prices");
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
    @FXML
    void loadPatientAnalysis() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("patientAnalysisStage.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Patient Analysis");
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
    @FXML
    void loadTestAnalysis() {
        if (LoginController.isAdmin()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("testAnalysisStage.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Test Analysis");
                Scene scene = new Scene(root);
                scene.getStylesheets().add(String.valueOf(Launcher.class.getResource("styles.css")));
                stage.setScene(scene);
                stage.setResizable(false);
                stage.getIcons().add(new Image("microscope.png"));
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            messageBox.showMessage("Admin Login Required", "Authenticator");
        }
    }
    @FXML
    void loadFinAnalysis() {
        if (LoginController.isAdmin()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("financialAnalysisStage.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Financial Analysis");
                Scene scene = new Scene(root);
                scene.getStylesheets().add(String.valueOf(Launcher.class.getResource("styles.css")));
                stage.setScene(scene);
                stage.setResizable(false);
                stage.getIcons().add(new Image("microscope.png"));
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            messageBox.showMessage("Admin Login Required", "Authenticator");
        }
    }
    @FXML
    void loadReportBaseConfig() {
        if (LoginController.isAdmin()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("ReportBaseConfigStage.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Reportbase Configuration");
                Scene scene = new Scene(root);
                scene.getStylesheets().add(String.valueOf(Launcher.class.getResource("styles.css")));
                stage.setScene(scene);
                stage.setResizable(false);
                stage.getIcons().add(new Image("microscope.png"));
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            messageBox.showMessage("Admin Login Required", "Authenticator");
        }
    }
    @FXML
    void loadManageDoctors() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("manageDoctorsStage.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Manage Doctors");
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
    @FXML
    void loadManagePatients() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ManagePatientsStage.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Manage Doctors");
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
    @FXML
    void loadReceiptManager() {
        SequentialTransition sqTra = ButtonAnimation.transitionByFading(btnInvoiceManager);
        sqTra.play();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ReceiptManagerStage.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Receipt Manager");
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
    @FXML
    void loadEgfrCalculator() {
        SequentialTransition sqTra = ButtonAnimation.transitionByFading(btnEGFR);
        sqTra.play();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("EgfrCalculatorStage.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("EGFR Calculator");
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

    private void btnSaveClickedNew() {
        ArrayList<String> dataToSave = new ArrayList<>();
        if (txtArray != null) {
            for (TextField textField : txtArray) {
                if (textField.getText().isEmpty()) {
                    dataToSave.add(" ");
                } else {
                    dataToSave.add(textField.getText());
                }
            }
        }
        if (comboBoxes != null) {
            for (ComboBox<String> comboBox : comboBoxes) {
                if (comboBox.isEditable()) {
                    if (comboBox.getEditor().getText().isEmpty()) {
                        dataToSave.add(" ");
                    } else {
                        dataToSave.add(comboBox.getEditor().getText());
                    }
                } else {
                    dataToSave.add(comboBox.getValue());
                }
            }
        }
        if (textArea != null) {
            for (TextArea textArea1 : textArea) {
                if (textArea1.getText().isEmpty()) {
                    dataToSave.add(" ");
                } else {
                    dataToSave.add(textArea1.getText());
                }
            }
        }
        Database.saveReportNew(selectedTest, dataToSave);
        stackPane.getChildren().clear();
        txtArray = null;
        textArea = null;
        comboBoxes = null;
        refreshTestList();
        stackPane.getChildren().clear();
    }
    private void btnSaveClicked() {
        if (textArea != null) {
            Database.saveCulture(txtArray, textArea, selectedTest);
            refreshTestList();
            stackPane.getChildren().clear();
            textArea = null;
            return;
        }
        Database.saveReport(txtArray, selectedTest);
        refreshTestList();
        stackPane.getChildren().clear();
    }
    private void calculateFBC() {
        if (txtArray[9].getText().isEmpty() || txtArray[10].getText().isEmpty()) {
            messageBox.showMessage("To calculate Enter RBC\nand HGB. first", "Attention");
            return;
        }
        if (!txtArray[11].getText().isEmpty()) {
            double hb;
            double pcv;
            double rbc;
            try {
                hb = Double.parseDouble(txtArray[10].getText());
                pcv = Double.parseDouble(txtArray[11].getText());
                rbc = Double.parseDouble(txtArray[9].getText());
            } catch (Exception e) {
                messageBox.showMessage(e.getMessage(), "Error");
                return;
            }
            DecimalFormat nf = new DecimalFormat();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);

            double mcv = ((pcv / rbc) * 10);
            double mch = (hb / rbc) * 10;
            double mchc = (hb / mch) * 100;
            txtArray[12].setText(nf.format(mcv));
            txtArray[13].setText(nf.format(mch));
            txtArray[14].setText(nf.format(mchc));
        } else {
            txtArray[12].setText(null);
            txtArray[13].setText(null);
            txtArray[14].setText(null);
        }
    }
}
