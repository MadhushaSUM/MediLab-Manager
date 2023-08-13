

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class testSpecimenController implements Initializable {

    @FXML private TableView<tableTests> table;
    @FXML private TableColumn<tableTests, String> colTest = new TableColumn<>();
    @FXML private TableColumn<tableTests, String> colSpecimen = new TableColumn<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setItems(Database.getTestSpecimenTableList());

        colTest.setCellValueFactory(new PropertyValueFactory<>("TestName"));
        colSpecimen.setCellValueFactory(new PropertyValueFactory<>("Specimen"));
        colSpecimen.setCellFactory(TextFieldTableCell.forTableColumn());
        colSpecimen.setOnEditCommit(Database::setTestSpecimen);
    }
}
