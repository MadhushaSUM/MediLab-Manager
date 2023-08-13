import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Database {
    private static final String URL = "jdbc:derby:Database";
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;

    private static String SQL;

    /*
    you must add all the tests available into String array below. And each test must have Table in Database of their own name
     */

    private static String tests[] = {"FBS", "PPBSALL", "PPBS", "FBC", "LIPID_PROFILE", "GLYCO_HEMO", "UFR", "SFR", "RHE_FACTORS", "LFT", "SCREATININE", "SELECTROLYTES", "URINEHCG", "ESR", "CRP",
            "BLOOD_UREA", "DENGUETEST", "FILARIALANTIBODYTEST", "GLUCOSETOLERANCE", "HB", "HIV", "MICROALBUMIN", "OTPT", "PTINR", "SALKPHOSPHATASE", "SCHOLESTEROL", "SIONIZED_CALCIUM",
            "SPROTEINS", "STOTAL_CALCIUM", "SURICSPHOSPHORUS", "URINESUGAR","BILIRUBIN","EGFR","CULTUREABSTEST", "VDRL","ORALGLUCOSE","GCT","RBS","WBCDC","GAMMAGT","BSP","BG"};

    public static void connect() {
        try {
            try {
                Class.forName(DRIVER).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public static void disconnect() {
        try {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ObservableList<patient> getPatientList() {
        ObservableList<patient> patientList = FXCollections.observableArrayList();
        try {
            connect();
            SQL = "select * from PATIENTS";
            result = statement.executeQuery(SQL);
            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                LocalDate DOB = result.getDate(4).toLocalDate();
                String gender = result.getString(3);
                int contactNumber = result.getInt(5);
                patientList.add(new patient(id, name, DOB, gender, contactNumber));
            }
            disconnect();
         } catch (SQLException e) {
            e.printStackTrace();
        }
        return patientList;
    }
    public static int addPatient(String name, LocalDate DOB, String gender, int contactNumber, boolean isPreciseAge) {
        ObservableList<patient> patients = getPatientList();
        int lastID = 0;
        if (!patients.isEmpty()) {
            lastID = patients.stream().map(patient::getID).max(Integer::compare).get();
        }
        lastID++;
        try {
            connect();
            SQL = "insert into PATIENTS (PATIENTID,NAME,GENDER,DATE_OF_BIRTH,CONTACT_NUMBER,PRECISE_AGE) values ("+lastID+",'"+name+"','"+gender+"','"+DOB+"',"+contactNumber+",'"+isPreciseAge+"')";
            statement.executeUpdate(SQL);
            disconnect();
            messageBox.showMessage("New Patient added Successfully","Database");
        } catch (SQLException e) {
            messageBox.showMessage("Patient duplication avoided","Database");
        }
        return lastID;
    }
    public static void changePatientName(TableColumn.CellEditEvent<patient, String> event) {
        try {
            connect();
            SQL = "update PATIENTS set NAME ='"+event.getNewValue()+"' where PATIENTID ="+event.getRowValue().getID();
            statement.execute(SQL);
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage("Changing name process failed","Database");
        }
    }
    public static void changePatientDOB(TableColumn.CellEditEvent<patient, String> event) {
        try {
            connect();
            SQL = "update PATIENTS set DATE_OF_BIRTH ='"+event.getNewValue()+"' where PATIENTID ="+event.getRowValue().getID();
            statement.execute(SQL);
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage("Changing DOB process failed","Database");
        }
    }
    public static void deletePatient(patient deletePatient) {
        try {
            connect();
            SQL = "delete from PATIENTS where PATIENTID = "+deletePatient.getID();
            statement.executeUpdate(SQL);
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage("Unable to delete Patient","Database");        }
    }

    public static int getLastReference() {
        int lastRef = 0;
        try {
            connect();
            SQL = "select * from TESTS";
            result = statement.executeQuery(SQL);
            while (result.next()) {
                if (result.getInt("REFERENCE") > lastRef) {
                    lastRef = result.getInt("REFERENCE");
                }
            }
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage("Error getting Last Ref","Error");
            e.printStackTrace();
        }
        return lastRef;
    }

    public static void AddRecord(int patientID, String test, boolean money, String Req_Doc, String ReqDocSecondLine, int advance,int refNo,LocalDate date){
        String testUpperCase = test.toUpperCase();
        System.out.println(date);
        try {
            connect();
            SQL = "SELECT * FROM TESTS WHERE PATIENTID = " + patientID + " AND DATE = '" + date + "'";
            result = statement.executeQuery(SQL);

            if (result.next()) {
                SQL = "UPDATE TESTS SET " + testUpperCase + " = 'true' WHERE PATIENTID = " + patientID + " AND DATE = '" + date + "'";

            } else {
                SQL = "INSERT INTO TESTS(REFERENCE, PATIENTID, DATE, " + testUpperCase + ") VALUES (" + refNo + "," + patientID + ",'" + date + "','true')";
            }
            statement.executeUpdate(SQL);

            //Adding Advance payment if it is not 0, otherwise field will be NULL
            if (advance != 0) {
                SQL = "UPDATE TESTS SET ADVANCE = " + advance + " WHERE PATIENTID = " + patientID + " AND DATE = '" + date + "'";
                statement.executeUpdate(SQL);
            }
            //Checking if there is a record on that table on same name and same date
            SQL = "SELECT * FROM " + testUpperCase + " WHERE PATIENTID = " + patientID + " AND DATE = '" + date + "'";
            result = statement.executeQuery(SQL);
            if (result.next()) {
                messageBox.showMessage("Record of this test for this Patient \n for today Already Exists", "Rejected");
            } else {
                //Adding new row to relevant test Table
                if (money) {
                    SQL = "INSERT INTO " + testUpperCase + "(PATIENTID, DATE, MONEY_COLLECTED) VALUES (" + patientID + ",'" + date + "','true')";
                } else {
                    SQL = "INSERT INTO " + testUpperCase + "(PATIENTID, DATE) VALUES (" + patientID + ",'" + date + "')";
                }
                statement.executeUpdate(SQL);
                messageBox.showMessage(testUpperCase + " Registered", "Successful");
            }
            if (!Req_Doc.isEmpty()) {
                SQL = "UPDATE " + testUpperCase + " SET REQ_DOC = '" + Req_Doc + "' WHERE PATIENTID = " + patientID + " AND DATE = '" + date + "'";
                statement.executeUpdate(SQL);
                if (!ReqDocSecondLine.isEmpty()) {
                    SQL = "UPDATE " + testUpperCase + " SET REQ_DOC2 = '" + ReqDocSecondLine + "' WHERE PATIENTID = " + patientID + " AND DATE = '" + date + "'";
                    statement.executeUpdate(SQL);
                }
            }
            disconnect();
        } catch (DerbySQLIntegrityConstraintViolationException e) {
            messageBox.showMessage("This ref No. already exists on DB\nReference must be unique","Error");
        } catch (SQLException e) {
            messageBox.showMessage("Error adding Test","Error");
            e.printStackTrace();
        }
    }
    public static int getAdvancePayment(int patientID, LocalDate date) {
        int advance = -1;
        try {
            connect();
            SQL = "select * from TESTS where PATIENTID = "+patientID+" and DATE = '"+date+"'";
            result = statement.executeQuery(SQL);
            while (result.next()) {
                advance = 0;
                advance = result.getInt("ADVANCE");
            }
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage("Error Retrieving Advance","Error");
            e.printStackTrace();
        }
        return advance;
    }
    public static ObservableList<test> getTestList() {
        ObservableList<test> testList = FXCollections.observableArrayList();
        ObservableList<patient> patients = getPatientList();
        try {
            connect();
            for (String test : tests) {
                SQL = "select * from "+ test + " where DONE_EDITING = 'false'";
                result = statement.executeQuery(SQL);
                String name;
                LocalDate date;
                while (result.next()) {
                    int patientID = result.getInt("PATIENTID");
                    date = result.getDate("DATE").toLocalDate();
                    patient Patient = patients.filtered(patient -> patient.getID() == patientID).get(0);
                    name = Patient.getName();
                    testList.add(new test(patientID,name,date,test,Patient));
                }
            }
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
            e.printStackTrace();
        }
        return testList;
    }
    public static void saveReport(TextField[] txtArray, test selectedTest) {
        try {
            connect();
            Statement statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            SQL = "select * from " + selectedTest.getTest() + " where PATIENTID = "+selectedTest.getPatientID()+" and DATE ='"+selectedTest.getDate()+"'";
            result = statement.executeQuery(SQL);
            ResultSetMetaData rsmd = result.getMetaData();
            int arrayLength = txtArray.length;
            while (result.next()) {
                String field;
                for (int i = 0; i < arrayLength; i++) {
                    field = rsmd.getColumnLabel(i + 3);
                    String value = txtArray[i].getText();
                    if (txtArray[i].getText().isEmpty()) continue;
                    SQL = "update " + selectedTest.getTest() + " set " + field + " = '" + value + "' where PATIENTID = "+selectedTest.getPatientID()+" and DATE ='" + selectedTest.getDate() + "'";
                    statement2.executeUpdate(SQL);
                }
                SQL = "update "+ selectedTest.getTest() +" set DONE_EDITING = 'true' where PATIENTID = "+selectedTest.getPatientID()+" and DATE ='"+selectedTest.getDate()+"'";
                statement2.executeUpdate(SQL);
            }
            statement2.close();
            disconnect();
            messageBox.showMessage("Report Saved Successfully","Successful");
        } catch (SQLException e) {
            messageBox.showMessage("Error saving Report","Error");
            e.printStackTrace();
        }
    }
    public static void saveReportNew(test selectedTest, ArrayList<String> dataToSave) {
        try {
            connect();
            Statement statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            SQL = "select * from " + selectedTest.getTest() + " where PATIENTID = "+selectedTest.getPatientID()+" and DATE ='"+selectedTest.getDate()+"'";
            result = statement.executeQuery(SQL);
            ResultSetMetaData rsmd = result.getMetaData();
            int arrayLength = dataToSave.size();
            while (result.next()) {
                String field;
                for (int i = 0; i < arrayLength; i++) {
                    field = rsmd.getColumnLabel(i + 3);
                    SQL = "update " + selectedTest.getTest() + " set " + field + " = '"+dataToSave.get(i)+"' where PATIENTID = "+selectedTest.getPatientID()+" and DATE ='" + selectedTest.getDate() + "'";
                    statement2.executeUpdate(SQL);
                }
                SQL = "update "+ selectedTest.getTest() +" set DONE_EDITING = 'true' where PATIENTID = "+selectedTest.getPatientID()+" and DATE ='"+selectedTest.getDate()+"'";
                statement2.executeUpdate(SQL);
            }
            statement2.close();
            disconnect();
            messageBox.showMessage("Report Saved Successfully","Successful");
        } catch (SQLException e) {
            messageBox.showMessage("Error saving Report","Error");
            e.printStackTrace();
        }
    }
    public static void saveCulture(TextField[] txtArray, TextArea[] textArea, test selectedTest) {
        try {
            connect();
            Statement statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            SQL = "select * from "+ selectedTest.getTest() +" where PATIENTID = "+selectedTest.getPatientID()+" and DATE ='" + selectedTest.getDate() + "'";
            result = statement.executeQuery(SQL);
            ResultSetMetaData rsmd = result.getMetaData();
            int arrayLength = txtArray.length;
            while (result.next()) {
                String field;
                for (int i = 0; i < arrayLength; i++) {
                    field = rsmd.getColumnLabel(i + 3);
                    String value = txtArray[i].getText();
                    //if (value.isEmpty()) continue;
                    if (rsmd.getColumnType(i+3) == 12) {
                        SQL = "update "+selectedTest.getTest()+" set " + field + " = '" + value + "' where PATIENTID = "+selectedTest.getPatientID()+" and DATE ='"+selectedTest.getDate()+"'";
                    }else {
                        SQL = "update "+selectedTest.getTest()+" set " + field + " = " + value + " where PATIENTID = "+selectedTest.getPatientID()+" and DATE ='"+selectedTest.getDate()+"'";
                    }
                    statement2.executeUpdate(SQL);
                }
                arrayLength = textArea.length;
                for (int i = 0; i < arrayLength; i++) {
                    //if (textArea[i].getText().isEmpty()) continue;
                    SQL = "update "+selectedTest.getTest()+" set VALA"+i+" = '"+ textArea[i].getText() +"' where PATIENTID = "+selectedTest.getPatientID()+" and DATE ='" + selectedTest.getDate() + "'";
                    statement2.executeUpdate(SQL);
                }

                SQL = "update "+selectedTest.getTest()+" set DONE_EDITING = 'true' where PATIENTID = "+selectedTest.getPatientID()+" and DATE ='"+selectedTest.getDate()+"'";
                statement2.executeUpdate(SQL);
            }
            statement2.close();
            disconnect();
            messageBox.showMessage("Report Saved Successfully","Successful");
        } catch (SQLException e) {
            messageBox.showMessage("Error saving Report\nDon't use   '   symbol","Error");
            e.printStackTrace();
        }
    }
    public static ArrayList<Object> retrieveSavedReportValues(test selectedTest, TextField[] txtArray, TextArea[] textAreaArray) {
        ArrayList<Object> retrievedData = new ArrayList<>();
        try {
            connect();
            SQL = "select * from "+ selectedTest.getTest() +" where PATIENTID = "+selectedTest.getPatientID()+" and DATE ='"+selectedTest.getDate()+"'";
            result = statement.executeQuery(SQL);
            int arrayLength = txtArray.length;
            while (result.next()) {
                String string;
                for (int i = 0; i < arrayLength; i++) {
                    string = result.getString(i+3);
                    if (string != null) {
                        txtArray[i].setText(result.getString(i + 3));
                    }
                }
                arrayLength = textAreaArray.length;
                for (int i = 0; i < arrayLength; i++) {
                    string = result.getString("VALA"+i);
                    if (string != null) {
                        textAreaArray[i].setText(result.getString("VALA" + i));
                    }
                }
            }
            disconnect();
            retrievedData.add(txtArray);
            retrievedData.add(textAreaArray);
        } catch (SQLException e) {
            messageBox.showMessage("Error retrieving Saved report","Error");
            e.printStackTrace();
        }
        return retrievedData;
    }
    public static void removeTest(test removedTest) {
        try {
            connect();
            SQL = "DELETE FROM "+ removedTest.getTest() +" WHERE PATIENTID = "+removedTest.getPatientID()+"  AND Date = '"+ removedTest.getDate() +"'";
            statement.executeUpdate(SQL);
            SQL = "UPDATE TESTS SET "+ removedTest.getTest() +" ='false' WHERE PATIENTID = "+removedTest.getPatientID()+" AND Date = '"+ removedTest.getDate() +"'";
            statement.executeUpdate(SQL);
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage("Error removing Report", "Error");
            e.printStackTrace();
        }
    }
    public static ObservableList<test> loadReportTable(boolean all){
        ObservableList<test> reportList = FXCollections.observableArrayList();
        ObservableList<patient> patients = getPatientList();

        try {
            connect();
            Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet result1;
            for (String test : tests) {
                if (all) {
                    SQL = "SELECT * FROM " + test + " WHERE Done_Editing ='true'";
                } else {
                    SQL = "SELECT * FROM " + test + " WHERE Done_Editing ='true' AND Printed_Before = 'false'";
                }
                result = statement.executeQuery(SQL);
                LocalDate date;
                String name;
                boolean isMoneyCollected;
                int refNo;
                while (result.next()) {
                    int patientID = result.getInt("PATIENTID");
                    date = result.getDate("DATE").toLocalDate();
                    isMoneyCollected = result.getBoolean("Money_Collected");
                    patient Patient = patients.filtered(patient -> patient.getID() == patientID).get(0);
                    name = Patient.getName();
                    result1 = statement1.executeQuery("select * from TESTS where PATIENTID ="+patientID+" and DATE ='"+date+"'");
                    result1.next();
                    refNo = result1.getInt("REFERENCE");
                    reportList.add(new test(patientID,name,test,date,isMoneyCollected,Patient,refNo));
                }
            }
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
            e.printStackTrace();
        }
        return reportList;
    }
    public static void updateMoneyStatus(ObservableList<test> paidTestList) {
        try {
            connect();
            for (test paidTest : paidTestList) {
                LocalDate date = paidTest.getDate();
                String name = paidTest.getName();
                String test = paidTest.getTest();
                SQL = "UPDATE " + test + " SET MONEY_COLLECTED = 'true' WHERE NAME = '" + name + "' AND DATE = '" + date + "'";
                statement.executeUpdate(SQL);
                System.out.println("Money status updated");
            }
            disconnect();
        }catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
        }
    }
    public static ResultSet getPatientDetailsToPrint(test report) {
        try {
            connect();
            SQL = "SELECT * FROM PATIENTS WHERE PATIENTID = "+report.getPatientID()+"";
            result = statement.executeQuery(SQL);
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
        }
        return result;
    }
    public static int getReferenceToPrint(test report) {
        int reference = 0;
        try {
            connect();
            SQL = "SELECT * FROM TESTS WHERE PATIENTID ="+report.getPatientID()+" AND DATE ='"+ report.getDate() +"'";
            result = statement.executeQuery(SQL);
            while (result.next()) {
                reference = result.getInt("REFERENCE");
            }
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage("Error retrieving Reference","Error");
            e.printStackTrace();
            disconnect();
        }
        return reference;
    }
    public static String getSpecimenToPrint(String test) {
        String specimen = null  ;
        try {
            connect();
            SQL = "SELECT * FROM SPECIMEN WHERE TESTCODE ='"+test+"'";
            System.out.println(test);
            result = statement.executeQuery(SQL);
            while (result.next()) {
                specimen = result.getString("SPECIMEN");
            }
            if (specimen == null) specimen = "";
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage("Error retrieving Specimen","Error");
            e.printStackTrace();
            disconnect();
        }
        return specimen;
    }
    public static ResultSet getReportDetailsToPrint(test report) {
        try {
            connect();
            SQL = "SELECT * FROM " + report.getTest() + " WHERE PATIENTID = " + report.getPatientID() + " AND DATE ='" + report.getDate() + "'";
            result = statement.executeQuery(SQL);
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
        }
        return result;
    }
    public static void MarkReportAsPrinted(test report) {
        connect();
        SQL = "UPDATE "+ report.getTest() +" SET PRINTED_BEFORE = 'true' WHERE PATIENTID = "+ report.getPatientID() +" AND DATE ='"+ report.getDate() +"'";
        try {
            statement.executeUpdate(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
            messageBox.showMessage("Error Updating Printed Before","Error");
        }
        disconnect();
    }
    public static List<String> getAuthentication(boolean isAdmin) {
        List<String> list = FXCollections.observableArrayList();
        try {
            connect();
            int userID;
            if (isAdmin) {
                userID = 1;
            } else {
                userID = 2;
            }
            SQL = "select * from AUTHENTICATION where USERID ="+userID;
            result = statement.executeQuery(SQL);
            while (result.next()) {
                list.add(result.getString("USERNAME"));
                list.add(result.getString("PASSWORD"));
            }
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static int getAuthentication(String username, String password) {
        try {
            connect();
            SQL = "select * from AUTHENTICATION";
            result = statement.executeQuery(SQL);
            while (result.next()) {
                if (result.getString("USERNAME").equals(username)) {
                    if (result.getString("PASSWORD").equals(password)) {
                        if (result.getInt("USERID") == 1) {
                            return 1;
                        } else {
                            return 0;
                        }
                    } else {
                        return -1;
                    }
                }
            }
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static void setAuthentication(String user, String pass, boolean isAdmin) {
        try {
            int userID;
            if (isAdmin) {
                userID = 1;
            } else {
                userID = 2;
            }
            connect();
            SQL = "update AUTHENTICATION set USERNAME = '"+ user +"' , PASSWORD ='"+ pass +"' where USERID = "+userID;
            statement.executeUpdate(SQL);
            disconnect();
            messageBox.showMessage("Username and Password are Updated", "Alert");
        } catch (SQLException e) {
            messageBox.showMessage("Error updating Username & Password", "Error");
        }
    }
    public static void resetAdmin() {
        try {
            connect();
            SQL = "update AUTHENTICATION set USERNAME = 'aaa' , PASSWORD ='111' where USERID = 1";
            statement.executeUpdate(SQL);
            SQL = "update AUTHENTICATION set USERNAME = 'bbb' , PASSWORD ='222' where USERID = 2";
            statement.executeUpdate(SQL);
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage("Error resetting...", "Error");
        }
    }
    public static ObservableList<tableTests> getTableTestsList() {
        ObservableList<tableTests> testsList = FXCollections.observableArrayList();
        try {
            connect();
            SQL = "select * from TESTPRICE";
            result = statement.executeQuery(SQL);
            while (result.next()) {
                testsList.add(new tableTests(result.getString("TEST"),result.getInt("PRICE")));
            }
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
        }
        return testsList;
    }
    public static void setTestPrice(TableColumn.CellEditEvent<tableTests, String> event) {
        try {
            int price;
            price = Integer.parseInt(event.getNewValue());
            connect();
            SQL = "update TESTPRICE set PRICE ="+ price +" where TEST ='"+ event.getRowValue().getTestCode() +"'";
            statement.executeUpdate(SQL);
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
        } catch (java.lang.NumberFormatException e) {
            messageBox.showMessage("Enter valid Price","Error");
        }
    }
    public static ObservableList<tableTests> getTestSpecimenTableList() {
        ObservableList<tableTests> testsList = FXCollections.observableArrayList();
        try {
            connect();
            SQL = "select * from SPECIMEN";
            result = statement.executeQuery(SQL);
            while (result.next()) {
                testsList.add(new tableTests(result.getString("TESTCODE"),result.getString("SPECIMEN")));
            }
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
        }
        return testsList;
    }
    public static void setTestSpecimen(TableColumn.CellEditEvent<tableTests, String> event) {
        try {
            String specimen;
            specimen = String.valueOf(event.getNewValue());
            connect();
            SQL = "update SPECIMEN set SPECIMEN ='"+ specimen +"' where TESTCODE ='"+ event.getRowValue().getTestCode() +"'";
            statement.executeUpdate(SQL);
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
        } catch (java.lang.NumberFormatException e) {
            messageBox.showMessage("Could not set Specimen","Error");
        }
    }
    public static ResultSet getTestData(String test, int patientID) {
        connect();
        SQL = "SELECT * FROM "+test+" WHERE PATIENTID = "+ patientID +"" ;
        try {
            result = statement.executeQuery(SQL);
        } catch (SQLException exception) {
            System.err.println(exception);
            messageBox.showMessage("Error occurred retrieving Data of particular patient","Error");
        }
        return result;
    }
    public static int getTestNumberDone(patient name) {
        int number = 0;
        try {
            connect();
            for (String test : tests) {
                SQL = "select * from "+ test + " where PATIENTID = "+ name.getID() +"";
                result = statement.executeQuery(SQL);
                result.last();
                number += result.getRow();
            }
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
        }
        return number;
    }
    public static ObservableList<String> getTestsDone(patient name) {
        ObservableList<String> testList = FXCollections.observableArrayList();
        try {
            connect();
            for (String test : tests) {
                SQL = "select * from "+ test + " where PATIENTID = "+ name.getID() +"";
                result = statement.executeQuery(SQL);
                result.last();
                if (result.getRow() != 0) {
                    testList.add(test);
                }
            }
            disconnect();
        } catch (SQLException e) {
            //messageBox.showMessage(e.getMessage(),"Error");
            e.printStackTrace();
        }
        return testList;
    }
    public static HashMap<String,Integer> getTestAnalysisNumbers(String date) {
        HashMap<String, Integer> testHash = new HashMap<>();
        try {
            connect();
            SQL = "select * from TESTS where DATE ='"+ date +"'";
            result = statement.executeQuery(SQL);
            ResultSetMetaData rsmd = result.getMetaData();
            while (result.next()) {
                for (int i = 5; i < 42; i++) {
                    if (result.getBoolean(i)) {
                        String key = rsmd.getColumnLabel(i);
                        if (testHash.containsKey(key)) {
                            testHash.compute(rsmd.getColumnLabel(i),(k,v) -> v = v + 1);
                        } else {
                            testHash.put(key,1);
                        }
                    }
                }
            }
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
        }
        return testHash;
    }
    public static XYChart.Series<String, Integer> setDailyLineChart(LocalDate dateGiven) {
        XYChart.Series<String, Integer> jobSeries = new XYChart.Series<>();
        jobSeries.setName("Total Test Count");
        try {
            connect();
            LocalDate date = dateGiven.minusDays(7);
            for (int i = 0; i < 7; i++) {
                date = date.plusDays(1);
                SQL = "select * from TESTS where DATE ='" + date + "'";
                result = statement.executeQuery(SQL);
                ResultSetMetaData rsmd = result.getMetaData();
                int j = 0;
                while (result.next()) {
                    for (int k = 5; k < 42; k++) {
                        if (result.getBoolean(k)) {
                            j++;
                        }
                    }
                }
                jobSeries.getData().add(new XYChart.Data<String, Integer>(date.toString(),j));
            }
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobSeries;
    }
    public static ArrayList<finAnalysistest> getFinAnalysisTestList() {
        ArrayList<finAnalysistest> testList = new ArrayList<>();
        try {
            connect();
            SQL = "select * from TESTPRICE";
            result = statement.executeQuery(SQL);
            while (result.next()) {
                testList.add(new finAnalysistest(result.getString("TEST"),result.getInt("PRICE")));
            }
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage(e.getMessage(),"Error");
        }
        return testList;
    }

    public static ArrayList<finAnalysistest> loadFinancialAnalysisTable(String date, ArrayList<finAnalysistest> testList) {
        HashMap<String, Integer> testNumbers = getTestAnalysisNumbers(date);
        for (Map.Entry<String, Integer> entry : testNumbers.entrySet()) {
            for (finAnalysistest finAnalysistest : testList) {
                if (finAnalysistest.getName().equals(entry.getKey())) {
                    finAnalysistest.setNumber(entry.getValue());
                }
            }
        }
        testList.removeIf(fin -> fin.getNumber() == 0);
        int totNumber = testList.stream().map(finAnalysistest::getNumber).reduce(0, Integer::sum);
        int totPrice = testList.stream().map(finAnalysistest::getTotal).reduce(0, Integer::sum);
        testList.add(new finAnalysistest("TOTAL",totNumber,totPrice));
        return testList;
    }
    public static XYChart.Series<String, Integer> loadFinancialLineChart(LocalDate date) {
        ArrayList<finAnalysistest> testList = getFinAnalysisTestList();
        date = date.minusDays(7);
        XYChart.Series<String,Integer> finSeries = new XYChart.Series<>();
        for (int i = 0; i < 7; i++) {
            date = date.plusDays(1);
            HashMap<String, Integer> testNumbers = getTestAnalysisNumbers(date.toString());
            for (Map.Entry<String, Integer> entry : testNumbers.entrySet()) {
                for (finAnalysistest fin : testList) {
                    if (fin.getName().equals(entry.getKey())) {
                        fin.setNumber(entry.getValue());
                    }
                }
            }
            int totPrice = testList.stream().map(finAnalysistest::getTotal).reduce(0, Integer::sum);
            finSeries.getData().add(new XYChart.Data<>(date.toString(),totPrice));
        }
        finSeries.setName("Daily Income");
        return finSeries;
    }
    public static void reEdit(ObservableList<test> reportList) {
        try {
            connect();
            for (test report : reportList) {
                SQL = "update " + report.getTest() + " set DONE_EDITING = 'false' where PATIENTID = " + report.getPatientID() + " and DATE = '" + report.getDate() + "'";
                statement.executeUpdate(SQL);
            }
            disconnect();
        } catch (SQLException e) {
            messageBox.showMessage("Failed to Update Test", "Error");
        }
    }
    public static ObservableList<Doctor> getDoctors() {
        ObservableList<Doctor> doctorList = FXCollections.observableArrayList();
        try {
            connect();
            SQL = "select * from DOCTORS";
            result = statement.executeQuery(SQL);
            while (result.next()) {
                doctorList.add(new Doctor(result.getString(1),result.getString(2)));
            }
            disconnect();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return doctorList;
    }
    public static void addDoctor(String firstLine, String secondLine) {
        try {
            connect();
            SQL = "insert into DOCTORS (LINEONE,LINETWO) values ('"+firstLine+"','"+secondLine+"')";
            statement.executeUpdate(SQL);
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteDoctor(Doctor doctor) {
        try {
            connect();
            SQL = "delete from DOCTORS where LINEONE ='"+ doctor.getFirstLine() +"' and LINETWO ='"+ doctor.getSecondLine() +"'";
            statement.executeUpdate(SQL);
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static HashSet<String> checkPreviousTestForReceipt(int patientID, LocalDate date) {
        HashSet<String> previousTests = new HashSet<>();
        try {
            connect();
            SQL = "SELECT * FROM TESTS WHERE PATIENTID = "+patientID+" AND DATE = '"+date+"'";
            result = statement.executeQuery(SQL);
            ResultSetMetaData rsmd = result.getMetaData();
            while (result.next()) {
                for (int i = 5; i <= 42; i++) {
                    if (result.getBoolean(i)) {
                        previousTests.add(rsmd.getColumnName(i));
                    }
                }
            }
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return previousTests;
    }
    public static ObservableList<TestForRecipt> getRegisteredTestsForReceipt(int patientID, LocalDate date) {
        ObservableList<TestForRecipt> registeredTestsList = FXCollections.observableArrayList();
        ObservableList<tableTests> testPrices = getTableTestsList();
        try {
            connect();
            SQL = "select * from TESTS where PATIENTID = "+patientID+" and DATE = '"+date+"'";
            result = statement.executeQuery(SQL);
            while (result.next()) {
                for (String test : tests) {
                    if (result.getBoolean(test)) {
                        registeredTestsList.add(new TestForRecipt(test,testPrices.filtered(tableTests -> tableTests.getTestCode().equalsIgnoreCase(test)).get(0).getPriceInt()));
                    }
                }
            }
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registeredTestsList;
    }
    public static String getLicensedKeyword() {
        String keyword = null;
        try {
            connect();
            SQL = "select * from LICENSE where USER_ID = 1";
            result = statement.executeQuery(SQL);
            while (result.next()) {
                keyword = result.getString("KEYWORD");
            }
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
            messageBox.showMessage("License Data Retieval FAILED!","ERROR");
        }
        return keyword;
    }
}
