/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anbar.controller;

import anbar.dao.MainDao;
import anbar.model.Hesabat;
import anbar.model.Mehsul;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author marda
 */
public class MainController implements Initializable {

    private Label label;
    @FXML
    private Button btnDepot;
    @FXML
    private Button btnCar;
    @FXML
    private TableView<Mehsul> productTable;
    @FXML
    private TableColumn<?, ?> colProductId;
    @FXML
    private TableColumn<?, ?> colProductName;
    @FXML
    private TableColumn<?, ?> colProductNum;
    @FXML
    private RadioButton depotOption;
    @FXML
    private ToggleGroup option;
    @FXML
    private RadioButton carOption;
    @FXML
    private TextField productName;
    @FXML
    private TextField productNum;
    @FXML
    private Button save;
    @FXML
    private Button delete;
    @FXML
    private Button reset;
    @FXML
    private Button plus;
    @FXML
    private Button minus;

    int id = 0;
    @FXML
    private Button log;

    Hesabat esasHesabat = new Hesabat();

    @FXML
    private Label errorMessage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        save.setDisable(true);
        plus.setDisable(true);
        minus.setDisable(true);

        productName.textProperty().addListener((observable, oldValue, newValue) -> {
            save.setDisable(false);
        });

        load("anbar");
        btnDepot.setStyle(" -fx-background-color: #00007f");
        depotOption.setSelected(true);
        productNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    productNum.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }

        });

    }

    @FXML
    private void loadDepot(ActionEvent event) {

        load("anbar");
        btnDepot.setStyle(" -fx-background-color: #00007f");
        btnCar.setStyle(" -fx-background-color: DODGERBLUE");
        depotOption.setSelected(true);

    }

    @FXML
    private void loadCar(ActionEvent event) {
        load("mashin");
        btnCar.setStyle(" -fx-background-color: #00007f");
        btnDepot.setStyle(" -fx-background-color: DODGERBLUE");
        carOption.setSelected(true);

    }

    @FXML
    private void save(ActionEvent event) throws SQLException {

        DeleteController dc = new DeleteController();
        String tableName = "";
        if (carOption.isSelected()) {
            tableName = "mashin";
        } else {
            tableName = "anbar";
        }

        MainDao dao = new MainDao();
//System.out.println("PRINTEDDD1!!");
        //if (esasHesabat.getAction() != null) {
//System.out.println("PRINTEDDD2!!");
            if (id == 0) {

                dao.save(productName.getText(), Integer.parseInt(productNum.getText()), tableName);
               // System.out.println("PRINTEDDD!!");
            } else {

                dao.update(id, productName.getText(), Integer.parseInt(productNum.getText()), tableName);

                if (esasHesabat.getReason() == "Maşın") {

                    if (dao.checkDb(productName.getText()) != 0) {

                        //  dao.update(id, esasHesabat.getName(), (Integer.parseInt(productNum.getText()) + esasHesabat.getNumber()), "mashin");
                        dao.updateCar(esasHesabat.getName(), esasHesabat.getNumber());
                    } else {

                        dao.save(esasHesabat.getName(), esasHesabat.getNumber(), "mashin");
                    }
                }

            }
            if (id == 0) {
                dao.saveHesab(productName.getText(), Integer.parseInt(productNum.getText()),  "Əlavə edildi", "Yeni məhsul - " + tableName);
            } else {
                dao.saveHesab(esasHesabat.getName(), esasHesabat.getNumber(), esasHesabat.getAction(), esasHesabat.getReason());
            }
//        } else {
//
//            resetAll();
//        }
        load(tableName);
        esasHesabat = new Hesabat();
    }

    @FXML
    private void delete(ActionEvent event) throws SQLException {
        String tableName = "";
        if (depotOption.isSelected()) {
            tableName = "Anbar";
        } else {
            tableName = "mashin";
        }

        MainDao dao = new MainDao();
        dao.delete(id, tableName);
        load(tableName);
      // esasHesabat = new Hesabat("", "", 6, "Məhsul tamamilə silindi", tableName);
       esasHesabat = new Hesabat();
       

    }

    @FXML
    private void reset(ActionEvent event
    ) {

        resetAll();
        esasHesabat = new Hesabat();

    }

    @FXML
    private void takeProduct(ActionEvent event) throws IOException {
        plus.setDisable(true);
        errorMessage.setText("");
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Delete.fxml"));

        Loader.load();

        DeleteController delControl = Loader.getController();
        delControl.setData(id, productName.getText());
        Parent root = Loader.getRoot();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        String tableNam;
        if (carOption.isSelected()) {

            stage.setTitle("Maşına əlavə et");
            delControl.setTitle("Maşına əlavə et");
            tableNam = "Maşın";
        } else {
            stage.setTitle("Anbara əlavə et");
            delControl.setTitle("Anbara əlavə et");
            tableNam = "Anbar";
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        if (carOption.isSelected()) {
            stage.setTitle("Maşından götür");
            delControl.setTitle("Maşından götür");
        } else {
            stage.setTitle("Anbardan götür");
            delControl.setTitle("Anbardan götür");
        }

        stage.getIcons().add(new Image("file:M.ico"));
        stage.setScene(scene);
        stage.showAndWait();
        if (delControl.sendOrder() == 1) {
            if (delControl.getData() > Integer.parseInt(productNum.getText())) {
                productNum.setText(productNum.getText());
                errorMessage.setText("Anbarda yetərli sayda məhsul yoxdur!");
            } else {
                productNum.setText("" + (Integer.parseInt(productNum.getText()) - delControl.getData()));
            }
        }

        esasHesabat = new Hesabat("", productName.getText(), delControl.getData(), "Götürüldü", delControl.sendReason() + "-" + tableNam);

    }

    @FXML
    private void addProduct(ActionEvent event) throws IOException {
        minus.setDisable(true);
        errorMessage.setText("");
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Add.fxml"));

        Loader.load();

        AddController delControl = Loader.getController();
                String tableNam = "";

        delControl.setData(id, productName.getText());
        Parent root = Loader.getRoot();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initModality(Modality.APPLICATION_MODAL);

        if (carOption.isSelected()) {

            stage.setTitle("Maşına əlavə et");
            delControl.setTitle("Maşına əlavə et");
            tableNam = "Maşın";
        } else {
            stage.setTitle("Anbara əlavə et");
            delControl.setTitle("Anbara əlavə et");
            tableNam = "Anbar";
        }

        stage.getIcons().add(new Image("file:M.ico"));
        stage.setScene(scene);
        stage.showAndWait();
        if (delControl.sendOrder() == 1) {
            productNum.setText("" + (Integer.parseInt(productNum.getText()) + delControl.getData()));
        }
        esasHesabat = new Hesabat("", productName.getText(), delControl.getData(), "Əlavə edildi", tableNam);

    }

    public void resetAll() {
        id = 0;
        productName.setText("");
        productNum.setText("");
        carOption.setDisable(false);
        depotOption.setDisable(false);
        productNum.setDisable(false);
        save.setDisable(true);
        plus.setDisable(true);
        minus.setDisable(true);
        errorMessage.setText("");
    }

    public void load(String tableName) {
        for (int i = 0; i < productTable.getItems().size(); i++) {
            productTable.getItems().clear();
        }

        MainDao dao = new MainDao();
        try {
            List<Mehsul> mehsullar = dao.list(tableName);
            for (Mehsul mehsul : mehsullar) {
                productTable.getItems().add(mehsul);

                colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
                colProductNum.setCellValueFactory(new PropertyValueFactory<>("number"));
                colProductId.setVisible(false);
                resetAll();
                productTable.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (productTable.getSelectionModel().getSelectedItem() != null) {
                            errorMessage.setText("");
                            carOption.setDisable(true);
                            depotOption.setDisable(true);
                            productNum.setDisable(true);
                            plus.setDisable(false);
                            minus.setDisable(false);
                            productNum.setStyle("-fx-opacity: 1;");
                            id = productTable.getSelectionModel().getSelectedItem().getId();
                            productName.setText(productTable.getSelectionModel().getSelectedItem().getName());
                            productNum.setText("" + productTable.getSelectionModel().getSelectedItem().getNumber());

                        }
                    }
                }
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadLog(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Hesabat.fxml"));

        Loader.load();
       
        LogController delControl = Loader.getController();
       //  if (esasHesabat.getReason() == "Maşın") {
        Parent root = Loader.getRoot();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Hesabat");
        stage.getIcons().add(new Image("file:M.ico"));
        stage.setScene(scene);
        stage.show();
        }
   // }

}
