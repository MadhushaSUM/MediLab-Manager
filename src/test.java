


import javafx.collections.ObservableList;

import java.time.LocalDate;

public class test {
    private int patientID;
    private patient Patient;
    private String name;
    private String test;
    private LocalDate date;
    private boolean moneyCollected;
    private int refNo;

    public test(int patientID, String name, LocalDate date, String test, patient Patient) {
        this.patientID = patientID;
        this.name = name;
        this.date = date;
        this.test = test;
        this.Patient = Patient;

    }

    public test(int patientID, String name, String test, LocalDate date, boolean moneyCollected,patient Patient,int refNo) {
        this.patientID = patientID;
        this.name = name;
        this.test = test;
        this.date = date;
        this.moneyCollected = moneyCollected;
        this.Patient = Patient;
        this.refNo = refNo;
    }
    public int getPatientID() { return patientID; }
    public patient getPatient() { return Patient; }
    public String getName() { return name; }
    public String getTest() { return test; }
    public LocalDate getDate() { return date; }
    public int getRefNo() { return refNo; }

    public String isMoneyCollected() {
        if (moneyCollected) {
            return "Yes";
        } else {
            return "No";
        }
    }

    @Override
    public String toString() {
        return "["+name+"] ["+date+"]\n["+test+"]";
    }
}
