

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TestForRecipt {
    private String test;
    private String testName;
    private BooleanProperty isSelected = new SimpleBooleanProperty(false);
    private int cost;

    public TestForRecipt(String test, int cost) {
        this.test = test;
        this.cost = cost;
        setTestName(test);
    }
    private void setTestName(String testCode) {
         /*
        Each test must have their own case : statement
        */
        switch (testCode) {
            case "BLOOD_UREA":
                this.testName = "Blood Urea";
                break;
            case "CRP":
                this.testName = "CRP";
                break;
            case "DENGUETEST":
                this.testName = "Dengue Test";
                break;
            case "FILARIALANTIBODYTEST":
                this.testName = "Filarial Antibody Test";
                break;
            case "GLUCOSETOLERANCE":
                this.testName = "Glucose Tolerance Test";
                break;
            case "FBS":
                this.testName = "FBS";
                break;
            case "PPBSALL":
                this.testName = "PPBS";
                break;
            case "FBC":
                this.testName = "FBC";
                break;
            case "LIPID_PROFILE":
                this.testName = "Lipid Profile";
                break;
            case "GLYCO_HEMO":
                this.testName = "Glycosylated Hemoglobin Test";
                break;
            case "UFR":
                this.testName = "UFR";
                break;
            case "SFR":
                this.testName = "SFR";
                break;
            case "RHE_FACTORS":
                this.testName = "RH Factors";
                break;
            case "VDRL":
                this.testName = "VDRL";
                break;
            case "LFT":
                this.testName = "LFT";
                break;
            case "SCREATININE":
                this.testName = "Serum Creatinine";
                break;
            case "SELECTROLYTES":
                this.testName = "Serum Electrolyte";
                break;
            case "URINEHCG":
                this.testName = "Urine H.C.G.";
                break;
            case "ESR":
                this.testName = "ESR";
                break;
            case "HB":
                this.testName = "HB";
                break;
            case "HIV":
                this.testName = "HIV";
                break;
            case "MICROALBUMIN":
                this.testName = "Micro Albumin";
                break;
            case "OTPT":
                this.testName = "S.G.O.T. / S.G.P.T.";
                break;
            case "PTINR":
                this.testName = "PT/INR";
                break;
            case "SALKPHOSPHATASE":
                this.testName = "S. Alk. Phosphatase";
                break;
            case "SCHOLESTEROL":
                this.testName = "Serum Cholesterol";
                break;
            case "SIONIZED_CALCIUM":
                this.testName = "Serum Ionized Calcium";
                break;
            case "SPROTEINS":
                this.testName = "Serum Proteins";
                break;
            case "STOTAL_CALCIUM":
                this.testName = "Serum Total Calcium";
                break;
            case "SURICSPHOSPHORUS":
                this.testName = "S. Uric Acid & S. Phosphorus";
                break;
            case "URINESUGAR":
                this.testName = "Urine Sugar";
                break;
            case "BILIRUBIN":
                this.testName = "Bilirubin";
                break;
            case "EGFR":
                this.testName = "e-GFR";
                break;
            case "CULTUREABSTEST":
                this.testName = "Culture A.B.S.T.";
                break;
            case "ORALGLUCOSE" :
                this.testName = "Oral Glucose Test";
                break;
            case "GCT" :
                this.testName = "Glucose Challenge Test";
                break;
            case "RBS" :
                this.testName = "Random Blood Sugar";
                break;
            case "WBCDC" :
                this.testName = "WBC/DC";
                break;
        }
    }

    public String getTestName() { return testName; }
    public void setSelected() { isSelected.set(true); }
    public String getTest() { return test +"   Rs."+cost; }
    public String getTestCode() { return test; }
    public BooleanProperty isSelected() { return isSelected; }
    public boolean isSelectedBoolean() {return  isSelected.get(); }

    @Override
    public String toString() {
        return test +"              Rs. "+cost;
    }
}
