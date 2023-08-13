

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;


public class ReportBase {

    private AnchorPane root = new AnchorPane();
    private Label headerLabel1 = new Label();
    private Label headerLabel2 = new Label();
    private Label headerLabel3 = new Label();
    private Label headerLabel4 = new Label();
    private Label headerLabel5 = new Label();
    private Label headerLabel6 = new Label();
    private Label headerLabel7 = new Label();
    private Label headerLabel8 = new Label();
    private Label headerLabel9 = new Label();

    private Label headerDotLabel1 = new Label();
    private Label headerDotLabel2 = new Label();
    private Label headerDotLabel3 = new Label();
    private Label headerDotLabel4 = new Label();
    private Label headerDotLabel5 = new Label();
    private Label headerDotLabel6 = new Label();
    private Label headerDotLabel7 = new Label();

    private Label mltLabel1 = new Label();
    private Label mltLabel2 = new Label();

    private Label addressLabel1 = new Label();
    private Label addressLabel2 = new Label();

    private Label footerLabel1 = new Label();
    private Label footerLabel2 = new Label();
    private Label footerLabel3 = new Label();
    private Label footerLabel4 = new Label();

    private Line line01 = new Line();
    private Line line02 = new Line();

    private VBox vBox = new VBox();

    private Label nameLabel = new Label();
    private Label ageLabel = new Label();
    private Label dateLabel = new Label();
    private Label reqDocLabel = new Label();
    private Label sexLabel = new Label();
    private Label refLabel = new Label();
    private Label specimenLabel = new Label();
    private Label timeLabel = new Label();

    private Label[] labels = {headerLabel1, headerLabel2, headerLabel3, headerLabel4, headerLabel5, headerLabel6, headerLabel7,headerLabel8,headerLabel9,
                              headerDotLabel1,headerDotLabel2,headerDotLabel3,headerDotLabel4,headerDotLabel5,headerDotLabel6,headerDotLabel7,
                              mltLabel1,mltLabel2,addressLabel1,addressLabel2,footerLabel1,footerLabel2,footerLabel3,footerLabel4,nameLabel,
                              ageLabel,dateLabel,reqDocLabel,sexLabel,refLabel,specimenLabel,timeLabel};
    private Scanner diskScan;


    public ReportBase() {
        initialize();
    }

    public void initialize() {
        root.getChildren().clear();
        setIDs();

        try {

            for (Label label : labels) {
                try {
                    diskScan = new Scanner(new File("Configure\\" + label.getId() + ".txt"));
                } catch (FileNotFoundException e) {
                    messageBox.showMessage("Configure file " + label.getId() + "is missing.\nDefault layout will be loaded", "File not found");
                    loadDefaults();
                    saveDefaults();
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

            root.setPrefHeight(820);
            root.setPrefWidth(584);

            AnchorPane.setTopAnchor(line01, 100.0);
            //AnchorPane.setLeftAnchor(line01,5.0);
            line01.setEndX(579.0);
            line01.setStartX(5.0);
            line01.setStrokeWidth(1.0);
            line01.setOpacity(0.5);

            AnchorPane.setLeftAnchor(vBox, 0.0);
            AnchorPane.setRightAnchor(vBox, 5.0);
            AnchorPane.setTopAnchor(vBox, 230.0);
            AnchorPane.setBottomAnchor(vBox, 130.0);
            //vBox.setStyle("-fx-border-color : #000000");

            AnchorPane.setTopAnchor(line02, 225.0);
            //AnchorPane.setLeftAnchor(line01,5.0);
            line02.setEndX(579.0);
            line02.setStartX(5.0);
            line02.setStrokeWidth(1.0);
            line02.setOpacity(0.5);

            root.getChildren().addAll(headerLabel1, headerLabel2, headerLabel3, headerLabel4, headerLabel5, headerLabel6, headerLabel7, headerLabel8,headerLabel9,
                    headerDotLabel1, headerDotLabel2, headerDotLabel3, headerDotLabel4, headerDotLabel5, headerDotLabel6,headerDotLabel7,
                    line01, line02, vBox, mltLabel1, mltLabel2, addressLabel1, addressLabel2, footerLabel1,
                    footerLabel2, footerLabel3, footerLabel4, nameLabel, ageLabel, reqDocLabel, dateLabel, sexLabel, refLabel,specimenLabel,timeLabel);
        } catch (Exception e) {
            messageBox.showMessage("Corrupted Configure file\nDefault layout will be loaded", "File Error");
            e.printStackTrace();
            saveDefaults();
            initialize();
        }
    }

    public void loadDefaults() {

        root.setPrefHeight(820);
        root.setPrefWidth(584);

        AnchorPane.setLeftAnchor(headerLabel1,0.0);
        AnchorPane.setRightAnchor(headerLabel1,0.0);
        AnchorPane.setTopAnchor(headerLabel1,25.0);
        headerLabel1.setText("Medical Laboratory Report");
        headerLabel1.setUnderline(true);
        headerLabel1.setFont(new Font("Arial",16));
        headerLabel1.setStyle("-fx-font-weight : bold");
        headerLabel1.setAlignment(Pos.CENTER);
        headerLabel1.setVisible(false);

        AnchorPane.setLeftAnchor(headerLabel2,5.0);
        //AnchorPane.setRightAnchor(headerLabel2,0.0);
        AnchorPane.setTopAnchor(headerLabel2,5.0);
        headerLabel2.setText("CONFIDENTIAL");
        headerLabel2.setUnderline(true);
        headerLabel2.setFont(new Font("Arial",10));
        headerLabel2.setStyle("-fx-font-weight : bold");
        headerLabel2.setAlignment(Pos.TOP_LEFT);
        headerLabel2.setVisible(false);

        AnchorPane.setLeftAnchor(headerLabel3,30.0);
        //AnchorPane.setRightAnchor(headerLabel2,0.0);
        AnchorPane.setTopAnchor(headerLabel3,110.0);
        headerLabel3.setText("NAME OF THE PATIENT");
        //headerLabel3.setUnderline(true);
        headerLabel3.setFont(new Font("Arial",12));
        //headerLabel3.setStyle("-fx-font-weight : bold");
        headerLabel3.setAlignment(Pos.TOP_LEFT);
        headerLabel3.setVisible(true);

        AnchorPane.setLeftAnchor(headerLabel4,30.0);
        //AnchorPane.setRightAnchor(headerLabel4,0.0);
        AnchorPane.setTopAnchor(headerLabel4,130.0);
        headerLabel4.setText("AGE");
        //headerLabel4.setUnderline(true);
        headerLabel4.setFont(new Font("Arial",12));
        //headerLabel4.setStyle("-fx-font-weight : bold");
        headerLabel4.setAlignment(Pos.TOP_LEFT);
        headerLabel4.setVisible(true);

        AnchorPane.setLeftAnchor(headerLabel5,30.0);
        //AnchorPane.setRightAnchor(headerLabel5,0.0);
        AnchorPane.setTopAnchor(headerLabel5,190.0);
        headerLabel5.setText("REFERRED BY");
        //headerLabel5.setUnderline(true);
        headerLabel5.setFont(new Font("Arial",12));
        //headerLabel5.setStyle("-fx-font-weight : bold");
        headerLabel5.setAlignment(Pos.TOP_LEFT);
        headerLabel5.setVisible(true);

        AnchorPane.setLeftAnchor(headerLabel6,400.0);
        //AnchorPane.setRightAnchor(headerLabel6,0.0);
        AnchorPane.setTopAnchor(headerLabel6,110.0);
        headerLabel6.setText("DATE");
        //headerLabel6.setUnderline(true);
        headerLabel6.setFont(new Font("Arial",12));
        //headerLabel6.setStyle("-fx-font-weight : bold");
        headerLabel6.setAlignment(Pos.TOP_LEFT);
        headerLabel6.setVisible(true);

        AnchorPane.setLeftAnchor(headerLabel7,30.0);
        //AnchorPane.setRightAnchor(headerLabel7,0.0);
        AnchorPane.setTopAnchor(headerLabel7,150.0);
        headerLabel7.setText("SEX");
        //headerLabel7.setUnderline(true);
        headerLabel7.setFont(new Font("Arial",12));
        //headerLabel7.setStyle("-fx-font-weight : bold");
        headerLabel7.setAlignment(Pos.TOP_LEFT);
        headerLabel7.setVisible(true);

        AnchorPane.setLeftAnchor(headerLabel8,30.0);
        //AnchorPane.setRightAnchor(headerLabel8,0.0);
        AnchorPane.setTopAnchor(headerLabel8,170.0);
        headerLabel8.setText("REFERENCE NO.");
        //headerLabel8.setUnderline(true);
        headerLabel8.setFont(new Font("Arial",12));
        //headerLabel8.setStyle("-fx-font-weight : bold");
        headerLabel8.setAlignment(Pos.TOP_LEFT);
        headerLabel8.setVisible(true);

        AnchorPane.setLeftAnchor(headerLabel9,30.0);
        //AnchorPane.setRightAnchor(headerLabel9,0.0);
        AnchorPane.setTopAnchor(headerLabel9,210.0);
        headerLabel9.setText("SPECIMEN");
        //headerLabel9.setUnderline(true);
        headerLabel9.setFont(new Font("Arial",12));
        //headerLabel9.setStyle("-fx-font-weight : bold");
        headerLabel9.setAlignment(Pos.TOP_LEFT);
        headerLabel9.setVisible(true);

        AnchorPane.setLeftAnchor(headerDotLabel1,185.0);
        //AnchorPane.setRightAnchor(headerDotLabel1,0.0);
        AnchorPane.setTopAnchor(headerDotLabel1,110.0);
        headerDotLabel1.setText(":");
        //headerDotLabel1.setUnderline(true);
        headerDotLabel1.setFont(new Font("Arial",12));
        //headerDotLabel1.setStyle("-fx-font-weight : bold");
        headerDotLabel1.setAlignment(Pos.TOP_LEFT);
        headerDotLabel1.setVisible(true);

        AnchorPane.setLeftAnchor(headerDotLabel2,185.0);
        //AnchorPane.setRightAnchor(headerDotLabel2,0.0);
        AnchorPane.setTopAnchor(headerDotLabel2,130.0);
        headerDotLabel2.setText(":");
        //headerDotLabel2.setUnderline(true);
        headerDotLabel2.setFont(new Font("Arial",12));
        //headerDotLabel2.setStyle("-fx-font-weight : bold");
        headerDotLabel2.setAlignment(Pos.TOP_LEFT);
        headerDotLabel2.setVisible(true);

        AnchorPane.setLeftAnchor(headerDotLabel3,185.0);
        //AnchorPane.setRightAnchor(headerDotLabel3,0.0);
        AnchorPane.setTopAnchor(headerDotLabel3,150.0);
        headerDotLabel3.setText(":");
        //headerDotLabel3.setUnderline(true);
        headerDotLabel3.setFont(new Font("Arial",12));
        //headerDotLabel3.setStyle("-fx-font-weight : bold");
        headerDotLabel3.setAlignment(Pos.TOP_LEFT);
        headerDotLabel3.setVisible(true);

        AnchorPane.setLeftAnchor(headerDotLabel4,185.0);
        //AnchorPane.setRightAnchor(headerDotLabel4,0.0);
        AnchorPane.setTopAnchor(headerDotLabel4,170.0);
        headerDotLabel4.setText(":");
        //headerDotLabel4.setUnderline(true);
        headerDotLabel4.setFont(new Font("Arial",12));
        //headerDotLabel4.setStyle("-fx-font-weight : bold");
        headerDotLabel4.setAlignment(Pos.TOP_LEFT);
        headerDotLabel4.setVisible(true);

        AnchorPane.setLeftAnchor(headerDotLabel5,185.0);
        //AnchorPane.setRightAnchor(headerDotLabel5,0.0);
        AnchorPane.setTopAnchor(headerDotLabel5,190.0);
        headerDotLabel5.setText(":");
        //headerDotLabel5.setUnderline(true);
        headerDotLabel5.setFont(new Font("Arial",12));
        //headerDotLabel5.setStyle("-fx-font-weight : bold");
        headerDotLabel5.setAlignment(Pos.TOP_LEFT);
        headerDotLabel5.setVisible(true);

        AnchorPane.setLeftAnchor(headerDotLabel6,450.0);
        //AnchorPane.setRightAnchor(headerDotLabel6,0.0);
        AnchorPane.setTopAnchor(headerDotLabel6,110.0);
        headerDotLabel6.setText(":");
        //headerDotLabel6.setUnderline(true);
        headerDotLabel6.setFont(new Font("Arial",12));
        //headerDotLabel6.setStyle("-fx-font-weight : bold");
        headerDotLabel6.setAlignment(Pos.TOP_LEFT);
        headerDotLabel6.setVisible(true);

        AnchorPane.setLeftAnchor(headerDotLabel7,185.0);
        //AnchorPane.setRightAnchor(headerDotLabel7,0.0);
        AnchorPane.setTopAnchor(headerDotLabel7,210.0);
        headerDotLabel7.setText(":");
        //headerDotLabel7.setUnderline(true);
        headerDotLabel7.setFont(new Font("Arial",12));
        //headerDotLabel7.setStyle("-fx-font-weight : bold");
        headerDotLabel7.setAlignment(Pos.TOP_LEFT);
        headerDotLabel7.setVisible(true);

        AnchorPane.setTopAnchor(line01,100.0);
        //AnchorPane.setLeftAnchor(line01,5.0);
        line01.setEndX(579.0);
        line01.setStartX(5.0);
        line01.setStrokeWidth(1.0);
        line01.setOpacity(0.5);

        AnchorPane.setLeftAnchor(vBox,5.0);
        AnchorPane.setRightAnchor(vBox,5.0);
        AnchorPane.setTopAnchor(vBox,230.0);
        AnchorPane.setBottomAnchor(vBox,130.0);
        vBox.setStyle("-fx-border-color : #000000");

        AnchorPane.setTopAnchor(line02,225.0);
        //AnchorPane.setLeftAnchor(line01,5.0);
        line02.setEndX(579.0);
        line02.setStartX(5.0);
        line02.setStrokeWidth(1.0);
        line02.setVisible(true);
        line02.setOpacity(0.5);

        AnchorPane.setLeftAnchor(mltLabel1,400.0);
        AnchorPane.setRightAnchor(mltLabel1,5.0);
        AnchorPane.setTopAnchor(mltLabel1,690.0);
        mltLabel1.setText("..........................................................");
        //mltLabel1.setUnderline(true);
        mltLabel1.setFont(new Font("Arial",10));
        //mltLabel1.setStyle("-fx-font-weight : bold");
        mltLabel1.setAlignment(Pos.CENTER);
        mltLabel1.setVisible(false);

        AnchorPane.setLeftAnchor(mltLabel2,400.0);
        AnchorPane.setRightAnchor(mltLabel2,5.0);
        AnchorPane.setTopAnchor(mltLabel2,750.0);
        mltLabel2.setText("Signature of Gov. Qua. MLT");
        //mltLabel2.setUnderline(true);
        mltLabel2.setFont(new Font("Arial",10));
        //mltLabel2.setStyle("-fx-font-weight : bold");
        mltLabel2.setAlignment(Pos.CENTER);
        mltLabel2.setVisible(true);

        AnchorPane.setLeftAnchor(addressLabel1,5.0);
        AnchorPane.setRightAnchor(addressLabel1,5.0);
        AnchorPane.setTopAnchor(addressLabel1,720.0);
        addressLabel1.setText(".............................Address Line.............................");
        //addressLabel1.setUnderline(true);
        addressLabel1.setFont(new Font("Arial",10));
        //addressLabel1.setStyle("-fx-font-weight : bold");
        addressLabel1.setAlignment(Pos.CENTER);
        addressLabel1.setVisible(false);

        AnchorPane.setLeftAnchor(addressLabel2,5.0);
        AnchorPane.setRightAnchor(addressLabel2,5.0);
        AnchorPane.setTopAnchor(addressLabel2,730.0);
        addressLabel2.setText(".............................Contact Information.............................");
        //addressLabel2.setUnderline(true);
        addressLabel2.setFont(new Font("Arial",10));
        //addressLabel2.setStyle("-fx-font-weight : bold");
        addressLabel2.setAlignment(Pos.CENTER);
        addressLabel2.setVisible(false);


        AnchorPane.setLeftAnchor(footerLabel1,420.0);
        //AnchorPane.setRightAnchor(footerLabel1,5.0);
        AnchorPane.setTopAnchor(footerLabel1,745.0);
        footerLabel1.setText("MLT info.line 01");
        //footerLabel1.setUnderline(true);
        footerLabel1.setFont(new Font("Arial",10));
        //footerLabel1.setStyle("-fx-font-weight : bold");
        footerLabel1.setAlignment(Pos.TOP_LEFT);
        footerLabel1.setVisible(false);

        AnchorPane.setLeftAnchor(footerLabel2,420.0);
        //AnchorPane.setRightAnchor(footerLabel2,5.0);
        AnchorPane.setTopAnchor(footerLabel2,756.0);
        footerLabel2.setText("MLT info.line 02");
        //footerLabel2.setUnderline(true);
        footerLabel2.setFont(new Font("Arial",10));
        //footerLabel2.setStyle("-fx-font-weight : bold");
        footerLabel2.setAlignment(Pos.TOP_LEFT);
        footerLabel2.setVisible(false);

        AnchorPane.setLeftAnchor(footerLabel3,420.0);
        //AnchorPane.setRightAnchor(footerLabel3,5.0);
        AnchorPane.setTopAnchor(footerLabel3,767.0);
        footerLabel3.setText("MLT info.line 03");
        //footerLabel3.setUnderline(true);
        footerLabel3.setFont(new Font("Arial",10));
        //footerLabel3.setStyle("-fx-font-weight : bold");
        footerLabel3.setAlignment(Pos.TOP_LEFT);
        footerLabel3.setVisible(false);

        AnchorPane.setLeftAnchor(footerLabel4,420.0);
        //AnchorPane.setRightAnchor(footerLabel4,5.0);
        AnchorPane.setTopAnchor(footerLabel4,778.0);
        footerLabel4.setText("MLT info.line 04");
        //footerLabel4.setUnderline(true);
        footerLabel4.setFont(new Font("Arial",10));
        //footerLabel4.setStyle("-fx-font-weight : bold");
        footerLabel4.setAlignment(Pos.TOP_LEFT);
        footerLabel4.setVisible(false);

        /*
         * these labels for inserting data */

        AnchorPane.setLeftAnchor(nameLabel,200.0);
        AnchorPane.setTopAnchor(nameLabel,110.0);
        nameLabel.setFont(new Font("Arial",12));
        nameLabel.setText("name");
        //nameLabel.setStyle("-fx-font-weight : bold");
        nameLabel.setAlignment(Pos.TOP_LEFT);
        nameLabel.setVisible(true);

        AnchorPane.setLeftAnchor(ageLabel,200.0);
        AnchorPane.setTopAnchor(ageLabel,130.0);
        ageLabel.setFont(new Font("Arial",12));
        ageLabel.setText("age");
        //ageLabel.setStyle("-fx-font-weight : bold");
        ageLabel.setAlignment(Pos.TOP_LEFT);
        ageLabel.setVisible(true);

        AnchorPane.setLeftAnchor(reqDocLabel,200.0);
        AnchorPane.setTopAnchor(reqDocLabel,190.0);
        reqDocLabel.setFont(new Font("Arial",12));
        reqDocLabel.setText("doctor");
        //reqDocLabel.setStyle("-fx-font-weight : bold");
        reqDocLabel.setAlignment(Pos.TOP_LEFT);
        reqDocLabel.setVisible(true);

        AnchorPane.setLeftAnchor(dateLabel,465.0);
        AnchorPane.setTopAnchor(dateLabel,110.0);
        dateLabel.setFont(new Font("Arial",12));
        dateLabel.setText("date");
        //dateLabel.setStyle("-fx-font-weight : bold");
        dateLabel.setAlignment(Pos.TOP_LEFT);
        dateLabel.setVisible(true);

        AnchorPane.setLeftAnchor(sexLabel,200.0);
        AnchorPane.setTopAnchor(sexLabel,150.0);
        sexLabel.setFont(new Font("Arial",12));
        sexLabel.setText("sex ");
        //sexLabel.setStyle("-fx-font-weight : bold");
        sexLabel.setAlignment(Pos.TOP_LEFT);
        sexLabel.setVisible(true);

        AnchorPane.setLeftAnchor(refLabel,200.0);
        AnchorPane.setTopAnchor(refLabel,170.0);
        refLabel.setFont(new Font("Arial",12));
        refLabel.setText("reference ");
        //refLabel.setStyle("-fx-font-weight : bold");
        refLabel.setAlignment(Pos.TOP_LEFT);
        refLabel.setVisible(true);

        AnchorPane.setLeftAnchor(specimenLabel,200.0);
        AnchorPane.setTopAnchor(specimenLabel,210.0);
        specimenLabel.setFont(new Font("Arial",12));
        specimenLabel.setText("specimen ");
        //specimenLabel.setStyle("-fx-font-weight : bold");
        specimenLabel.setAlignment(Pos.TOP_LEFT);
        specimenLabel.setVisible(true);

        AnchorPane.setLeftAnchor(timeLabel,30.0);
        AnchorPane.setTopAnchor(timeLabel,750.0);
        timeLabel.setFont(new Font("Arial",12));
        timeLabel.setText("time");
        //timeLabel.setStyle("-fx-font-weight : bold");
        timeLabel.setAlignment(Pos.TOP_LEFT);
        timeLabel.setVisible(false);

        root.getChildren().clear();
        root.getChildren().addAll(headerLabel1,headerLabel2,headerLabel3,headerLabel4,headerLabel5,headerLabel6,headerLabel7,headerLabel8,headerLabel9,
                headerDotLabel1,headerDotLabel2,headerDotLabel3,headerDotLabel4,headerDotLabel5,headerDotLabel6,headerDotLabel7,
                line01,line02,vBox,mltLabel1,mltLabel2,addressLabel1,addressLabel2,footerLabel1,
                footerLabel2,footerLabel3,footerLabel4,nameLabel,ageLabel,reqDocLabel,dateLabel,sexLabel,refLabel,specimenLabel,timeLabel);
    }
    public void saveDefaults() {
        try {
            PrintStream saveLayout = new PrintStream("Configure\\headerLabel1.txt");
            saveLayout.println(0.0);
            saveLayout.println(25.0);
            saveLayout.println(0.0);
            saveLayout.println("null");
            saveLayout.println("true");
            saveLayout.println("true");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(16.0);
            saveLayout.println("CENTER");
            saveLayout.println("Medical Laboratory Report");
            saveLayout.println("false");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerLabel2.txt");
            saveLayout.println(5.0);
            saveLayout.println(5.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("true");
            saveLayout.println("true");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(10.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("CONFIDENTIAL");
            saveLayout.println("false");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerLabel3.txt");
            saveLayout.println(30.0);
            saveLayout.println(110.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("NAME OF THE PATIENT");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerLabel4.txt");
            saveLayout.println(30.0);
            saveLayout.println(130.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("AGE");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerLabel5.txt");
            saveLayout.println(30.0);
            saveLayout.println(190.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("REFERRED BY");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerLabel6.txt");
            saveLayout.println(400.0);
            saveLayout.println(110.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("DATE");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerLabel7.txt");
            saveLayout.println(30.0);
            saveLayout.println(150.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("SEX");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerLabel8.txt");
            saveLayout.println(30.0);
            saveLayout.println(170.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("REFERENCE NO.");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerLabel9.txt");
            saveLayout.println(30.0);
            saveLayout.println(210.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("SPECIMEN");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerDotLabel1.txt");
            saveLayout.println(185.0);
            saveLayout.println(110.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println(":");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerDotLabel2.txt");
            saveLayout.println(185.0);
            saveLayout.println(130.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println(":");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerDotLabel3.txt");
            saveLayout.println(185.0);
            saveLayout.println(150.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println(":");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerDotLabel4.txt");
            saveLayout.println(185.0);
            saveLayout.println(170.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println(":");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerDotLabel5.txt");
            saveLayout.println(185.0);
            saveLayout.println(190.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println(":");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerDotLabel6.txt");
            saveLayout.println(450.0);
            saveLayout.println(110.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println(":");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\headerDotLabel7.txt");
            saveLayout.println(185.0);
            saveLayout.println(210.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println(":");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\mltLabel1.txt");
            saveLayout.println(400.0);
            saveLayout.println(690.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(10.0);
            saveLayout.println("CENTER");
            saveLayout.println("..........................................................");
            saveLayout.println("false");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\mltLabel2.txt");
            saveLayout.println(400.0);
            saveLayout.println(750.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(10.0);
            saveLayout.println("CENTER");
            saveLayout.println("Signature of Gov. Qua. MLT");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\addressLabel1.txt");
            saveLayout.println(5.0);
            saveLayout.println(720.0);
            saveLayout.println(0.0);
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(10.0);
            saveLayout.println("CENTER");
            saveLayout.println(".............................Address Line.............................");
            saveLayout.println("false");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\addressLabel2.txt");
            saveLayout.println(5.0);
            saveLayout.println(730.0);
            saveLayout.println(0.0);
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(10.0);
            saveLayout.println("CENTER");
            saveLayout.println(".............................Contact Information.............................");
            saveLayout.println("false");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\footerLabel1.txt");
            saveLayout.println(420.0);
            saveLayout.println(745.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(10.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("MLT info.line 01");
            saveLayout.println("false");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\footerLabel2.txt");
            saveLayout.println(420.0);
            saveLayout.println(756.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(10.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("MLT info.line 02");
            saveLayout.println("false");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\footerLabel3.txt");
            saveLayout.println(420.0);
            saveLayout.println(767.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(10.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("MLT info.line 03");
            saveLayout.println("false");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\footerLabel4.txt");
            saveLayout.println(420.0);
            saveLayout.println(778.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(10.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("MLT info.line 04");
            saveLayout.println("false");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\nameLabel.txt");
            saveLayout.println(200.0);
            saveLayout.println(110.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("name");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\dateLabel.txt");
            saveLayout.println(465.0);
            saveLayout.println(110.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("date");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\ageLabel.txt");
            saveLayout.println(200.0);
            saveLayout.println(130.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("age");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\reqDocLabel.txt");
            saveLayout.println(200.0);
            saveLayout.println(190.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("doctor");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\sexLabel.txt");
            saveLayout.println(200.0);
            saveLayout.println(150.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("sex");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\refLabel.txt");
            saveLayout.println(200.0);
            saveLayout.println(170.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("reference");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\specimenLabel.txt");
            saveLayout.println(200.0);
            saveLayout.println(210.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("specimen");
            saveLayout.println("true");
            saveLayout.close();

            saveLayout = new PrintStream("Configure\\timeLabel.txt");
            saveLayout.println(30.0);
            saveLayout.println(750.0);
            saveLayout.println("null");
            saveLayout.println("null");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("false");
            saveLayout.println("Arial");
            saveLayout.println(12.0);
            saveLayout.println("TOP_LEFT");
            saveLayout.println("time");
            saveLayout.println("false");
            saveLayout.close();
        } catch (Exception e) {
            e.printStackTrace();
            messageBox.showMessage("Error restoring Defaults","Error");
        }
    }

    private void setIDs() {
        headerLabel1.setId("headerLabel1");
        headerLabel2.setId("headerLabel2");
        headerLabel3.setId("headerLabel3");
        headerLabel4.setId("headerLabel4");
        headerLabel5.setId("headerLabel5");
        headerLabel6.setId("headerLabel6");
        headerLabel7.setId("headerLabel7");
        headerLabel8.setId("headerLabel8");
        headerLabel9.setId("headerLabel9");

        headerDotLabel1.setId("headerDotLabel1");
        headerDotLabel2.setId("headerDotLabel2");
        headerDotLabel3.setId("headerDotLabel3");
        headerDotLabel4.setId("headerDotLabel4");
        headerDotLabel5.setId("headerDotLabel5");
        headerDotLabel6.setId("headerDotLabel6");
        headerDotLabel7.setId("headerDotLabel7");

        mltLabel1.setId("mltLabel1");
        mltLabel2.setId("mltLabel2");

        addressLabel1.setId("addressLabel1");
        addressLabel2.setId("addressLabel2");

        footerLabel1.setId("footerLabel1");
        footerLabel2.setId("footerLabel2");
        footerLabel3.setId("footerLabel3");
        footerLabel4.setId("footerLabel4");

        nameLabel.setId("nameLabel");
        ageLabel.setId("ageLabel");
        dateLabel.setId("dateLabel");
        reqDocLabel.setId("reqDocLabel");
        sexLabel.setId("sexLabel");
        refLabel.setId("refLabel");
        specimenLabel.setId("specimenLabel");
        timeLabel.setId("timeLabel");
    }

    public Label getNode(String ID) {
        for (Label label : labels) {
            if (label.getId().equals(ID)) {
                return label;
            }
        }
        return null;
    }

    public VBox getvBox() {
        return vBox;
    }
    public AnchorPane getBase() {
        return root;
    }
    public Label getNameLabel() {
        return nameLabel;
    }
    public Label getAgeLabel() {
        return ageLabel;
    }
    public Label getDateLabel() {
        return dateLabel;
    }
    public Label getReqDocLabel() {
        return reqDocLabel;
    }
    public Label getsexLabel() {
        return sexLabel;
    }
    public Label getRefLabel() { return refLabel; }
    public Label getSpecimenLabel() { return specimenLabel; }
    public Label getTimeLabel() { return timeLabel; }
}
