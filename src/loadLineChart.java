

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loadLineChart {
     static ResultSet resultSet;

    public static void loadFBS(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("FBS", patientID);
            lineChart.getData().clear();
            lineChart.setTitle("FBS");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("ESR Values (mm/hr)");
            XYChart.Series<String, Number> fastingSeries = new XYChart.Series<>();
            while (resultSet.next()) {
                try {
                    fastingSeries.getData().add(new XYChart.Data<String, Number>(resultSet.getString("DATE"), resultSet.getDouble("FBSMG")));
                } catch (NumberFormatException e) {
                    continue;
                }
            }
            fastingSeries.setName("Fasting values");
            Database.disconnect();
            lineChart.getData().add(fastingSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
            e.printStackTrace();
        }

    }
    public static void loadPPBS(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("PPBS", patientID);
            lineChart.getData().clear();
            lineChart.setTitle("POST PRANDIAL BLOOD SUGAR");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> ppbsSeries = new XYChart.Series<>();
            while (resultSet.next()) {
                try {
                    ppbsSeries.getData().add(new XYChart.Data<String, Number>(resultSet.getString("DATE"), resultSet.getDouble("PPBSMG")));
                } catch (Exception e) {
                    continue;
                }
            }
            ppbsSeries.setName("PPBS");
            Database.disconnect();
            lineChart.getData().add(ppbsSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadPPBSALL(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("PPBSALL", patientID);
            lineChart.getData().clear();
            lineChart.setTitle("POST PRANDIAL BLOOD SUGAR - ALL");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> fastingSeries = new XYChart.Series<>();
            while (resultSet.next()) {
                try {
                    fastingSeries.getData().add(new XYChart.Data<String, Number>(resultSet.getString("DATE"), resultSet.getDouble("PPBSBRMG")));
                    } catch (Exception e) {
                continue;
                }
            }
            fastingSeries.setName("PPBS");
            Database.disconnect();
            lineChart.getData().add(fastingSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadESR(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("ESR", patientID);
            lineChart.setTitle("ESR");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("ESR Values (mm/hr)");
            XYChart.Series<String, Number> esr1Series = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                esr1Series.getData().add(new XYChart.Data<String, Number>(resultSet.getString("DATE"), resultSet.getDouble("ESR1ST")));
                } catch (Exception e) {
                    continue;
                }
            }
            esr1Series.setName("ESR 1st hr. values");
            Database.disconnect();
            lineChart.getData().add(esr1Series);
        }catch (SQLException e){
        messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadLipid_Profile(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("LIPID_PROFILE", patientID);
            lineChart.setTitle("Lipid_Profile");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> TChoSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> HDLChoSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> LDLChoSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> VLDLChoSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> TriSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> TchoHDLChoSeries = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                TChoSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                HDLChoSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(4)));
                LDLChoSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(5)));
                VLDLChoSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(7)));
                TriSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(6)));
                TchoHDLChoSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(8)));
                } catch (Exception e) {
                    continue;
                }
            }
            TChoSeries.setName("Total Cholesterol");
            HDLChoSeries.setName("HDL Cholesterol");
            LDLChoSeries.setName("LDL Cholesterol");
            VLDLChoSeries.setName("VLDL Cholesterol");
            TriSeries.setName("Triglycerides");
            TchoHDLChoSeries.setName("Total Cho. / HDL Cho.");
            Database.disconnect();
            lineChart.getData().addAll(TChoSeries, HDLChoSeries, LDLChoSeries, VLDLChoSeries, TriSeries, TchoHDLChoSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadSElectro(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("SELECTROLYTES", patientID);
            lineChart.setTitle("S. Electrolytes");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> NaSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> KSeries = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                NaSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                KSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(4)));
                } catch (Exception e) {
                    continue;
                }
            }
            NaSeries.setName("Serum Na+");
            KSeries.setName("Serum K+");
            Database.disconnect();
            lineChart.getData().addAll(NaSeries, KSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadMicroAlbumin(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("MICROALBUMIN", patientID);
            lineChart.setTitle("Microalbumin");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> uml = new XYChart.Series<>();
            XYChart.Series<String, Number> ucre = new XYChart.Series<>();
            XYChart.Series<String, Number> acr = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                    uml.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                    ucre.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(4)));
                    acr.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(5)));
                } catch (Exception e) {
                    continue;
                }
            }
            uml.setName("Urine Microalbumin");
            ucre.setName("Urine Creatinine");
            acr.setName("[ACR]");
            Database.disconnect();
            lineChart.getData().addAll(uml, ucre, acr);
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(), "Error");
        }
    }
    public static void loadSProteins(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("SPROTEINS", patientID);
            lineChart.setTitle("S. Proteins");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> totalP = new XYChart.Series<>();
            XYChart.Series<String, Number> albumin = new XYChart.Series<>();
            XYChart.Series<String, Number> globulin = new XYChart.Series<>();
            XYChart.Series<String, Number> ratio = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                    totalP.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                    albumin.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(4)));
                    globulin.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(5)));
                    ratio.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(6)));
                } catch (Exception e) {
                    continue;
                }
            }
            totalP.setName("Urine Proteins");
            albumin.setName("Albumin");
            globulin.setName("Globulin");
            ratio.setName("A/G");
            Database.disconnect();
            lineChart.getData().addAll(totalP, albumin, globulin, ratio);
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(), "Error");
        }
    }
    public static void loadOTPT(LineChart<String, Number> lineChart, int patientID){
        try {
            resultSet = Database.getTestData("OTPT", patientID);
            lineChart.setTitle("OT / PT");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> ot = new XYChart.Series<>();
            XYChart.Series<String, Number> pt = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                ot.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                pt.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(4)));
                } catch (Exception e) {
                    continue;
                }
            }
            ot.setName("OT");
            pt.setName("PT");
            Database.disconnect();
            lineChart.getData().addAll(ot, pt);
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(), "Error");
        }
    }
    public static void loadBilirubin(LineChart<String, Number> lineChart, int patientID){
        try {
            resultSet = Database.getTestData("BILIRUBIN", patientID);
            lineChart.setTitle("Bilirubin");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> uml = new XYChart.Series<>();
            XYChart.Series<String, Number> ucre = new XYChart.Series<>();
            XYChart.Series<String, Number> acr = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                uml.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                ucre.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(4)));
                acr.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(5)));
                } catch (Exception e) {
                    continue;
                }
            }
            uml.setName("Total Bilirubin");
            ucre.setName("Direct Bilirubin");
            acr.setName("Indirect Bilirubin");
            Database.disconnect();
            lineChart.getData().addAll(uml, ucre, acr);
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(), "Error");
        }
    }
    public static void loadOGTT(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("ORALGLUCOSE", patientID);
            lineChart.setTitle("O.G.T.T.");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> FBSSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> PG1Series = new XYChart.Series<>();
            XYChart.Series<String, Number> PG2Series = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                FBSSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                PG1Series.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(5)));
                PG2Series.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(7)));
            }
            FBSSeries.setName("FBS");
            PG1Series.setName("1st hr Plasma Glucose");
            PG2Series.setName("2nd hr Plasma Glucose");
            Database.disconnect();
            lineChart.getData().addAll(FBSSeries, PG1Series, PG2Series);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadLFT(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("LFT", patientID);
            lineChart.setTitle("LFT");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> Series1 = new XYChart.Series<>();
            XYChart.Series<String, Number> Series2 = new XYChart.Series<>();
            XYChart.Series<String, Number> Series3 = new XYChart.Series<>();
            XYChart.Series<String, Number> Series4 = new XYChart.Series<>();
            XYChart.Series<String, Number> Series5 = new XYChart.Series<>();
            XYChart.Series<String, Number> Series6 = new XYChart.Series<>();
            XYChart.Series<String, Number> Series7 = new XYChart.Series<>();
            XYChart.Series<String, Number> Series8 = new XYChart.Series<>();
            XYChart.Series<String, Number> Series9 = new XYChart.Series<>();
            XYChart.Series<String, Number> Series10 = new XYChart.Series<>();
            XYChart.Series<String, Number> Series11 = new XYChart.Series<>();
            XYChart.Series<String, Number> Series12 = new XYChart.Series<>();
            XYChart.Series<String, Number> Series13 = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                Series1.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                Series2.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(4)));
                Series3.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(5)));
                Series4.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(7)));
                Series5.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(8)));
                Series6.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(9)));
                Series7.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(10)));
                Series8.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(11)));
                Series9.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(12)));
                Series10.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(13)));
                Series11.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(14)));
                Series12.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(15)));
                Series13.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(16)));
                } catch (Exception e) {
                    continue;
                }
            }
            Series1.setName("Total Bilirubin");
            Series2.setName("Direct Bilirubin");
            Series3.setName("Indirect Bilirubin");
            Series4.setName("OT");
            Series5.setName("PT");
            Series6.setName("S. Alk. Phosphatase");
            Series7.setName("Gamma GT");
            Series8.setName("Total Proteins");
            Series9.setName("Albumin");
            Series10.setName("Globulin");
            Series11.setName("A/G");
            Series12.setName("Test");
            Series13.setName("Control");
            Database.disconnect();
            lineChart.getData().addAll(Series1,Series2,Series3,Series4,Series5,Series6,Series7,Series8,Series9,Series10,Series11,Series12,Series13);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadRheFac(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("RHE_FACTORS", patientID);
            lineChart.setTitle("Rhe_Factors");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> TitreSeries = new XYChart.Series<>();
            lineChart.getData().clear();

            while (resultSet.next()) {
                try {
                TitreSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble("TITRE")));
                } catch (Exception e) {
                    continue;
                }
            }
            TitreSeries.setName("Titre");
            Database.disconnect();
            lineChart.getData().add(TitreSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
            e.printStackTrace();
        }
    }
    public static void loadEGFR(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("EGFR", patientID);
            lineChart.setTitle("EGFR");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> TitreSeries = new XYChart.Series<>();
            lineChart.getData().clear();

            while (resultSet.next()) {
                try {
                TitreSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                } catch (Exception e) {
                    continue;
                }
            }
            TitreSeries.setName("EGFR");
            Database.disconnect();
            lineChart.getData().add(TitreSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
            e.printStackTrace();
        }
    }
    public static void loadCRP(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("CRP", patientID);
            lineChart.setTitle("CRP");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> TitreSeries = new XYChart.Series<>();
            lineChart.getData().clear();

            while (resultSet.next()) {
                try {
                TitreSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble("TITRE")));
                } catch (Exception e) {
                    continue;
                }
            }
            TitreSeries.setName("Titre");
            Database.disconnect();
            lineChart.getData().add(TitreSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
            e.printStackTrace();
        }
    }
    public static void loadGlyHemo(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("GLYCO_HEMO", patientID);
            lineChart.setTitle("Glyco_Hemo");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> HBA1CSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> MPGSeries = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                HBA1CSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble("GHA1C")));
                MPGSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble("MEAN_BLOOD_GLUCOSE")));
                } catch (Exception e) {
                    continue;
                }
            }
            HBA1CSeries.setName("GHA1C");
            MPGSeries.setName("Mean Plasma Glucose");
            Database.disconnect();
            lineChart.getData().addAll(HBA1CSeries, MPGSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadSUSP(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("SURICSPHOSPHORUS", patientID);
            lineChart.setTitle("S. Uric Acid & S. Phosphorus");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> HBA1CSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> MPGSeries = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                HBA1CSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                MPGSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(4)));
                } catch (Exception e) {
                    continue;
                }
            }
            HBA1CSeries.setName("S. Uric Acid");
            MPGSeries.setName("S. Phosphorus");
            Database.disconnect();
            lineChart.getData().addAll(HBA1CSeries, MPGSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadSCre(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("SCREATININE", patientID);
            lineChart.setTitle("S. Creatinine");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> SCreSeries = new XYChart.Series<>();

            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                SCreSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                } catch (Exception e) {
                    continue;
                }
            }
            SCreSeries.setName("S. Creatinine");
            Database.disconnect();
            lineChart.getData().add(SCreSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadSIC(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("SIONIZED_CALCIUM", patientID);
            lineChart.setTitle("S. Ionized Calcium");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> SCreSeries = new XYChart.Series<>();

            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                SCreSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                } catch (Exception e) {
                    continue;
                }
            }
            SCreSeries.setName("S. Ionized Calcium");
            Database.disconnect();
            lineChart.getData().add(SCreSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadSTC(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("STOTAL_CALCIUM", patientID);
            lineChart.setTitle("S. Total Calcium");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> SCreSeries = new XYChart.Series<>();

            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                SCreSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                } catch (Exception e) {
                    continue;
                }
            }
            SCreSeries.setName("S. Total Calcium");
            Database.disconnect();
            lineChart.getData().add(SCreSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadSChol(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("SCHOLESTEROL", patientID);
            lineChart.setTitle("S. Cholesterol");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> SCreSeries = new XYChart.Series<>();

            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                SCreSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                } catch (Exception e) {
                    continue;
                }
            }
            SCreSeries.setName("S. Cholesterol");
            Database.disconnect();
            lineChart.getData().add(SCreSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadSAlkPhos(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("SALKPHOSPHATASE", patientID);
            lineChart.setTitle("S. Alk. Phosphatase");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> SCreSeries = new XYChart.Series<>();

            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                SCreSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                } catch (Exception e) {
                    continue;
                }
            }
            SCreSeries.setName("S. Alk. Phosphatase");
            Database.disconnect();
            lineChart.getData().add(SCreSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadHB(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("HB", patientID);
            lineChart.setTitle("Hb");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> hbSeries = new XYChart.Series<>();

            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                hbSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                } catch (Exception e) {
                    continue;
                }
            }
            hbSeries.setName("S. Creatinine");
            Database.disconnect();
            lineChart.getData().add(hbSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadFBC(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("FBC", patientID);
            lineChart.setTitle("FBC");
            lineChart.getYAxis().setLabel("FBC Values");
            lineChart.getXAxis().setLabel("Date");
            XYChart.Series<String, Number> wbcSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> neutroSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> lymphoSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> eosinoSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> monoSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> basoSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> plateletSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> hbSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> pcvSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> rbcSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> mcvSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> mchSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> mchcSeries = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                    wbcSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(3) / 100));
                    neutroSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(4)));
                    lymphoSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(5)));
                    eosinoSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(6)));
                    monoSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(7)));
                    basoSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(8)));
                    hbSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(9)));
                    pcvSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(10)));
                    plateletSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(15)));
                    mcvSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(14)));
                    mchSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(13)));
                    mchcSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(12)));
                    rbcSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(11)));
                } catch (Exception e) {
                    continue;
                }
            }
            wbcSeries.setName("WBC");
            neutroSeries.setName("Neutrophils");
            lymphoSeries.setName("Lymphocytes");
            eosinoSeries.setName("Eosinophils");
            monoSeries.setName("Monocytes");
            basoSeries.setName("Basophills");
            hbSeries.setName("Hb%");
            pcvSeries.setName("P.V.C.");
            plateletSeries.setName("Platelet Count x10^3");
            mcvSeries.setName("Mean Cell Volume");
            mchSeries.setName("Mean Cell Hemoglobin");
            mchcSeries.setName("M.C.H.C.Contration");
            rbcSeries.setName("R.B.C.");
            Database.disconnect();
            lineChart.getData().addAll(wbcSeries, neutroSeries, lymphoSeries, eosinoSeries, monoSeries, basoSeries, plateletSeries, hbSeries, pcvSeries, rbcSeries, mcvSeries, mchSeries, mchcSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
            e.printStackTrace();
        }
    }
    public static void loadGlucoTolerance(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("GLUCOSETOLERANCE", patientID);
            lineChart.setTitle("Glucose Tolerance");
            lineChart.getYAxis().setLabel("Values");
            lineChart.getXAxis().setLabel("Date");
            XYChart.Series<String, Number> B1 = new XYChart.Series<>();

            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                B1.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(3)));
                } catch (Exception e) {
                    continue;
                }
            }
            B1.setName("Glucose Tolerance");
            Database.disconnect();
            lineChart.getData().add(B1);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadGCT(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("GCT", patientID);
            lineChart.setTitle("GCT");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> GCTSeries = new XYChart.Series<>();

            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                    GCTSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                } catch (Exception e) {
                    continue;
                }
            }
            GCTSeries.setName("Glucose Challenge Test");
            Database.disconnect();
            lineChart.getData().add(GCTSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadBloodUrea(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("BLOOD_UREA", patientID);
            lineChart.setTitle("Blood Urea");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> bloodUrea = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                    bloodUrea.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                } catch (Exception e) {
                    continue;
                }
            }
            bloodUrea.setName("Blood Urea");
            Database.disconnect();
            lineChart.getData().add(bloodUrea);
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(), "Error");
        }
    }
    public static void loadRBS(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("RBS", patientID);
            lineChart.getData().clear();
            lineChart.setTitle("RBS");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> fastingSeries = new XYChart.Series<>();
            while (resultSet.next()) {
                try {
                    fastingSeries.getData().add(new XYChart.Data<String, Number>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                } catch (NumberFormatException e) {
                    continue;
                }
            }
            fastingSeries.setName("RBS");
            Database.disconnect();
            lineChart.getData().add(fastingSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
            e.printStackTrace();
        }

    }
    public static void loadUrineSugar(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("URINESUGAR", patientID);
            lineChart.getData().clear();
            lineChart.setTitle("Urine Sugar");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> urineSugarSeries = new XYChart.Series<>();
            while (resultSet.next()) {
                try {
                    urineSugarSeries.getData().add(new XYChart.Data<String, Number>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                } catch (NumberFormatException e) {
                    continue;
                }
            }
            urineSugarSeries.setName("Urine Sugar");
            Database.disconnect();
            lineChart.getData().add(urineSugarSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
            e.printStackTrace();
        }

    }
    public static void loadWBCDC(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("WBCDC", patientID);
            lineChart.setTitle("WBC / DC");
            lineChart.getYAxis().setLabel("FBC Values");
            lineChart.getXAxis().setLabel("Date");
            XYChart.Series<String, Number> wbcSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> neutroSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> lymphoSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> eosinoSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> monoSeries = new XYChart.Series<>();
//            XYChart.Series<String, Number> basoSeries = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                    wbcSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(3) / 100));
                    neutroSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(4)));
                    lymphoSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(5)));
                    eosinoSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(6)));
                    monoSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(7)));
//                    basoSeries.getData().add(new XYChart.Data<>(resultSet.getString("Date"), resultSet.getDouble(8)));
                } catch (Exception e) {
                    continue;
                }
            }
            wbcSeries.setName("WBC");
            neutroSeries.setName("Neutrophils");
            lymphoSeries.setName("Lymphocytes");
            eosinoSeries.setName("Eosinophils");
            monoSeries.setName("Monocytes");
//            basoSeries.setName("Basophills");
            Database.disconnect();
            lineChart.getData().addAll(wbcSeries, neutroSeries, lymphoSeries, eosinoSeries, monoSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
            e.printStackTrace();
        }
    }
    public static void loadGammaGT(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("GAMMAGT", patientID);
            lineChart.setTitle("GammaGT");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> gammaSeries = new XYChart.Series<>();

            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                    gammaSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                } catch (Exception e) {
                    continue;
                }
            }
            gammaSeries.setName("Gamma GT");
            Database.disconnect();
            lineChart.getData().add(gammaSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static void loadBSP(LineChart<String, Number> lineChart, int patientID) {
        try {
            resultSet = Database.getTestData("BSP", patientID);
            lineChart.setTitle("Blood Sugar Profile");
            lineChart.getXAxis().setLabel("Date");
            lineChart.getYAxis().setLabel("Values");
            XYChart.Series<String, Number> FBSSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> PPBSBSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> RBSLSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> PPBSLSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> RBSDSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> PPBSDSeries = new XYChart.Series<>();
            lineChart.getData().clear();
            while (resultSet.next()) {
                try {
                    FBSSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(3)));
                    PPBSBSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(4)));
                    RBSLSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(5)));
                    PPBSLSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(7)));
                    RBSDSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(6)));
                    PPBSDSeries.getData().add(new XYChart.Data<>(resultSet.getString("DATE"), resultSet.getDouble(8)));
                } catch (Exception e) {
                    continue;
                }
            }
            FBSSeries.setName("FBS");
            PPBSBSeries.setName("PPBS post Br.");
            RBSLSeries.setName("RBS After Ln.");
            PPBSLSeries.setName("PPBS post Ln.");
            RBSDSeries.setName("RBS After Dn.");
            PPBSDSeries.setName("PPBS post Dn.");
            Database.disconnect();
            lineChart.getData().addAll(FBSSeries, PPBSBSeries, RBSLSeries, PPBSLSeries, RBSDSeries, PPBSDSeries);
        }catch (SQLException e){
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
}