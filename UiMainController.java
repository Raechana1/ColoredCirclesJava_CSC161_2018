package main;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rahon
 */
public class UiMainController implements Initializable {

    @FXML
    private TextField modelNumtxt;

    @FXML
    private TextField productNametxt;

    @FXML
    private TextField brandtxt;

    @FXML
    private TextField prodTypetxt;

    @FXML
    private TextField quantitytxt;

    @FXML
    private TextField firstntxt;

    @FXML
    private TextField lastntxt;

    @FXML
    private TextField usernametxt;

    @FXML
    private Button Search;

    @FXML
    private Button Reset;

    @FXML
    private Button AddItem;

    @FXML
    private Button ChangeItem;

    @FXML
    private Button DeleteItem;

    @FXML
    private Button AddEmp;

    @FXML
    private Button ChangeEmp;

    @FXML
    private Button DeleteEmp;

    @FXML
    private Spinner<Integer> authlvl;

    @FXML
    private PasswordField psswdtxt;

    @FXML
    private TableView<Inventory> inventory;

    //final ObservableList<Inventory> invtdata = FXCollections.observaleArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //sets spinner to control authority level 0-2
        authlvl.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2, 0));

        //displays data on tableview
        showTableData();

        
        ////////////////Set Search Button////////////////
        Search.setOnAction((event) -> {
            String sql = "SELECT * FROM inventory WHERE Model Number = ?";
            try {
                Connection conn = DBUtil.getConnection2(DBType.SQLDB);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                if (!modelNumtxt.getText().isEmpty()) {
                    pstmt.setString(1, modelNumtxt.getText());
                }
            } catch (SQLException ex) {
                DBUtil.showErrorMessage(ex);
            }
        });

        
        ////////////////Reset data shown on table////////////////
        Reset.setOnAction((event) -> {
            showTableData();
        });

        
        ////////////////Button add to inventory////////////////
        AddItem.setOnAction((event) -> {
            Connection conn = null;
            PreparedStatement pstmt = null;
            if (modelNumtxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: no Model #");
                alert.setHeaderText("No Model Number to add");
                alert.setContentText("Please add Model Number to add item");
                alert.showAndWait();
            } else if (productNametxt.getText().isEmpty() || brandtxt.getText().isEmpty() || quantitytxt.getText().isEmpty() || prodTypetxt.getText().isEmpty()) {
                Alert warn = new Alert(Alert.AlertType.WARNING);
                warn.setTitle("Missing Fields");
                warn.setHeaderText("Information is not complete");
                warn.setContentText("Please check that all fields are filled out"
                        + "\n Would you still like to add to inventory?");
                ButtonType yes = new ButtonType("Yes");
                ButtonType no = new ButtonType("No");

                warn.getButtonTypes().setAll(yes, no);
                Optional<ButtonType> result = warn.showAndWait();
                if (result.get() == yes) {
                    try {
                        String query = "INSERT INTO inventory (Model_Number, Product_Name, Brand_Name, Product_Type, Quantity)"
                                + "VALUES(?,?,?,?,?);";
                        pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, modelNumtxt.getText());
                        pstmt.setString(2, productNametxt.getText());
                        pstmt.setString(3, brandtxt.getText());
                        pstmt.setString(4, prodTypetxt.getText());
                        pstmt.setString(5, quantitytxt.getText());
                    } catch (SQLException ex) {
                        DBUtil.showErrorMessage(ex);
                    }
                }
            } else {
                Alert information = new Alert(Alert.AlertType.INFORMATION);
                information.setTitle("Item added to inventory");
                information.setHeaderText("");
                information.setContentText("Item added");

                information.showAndWait();
            }

        });

        
        ////////////////Change inventory item////////////////
        ChangeItem.setOnAction((event) -> {
            Connection conn = null;
            PreparedStatement pstmt = null;
            if (!(modelNumtxt.getText().isEmpty() && productNametxt.getText().isEmpty() && brandNametxt.getText().isEmpty() && quantitytxt.getText().isEmpty())) {
                System.out.println("Change Item button pressed.");
                /*String query = "*/
                try {
                    String query = "UPDATE inventory (Model_Number, Product_Name, Brand_Name, Product_Type, Quantity) "
                            + "VALUES(?, ?, ?, ?, ?);";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, modelNumtxt.getText());
                    pstmt.setString(2, productNametxt.getText());
                    pstmt.setString(3, brandtxt.getText());
                    pstmt.setString(4, prodTypetxt.getText());
                    pstmt.setString(5, quantitytxt.getText());
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }

            }
        });

        
        ////////////////Delete Item from Inventory
        DeleteItem.setOnAction((event) -> {
            Connection conn = null;
            PreparedStatement pstmt = null;
            System.out.println("Delete Item button pressed.");
            Alert warn = new Alert(Alert.AlertType.WARNING);
            warn.setTitle("Warning");
            warn.setHeaderText("Delete Item");
            warn.setContentText("Are you sure you want to delete the item from inventory?");
            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No");

            warn.getButtonTypes().setAll(yes, no);
            Optional<ButtonType> result = warn.showAndWait();
            if (result.get() == yes) {
                try {
                    String query = "DELETE FROM inventory WHERE Model Number = ";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(0, modelNumtxt.getText());
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }
        });

    }

    
    ////////////////Function to display Table data on UI////////////////
    public void showTableData() {

        try {
            Connection conn = DBUtil.getConnection2(DBType.SQLDB);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inventory");
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

    }

}
