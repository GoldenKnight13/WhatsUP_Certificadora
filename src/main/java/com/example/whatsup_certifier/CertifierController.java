package com.example.whatsup_certifier;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class CertifierController implements Initializable {

    //FXML connectors
    @FXML ScrollPane Log;
    @FXML VBox logRecord;

    //Certifier variables
    private SQL database;
    private ServerListener server;

    @Override public void initialize(URL location, ResourceBundle resources){
        startCertifier();
    }

    private void startCertifier(){

        this.database = new SQL();
        this.server = new ServerListener(this.database, logRecord);

        addLog("Certifier started at: " + java.time.LocalDate.now() + ", " + java.time.LocalTime.now(), logRecord);
    }

    public static void addLog(String server_log, VBox logRecord){

        //When the message is available
        Platform.runLater(() -> {

            //Creates a new element that contains the text
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(2, 2, 2, 5));
            Text text = new Text(server_log);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(2, 2, 2, 5));

            //Adds the record at the end of the log
            hBox.getChildren().add(textFlow);
            logRecord.getChildren().add(hBox);

        });

    }


}
