package com.kil;

import com.kil.factory.FactoryModel;
import com.kil.factory.processor.Processor;
import com.kil.factory.processor.ProcessorSlot;
import com.kil.stream.GaussianRandomDurationStream;
import com.kil.stream.RandomTimeStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.time.Duration;
import java.util.Arrays;

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
    private TextField modelingTime;

    @FXML
    void compute(ActionEvent event) {
        showModelStatistic(new Computer(getModel()).compute());
    }

    private FactoryModel getModel() {
        return new FactoryModel(getDuration(modelingTime),
                new RandomTimeStream(new GaussianRandomDurationStream(getDuration(inputM), getDuration(inputS))),
                Arrays.asList(
                        new Processor(new ProcessorSlot(new GaussianRandomDurationStream(getDuration(tool1M), getDuration(tool1S)))),
                        new Processor(new ProcessorSlot(new GaussianRandomDurationStream(getDuration(tool2M), getDuration(tool2S)))),
                        new Processor(new ProcessorSlot(new GaussianRandomDurationStream(getDuration(tool3M), getDuration(tool3S)))))
                );
    }

    private Duration getDuration(TextField field) {
        return Duration.parse(field.getText());
    }

    private void showModelStatistic(FactoryModel factoryModel) {

    }

    private void collectStatistic(FactoryModel factoryModel) {

    }

    @FXML
    void showHelp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Справка");
        alert.setHeaderText(null);
        String contentText = "ISO 8601 — международный стандарт, выпущенный организацией ISO (International Organization for Standardization),\n который описывает форматы дат и времени и даёт рекомендации для его использования в международном контексте.\n" +
                "\n" +
                "Формат: P (n) DT (n) H (n) M (n) S\n" +
                "\n" +
                "Где:\n" +
                "\n" +
                "P - это обозначение продолжительности (именуемое «периодом»), которое всегда ставится в начале продолжительности.\n" +
                "D - обозначение дня, которое следует за значением количества дней.\n" +
                "T - обозначение времени, которое предшествует компонентам времени.\n" +
                "H - обозначение часа, которое следует за значением количества часов.\n" +
                "M - указатель минут, следующий за значением количества минут.\n" +
                "S - второй указатель, следующий за значением количества секунд.\n" +
                "\n" +
                "Например:\n" +
                "P3DT12H30M5S\n" +
                "\n" +
                "Представляет продолжительность в четыре дня, двенадцать часов, тридцать минут и пять секунд.";

        TextArea textArea = new TextArea(contentText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        Label label = new Label("Формат продолжительности ISO 8601");
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setContent(expContent);
        alert.showAndWait();
    }

}
