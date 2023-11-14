package com.example.whatsup_certifier;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CertifierController implements Initializable {

    @Override public void initialize(URL location, ResourceBundle resources){
        SQL database = new SQL();
        database.getQuery("");
    }

}
