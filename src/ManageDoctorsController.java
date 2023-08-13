

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class ManageDoctorsController implements Initializable {
    @FXML private TextField txtSearch;
    @FXML private TextField txtFirst;
    @FXML private TextField txtSecond;
    @FXML private TableView<Doctor> table;
    @FXML private TableColumn<Doctor,String> colFirst;
    @FXML private TableColumn<Doctor,String> colSecond;

    private ObservableList<Doctor> doctorObservableList;
    private ObservableList<Doctor> filteredList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        doctorObservableList = Database.getDoctors();
        table.setItems(doctorObservableList);
        colFirst.setCellValueFactory(new PropertyValueFactory<>("FirstLine"));
        colSecond.setCellValueFactory(new PropertyValueFactory<>("SecondLine"));

        txtSearch.textProperty().addListener(observable -> searchDocters());
    }

    private void searchDocters() {
        if (!txtSearch.getText().isEmpty()) {
            filteredList = doctorObservableList.filtered(doctor -> doctor.getFirstLine().toLowerCase().contains(txtSearch.getText().toLowerCase()));
            table.setItems(filteredList);
        } else {
            table.setItems(doctorObservableList);
        }
    }

    @FXML
    void addDoctor() {
        if (!txtFirst.getText().isEmpty()) {
            Database.addDoctor(txtFirst.getText(),txtSecond.getText());
            doctorObservableList = Database.getDoctors();
            table.setItems(doctorObservableList);
        }
    }
    @FXML
    void deleteDoctor() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            if (!messageBox.confirmAsk("Are you sure?")) {
                return;
            }
            Database.deleteDoctor(table.getSelectionModel().getSelectedItem());
            doctorObservableList = Database.getDoctors();
            table.setItems(doctorObservableList);
        }
    }
}
