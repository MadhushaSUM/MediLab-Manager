

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalTime;


public class Printing {
    private Label label;
    private Parent root;

    public static AnchorPane base;
    public static ObservableList<Parent> parentCollection = FXCollections.observableArrayList();
    public static ObservableList<String> testOrder = FXCollections.observableArrayList();
    public static ObservableList<test> reportList;
    public static ReportBase reportBase;

    public void printReport(ObservableList<test> reportList) throws IOException {
        for (test report : reportList) {
//            try {
//                root = FXMLLoader.load(getClass().getResource(report.getTest().toLowerCase() + ".fxml"));
//            } catch (NullPointerException e) {
//                messageBox.showMessage(report.getTest() + " is not Supported\nfor Stand-Alone Report", "Illegal");
//                continue;
//            }
//            try {
//                ResultSet resultSet = Database.getPatientDetailsToPrint(report);
//                while (resultSet.next()) {
//                    label = (Label) root.lookup("#lblName");
//                    if (label != null) label.setText(resultSet.getString("Name"));
//
//                    label = (Label) root.lookup("#lblGender");
//                    if (label != null) label.setText(resultSet.getString("Gender"));
//
//                    label = (Label) root.lookup("#lblAge");
//                    LocalDate DOB = LocalDate.parse(resultSet.getString("Date_of_Birth"));
//                    LocalDate now = LocalDate.now();
//                    int age = now.getYear() - DOB.getYear();
//                    if (age > 0) {
//                        if (label != null) label.setText(age + " yrs.");
//                    } else {
//                        age = now.getMonthValue() - DOB.getMonthValue();
//                        if (label != null) label.setText(age + " months.");
//                    }
//                    label = (Label) root.lookup("#lblDate");
//                    if (label != null) label.setText(LocalDate.now().toString());
//                }
//                Database.disconnect();
//                resultSet = Database.getReqDocToPrint(report);
//                while (resultSet.next()) {
//                    label = (Label) root.lookup("#lblReqDoc");
//                    if (label != null) label.setText(resultSet.getString(37));
//
//                    label = (Label) root.lookup("#lblReqDoc1");
//                    if (label != null) label.setText(resultSet.getString(38));
//
//                    label = (Label) root.lookup("#lblReference");
//                    if (label != null) label.setText(resultSet.getString(39));
//                }
//                Database.disconnect();
//                resultSet = Database.getReportDetailsToPrint(report);
//                String test = report.getTest();
//
//                directToTest(test,resultSet);
//                Database.disconnect();
//            } catch (SQLException exception) {
//                exception.printStackTrace();
//                messageBox.showMessage("Error on printing class","Error");
//            }
//
//            sendToPrinter(report, root);
            reportBase = new ReportBase();
            try {
                root = FXMLLoader.load(getClass().getResource(report.getTest().toLowerCase() + "Merged.fxml"));
            } catch (NullPointerException e) {
                messageBox.showMessage(report.getTest() + " is not Supported\nfor Stand-Alone Report", "Illegal");
                continue;
            }
            try {
                reportBase.getRefLabel().setText(String.valueOf(Database.getReferenceToPrint(report)));
                reportBase.getSpecimenLabel().setText(String.valueOf(Database.getSpecimenToPrint(report.getTest())));
                reportBase.getTimeLabel().setText("TIME : "+ getTime());
                ResultSet resultSet = Database.getPatientDetailsToPrint(report);
                while (resultSet.next()) {
                    reportBase.getNameLabel().setText(resultSet.getString("Name"));
                    reportBase.getsexLabel().setText(resultSet.getString("Gender"));
                    reportBase.getAgeLabel().setText(report.getPatient().getAgeToPrint());
                    reportBase.getDateLabel().setText(report.getDate().toString());
                }
                Database.disconnect();
                resultSet = Database.getReportDetailsToPrint(report);
                while (resultSet.next()) {
                    reportBase.getReqDocLabel().setText(resultSet.getString("REQ_DOC"));

//                    label = (Label) root.lookup("#lblReqDoc1");
//                    if (label != null) label.setText(resultSet.getString(38));
//
//                    label = (Label) root.lookup("#lblReference");
//                    if (label != null) label.setText(resultSet.getString(39));
                }
                Database.disconnect();
                resultSet = Database.getReportDetailsToPrint(report);
                String test = report.getTest();

                directToTest(resultSet,report);
                Database.disconnect();
            } catch (SQLException exception) {
                exception.printStackTrace();
                messageBox.showMessage("Error on printing class","Error");
            }

            root.setScaleX(1);
            root.setScaleY(1);
            reportBase.getvBox().getChildren().add(root);
            sendToPrinter(report, reportBase.getBase());
        }
    }
    public void printPreview(test report) throws IOException {
        reportBase = new ReportBase();
        AnchorPane anchorPane;
        try {
            root = FXMLLoader.load(getClass().getResource(report.getTest().toLowerCase() + "Merged.fxml"));
        } catch (NullPointerException e) {
            messageBox.showMessage(report.getTest() + " is not Supported\nfor Stand-Alone Report", "Illegal");
            return;
        }
        try {
            reportBase.getRefLabel().setText(String.valueOf(Database.getReferenceToPrint(report)));
            reportBase.getSpecimenLabel().setText(String.valueOf(Database.getSpecimenToPrint(report.getTest())));
            reportBase.getTimeLabel().setText("TIME : "+ getTime());
            ResultSet resultSet = Database.getPatientDetailsToPrint(report);
            while (resultSet.next()) {
                reportBase.getNameLabel().setText(resultSet.getString("Name"));
                reportBase.getsexLabel().setText(resultSet.getString("Gender"));
                reportBase.getAgeLabel().setText(report.getPatient().getAgeToPrint());
                reportBase.getDateLabel().setText(report.getDate().toString());
            }
            Database.disconnect();
            resultSet = Database.getReportDetailsToPrint(report);
            while (resultSet.next()) {
                reportBase.getReqDocLabel().setText(resultSet.getString("REQ_DOC"));

//                label = (Label) root.lookup("#lblReqDoc1");
//                if (label != null) label.setText(resultSet.getString(38));
//
//                label = (Label) root.lookup("#lblReference");
//                if (label != null) label.setText(resultSet.getString(39));
            }
            Database.disconnect();
            resultSet = Database.getReportDetailsToPrint(report);
            String test = report.getTest();

            directToTest(resultSet,report);
            Database.disconnect();
        } catch (SQLException exception) {
            exception.printStackTrace();
            messageBox.showMessage("Error on printing class","Error");
        }
        reportBase.getvBox().getChildren().add(root);

        anchorPane = reportBase.getBase();
        //anchorPane.setScaleX(0.9);
        //anchorPane.setScaleY(0.9);
        //AnchorPane.setLeftAnchor(anchorPane,-30.0);
        //AnchorPane.setTopAnchor(anchorPane,-40.0);

        try {
            Parent root = FXMLLoader.load(getClass().getResource("printPreviewStage2.fxml"));
            AnchorPane preview = (AnchorPane) root.lookup("#preview");
            preview.getChildren().add(anchorPane);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Print Preview");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image("microscope.png"));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void printMergedReport(ObservableList<test> reportList) throws IOException {
        if (Printing.reportList != null) {
            Printing.reportList = null;
            Printing.parentCollection.clear();
            Printing.testOrder.clear();
        }
        Printing.reportList = reportList;

//        base = FXMLLoader.load(getClass().getResource("mergeBase.fxml"));
//        VBox baseVBox = (VBox) base.lookup("#mergeVbox");

        reportBase = null;
        reportBase = new ReportBase();
        VBox baseVBox = reportBase.getvBox();
        base = reportBase.getBase();
        try {
            ResultSet resultSet;
            reportBase.getRefLabel().setText(String.valueOf(Database.getReferenceToPrint(reportList.get(0))));
            reportBase.getSpecimenLabel().setText("");
            reportBase.getTimeLabel().setText("TIME : "+ getTime());
            resultSet = Database.getPatientDetailsToPrint(reportList.get(0));
            while (resultSet.next()) {
                reportBase.getNameLabel().setText(resultSet.getString("Name"));
                reportBase.getsexLabel().setText(resultSet.getString("Gender"));
                reportBase.getAgeLabel().setText(reportList.get(0).getPatient().getAgeToPrint());
                reportBase.getDateLabel().setText(reportList.get(0).getDate().toString());
            }
            Database.disconnect();
            resultSet = Database.getReportDetailsToPrint(reportList.get(0));
            while (resultSet.next()) {
                reportBase.getReqDocLabel().setText(resultSet.getString("REQ_DOC"));

//                label = (Label) base.lookup("#lblReqDoc1");
//                if (label != null) label.setText(resultSet.getString(38));
//
//                label = (Label) base.lookup("#lblReference");
//                if (label != null) label.setText(resultSet.getString(39));
            }
            Database.disconnect();
            for (test report : reportList) {
                resultSet = Database.getReportDetailsToPrint(report);
                try {
                    root = FXMLLoader.load(getClass().getResource(report.getTest().toLowerCase() + "Merged.fxml"));
                } catch (NullPointerException e) {
                    messageBox.showMessage("Merging "+report.getTest()+" is not Supported", "Illegal");
                    return;
                }
                directToTest(resultSet, report);
                Database.disconnect();
                testOrder.add(report.getTest());
                ScaleNodes.resetScale(root);
                parentCollection.add(root);
            }
            baseVBox.getChildren().addAll(parentCollection);
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("printPreviewStage.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Print Preview");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image("microscope.png"));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        sendToPrinter(base, reportList);
    }

    private String getTime() {
        int hour = LocalTime.now().getHour();
        int minute = LocalTime.now().getMinute();
        DecimalFormat dF = new DecimalFormat();
        dF.setMinimumIntegerDigits(2);
        boolean isPM = false;
        if (hour > 12) {
            hour -= 12;
            isPM = true;
        }
        if (isPM) {
            return dF.format(hour)+":"+dF.format(minute)+" pm";
        } else {
            return dF.format(hour)+":"+dF.format(minute)+" am";
        }
    }

    public void sendToPrinter(Parent form, ObservableList<test> reportList) {
        PrinterJob job = PrinterJob.createPrinterJob();
        JobSettings jobSettings = job.getJobSettings();
        Printer printer = job.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 1, 1,1,1);
        jobSettings.setPageLayout(pageLayout);

        boolean printed = job.printPage(form);
        if (printed) {
            job.endJob();
            for (test report : reportList) {
                Database.MarkReportAsPrinted(report);
            }
        }
        else {
            messageBox.showMessage("Printing failed","Error");
        }
    }
    public void sendToPrinter(test report, Parent form) {
        ScaleNodes.resetScale(form);

        PrinterJob job = PrinterJob.createPrinterJob();
        JobSettings jobSettings = job.getJobSettings();
        Printer printer = job.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 1, 1,1,1);
        jobSettings.setPageLayout(pageLayout);

        boolean printed = job.printPage(form);
        if (printed) {
            job.endJob();
            Database.MarkReportAsPrinted(report);
        }
        else {
            messageBox.showMessage("Printing failed","Error");
        }

    }
    public void sendReceiptToPrinter(Parent form) {
        PrinterJob job = PrinterJob.createPrinterJob();
        JobSettings jobSettings = job.getJobSettings();
        Printer printer = job.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 1, 1,1,1);
        jobSettings.setPageLayout(pageLayout);

        boolean printed = job.printPage(form);
        if (printed) {
            job.endJob();
        }
        else {
            messageBox.showMessage("Printing failed","Error");
        }

    }

    private void directToTest(ResultSet resultSet, test report) throws SQLException {
        /*
        Each test must have their own case : statement
        */
        switch (report.getTest()) {
            case "BLOOD_UREA":
                printBlood_Urea(resultSet);
                break;
            case "CRP":
                printCRP(resultSet);
                break;
            case "DENGUETEST":
                printDengue(resultSet);
                break;
            case "FILARIALANTIBODYTEST":
                printFAntibody(resultSet);
                break;
            case "GLUCOSETOLERANCE":
                printGLUCOSETOLERANCE(resultSet);
                break;
            case "FBS":
                printFBS(resultSet);
                break;
            case "PPBSALL":
                printPPBSALL(resultSet);
                break;
            case "PPBS":
                printPPBS(resultSet);
                break;
            case "FBC":
                printFBC(resultSet,report);
                break;
            case "LIPID_PROFILE":
                printLipid_Profile(resultSet);
                break;
            case "GLYCO_HEMO":
                printGlyco_Hemo(resultSet);
                break;
            case "UFR":
                printUFR(resultSet);
                break;
            case "SFR":
                printSFR(resultSet);
                break;
            case "RHE_FACTORS":
                printRhe_Factors(resultSet);
                break;
            case "VDRL":
                printVDRL(resultSet);
                break;
            case "LFT":
                printLFT(resultSet);
                break;
            case "SCREATININE":
               printSCREATININE(resultSet);
                break;
            case "GAMMAGT":
                printGammaGT(resultSet);
                break;
            case "SELECTROLYTES":
                printSElectrolytes(resultSet);
                break;
            case "URINEHCG":
                printUrineHCG(resultSet);
                break;
            case "ESR":
                printESR(resultSet);
                break;
            case "HB":
                printHB(resultSet);
                break;
            case "HIV":
                printHIV(resultSet);
                break;
            case "MICROALBUMIN":
                printMicroalbumin(resultSet);
                break;
            case "OTPT":
                printOTPT(resultSet);
                break;
            case "PTINR":
                printPTINR(resultSet);
                break;
            case "SALKPHOSPHATASE":
                printSALKPHOSPHATASE(resultSet);
                break;
            case "SCHOLESTEROL":
                printSCholesterol(resultSet);
                break;
            case "SIONIZED_CALCIUM":
                printIONIZEDCALCIUM(resultSet);
                break;
            case "SPROTEINS":
                printSProteins(resultSet);
                break;
            case "STOTAL_CALCIUM":
                printTOTALCALCIUM(resultSet);
                break;
            case "SURICSPHOSPHORUS":
                printSURICSPHOSPHORUS(resultSet);
                break;
            case "URINESUGAR":
                printUSugar(resultSet);
                break;
            case "BILIRUBIN":
                printBilirubin(resultSet);
                break;
            case "EGFR":
                printEGFR(resultSet);
                break;
            case "CULTUREABSTEST":
                printCULTUREABSTEST(resultSet);
                break;
            case "ORALGLUCOSE" :
                printORALGLUCOSE(resultSet);
                break;
            case "GCT" :
                printGCT(resultSet);
                break;
            case "RBS" :
                printRBS(resultSet);
                break;
            case "WBCDC" :
                printWBCDC(resultSet,report);
                break;
            case "BSP" :
                printBSP(resultSet);
                break;
            case "BG" :
                printBG(resultSet);
                break;
        }
    }
    private void printRBS(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblRBS");
            if (label != null) label.setText(resultSet.getString("RBS"));

            label = (Label) root.lookup("#flag1");
            if (label != null) label.setText(resultSet.getString("FLAG"));

            label = (Label) root.lookup("#lblTime");
            if (label != null) label.setText("( Time at " + resultSet.getString("CLOCK") + " )");

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,156d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((156d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printGCT(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblGCT");
            if (label != null) label.setText(resultSet.getString("GCT"));

            label = (Label) root.lookup("#flag1");
            if (label != null) label.setText(resultSet.getString("FLAG1"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,247d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((247d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printORALGLUCOSE(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int i = 0;
            int l = 40;
            Font font = new Font("Courier New Italic",12);
            Font ArialRoundedMT = new Font("Arial Rounded MT Bold",12);

            label = (Label) root.lookup("#lblHeader");
            if (label != null) label.setText("ORAL GLUCOSE TOLERANCE  ("+resultSet.getString("GLUCOSEAMOUNT")+"mg)");

            if (!resultSet.getString("FBSMG").startsWith(" ")) {
                label = new Label();
                label.setFont(ArialRoundedMT);
                label.setText("FBS");
                AnchorPane.setLeftAnchor(label,34d);
                AnchorPane.setTopAnchor(label,55d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FBSMG"));
                label.setFont(ArialRoundedMT);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,57d);
                AnchorPane.setRightAnchor(label,327d);
                AnchorPane.setLeftAnchor(label,177d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("mg/dl");
                AnchorPane.setRightAnchor(label,265d);
                AnchorPane.setTopAnchor(label,55d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FBSMMOL"));
                label.setFont(font);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,73d);
                AnchorPane.setRightAnchor(label,327d);
                AnchorPane.setLeftAnchor(label,177d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("m.mol/L");
                AnchorPane.setRightAnchor(label,258d);
                AnchorPane.setTopAnchor(label,71d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FLAG1"));
                label.setFont(font);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,57d);
                AnchorPane.setRightAnchor(label,158d);
                ((AnchorPane) root).getChildren().add(label);

                i++;
            }
            if (!resultSet.getString("ONEHOURMG").startsWith(" ")) {
                label = new Label();
                label.setText("1 HOUR");
                label.setFont(ArialRoundedMT);
                AnchorPane.setLeftAnchor(label,34d);
                AnchorPane.setTopAnchor(label,55d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("ONEHOURMG"));
                label.setFont(ArialRoundedMT);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,57d+i*l);
                AnchorPane.setRightAnchor(label,327d);
                AnchorPane.setLeftAnchor(label,177d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("mg/dl");
                AnchorPane.setRightAnchor(label,265d);
                AnchorPane.setTopAnchor(label,55d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("ONEHOURMMOL"));
                label.setFont(font);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,73d+i*l);
                AnchorPane.setRightAnchor(label,327d);
                AnchorPane.setLeftAnchor(label,177d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("m.mol/L");
                AnchorPane.setRightAnchor(label,258d);
                AnchorPane.setTopAnchor(label,71d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FLAG2"));
                label.setFont(font);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,57d+i*l);
                AnchorPane.setRightAnchor(label,158d);
                ((AnchorPane) root).getChildren().add(label);

                i++;
            }
            if (!resultSet.getString("TWOHOURMG").startsWith(" ")) {
                label = new Label();
                label.setText("2 HOUR");
                label.setFont(ArialRoundedMT);
                AnchorPane.setLeftAnchor(label,34d);
                AnchorPane.setTopAnchor(label,55d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("TWOHOURMG"));
                label.setFont(ArialRoundedMT);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,57d+i*l);
                AnchorPane.setRightAnchor(label,327d);
                AnchorPane.setLeftAnchor(label,177d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("mg/dl");
                AnchorPane.setRightAnchor(label,265d);
                AnchorPane.setTopAnchor(label,55d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("TWOHOURMMOL"));
                label.setFont(font);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,73d+i*l);
                AnchorPane.setRightAnchor(label,327d);
                AnchorPane.setLeftAnchor(label,177d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("m.mol/L");
                AnchorPane.setRightAnchor(label,258d);
                AnchorPane.setTopAnchor(label,71d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FLAG3"));
                label.setFont(font);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,57d+i*l);
                AnchorPane.setRightAnchor(label,158d);
                ((AnchorPane) root).getChildren().add(label);

                i++;
            }
            ((AnchorPane) root).setMinHeight(60d+i*l);
            Text text1 = null;
            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                comment = "REMARK :"+"\n"+comment;
                text1 = new Text(comment);
                text1.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,60d+i*l);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight(60d+i*l+text1.getLayoutBounds().getHeight());
            }
            AnchorPane references = null;
            double j = 0;
            try {
                references = FXMLLoader.load(getClass().getResource("oralglucoseReferences.fxml"));
                j = 200d;
            } catch (IOException e) {
                e.printStackTrace();
            }
            double k = 0;
            if (text1 != null) k = text1.getLayoutBounds().getHeight();
            if (text1 != null) j += text1.getLayoutBounds().getHeight();
            AnchorPane.setLeftAnchor(references,34d);
            AnchorPane.setTopAnchor(references,60d+i*l+k);
            ((AnchorPane) root).getChildren().add(references);
            ((AnchorPane) root).setMinHeight(60d+i*l+j);

            comment = resultSet.getString("VALA1");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,60d+i*l+j);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((70d+i*l+j+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printBilirubin(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int i = 0;
            Font font = new Font("Courier New Bold Italic",12);
            Font Arial = new Font("Arial",12);

            if (!resultSet.getString("TOTAL_BILIRUBIN").startsWith(" ")) {
                ((Label) root.lookup("#lblTitle")).setText("BILIRUBIN - TOTAL");
                label = new Label();
                label.setFont(Arial);
                label.setText("Total Bilirubin");
                AnchorPane.setLeftAnchor(label,34d);
                AnchorPane.setTopAnchor(label,100d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("TOTAL_BILIRUBIN"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,100d);
                AnchorPane.setRightAnchor(label,320d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("mg/dl");
                AnchorPane.setLeftAnchor(label,275d);
                AnchorPane.setTopAnchor(label,100d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("Adult 0.2 - 1.2\n" +
                        "Cord Blood < 2.9\n" +
                        "Premature Infants < 3.4\n" +
                        "1st 24 hours < 6.1\n" +
                        "2 - 5 days < 12.0\n" +
                        "> 1 month");
                AnchorPane.setLeftAnchor(label,370d);
                AnchorPane.setTopAnchor(label,100d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FLAG1"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,100d);
                AnchorPane.setRightAnchor(label,20d);
                ((AnchorPane) root).getChildren().add(label);

                i += 110;
            }
            if (!resultSet.getString("DIRECT_BILIRUBIN").startsWith(" ")) {
                ((Label) root.lookup("#lblTitle")).setText("BILIRUBIN - TOTAL - DIRECT");
                label = new Label();
                label.setText("Direct Bilirubin");
                label.setFont(Arial);
                AnchorPane.setLeftAnchor(label,34d);
                AnchorPane.setTopAnchor(label,100d+i);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("DIRECT_BILIRUBIN"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,102d+i);
                AnchorPane.setRightAnchor(label,320d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("mg/dl");
                AnchorPane.setLeftAnchor(label,275d);
                AnchorPane.setTopAnchor(label,100d+i);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("Adult < 0.5");
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setLeftAnchor(label,370d);
                AnchorPane.setTopAnchor(label,100d+i);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FLAG2"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,102d+i);
                AnchorPane.setRightAnchor(label,20d);
                ((AnchorPane) root).getChildren().add(label);

                i += 20;
            }
            if (!resultSet.getString("INDIRECT_BILIRUBIN").startsWith(" ")) {
                label = new Label();
                label.setText("Indirect Bilirubin");
                label.setFont(Arial);
                AnchorPane.setLeftAnchor(label,34d);
                AnchorPane.setTopAnchor(label,100d+i);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("INDIRECT_BILIRUBIN"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,102d+i);
                AnchorPane.setRightAnchor(label,320d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("mg/dl");
                AnchorPane.setLeftAnchor(label,275d);
                AnchorPane.setTopAnchor(label,100d+i);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("Adult < 0.5");
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setRightAnchor(label,370d);
                AnchorPane.setTopAnchor(label,100d+i);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FLAG3"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,102d+i);
                AnchorPane.setRightAnchor(label,20d);
                ((AnchorPane) root).getChildren().add(label);

                i += 20;
            }
            ((AnchorPane) root).setMinHeight(100d+i);
            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,105d+i);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((105d+i+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printFBS(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblFBSmg");
            if (label != null) label.setText(resultSet.getString("FBSMG"));

            label = (Label) root.lookup("#lblFBSmmol");
            if (label != null) label.setText(resultSet.getString("FBSMMOL"));

            label = (Label) root.lookup("#lblFlag");
            if (label != null) label.setText(resultSet.getString("FLAG"));

            label = (Label) root.lookup("#lblFastingHours");
            if (label != null) label.setText(resultSet.getString("FASTING_HOURS"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,270d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight(270d+text.getLayoutBounds().getHeight());
            }
        }
    }
    private void printSCREATININE(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblSCreatinine");
            if (label != null) label.setText(resultSet.getString("SCREATININE"));

            label = (Label) root.lookup("#flag");
            if (label != null) label.setText(resultSet.getString("FLAG1"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,150d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((150d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printGammaGT(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblGammaGT");
            if (label != null) label.setText(resultSet.getString("GAMMAGT"));

            label = (Label) root.lookup("#flag");
            if (label != null) label.setText(resultSet.getString("FLAG1"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,150d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((150d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printFBC(ResultSet resultSet,test report) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblWBC");
            if (label != null) label.setText(resultSet.getString("WBC"));

            label = (Label) root.lookup("#lblNeutro");
            if (label != null) label.setText(resultSet.getString("NEUTROPHILS"));

            label = (Label) root.lookup("#lblLympho");
            if (label != null) label.setText(resultSet.getString("LYMPHOCYTES"));

            label = (Label) root.lookup("#lblEosino");
            if (label != null) label.setText(resultSet.getString("EOSINOPHILS"));

            label = (Label) root.lookup("#lblMono");
            if (label != null) label.setText(resultSet.getString("MONOCYTES"));

            label = (Label) root.lookup("#lblBaso");
            if (label != null) label.setText(resultSet.getString("BASOPHILS"));

            label = (Label) root.lookup("#lblRBC");
            if (label != null) label.setText(resultSet.getString("RBC"));

            label = (Label) root.lookup("#lblHB");
            if (label != null) label.setText(resultSet.getString("HB"));

            label = (Label) root.lookup("#lblPCV");
            if (label != null) label.setText(resultSet.getString("PCV"));

            label = (Label) root.lookup("#lblMCV");
            if (label != null) label.setText(resultSet.getString("MCV"));

            label = (Label) root.lookup("#lblMCH");
            if (label != null) label.setText(resultSet.getString("MCH"));

            label = (Label) root.lookup("#lblMCHC");
            if (label != null) label.setText(resultSet.getString("MCHC"));

            label = (Label) root.lookup("#lblPLCount");
            if (label != null) label.setText(resultSet.getString("PLATELET_COUNT"));



            label = (Label) root.lookup("#lblFlag0");
            if (label != null) label.setText(resultSet.getString("FLAG0"));

            label = (Label) root.lookup("#lblFlag1");
            if (label != null) label.setText(resultSet.getString("FLAG1"));

            label = (Label) root.lookup("#lblFlag2");
            if (label != null) label.setText(resultSet.getString("FLAG2"));

            label = (Label) root.lookup("#lblFlag3");
            if (label != null) label.setText(resultSet.getString("FLAG3"));

            label = (Label) root.lookup("#lblFlag4");
            if (label != null) label.setText(resultSet.getString("FLAG4"));

            label = (Label) root.lookup("#lblFlag5");
            if (label != null) label.setText(resultSet.getString("FLAG5"));

            label = (Label) root.lookup("#lblFlag6");
            if (label != null) label.setText(resultSet.getString("FLAG6"));

            label = (Label) root.lookup("#lblFlag7");
            if (label != null) label.setText(resultSet.getString("FLAG7"));

            label = (Label) root.lookup("#lblFlag8");
            if (label != null) label.setText(resultSet.getString("FLAG8"));

            label = (Label) root.lookup("#lblFlag9");
            if (label != null) label.setText(resultSet.getString("FLAG9"));

            label = (Label) root.lookup("#lblFlag10");
            if (label != null) label.setText(resultSet.getString("FLAG10"));

            label = (Label) root.lookup("#lblFlag11");
            if (label != null) label.setText(resultSet.getString("FLAG11"));

            label = (Label) root.lookup("#lblFlag12");
            if (label != null) label.setText(resultSet.getString("FLAG12"));

            /*
            This code is used to deffer references based on patients age

            int ageY = Period.between(report.getPatient().getDOB(),report.getDate()).getYears();
            int ageM = Period.between(report.getPatient().getDOB(),report.getDate()).getMonths();
            int ageD = Period.between(report.getPatient().getDOB(),report.getDate()).getDays();



            if (ageY ==0 && ageM ==0 && ageD <= 28) {
                label = (Label) root.lookup("#ref0");
                label.setText("(Age below 28 days)");

                label = (Label) root.lookup("#ref1");
                label.setText("(4 - 20)");

                label = (Label) root.lookup("#ref2");
                label.setText("(40 - 80)");

                label = (Label) root.lookup("#ref3");
                label.setText("(10 - 60)");

                label = (Label) root.lookup("#ref4");
                label.setText("(3.5 - 07)");

                label = (Label) root.lookup("#ref5");
                label.setText("(17 - 20)");

                label = (Label) root.lookup("#ref6");
                label.setText("(38 - 64)");

                label = (Label) root.lookup("#ref7");
                label.setText("(95 - 125)");

                label = (Label) root.lookup("#ref8");
                label.setText("(30 - 34)");
            } else if (ageY <= 13) {
                label = (Label) root.lookup("#ref0");
                label.setText("(28 days - 13 years)");

                label = (Label) root.lookup("#ref1");
                label.setText("(4 - 12)");

                label = (Label) root.lookup("#ref2");
                label.setText("(50 - 70)");

                label = (Label) root.lookup("#ref3");
                label.setText("(20 - 60)");

                label = (Label) root.lookup("#ref4");
                label.setText("(3.5 - 5.5)");

                label = (Label) root.lookup("#ref5");
                label.setText("(12 - 16)");

                label = (Label) root.lookup("#ref6");
                label.setText("(37 - 54)");

                label = (Label) root.lookup("#ref7");
                label.setText("(80 - 100)");

                label = (Label) root.lookup("#ref8");
                label.setText("(27 - 34)");

            } else {
                label = (Label) root.lookup("#ref0");
                label.setText("(Age above 13 years)");

                label = (Label) root.lookup("#ref1");
                label.setText("(4 - 10)");

                label = (Label) root.lookup("#ref2");
                label.setText("(50 - 70)");

                label = (Label) root.lookup("#ref3");
                label.setText("(20 - 40)");

                label = (Label) root.lookup("#ref4");
                label.setText("(3.5 - 5.5)");

                label = (Label) root.lookup("#ref5");
                label.setText("(11 - 16)");

                label = (Label) root.lookup("#ref6");
                label.setText("(37 - 54)");

                label = (Label) root.lookup("#ref7");
                label.setText("(80 - 100)");

                label = (Label) root.lookup("#ref8");
                label.setText("(27 - 34)");

            }
            */

//            if (report.getPatient().getGender().equalsIgnoreCase("male")) {
//                label = (Label) root.lookup("#refMaleFemale");
//                if (label != null) label.setText("(Male)");
//
//                label = (Label) root.lookup("#ref1");
//                if (label != null) label.setText("(13.0 - 17.5)");
//
//                label = (Label) root.lookup("#ref2");
//                if (label != null) label.setText("(04.5 - 06.5)");
//
//                label = (Label) root.lookup("#ref3");
//                if (label != null) label.setText("(40 - 52)");
//            } else {
//                label = (Label) root.lookup("#refMaleFemale");
//                if (label != null) label.setText("(Female)");
//
//                label = (Label) root.lookup("#ref1");
//                if (label != null) label.setText("(11.5 - 16.0)");
//
//                label = (Label) root.lookup("#ref2");
//                if (label != null) label.setText("(03.8 - 05.8)");
//
//                label = (Label) root.lookup("#ref3");
//                if (label != null) label.setText("(37 - 47)");
//            }

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,490d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                System.out.println(text.getLayoutBounds().getHeight());
                ((AnchorPane) root).setMinHeight(490d+text.getLayoutBounds().getHeight());
            }
        }
    }
    private void printWBCDC(ResultSet resultSet,test report) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblWBC");
            if (label != null) label.setText(resultSet.getString("WBC"));

            label = (Label) root.lookup("#lblNeutro");
            if (label != null) label.setText(resultSet.getString("NEUTROPHILS"));

            label = (Label) root.lookup("#lblLympho");
            if (label != null) label.setText(resultSet.getString("LYMPHOCYTES"));

            label = (Label) root.lookup("#lblEosino");
            if (label != null) label.setText(resultSet.getString("EOSINOPHILS"));

            label = (Label) root.lookup("#lblMono");
            if (label != null) label.setText(resultSet.getString("MONOCYTES"));

            label = (Label) root.lookup("#lblBaso");
            if (label != null) label.setText(resultSet.getString("BASOPHILS"));

            label = (Label) root.lookup("#lblNeutroAb");
            if (label != null) label.setText(resultSet.getString("NEUTROPHILS_AB"));

            label = (Label) root.lookup("#lblLymphoAb");
            if (label != null) label.setText(resultSet.getString("LYMPHOCYTES_AB"));

            label = (Label) root.lookup("#lblEosinoAb");
            if (label != null) label.setText(resultSet.getString("EOSINOPHILS_AB"));

            label = (Label) root.lookup("#lblMonoAb");
            if (label != null) label.setText(resultSet.getString("MONOCYTES_AB"));

            label = (Label) root.lookup("#lblBasoAb");
            if (label != null) label.setText(resultSet.getString("BASOPHILS_AB"));

            label = (Label) root.lookup("#lblFlag0");
            if (label != null) label.setText(resultSet.getString("FLAG0"));

            label = (Label) root.lookup("#lblFlag1");
            if (label != null) label.setText(resultSet.getString("FLAG1"));

            label = (Label) root.lookup("#lblFlag2");
            if (label != null) label.setText(resultSet.getString("FLAG2"));

            label = (Label) root.lookup("#lblFlag3");
            if (label != null) label.setText(resultSet.getString("FLAG3"));

            label = (Label) root.lookup("#lblFlag4");
            if (label != null) label.setText(resultSet.getString("FLAG4"));

            label = (Label) root.lookup("#lblFlag5");
            if (label != null) label.setText(resultSet.getString("FLAG5"));


//            if (report.getPatient().getGender().equalsIgnoreCase("male")) {
//                label = (Label) root.lookup("#refMaleFemale");
//                if (label != null) label.setText("(Male)");
//
//                label = (Label) root.lookup("#ref1");
//                if (label != null) label.setText("(13.0 - 17.5)");
//
//                label = (Label) root.lookup("#ref2");
//                if (label != null) label.setText("(04.5 - 06.5)");
//
//                label = (Label) root.lookup("#ref3");
//                if (label != null) label.setText("(40 - 52)");
//            } else {
//                label = (Label) root.lookup("#refMaleFemale");
//                if (label != null) label.setText("(Female)");
//
//                label = (Label) root.lookup("#ref1");
//                if (label != null) label.setText("(11.5 - 16.0)");
//
//                label = (Label) root.lookup("#ref2");
//                if (label != null) label.setText("(03.8 - 05.8)");
//
//                label = (Label) root.lookup("#ref3");
//                if (label != null) label.setText("(37 - 47)");
//            }

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,220d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                System.out.println(text.getLayoutBounds().getHeight());
                ((AnchorPane) root).setMinHeight(220d+text.getLayoutBounds().getHeight());
            }
        }
    }
    private void printBSP(ResultSet resultSet) throws SQLException {
        {
            while (resultSet.next()) {
                label = (Label) root.lookup("#lblFBS");
                if (label != null) label.setText(resultSet.getString("FBS"));

                label = (Label) root.lookup("#lblPPBSB");
                if (label != null) label.setText(resultSet.getString("PPBSB"));

                label = (Label) root.lookup("#lblRBSL");
                if (label != null) label.setText(resultSet.getString("RBSL"));

                label = (Label) root.lookup("#lblPPBSL");
                if (label != null) label.setText(resultSet.getString("PPBSL"));

                label = (Label) root.lookup("#lblRBSD");
                if (label != null) label.setText(resultSet.getString("RBSD"));

                label = (Label) root.lookup("#lblPPBSD");
                if (label != null) label.setText(resultSet.getString("PPBSD"));

                label = (Label) root.lookup("#flag1");
                if (label != null) label.setText(resultSet.getString("FLAG1"));

                label = (Label) root.lookup("#flag2");
                if (label != null) label.setText(resultSet.getString("FLAG2"));

                label = (Label) root.lookup("#flag3");
                if (label != null) label.setText(resultSet.getString("FLAG3"));

                label = (Label) root.lookup("#flag4");
                if (label != null) label.setText(resultSet.getString("FLAG4"));

                label = (Label) root.lookup("#flag5");
                if (label != null) label.setText(resultSet.getString("FLAG5"));

                label = (Label) root.lookup("#flag6");
                if (label != null) label.setText(resultSet.getString("FLAG6"));

                String comment = resultSet.getString("VALA0");
                if (!comment.startsWith(" ")) {
                    Text text = new Text(comment);
                    text.applyCss();
                    Label label1 = new Label();
                    label1.setFont(new Font("Courier New Bold Italic",12));
                    label1.setText(comment);
                    label1.setWrapText(true);
                    ((AnchorPane) root).getChildren().add(label1);
                    AnchorPane.setTopAnchor(label1,304d);
                    AnchorPane.setLeftAnchor(label1,30d);
                    AnchorPane.setRightAnchor(label1,30d);
                    ((AnchorPane) root).setMinHeight((304d+text.getLayoutBounds().getHeight()));
                }

            }
        }
    }
    private void printLipid_Profile(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblCho");
            if (label != null) label.setText(resultSet.getString("TCHOLESTEROL"));

            label = (Label) root.lookup("#lblHDL");
            if (label != null) label.setText(resultSet.getString("HDL_CHOLESTEROL"));

            label = (Label) root.lookup("#lblLDL");
            if (label != null) label.setText(resultSet.getString("LDL_CHOLESTEROL"));

            label = (Label) root.lookup("#lblVLDL");
            if (label != null) label.setText(resultSet.getString("VLDL_CHOLESTEROL"));

            label = (Label) root.lookup("#lblTri");
            if (label != null) label.setText(resultSet.getString("TRIGLYCERIDES"));

            label = (Label) root.lookup("#lblCHDLC");
            if (label != null) label.setText(resultSet.getString("CHOHDLCHO"));

            label = (Label) root.lookup("#flag1");
            if (label != null) label.setText(resultSet.getString("FLAG1"));

            label = (Label) root.lookup("#flag2");
            if (label != null) label.setText(resultSet.getString("FLAG2"));

            label = (Label) root.lookup("#flag3");
            if (label != null) label.setText(resultSet.getString("FLAG3"));

            label = (Label) root.lookup("#flag4");
            if (label != null) label.setText(resultSet.getString("FLAG4"));

            label = (Label) root.lookup("#flag5");
            if (label != null) label.setText(resultSet.getString("FLAG5"));

            label = (Label) root.lookup("#flag6");
            if (label != null) label.setText(resultSet.getString("FLAG6"));

            label = (Label) root.lookup("#lblFastingHours");
            if (label != null) label.setText(resultSet.getString("FASTING_HOURS"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,347d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((347d+text.getLayoutBounds().getHeight()));
            }

        }
    }
    private void printPTINR(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblTest");
            if (label != null) label.setText(resultSet.getString("TEST"));

            label = (Label) root.lookup("#lblControl");
            if (label != null) label.setText(resultSet.getString("CONTROL"));

            label = (Label) root.lookup("#lblINR");
            if (label != null) label.setText(resultSet.getString("INR"));

            label = (Label) root.lookup("#lblValue1");
            if (label != null) label.setText(resultSet.getString("VALUE1"));

            label = (Label) root.lookup("#lblValue2");
            if (label != null) label.setText(resultSet.getString("VALUE2"));

            label = (Label) root.lookup("#lblComments");
            if (label != null) label.setText(resultSet.getString("VALA0"));
        }
    }
    private void printMicroalbumin(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblUMicroAl");
            if (label != null) label.setText(resultSet.getString("UMICROAL"));

            label = (Label) root.lookup("#lblCreatinine");
            if (label != null) label.setText(resultSet.getString("UCREATININE"));

            label = (Label) root.lookup("#lblACR");
            if (label != null) label.setText(resultSet.getString("ACR"));

            label = (Label) root.lookup("#lblComments");
            if (label != null) label.setText(resultSet.getString("VALA0"));
        }
    }
    private void printOTPT(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int i = 0;
            Font font = new Font("Courier New Bold Italic",12);
            Font ArialRoundedMT = new Font("Arial Rounded MT Bold",12);
            Label label2 = new Label();
            label2.setFont(new Font("Arial Rounded MT Bold",14));
            label2.setAlignment(Pos.CENTER);
            AnchorPane.setRightAnchor(label2, 5d);
            AnchorPane.setLeftAnchor(label2, 5d);
            AnchorPane.setTopAnchor(label2, 45d);
            ((AnchorPane) root).getChildren().add(label2);

            if (!resultSet.getString("OT").startsWith(" ")) {
                label2.setText("SGOT");
                label = new Label();
                label.setText("AST / SGOT");
                label.setFont(ArialRoundedMT);
                AnchorPane.setLeftAnchor(label,50d);
                AnchorPane.setTopAnchor(label,115d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("OT"));
                label.setFont(ArialRoundedMT);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,117d);
                AnchorPane.setLeftAnchor(label,280d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("U/L");
                AnchorPane.setTopAnchor(label,115d);
                AnchorPane.setLeftAnchor(label,350d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("07 - 40");
                AnchorPane.setTopAnchor(label,115d);
                AnchorPane.setLeftAnchor(label,420d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FLAG1"));
                label.setFont(font);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,115d);
                //AnchorPane.setRightAnchor(label,300d);
                AnchorPane.setLeftAnchor(label,490d);
                ((AnchorPane) root).getChildren().add(label);

                i++;
            }
            if (!resultSet.getString("PT").startsWith(" ")) {
                if (label2.getText().equals("SGOT")) {
                    label2.setText("SGOT | PT");
                } else {
                    label2.setText("SGPT");
                }
                label = new Label();
                label.setText("ALT / SGPT");
                label.setFont(ArialRoundedMT);
                AnchorPane.setTopAnchor(label,115d+i*32d);
                AnchorPane.setLeftAnchor(label,50d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("PT"));
                label.setFont(ArialRoundedMT);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,117d+i*32d);
                AnchorPane.setLeftAnchor(label,280d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("U/L");
                AnchorPane.setTopAnchor(label,115d+i*32d);
                AnchorPane.setLeftAnchor(label,350d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FLAG2"));
                label.setFont(font);
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setTopAnchor(label,117d+i*32d);
                AnchorPane.setLeftAnchor(label,490d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("07 - 35");
                AnchorPane.setTopAnchor(label,115d+i*32d);
                AnchorPane.setLeftAnchor(label,420d);
                ((AnchorPane) root).getChildren().add(label);

                i++;
            }

            ((AnchorPane) root).setMinHeight(120d+i*32d);
            Text text1;
            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                comment = "COMMENT :"+"\n"+comment;
                text1 = new Text(comment);
                text1.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,120d+i*32d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight(120d+i*32d+text1.getLayoutBounds().getHeight());
            }
            /*
            AnchorPane references = null;
            try {
                references = FXMLLoader.load(getClass().getResource("otptEnd.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            double minHeight = ((AnchorPane) root).getMinHeight();
            AnchorPane.setLeftAnchor(references,34d);
            AnchorPane.setTopAnchor(references,minHeight);
            ((AnchorPane) root).getChildren().add(references);
            ((AnchorPane) root).setMinHeight(minHeight + 40d);

             */
        }
    }
    private void printGlyco_Hemo(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblGHA1C");
            if (label != null) label.setText(resultSet.getString("GHA1C"));

            label = (Label) root.lookup("#lblMBG");
            if (label != null) label.setText(resultSet.getString("MEAN_BLOOD_GLUCOSE"));

            label = (Label) root.lookup("#lblComments");
            if (label != null) label.setText(resultSet.getString("VALA0"));
        }
    }
    private void printUFR(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblColour");
            if (label != null) label.setText(resultSet.getString("COLOUR"));

            label = (Label) root.lookup("#lblAppear");
            if (label != null) label.setText(resultSet.getString("APPEARANCE"));

            label = (Label) root.lookup("#lblSpecificGravity");
            if (label != null) label.setText(resultSet.getString("SPECIFICGRAVITY"));

            label = (Label) root.lookup("#lblReaction");
            if (label != null) label.setText(resultSet.getString("REACTION"));

            label = (Label) root.lookup("#lblAlbu");
            if (label != null) label.setText(resultSet.getString("ALBUMIN"));

            label = (Label) root.lookup("#lblRedu");
            if (label != null) label.setText(resultSet.getString("REDUCINGSUBS"));

            label = (Label) root.lookup("#lblBile");
            if (label != null) label.setText(resultSet.getString("BILE"));

            label = (Label) root.lookup("#lblKetoneBodies");
            if (label != null) label.setText(resultSet.getString("KETONEBODIES"));

            label = (Label) root.lookup("#lblUro");
            if (label != null) label.setText(resultSet.getString("UROBILINOGEN"));

            label = (Label) root.lookup("#lblPusC");
            if (label != null) label.setText(resultSet.getString("PUS_CELLS"));

            label = (Label) root.lookup("#lblRedC");
            if (label != null) label.setText(resultSet.getString("RED_CELLS"));

            label = (Label) root.lookup("#lblEpiC");
            if (label != null) label.setText(resultSet.getString("EPI_CELLS"));

            label = (Label) root.lookup("#lblRenalEpiC");
            if (label != null) label.setText(resultSet.getString("POLIGONAL_EPI_CELLS"));

            label = (Label) root.lookup("#lblCasts");
            if (label != null) label.setText(resultSet.getString("CASTS"));

            label = (Label) root.lookup("#lblCryst");
            if (label != null) label.setText(resultSet.getString("CRYSTALS"));

            label = (Label) root.lookup("#lblOrga");
            if (label != null) label.setText(resultSet.getString("ORGANISMS"));

            label = (Label) root.lookup("#lblMThreads");
            if (label != null) label.setText(resultSet.getString("MUCOUSTHREADS"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,380d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                root.prefHeight(380d+text.getLayoutBounds().getHeight());
            }
        }
    }
    private void printCULTUREABSTEST(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#val0");
            if (label != null) label.setText(resultSet.getString("VALA0"));

            label = (Label) root.lookup("#val1");
            if (label != null) label.setText(resultSet.getString("VAL0"));

            label = (Label) root.lookup("#val2");
            if (label != null) label.setText(resultSet.getString("VAL1"));

            label = (Label) root.lookup("#val3");
            if (label != null) label.setText(resultSet.getString("VAL2"));

            label = (Label) root.lookup("#val4");
            if (label != null) label.setText(resultSet.getString("VAL3"));

            label = (Label) root.lookup("#val5");
            if (label != null) label.setText(resultSet.getString("VAL4"));

            label = (Label) root.lookup("#val6");
            if (label != null) label.setText(resultSet.getString("VAL5"));

            label = (Label) root.lookup("#val7");
            if (label != null) label.setText(resultSet.getString("VAL6"));

            label = (Label) root.lookup("#val8");
            if (label != null) label.setText(resultSet.getString("VAL7"));

            label = (Label) root.lookup("#val9");
            if (label != null) label.setText(resultSet.getString("VAL8"));

            label = (Label) root.lookup("#val10");
            if (label != null) label.setText(resultSet.getString("VAL9"));

            label = (Label) root.lookup("#val11");
            if (label != null) label.setText(resultSet.getString("VAL10"));

            label = (Label) root.lookup("#val12");
            if (label != null) label.setText(resultSet.getString("VAL11"));

            label = (Label) root.lookup("#val13");
            if (label != null) label.setText(resultSet.getString("VAL12"));

            label = (Label) root.lookup("#val14");
            if (label != null) label.setText(resultSet.getString("VAL13"));

            label = (Label) root.lookup("#val15");
            if (label != null) label.setText(resultSet.getString("VAL14"));

            label = (Label) root.lookup("#val16");
            if (label != null) label.setText(resultSet.getString("VAL15"));

            label = (Label) root.lookup("#val17");
            if (label != null) label.setText(resultSet.getString("VAL16"));

            label = (Label) root.lookup("#val18");
            if (label != null) label.setText(resultSet.getString("VAL17"));

            label = (Label) root.lookup("#val19");
            if (label != null) label.setText(resultSet.getString("VAL18"));

            label = (Label) root.lookup("#val20");
            if (label != null) label.setText(resultSet.getString("VAL19"));

            label = (Label) root.lookup("#val21");
            if (label != null) label.setText(resultSet.getString("VAL20"));

            label = (Label) root.lookup("#val22");
            if (label != null) label.setText(resultSet.getString("VAL21"));

            label = (Label) root.lookup("#val23");
            if (label != null) label.setText(resultSet.getString("VAL22"));

            label = (Label) root.lookup("#val24");
            if (label != null) label.setText(resultSet.getString("VAL23"));

            label = (Label) root.lookup("#val25");
            if (label != null) label.setText(resultSet.getString("VAL24"));

            label = (Label) root.lookup("#val26");
            if (label != null) label.setText(resultSet.getString("VAL25"));

            label = (Label) root.lookup("#val27");
            if (label != null) label.setText(resultSet.getString("VAL26"));

            label = (Label) root.lookup("#val28");
            if (label != null) label.setText(resultSet.getString("VAL27"));

            label = (Label) root.lookup("#val29");
            if (label != null) label.setText(resultSet.getString("VAL28"));

            label = (Label) root.lookup("#val30");
            if (label != null) label.setText(resultSet.getString("VAL29"));

            label = (Label) root.lookup("#val31");
            if (label != null) label.setText(resultSet.getString("VAL30"));

            label = (Label) root.lookup("#val32");
            if (label != null) label.setText(resultSet.getString("VAL31"));

            label = (Label) root.lookup("#val33");
            if (label != null) label.setText(resultSet.getString("VAL32"));

            label = (Label) root.lookup("#val34");
            if (label != null) label.setText(resultSet.getString("VAL33"));

            label = (Label) root.lookup("#val35");
            if (label != null) label.setText(resultSet.getString("VAL34"));

            //////////////////////////


            label = (Label) root.lookup("#val36");
            if (label != null) label.setText(resultSet.getString("VAL35"));

            label = (Label) root.lookup("#val37");
            if (label != null) label.setText(resultSet.getString("VAL36"));

            label = (Label) root.lookup("#val38");
            if (label != null) label.setText(resultSet.getString("VAL37"));

            label = (Label) root.lookup("#val39");
            if (label != null) label.setText(resultSet.getString("VAL38"));

            label = (Label) root.lookup("#val40");
            if (label != null) label.setText(resultSet.getString("VAL39"));

            label = (Label) root.lookup("#val41");
            if (label != null) label.setText(resultSet.getString("VAL40"));

            label = (Label) root.lookup("#val42");
            if (label != null) label.setText(resultSet.getString("VAL41"));

            label = (Label) root.lookup("#val43");
            if (label != null) label.setText(resultSet.getString("VAL42"));

            label = (Label) root.lookup("#val44");
            if (label != null) label.setText(resultSet.getString("VAL43"));

            label = (Label) root.lookup("#val45");
            if (label != null) label.setText(resultSet.getString("VAL44"));

            label = (Label) root.lookup("#val46");
            if (label != null) label.setText(resultSet.getString("VAL45"));

            label = (Label) root.lookup("#val47");
            if (label != null) label.setText(resultSet.getString("VAL46"));

            label = (Label) root.lookup("#val48");
            if (label != null) label.setText(resultSet.getString("VAL47"));

            label = (Label) root.lookup("#val49");
            if (label != null) label.setText(resultSet.getString("VAL48"));

            label = (Label) root.lookup("#val50");
            if (label != null) label.setText(resultSet.getString("VAL49"));
        }
    }
    private void printSFR(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblColour");
            if (label != null) label.setText(resultSet.getString("COLOUR"));

            label = (Label) root.lookup("#lblAppear");
            if (label != null) label.setText(resultSet.getString("APPEARANCE"));

            label = (Label) root.lookup("#lblSugar");
            if (label != null) label.setText(resultSet.getString("SUGAR"));

            label = (Label) root.lookup("#lblAOC");
            if (label != null) label.setText(resultSet.getString("AOC"));

            label = (Label) root.lookup("#lblRedC");
            if (label != null) label.setText(resultSet.getString("RED"));

            label = (Label) root.lookup("#lblPusC");
            if (label != null) label.setText(resultSet.getString("PUS"));

            label = (Label) root.lookup("#lblEpiC");
            if (label != null) label.setText(resultSet.getString("EPI"));

            label = (Label) root.lookup("#lblFat");
            if (label != null) label.setText(resultSet.getString("FAT"));

            label = (Label) root.lookup("#lblMucus");
            if (label != null) label.setText(resultSet.getString("MUCUS"));

            label = (Label) root.lookup("#lblVeg");
            if (label != null) label.setText(resultSet.getString("VEG"));

            label = (Label) root.lookup("#lblYeasts");
            if (label != null) label.setText(resultSet.getString("YEAST"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,320d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((320d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printGLUCOSETOLERANCE(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblValue1");
            if (label != null) label.setText(resultSet.getString("VALUE1"));

            label = (Label) root.lookup("#lblValue2");
            if (label != null) label.setText(resultSet.getString("VALUE2"));

            label = (Label) root.lookup("#lblValue3");
            if (label != null) label.setText(resultSet.getString("VALUE3"));

            label = (Label) root.lookup("#lblValue4");
            if (label != null) label.setText(resultSet.getString("VALUE4"));

            label = (Label) root.lookup("#lblValue5");
            if (label != null) label.setText(resultSet.getString("VALUE5"));

            label = (Label) root.lookup("#lblValue6");
            if (label != null) label.setText(resultSet.getString("VALUE6"));

            label = (Label) root.lookup("#lblValue7");
            if (label != null) label.setText(resultSet.getString("VALUE7"));

            label = (Label) root.lookup("#lblValue8");
            if (label != null) label.setText(resultSet.getString("VALUE8"));

            label = (Label) root.lookup("#lblValue9");
            if (label != null) label.setText(resultSet.getString("VALUE9"));

            label = (Label) root.lookup("#lblValue10");
            if (label != null) label.setText(resultSet.getString("VALUE10"));

            label = (Label) root.lookup("#lblValue11");
            if (label != null) label.setText(resultSet.getString("VALUE11"));

            label = (Label) root.lookup("#lblComments");
            if (label != null) label.setText(resultSet.getString("VALA0"));
        }
    }
    private void printRhe_Factors(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblRhFactor");
            if (label != null) label.setText(resultSet.getString("RHEUMATOID_FACTORS"));

            if (label.getText().equalsIgnoreCase("negative")) {
                label = (Label) root.lookup("#lblTitre");
                label.setVisible(false);
                label = (Label) root.lookup("#lbl1");
                label.setVisible(false);
                label = (Label) root.lookup("#lbl2");
                label.setVisible(false);
                return;
            }

            label = (Label) root.lookup("#lblTitre");
            if (label != null) label.setText(resultSet.getString("TITRE"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,156d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                System.out.println(text.getLayoutBounds().getHeight());
                ((AnchorPane) root).setMinHeight(156d+text.getLayoutBounds().getHeight());
            }
        }
    }
    private void printVDRL(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblRhFactor");
            if (label != null) label.setText(resultSet.getString("VDRL"));

            if (!label.getText().equalsIgnoreCase("reactive")) {
                label = (Label) root.lookup("#lblTitre");
                label.setVisible(false);
                label = (Label) root.lookup("#lbl1");
                label.setVisible(false);
                return;
            }

            label = (Label) root.lookup("#lblComments");
            if (label != null) label.setText(resultSet.getString("VALA0"));

            label = (Label) root.lookup("#lblTitre");
            if (label != null) label.setText(resultSet.getString("TITRE"));
        }
    }
    private void printLFT(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblOT");
            if (label != null) label.setText(resultSet.getString("SGOT"));

            label = (Label) root.lookup("#lblPT");
            if (label != null) label.setText(resultSet.getString("SGPT"));

            label = (Label) root.lookup("#lblSAlkPhos");
            if (label != null) label.setText(resultSet.getString("SALKPHOS"));

            label = (Label) root.lookup("#lblTotalBil");
            if (label != null) label.setText(resultSet.getString("TOTALBILIRUBIN"));

            label = (Label) root.lookup("#lblDirBil");
            if (label != null) label.setText(resultSet.getString("DIRECTBILIRUBIN"));

            label = (Label) root.lookup("#lblIndirBil");
            if (label != null) label.setText(resultSet.getString("INDIRECTBILIRUBIN"));

            label = (Label) root.lookup("#lblTotalPro");
            if (label != null) label.setText(resultSet.getString("TOTALPROTEINS"));

            label = (Label) root.lookup("#lblAlbu");
            if (label != null) label.setText(resultSet.getString("ALBUMIN"));

            label = (Label) root.lookup("#lblGlobu");
            if (label != null) label.setText(resultSet.getString("GLOBULIN"));

            label = (Label) root.lookup("#lblAGRatio");
            if (label != null) label.setText(resultSet.getString("AGRATIO"));

            label = (Label) root.lookup("#lblGamma");
            if (label != null) label.setText(resultSet.getString("GAMMAGT"));

            label = (Label) root.lookup("#lblFlag1");
            if (label != null) label.setText(resultSet.getString("FLAG1"));

            label = (Label) root.lookup("#lblFlag2");
            if (label != null) label.setText(resultSet.getString("FLAG2"));

            label = (Label) root.lookup("#lblFlag3");
            if (label != null) label.setText(resultSet.getString("FLAG3"));

            label = (Label) root.lookup("#lblFlag4");
            if (label != null) label.setText(resultSet.getString("FLAG4"));

            label = (Label) root.lookup("#lblFlag5");
            if (label != null) label.setText(resultSet.getString("FLAG5"));

            label = (Label) root.lookup("#lblFlag6");
            if (label != null) label.setText(resultSet.getString("FLAG6"));

            label = (Label) root.lookup("#lblFlag7");
            if (label != null) label.setText(resultSet.getString("FLAG7"));

            label = (Label) root.lookup("#lblFlag8");
            if (label != null) label.setText(resultSet.getString("FLAG8"));

            label = (Label) root.lookup("#lblFlag9");
            if (label != null) label.setText(resultSet.getString("FLAG9"));

            label = (Label) root.lookup("#lblFlag10");
            if (label != null) label.setText(resultSet.getString("FLAG10"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,400d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((400d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printSProteins(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int i = 0;
            int l = 25;
            Font font = new Font("Courier New Bold Italic",12);
            Font Arial = new Font("Arial",12);

            if (!resultSet.getString("TOTALPROTEINS").startsWith(" ")) {
                label = new Label();
                label.setFont(Arial);
                label.setText("Total Proteins");
                AnchorPane.setLeftAnchor(label,34d);
                AnchorPane.setTopAnchor(label,100d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("TOTALPROTEINS"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,100d);
                AnchorPane.setRightAnchor(label,280d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("g/dl");
                AnchorPane.setLeftAnchor(label,320d);
                AnchorPane.setTopAnchor(label,100d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("6.2 - 8.5");
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setLeftAnchor(label,390d);
                AnchorPane.setRightAnchor(label,110d);
                AnchorPane.setTopAnchor(label,100d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FLAG1"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,100d);
                AnchorPane.setRightAnchor(label,20d);
                ((AnchorPane) root).getChildren().add(label);

                i++;
            }
            if (!resultSet.getString("ALBUMIN").startsWith(" ")) {
                label = new Label();
                label.setText("Albumin");
                label.setFont(Arial);
                AnchorPane.setLeftAnchor(label,34d);
                AnchorPane.setTopAnchor(label,100d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("ALBUMIN"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,102d+i*l);
                AnchorPane.setRightAnchor(label,280d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("g/dl");
                AnchorPane.setLeftAnchor(label,320d);
                AnchorPane.setTopAnchor(label,100d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("2.5 - 5.3");
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setLeftAnchor(label,390d);
                AnchorPane.setRightAnchor(label,110d);
                AnchorPane.setTopAnchor(label,100d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FLAG2"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,102d+i*l);
                AnchorPane.setRightAnchor(label,20d);
                ((AnchorPane) root).getChildren().add(label);

                i++;
            }
            if (!resultSet.getString("GLOBULIN").startsWith(" ")) {
                label = new Label();
                label.setText("Globulin");
                label.setFont(Arial);
                AnchorPane.setLeftAnchor(label,34d);
                AnchorPane.setTopAnchor(label,100d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("GLOBULIN"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,102d+i*l);
                AnchorPane.setRightAnchor(label,280d);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("g/dl");
                AnchorPane.setLeftAnchor(label,320d);
                AnchorPane.setTopAnchor(label,100d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText("2.0 - 3.5");
                label.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setRightAnchor(label,390d);
                AnchorPane.setRightAnchor(label,110d);
                AnchorPane.setTopAnchor(label,100d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("FLAG3"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,102d+i*l);
                AnchorPane.setRightAnchor(label,20d);
                ((AnchorPane) root).getChildren().add(label);

                i++;
            }
            if (!resultSet.getString("AGRATIO").startsWith(" ")) {
                label = new Label();
                label.setText("Alb. Glob. Ratio (A/G)");
                label.setFont(Arial);
                AnchorPane.setLeftAnchor(label,34d);
                AnchorPane.setTopAnchor(label,100d+i*l);
                ((AnchorPane) root).getChildren().add(label);

                label = new Label();
                label.setText(resultSet.getString("AGRATIO"));
                label.setFont(font);
                AnchorPane.setTopAnchor(label,102d+i*l);
                AnchorPane.setRightAnchor(label,280d);
                ((AnchorPane) root).getChildren().add(label);

                i++;
            }
            ((AnchorPane) root).setMinHeight(60d+i*l);
            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,105d+i*l);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((105d+i*l+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printIONIZEDCALCIUM(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblIonizedCal");
            if (label != null) label.setText(resultSet.getString("IONIZEDCALCIUM"));

            label = (Label) root.lookup("#lblComments");
            if (label != null) label.setText(resultSet.getString("VALA0"));
        }
    }
    private void printEGFR(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblEGFR");
            if (label != null) label.setText(resultSet.getString("EGFR"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,420d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((420d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printSElectrolytes(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblNa");
            if (label != null) label.setText(resultSet.getString("NA"));

            label = (Label) root.lookup("#flagNa");
            if (label != null) label.setText(resultSet.getString("FLAG0"));

            label = (Label) root.lookup("#lblK");
            if (label != null) label.setText(resultSet.getString("K"));

            label = (Label) root.lookup("#flagK");
            if (label != null) label.setText(resultSet.getString("FLAG1"));

//            label = (Label) root.lookup("#lblCl");
//            if (label != null) label.setText(resultSet.getString("CL"));
//
//            label = (Label) root.lookup("#flagCl");
//            if (label != null) label.setText(resultSet.getString("FLAG2"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,215d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((215d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printFAntibody(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblGAntibodies");
            if (label != null) label.setText(resultSet.getString("G_ANTIBODIES"));

            label = (Label) root.lookup("#lblMAntibodies");
            if (label != null) label.setText(resultSet.getString("M_ANTIBODIES"));

            label = (Label) root.lookup("#lblComments");
            if (label != null) label.setText(resultSet.getString("VALA0"));
        }
    }
    private void printSURICSPHOSPHORUS(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblUricAcid");
            if (label != null) label.setText(resultSet.getString("URIC_ACID"));

            label = (Label) root.lookup("#lblPhos");
            if (label != null) label.setText(resultSet.getString("PHOSPHORUS"));

            label = (Label) root.lookup("#ref1");
            if (label != null) label.setText(resultSet.getString("REF1"));

            label = (Label) root.lookup("#ref2");
            if (label != null) label.setText(resultSet.getString("REF2"));

            label = (Label) root.lookup("#ref3");
            if (label != null) label.setText(resultSet.getString("REF3"));

            label = (Label) root.lookup("#ref4");
            if (label != null) label.setText(resultSet.getString("REF4"));

            label = (Label) root.lookup("#ref5");
            if (label != null) label.setText(resultSet.getString("REF5"));

            label = (Label) root.lookup("#ref6");
            if (label != null) label.setText(resultSet.getString("REF6"));

            label = (Label) root.lookup("#ref7");
            if (label != null) label.setText(resultSet.getString("REF7"));

            label = (Label) root.lookup("#lblComments");
            if (label != null) label.setText(resultSet.getString("VALA0"));
        }
    }
    private void printCRP(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblCRP");
            if (label != null) label.setText(resultSet.getString("CRP"));

            if (label.getText().equalsIgnoreCase("positive")) {
                label = (Label) root.lookup("#lblTitreData");
                if (label != null) label.setText(resultSet.getString("TITRE") + " mg/L");
            } else {
                root.lookup("#lblTitre").setVisible(false);
                root.lookup("#lblTitreData").setVisible(false);
            }

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,160d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((160d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printUrineHCG(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblHCG");
            if (label != null) label.setText(resultSet.getString("HCG"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,130d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                System.out.println(text.getLayoutBounds().getHeight());
                ((AnchorPane) root).setMinHeight(130d+text.getLayoutBounds().getHeight());
            }
        }
    }
    private void printESR(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblESR1ST");
            if (label != null) label.setText(resultSet.getString("ESR1ST"));

            label = (Label) root.lookup("#flag1");
            if (label != null) label.setText(resultSet.getString("FLAG1"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,150d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((150d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printHIV(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblHIV1");
            if (label != null) label.setText(resultSet.getString("HIV1"));

            label = (Label) root.lookup("#lblHIV2");
            if (label != null) label.setText(resultSet.getString("HIV2"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,170d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((170d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printBlood_Urea(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblBloodUrea");
            if (label != null) label.setText(resultSet.getString("BLOODUREA"));

            label = (Label) root.lookup("#flag");
            if (label != null) label.setText(resultSet.getString("FLAG"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,140d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((140d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printDengue(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblVal");
            if (label != null) label.setText(resultSet.getString("DENGUE"));

            label = (Label) root.lookup("#lblComments");
            if (label != null) label.setText(resultSet.getString("VALA0"));
        }
    }
    private void printHB(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblHB");
            if (label != null) label.setText(resultSet.getString("HB"));

            label = (Label) root.lookup("#flag1");
            if (label != null) label.setText(resultSet.getString("FLAG"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,250d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((250d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printSALKPHOSPHATASE(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblSAlkPhos");
            if (label != null) label.setText(resultSet.getString("SALKPHOS"));

            label = (Label) root.lookup("#flag");
            if (label != null) label.setText(resultSet.getString("FLAG0"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,414d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((414d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printSCholesterol(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblCho");
            if (label != null) label.setText(resultSet.getString("SCHOLESTEROL"));

            label = (Label) root.lookup("#flag");
            if (label != null) label.setText(resultSet.getString("FLAG"));

            label = (Label) root.lookup("#lblFastingHours");
            if (label != null) label.setText(resultSet.getString("FASTING_HOURS"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,150d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight((150d+text.getLayoutBounds().getHeight()));
            }
        }
    }
    private void printPPBSALL(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblValPPBSBRMG");
            if (label != null) label.setText(resultSet.getString("PPBSBRMG"));

            label = (Label) root.lookup("#lblValPPBSBRMMOL");
            if (label != null) label.setText(resultSet.getString("PPBSBRMMOL"));

            label = (Label) root.lookup("#lblFlagBR");
            if (label != null) label.setText(resultSet.getString("FLAGBR"));

            label = (Label) root.lookup("#lblValPPBSLNMG");
            if (label != null) label.setText(resultSet.getString("PPBSLNMG"));

            label = (Label) root.lookup("#lblValPPBSLNMMOL");
            if (label != null) label.setText(resultSet.getString("PPBSLNMMOL"));

            label = (Label) root.lookup("#lblFlagLN");
            if (label != null) label.setText(resultSet.getString("FLAGLN"));

            label = (Label) root.lookup("#lblValPPBSDNMG");
            if (label != null) label.setText(resultSet.getString("PPBSDNMG"));

            label = (Label) root.lookup("#lblValPPBSDNMMOL");
            if (label != null) label.setText(resultSet.getString("PPBSDNMMOL"));

            label = (Label) root.lookup("#lblFlagDN");
            if (label != null) label.setText(resultSet.getString("FLAGDN"));




            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,350d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight(350d+text.getLayoutBounds().getHeight());
            }
        }
    }
    private void printPPBS(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblValPPBSMG");
            if (label != null) label.setText(resultSet.getString("PPBSMG"));

            label = (Label) root.lookup("#lblValPPBSMMOL");
            if (label != null) label.setText(resultSet.getString("PPBSMMOL"));

            label = (Label) root.lookup("#lblFlag");
            if (label != null) label.setText(resultSet.getString("FLAG"));

            label = (Label) root.lookup("#lblValTime");
            if (label != null) label.setText("( " + resultSet.getString("TIMEOFDAY") + " )");


            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic",12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1,350d);
                AnchorPane.setLeftAnchor(label1,30d);
                AnchorPane.setRightAnchor(label1,30d);
                ((AnchorPane) root).setMinHeight(350d+text.getLayoutBounds().getHeight());
            }
        }
    }
    private void printTOTALCALCIUM(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblTCal");
            if (label != null) label.setText(resultSet.getString("TOTALCALCIUM"));

            label = (Label) root.lookup("#lblFlag0");
            if (label != null) label.setText(resultSet.getString("FLAG1"));

            label = (Label) root.lookup("#lblICal");
            if (label != null) label.setText(resultSet.getString("TOTALCALCIUM"));

            label = (Label) root.lookup("#lblFlag1");
            if (label != null) label.setText(resultSet.getString("FLAG2"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic", 12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1, 180d);
                AnchorPane.setLeftAnchor(label1, 30d);
                AnchorPane.setRightAnchor(label1, 30d);
                ((AnchorPane) root).setMinHeight((210d + text.getLayoutBounds().getHeight()));
            }

        }
    }
    private void printUSugar(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblUSugar");
            if (label != null) label.setText(resultSet.getString("USUGAR"));
        }
    }
    private void printBG(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            label = (Label) root.lookup("#lblBG");
            if (label != null) label.setText(resultSet.getString("BG"));

            String comment = resultSet.getString("VALA0");
            if (!comment.startsWith(" ")) {
                Text text = new Text(comment);
                text.applyCss();
                Label label1 = new Label();
                label1.setFont(new Font("Courier New Bold Italic", 12));
                label1.setText(comment);
                label1.setWrapText(true);
                ((AnchorPane) root).getChildren().add(label1);
                AnchorPane.setTopAnchor(label1, 180d);
                AnchorPane.setLeftAnchor(label1, 30d);
                AnchorPane.setRightAnchor(label1, 30d);
                ((AnchorPane) root).setMinHeight((180d + text.getLayoutBounds().getHeight()));
            }
        }
    }
}
