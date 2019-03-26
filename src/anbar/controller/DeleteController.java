/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anbar.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 * @author Mardan Safarov
 */
public class DeleteController implements Initializable {

    @FXML
    private Label lblLogin;
    @FXML
    private Label productName;
    @FXML
    private TextField productNum;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private RadioButton saleOption;
    @FXML
    private ToggleGroup option;
    @FXML
    private RadioButton carOption;

    int saveData = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        save.setDisable(true);
        productNum.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer.parseInt(newValue);
                save.setDisable(false);
            } catch (Exception e) {
                save.setDisable(true);
            }
        });
        productNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    productNum.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }

        });
        // TODO
    }

    @FXML
    private void save(ActionEvent event) {
        saveData = 1;
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void cancel(ActionEvent event) {
        saveData = 0;
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void setData(int id, String productNam) {
        productName.setText(productNam);

    }

    public int getData() {
        if (!productNum.getText().equals("")) {
            return Integer.parseInt(productNum.getText());
        } else {
            return 0;
        }
    }

    public int sendOrder() {
        return saveData;
    }

    public String sendReason() {
        String action = "";
        if (saleOption.isSelected()) {
            action = "Satış";
        } else {
            action = "Maşın";
        }
        return action;
    }

    public void setTitle(String name) {
        if (name.contains("Maşın")) {
            carOption.setVisible(false);
        }
        lblLogin.setText(name);

    }

}
