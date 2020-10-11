package com.kil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainWindowController {

    @FXML
    private TextField tool1M;

    @FXML
    private TextField transport12M;

    @FXML
    private TextField tool2M;

    @FXML
    private TextField transport23M;

    @FXML
    private TextField tool3M;

    @FXML
    private TextField tool1S;

    @FXML
    private TextField transport12S;

    @FXML
    private TextField tool2S;

    @FXML
    private TextField transport23S;

    @FXML
    private TextField tool3S;

    @FXML
    private TextField inputM;

    @FXML
    private TextField inputS;

    @FXML
    private Label totalPartsGenarated;

    @FXML
    private Label tool1Load;

    @FXML
    private Label tool1AverageTime;

    @FXML
    private Label tool2Load;

    @FXML
    private Label tool3Load;

    @FXML
    private Label tool2AverageTime;

    @FXML
    private Label tool3AverageTime;

    @FXML
    private Label tool2MaxPartCount;

    @FXML
    private Label tool1MaxPartCount;

    @FXML
    private Label tool3MaxPartCount;

    @FXML
    private Label tool1AverageTimeInQueue;

    @FXML
    private Label tool2AverageTimeInQueue;

    @FXML
    private Label tool3AverageTimeInQueue;

    @FXML
    private Label tool1AveragePartCountInQueue;

    @FXML
    private Label tool2AveragePartCountInQueue;

    @FXML
    private Label tool3AveragePartCountInQueue;

    @FXML
    void compute(ActionEvent event) {

    }

    @FXML
    void showHelp(ActionEvent event) {

    }

}
