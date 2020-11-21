package com.kil;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.SneakyThrows;

import java.io.File;

public class MainWindowController {
    private static final FileChooser FILE_CHOOSER = new FileChooser();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String LINE_SEPARATOR = System.lineSeparator();

    //    Checksum checksum = new CRC32();

    @FXML
    private ChoiceBox<EncodingType> encodingTypeChoiceBox;

    @FXML
    private ChoiceBox<EncodingMethod> encodingMethodChoiceBox;

    @FXML
    private TextField polynomial;

    @FXML
    private TextField data;

    @FXML
    void initialize() {
        encodingTypeChoiceBox.getItems().addAll(EncodingType.values());
        encodingMethodChoiceBox.getItems().addAll(EncodingMethod.values());

        encodingTypeChoiceBox.getSelectionModel().select(0);
        encodingMethodChoiceBox.getSelectionModel().select(0);
    }

    @FXML
    void checkFile(ActionEvent event) {

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
        encodingMethodChoiceBox.getSelectionModel().select(crcFile.getEncodingMethod());
    }

    private CrcFile getCrcFile() {
        CrcFile crcFile = new CrcFile();
        crcFile.setData(data.getText());
        crcFile.setEncodingType(encodingTypeChoiceBox.getValue());
        crcFile.setEncodingMethod(encodingMethodChoiceBox.getValue());
        return crcFile;
    }

    private Window getWindow(ActionEvent event) {
        return ((Node) event.getTarget()).getScene().getWindow();
    }

}
