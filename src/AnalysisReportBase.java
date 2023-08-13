

import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class AnalysisReportBase {
    private patient selectedPatient;
    private LineChart<String,Integer> lineChart;

    private AnchorPane root;
    private Label headerLabel3 = new Label();
    private Label headerLabel4 = new Label();
    private Label headerLabel5 = new Label();
    private Label headerLabel6 = new Label();
    private Label headerLabel7 = new Label();
    private Label headerLabel8 = new Label();
    private Label nameLabel = new Label();
    private Label ageLabel = new Label();
    private Label dateLabel = new Label();
    private Label reqDocLabel = new Label();
    private Label timeLabel = new Label();
    private Label refLabel = new Label();

    private Label[] labels = {headerLabel3, headerLabel4, headerLabel5, headerLabel6, headerLabel7,headerLabel8,nameLabel,
            ageLabel,dateLabel,reqDocLabel,timeLabel,refLabel};
    private Scanner diskScan;

    public AnalysisReportBase(patient selectedPatient, LineChart<String,Integer> lineChart) {
        this.selectedPatient = selectedPatient;
        this.lineChart = lineChart;
    }

    private void setReportEnvironment() {
        root = new AnchorPane();

        root.setPrefHeight(820);
        root.setPrefWidth(584);

        root.getChildren().clear();
        setIDs();
        try {

            for (Label label : labels) {
                try {
                    diskScan = new Scanner(new File("Configure\\" + label.getId() + ".txt"));
                } catch (FileNotFoundException e) {
                    messageBox.showMessage("Configure file " + label.getId() + "is missing.\nDefault layout will be loaded", "File not found");
                    new ReportBase().loadDefaults();
                    new ReportBase().saveDefaults();
                    return;
                }

                AnchorPane.setLeftAnchor(label, diskScan.nextDouble());
                diskScan.nextLine();

                AnchorPane.setTopAnchor(label, diskScan.nextDouble());
                diskScan.nextLine();

                String line = diskScan.nextLine();
                if (!line.equals("null")) {
                    AnchorPane.setRightAnchor(label, Double.parseDouble(line));
                }
                line = diskScan.nextLine();
                if (!line.equals("null")) {
                    AnchorPane.setBottomAnchor(label, Double.parseDouble(line));
                }

                label.setUnderline(diskScan.nextBoolean());
                diskScan.nextLine();

                if (diskScan.nextBoolean()) {
                    label.setStyle(" -fx-font-weight : bold; ");
                }
                diskScan.nextLine();
                if (diskScan.nextBoolean()) {
                    label.setStyle(" -fx-font-style : Italic; ");
                }
                diskScan.nextLine();

                label.setFont(new Font(diskScan.nextLine(), diskScan.nextDouble()));
                diskScan.nextLine();

                if (diskScan.nextLine().equalsIgnoreCase("CENTER")) {
                    label.setAlignment(Pos.CENTER);
                } else {
                    label.setAlignment(Pos.TOP_LEFT);
                }
                if (diskScan.hasNextLine()) {
                    label.setText(diskScan.nextLine());
                }

                label.setVisible(diskScan.nextBoolean());
                diskScan.nextLine();
            }
            diskScan.close();
            root.getChildren().addAll(headerLabel3, headerLabel4, headerLabel5, headerLabel6, headerLabel7, headerLabel8
                , nameLabel, ageLabel, reqDocLabel, dateLabel, timeLabel, refLabel);
        } catch (Exception e) {
            messageBox.showMessage("Corrupted Configure file\nDefault layout will be loaded", "File Error");
            e.printStackTrace();
            new ReportBase().saveDefaults();
            setReportEnvironment();
        }


    }
    private void setIDs() {
        headerLabel3.setId("headerLabel3");
        headerLabel4.setId("headerLabel4");
        headerLabel5.setId("headerLabel5");
        headerLabel6.setId("headerLabel6");
        headerLabel7.setId("headerLabel7");
        headerLabel8.setId("headerLabel8");

        nameLabel.setId("nameLabel");
        ageLabel.setId("ageLabel");
        dateLabel.setId("dateLabel");
        reqDocLabel.setId("reqDocLabel");
        timeLabel.setId("timeLabel");
        refLabel.setId("refLabel");
    }
    private void setPatientData() {
        nameLabel.setText(selectedPatient.getName());
        ageLabel.setText(String.valueOf(selectedPatient.getAge()));
        dateLabel.setText(LocalDate.now().toString());
    }
}
