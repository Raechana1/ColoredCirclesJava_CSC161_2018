/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 *
 * @author rahon
 */
public class uiController implements Initializable {

    @FXML
    private Button Staff;

    @FXML
    private TextField Username;

    @FXML
    private PasswordField Password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBConnect();
        createDatabase();
        createInventoryTable();
        createEmployeeTable();
    }

    
    
    
    ////////////////connect to database////////////////
    public static void DBConnect() {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection(DBType.SQLDB);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    

    ////////////////create database if it doesn't exist////////////////
    public void createDatabase() {
        Connection conn = null;
        Statement stmt = null;

        try {

            String sqlStmt = "CREATE DATABASE IF NOT EXISTS inventory_mgmt;";
            conn = DBUtil.getConnection(DBType.SQLDB);
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

    }

    ////////////////create table if it doesn't exist////////////////
    public void createInventoryTable() {
        Connection conn = null;
        Statement stmt = null;

        try {

            String sql = "CREATE TABLE IF NOT EXISTS inventory "
                    + "(Model_Number VARCHAR(20) not NULL, "
                    + "Product_Name VARCHAR(50), "
                    + "Brand_Name VARCHAR(50), "
                    + "Product_Type VARCHAR(30), "
                    + "Quantity INTEGER, "
                    + "PRIMARY KEY(Model_Number))";

            conn = DBUtil.getConnection2(DBType.SQLDB);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

    }

    
    ////////////////Creates a table to hold employee data////////////////
    public void createEmployeeTable() {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;

        try {

            String sql = "CREATE TABLE IF NOT EXISTS employees "
                    + "(FIRST_NAME VARCHAR(50), "
                    + "LAST_NAME VARCHAR(50), "
                    + "USERNAME VARCHAR(50) NOT NULL, "
                    + "AUTH_LVL INTEGER NOT NULL, "
                    + "PASSWORD VARCHAR(50) NOT NULL,"
                    + "PRIMARY KEY(USERNAME))";

            conn = DBUtil.getConnection2(DBType.SQLDB);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            
            String addAdmin = "INSERT INTO employees(USERNAME, PASSWORD, AUTH_LVL)"
                  + "VALUES('admin','admin',0);";;
            stmt.executeUpdate(addAdmin);
            System.out.println("Employee table created and guest added");
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

    }

    ////////////////show main screen from guest login////////////////
    /*public void guestLogin(ActionEvent event) throws Exception {
        System.out.println("Guest Button pressed");
        Stage mainStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("uiMain.fxml"));

        TabPane tabp = loader.load();

        Scene mainScene = new Scene(tabp, 1000, 700);

        mainStage.setScene(mainScene);
        mainStage.setTitle("CCBookstore Inventory");
        mainStage.setAlwaysOnTop(false);
        mainStage.setResizable(false);

        mainStage.show();
    }*/
    
    ////////////////show main screen from employee login////////////////
    public void EmpLogin(ActionEvent event) throws Exception {
        System.out.println("Employee Login Button pressed");
        Stage mainStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("uiMain.fxml"));

        TabPane tabp = loader.load();

        Scene mainScene = new Scene(tabp, 1000, 700);

        mainStage.setScene(mainScene);
        mainStage.setTitle("CCBookstore Inventory");
        mainStage.setAlwaysOnTop(false);
        mainStage.setResizable(false);

        mainStage.show();
    }
}
