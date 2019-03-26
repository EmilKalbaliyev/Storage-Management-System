/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anbar.controller;

import anbar.dao.MainDao;
import anbar.model.Hesabat;
import anbar.model.Mehsul;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Mardan Safarov
 */
public class LogController implements Initializable {

    @FXML
    private TableView<Hesabat> productTable;
    @FXML
    private TableColumn<?, ?> colProductId;
    @FXML
    private TableColumn<?, ?> colProductName;
    @FXML
    private TableColumn<?, ?> colProductNum;
    @FXML
    private TableColumn<?, ?> action;
    @FXML
    private TableColumn<?, ?> reason;
    @FXML
    private Button btnCarHist;
    @FXML
    private Button btnDepotHist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                        for (int i = 0; i < productTable.getItems().size(); i++) {
            productTable.getItems().clear();
        }
        loadHesab("Yeni məhsul - mashin");
        loadHesab("Maşın");
        loadHesab("Satış-Maşın");
    }
    
    public void loadHesab(String hesReason) {
        

                MainDao dao = new MainDao();
        try {
            List<Hesabat> hesablar = dao.listHesab(hesReason);
            for (Hesabat hesab : hesablar) {
                productTable.getItems().add(hesab);
                
                colProductId.setCellValueFactory(new PropertyValueFactory<>("date"));
                colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
                colProductNum.setCellValueFactory(new PropertyValueFactory<>("number"));
                action.setCellValueFactory(new PropertyValueFactory<>("action"));
                reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
               // resetHesab();
              //  colProductId.setSortType(TableColumn.SortType.ASCENDING);

            } 
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void resetHesab() {
        colProductNum.setText("");
        action.setText("");
        reason.setText("");
        
     }

    @FXML
    private void loadCarHist(ActionEvent event) {
                        for (int i = 0; i < productTable.getItems().size(); i++) {
            productTable.getItems().clear();
        }
                        
        loadHesab("Yeni məhsul - mashin");
        loadHesab("Maşın");
        loadHesab("Satış-Maşın");
        

        
    }

    @FXML
    private void loadDepotHist(ActionEvent event) {
                        for (int i = 0; i < productTable.getItems().size(); i++) {
            productTable.getItems().clear();
        }
        loadHesab("Yeni məhsul - anbar");
        loadHesab("Anbar");
        loadHesab("Satış-Anbar");
        loadHesab("Maşın-Anbar");

    }

}
