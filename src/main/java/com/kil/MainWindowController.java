package com.kil;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import java.io.File;

public class MainWindowController {
    private static final FileChooser FILE_CHOOSER = new FileChooser();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final CrcService crcService = new CrcService();

    @FXML
    private ChoiceBox<EncodingType> encodingTypeChoiceBox;

    @FXML
    private TextField polynomial;

    @FXML
    private TextField data;

    @FXML
    private Label crcValue;

    @FXML
    void initialize() {
        encodingTypeChoiceBox.getItems().addAll(EncodingType.values());
        encodingTypeChoiceBox.getSelectionModel().select(0);
    }

    @FXML
    void updateCrc(ActionEvent event) {
        CrcFile crcFile = getCrcFile();
        String crc = crcService.calculateCrc(
                crcFile.getData(),
                crcFile.getEncodingType(),
                Integer.parseInt(crcFile.getPolynomial(), 2));
        crcFile.setCrc(crc);
        initFile(crcFile);
    }

    @FXML
    void checkFile(ActionEvent event) {
        CrcFile crcFile = getCrcFile();
        String calculatedCrc = crcService.calculateCrc(
                crcFile.getData(),
                crcFile.getEncodingType(),
                Integer.parseInt(crcFile.getPolynomial(), 2));

        String savedCrc = crcFile.getCrc();
        showDialog(savedCrc.equals(calculatedCrc) ?
                "Файл не поврежден" :
                "Файл поврежден");
    }

    @SneakyThrows
    @FXML
    void loadFile(ActionEvent event) {
        File file = FILE_CHOOSER.showOpenDialog(getWindow(event));
        if (file == null) {
            return;
        }
        CrcFile crcFile = MAPPER.readValue(file, CrcFile.class);
        initFile(crcFile);
    }

    @SneakyThrows
    @FXML
    void saveFile(ActionEvent event) {
        File file = FILE_CHOOSER.showSaveDialog(getWindow(event));
        if (file == null) {
            return;
        }
        MAPPER.writeValue(file, getCrcFile());
    }

    private void initFile(CrcFile crcFile) {
        data.setText(crcFile.getData());
        encodingTypeChoiceBox.getSelectionModel().select(crcFile.getEncodingType());
        polynomial.setText(crcFile.getPolynomial());
        crcValue.setText(crcFile.getCrc());
    }

    private CrcFile getCrcFile() {
        CrcFile crcFile = new CrcFile();
        crcFile.setData(data.getText());
        crcFile.setEncodingType(encodingTypeChoiceBox.getValue());
        crcFile.setPolynomial(polynomial.getText());
        crcFile.setCrc(crcValue.getText());
        return crcFile;
    }

    private void showDialog(@Nonnull String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Уведомление:");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Window getWindow(ActionEvent event) {
        return ((Node) event.getTarget()).getScene().getWindow();
    }

}
