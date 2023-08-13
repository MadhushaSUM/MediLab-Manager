

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class testPriceController implements Initializable {

    @FXML private TableView<tableTests> table;
    @FXML private TableColumn<tableTests, String> colTest = new TableColumn<>();
    @FXML private TableColumn<tableTests, String> colPrice = new TableColumn<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setItems(Database.getTableTestsList());

        colTest.setCellValueFactory(new PropertyValueFactory<>("TestName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colPrice.setCellFactory(TextFieldTableCell.forTableColumn());
        colPrice.setOnEditCommit(Database::setTestPrice);
    }
}
