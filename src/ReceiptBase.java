

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReceiptBase {
    private AnchorPane root = new AnchorPane();
    private AnchorPane lastPartAnchorPane = new AnchorPane();
    private Label lblHeader1 = new Label();
    private Label lblHeader2 = new Label();
    private Label lblHeader3 = new Label();
    private Label lblHeader4 = new Label();
    private Label lblHeader5 = new Label();
    private Label lblHeader6 = new Label();
    private Label lblAddress = new Label();
    private Label lblPatientDetailsHeader = new Label();
    private Label lblName = new Label();
    private Label lblAge = new Label();
    private Label lblPhoneNumber = new Label();
    private Label lblDate = new Label();
    private Label lblNameData = new Label();
    private Label lblAgeData = new Label();
    private Label lblPhoneNumberData = new Label();
    private Label lblDateData = new Label();
    private Label lblTest = new Label();
    private Label lblCost = new Label();
    private Label[] lblTestNames;
    private Label[] lblTestCosts;
    private Label lblTotal = new Label();
    private Label lblAdvance = new Label();
    private Label lblBalance = new Label();
    private Label lblTotalData = new Label();
    private Label lblAdvanceData = new Label();
    private Label lblBalanceData = new Label();
    private Line line1 = new Line();
    private Line line2 = new Line();
    private Line line3 = new Line();
    private Line line4 = new Line();
    private Line line5 = new Line();
    private VBox vBox = new VBox();
    private HBox[] hBoxes;

    private ObservableList<TestForRecipt> testForRecipts;
    private ObservableList<tableTests> testPrices;
    private patient selectedPatient;
    private int advance;
    private LocalDate date;
    private int total = 0;

    public ReceiptBase(ObservableList<TestForRecipt> testForRecipts, patient selectedPatient, int advance, LocalDate date) {
        this.testForRecipts = testForRecipts;
        this.selectedPatient = selectedPatient;
        this.advance = advance;
        this.date = date;
    }
//    public ReceiptBase(ObservableList<TestForRecipt> testForRecipts, patient selectedPatient, int advance, LocalDate date) {
//        this.testForRecipts = testForRecipts;
//        this.selectedPatient = selectedPatient;
//        this.advance = advance;
//        this.date = date;
//    }

    public void initialize() {
        // Retrieve Test Costs
        testPrices = Database.getTableTestsList();
        // Setting up number of Labels for table in the receipt
        lblTestNames = new Label[testForRecipts.size()];
        lblTestCosts = new Label[testForRecipts.size()];
        hBoxes = new HBox[testForRecipts.size()];

        //Setting up Page size(A4)
        root.setPrefWidth(584);
        root.setPrefHeight(820);

        setReceiptAppearance();
        setPatientDetails();
        int i = 0;
        for (TestForRecipt test : testForRecipts) {
            lblTestNames[i] = new Label();
            lblTestNames[i].setText(test.getTestName());
            lblTestNames[i].setPrefWidth(457);
            lblTestCosts[i] = new Label();
            lblTestCosts[i].setText("Rs. "+testPrices.filtered(tableTests -> tableTests.getTestCode().equalsIgnoreCase(test.getTestCode())).get(0).getPrice());
            total += testPrices.filtered(tableTests -> tableTests.getTestCode().equalsIgnoreCase(test.getTestCode())).get(0).getPriceInt();
            hBoxes[i] = new HBox();
            hBoxes[i].setAlignment(Pos.CENTER);
            hBoxes[i].setPrefWidth(545);
            hBoxes[i].getChildren().addAll(lblTestNames[i],lblTestCosts[i]);
            vBox.getChildren().add(hBoxes[i]);
            i++;
        }
        addLastPartToVBox();
        calculateFinal();
        // root should send to printer
        Printing printing = new Printing();
        printing.sendReceiptToPrinter(root);
    }

    private void setReceiptAppearance() {
        //Setting up Receipt Appearance
        lblHeader1.setText("M");
        Font font = new Font("Broadway",36);
        lblHeader1.setFont(font);
        AnchorPane.setLeftAnchor(lblHeader1,98d);
        AnchorPane.setBottomAnchor(lblHeader1,725d);

        lblHeader3.setText("L");
        lblHeader3.setFont(font);
        AnchorPane.setLeftAnchor(lblHeader3,120d);
        AnchorPane.setBottomAnchor(lblHeader3,696d);

        lblHeader2.setText("edi");
        font = new Font("System",36);
        lblHeader2.setFont(font);
        AnchorPane.setLeftAnchor(lblHeader2,132d);
        AnchorPane.setBottomAnchor(lblHeader2,719d);

        lblHeader4.setText("ab");
        lblHeader4.setFont(font);
        AnchorPane.setLeftAnchor(lblHeader4,145d);
        AnchorPane.setBottomAnchor(lblHeader4,690d);

        lblAddress.setText("Kamburupitiya Road, Matara.");
        AnchorPane.setLeftAnchor(lblAddress,29d);
        AnchorPane.setBottomAnchor(lblAddress,687d);

        lblHeader5.setText("MEDICAL");
        font = new Font("Britannic bold",36);
        lblHeader5.setFont(font);
        AnchorPane.setLeftAnchor(lblHeader5,394d);
        AnchorPane.setBottomAnchor(lblHeader5,657d);
        AnchorPane.setRightAnchor(lblHeader5,50d);
        AnchorPane.setTopAnchor(lblHeader5,127d);

        lblHeader6.setText("INVOICE");
        font = new Font("Arial",24);
        lblHeader6.setFont(font);
        AnchorPane.setLeftAnchor(lblHeader6,437d);
        AnchorPane.setBottomAnchor(lblHeader6,638d);
        AnchorPane.setRightAnchor(lblHeader6,48d);
        AnchorPane.setTopAnchor(lblHeader6,155d);

        lblPatientDetailsHeader.setText("Patient Details :");
        font = new Font("System",14);
        lblPatientDetailsHeader.setFont(font);
        AnchorPane.setLeftAnchor(lblPatientDetailsHeader,29d);
        AnchorPane.setTopAnchor(lblPatientDetailsHeader,182d);

        lblName.setText("Name :");
        AnchorPane.setLeftAnchor(lblName,60d);
        AnchorPane.setTopAnchor(lblName,199d);

        lblAge.setText("Age :");
        AnchorPane.setLeftAnchor(lblAge,60d);
        AnchorPane.setTopAnchor(lblAge,216d);

        lblPhoneNumber.setText("Phone Number :");
        AnchorPane.setLeftAnchor(lblPhoneNumber,60d);
        AnchorPane.setTopAnchor(lblPhoneNumber,233d);

        lblDate.setText("Date :");
        AnchorPane.setLeftAnchor(lblDate,60d);
        AnchorPane.setTopAnchor(lblDate,250d);

        font = new Font("Courier New",12);

        lblNameData.setFont(font);
        lblNameData.setStyle("-fx-font-style : italic; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(lblNameData,201d);
        AnchorPane.setLeftAnchor(lblNameData,101d);

        lblAgeData.setFont(font);
        lblAgeData.setStyle("-fx-font-style : italic; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(lblAgeData,218d);
        AnchorPane.setLeftAnchor(lblAgeData,91d);

        lblPhoneNumberData.setFont(font);
        lblPhoneNumberData.setStyle("-fx-font-style : italic; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(lblPhoneNumberData,235d);
        AnchorPane.setLeftAnchor(lblPhoneNumberData,150d);

        lblDateData.setFont(font);
        lblDateData.setStyle("-fx-font-style : italic; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(lblDateData,252d);
        AnchorPane.setLeftAnchor(lblDateData,101d);

        line1.setStrokeWidth(1d);
        line1.setStartX(0);
        line1.setEndX(570);
        AnchorPane.setLeftAnchor(line1,5d);
        AnchorPane.setTopAnchor(line1,270d);

        font = new Font("System",14);

        lblTest.setText("Test");
        lblTest.setFont(font);
        lblTest.setStyle("-fx-font-weight: bold;");
        AnchorPane.setLeftAnchor(lblTest,29d);
        AnchorPane.setTopAnchor(lblTest,272d);

        lblCost.setText("Cost");
        lblCost.setFont(font);
        lblCost.setStyle("-fx-font-weight: bold;");
        AnchorPane.setLeftAnchor(lblCost,489d);
        AnchorPane.setTopAnchor(lblCost,272d);

        line2.setStrokeWidth(1d);
        line2.setStartX(0);
        line2.setEndX(570);
        AnchorPane.setLeftAnchor(line2,5d);
        AnchorPane.setTopAnchor(line2,290d);

        vBox.setAlignment(Pos.TOP_LEFT);
        AnchorPane.setLeftAnchor(vBox,4d);
        AnchorPane.setRightAnchor(vBox,4d);
        AnchorPane.setTopAnchor(vBox,293d);

        //this part should be added last into the VBox
        lastPartAnchorPane.setPrefHeight(82);
        lastPartAnchorPane.setPrefWidth(562);

        line3.setStrokeWidth(1d);
        line3.setStartX(0);
        line3.setEndX(570);
        AnchorPane.setLeftAnchor(line3,1d);
        AnchorPane.setTopAnchor(line3,3d);

        font = new Font("System",14);

        lblTotal.setText("Total");
        lblTotal.setFont(font);
        lblTotal.setStyle("-fx-font-weight: bold;");
        AnchorPane.setTopAnchor(lblTotal,12d);
        AnchorPane.setLeftAnchor(lblTotal,425d);

        AnchorPane.setLeftAnchor(lblTotalData,497d);
        AnchorPane.setTopAnchor(lblTotalData,12d);

        lblAdvance.setText("Advance");
        lblAdvance.setFont(font);
        lblAdvance.setStyle("-fx-font-weight: bold;");
        AnchorPane.setTopAnchor(lblAdvance,32d);
        AnchorPane.setLeftAnchor(lblAdvance,425d);

        AnchorPane.setLeftAnchor(lblAdvanceData,497d);
        AnchorPane.setTopAnchor(lblAdvanceData,33d);

        line4.setStrokeWidth(1d);
        line4.setStartX(0);
        line4.setEndX(150);
        AnchorPane.setLeftAnchor(line4,417d);
        AnchorPane.setTopAnchor(line4,50d);

        lblBalance.setText("Balance");
        lblBalance.setFont(font);
        lblBalance.setStyle("-fx-font-weight: bold;");
        AnchorPane.setTopAnchor(lblBalance,52d);
        AnchorPane.setLeftAnchor(lblBalance,425d);

        AnchorPane.setLeftAnchor(lblBalanceData,497d);
        AnchorPane.setTopAnchor(lblBalanceData,54d);

        line5.setStrokeWidth(1d);
        line5.setStartX(0);
        line5.setEndX(150);
        AnchorPane.setLeftAnchor(line5,417d);
        AnchorPane.setTopAnchor(line5,73d);

        lastPartAnchorPane.getChildren().addAll(line3,lblTotal,lblTotalData,lblAdvance,lblAdvanceData,line4,lblBalance,lblBalanceData,line5);
        root.getChildren().addAll(lblHeader1,lblHeader2,lblHeader3,lblHeader4,lblHeader5,lblHeader6,lblAddress,lblPatientDetailsHeader,lblName,lblNameData,
                lblAge,lblAgeData,lblPhoneNumber,lblPhoneNumberData,lblDate,lblDateData,line1,lblTest,lblCost,line2,vBox);
    }
    private void addLastPartToVBox() {
        vBox.getChildren().add(lastPartAnchorPane);
    }
    private void setPatientDetails() {
        lblNameData.setText(selectedPatient.getName());
        lblAgeData.setText(String.valueOf(selectedPatient.getAge()));
        lblPhoneNumberData.setText(String.valueOf(selectedPatient.getContactNumber()));
        lblDateData.setText(date.toString());
    }
    private void calculateFinal() {
        lblTotalData.setText("Rs. " + total);
        lblAdvanceData.setText("Rs. " + advance);
        lblBalanceData.setText("Rs. " + (total - advance));
    }
}