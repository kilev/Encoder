package com.kil;

import com.google.common.collect.Lists;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MainWindowController {
    private static final Character LINE_SEPARATOR = '\n';

    private static final String DEFAULT_KEY = "ВАЗА";
    private static final String DEFAULT_OPEN_TEXT = "КРИПТОГРАФИЯ";

    private static final List<Character> DICTIONARY = List.of(
            'А', 'а', 'Б', 'б', 'В', 'в', 'Г', 'г',
            'Д', 'д', 'Е', 'е', 'Ё', 'ё', 'Ж', 'ж',
            'З', 'з', 'И', 'и', 'Й', 'й', 'К', 'к',
            'Л', 'л', 'М', 'м', 'Н', 'н', 'О', 'о',
            'П', 'п', 'Р', 'р', 'С', 'с', 'Т', 'т',
            'У', 'у', 'Ф', 'ф', 'Х', 'х', 'Ц', 'ц',
            'Ч', 'ч', 'Ш', 'ш', 'Щ', 'щ', 'Ъ', 'ъ',
            'Ы', 'ы', 'Ь', 'ь', 'Э', 'э', 'Ю', 'ю',
            'Я', 'я', '?', '#', '@', '$', '%', '^',
            '&', '*', LINE_SEPARATOR);

    private static final FileChooser fileChooser = new FileChooser();

    @FXML
    private TextField keyField;

    @FXML
    private TextArea openText;

    @FXML
    private TextArea closeText;

    @FXML
    void decodeCloseText(ActionEvent event) {
        openText.setText(decode(closeText.getText(), keyField.getText()));
    }

    @FXML
    void encodeOpenText(ActionEvent event) {
        closeText.setText(encode(openText.getText(), keyField.getText()));
    }

    @FXML
    void loadCloseDataFromFile(ActionEvent event) {
        File file = fileChooser.showOpenDialog(getWindow());
        if (file != null) {
            closeText.setText(readFromFile(file));
        }
    }

    @FXML
    void loadOpenDataFromFile(ActionEvent event) {
        File file = fileChooser.showOpenDialog(getWindow());
        if (file != null) {
            openText.setText(readFromFile(file));
        }
    }


    @FXML
    void saveCloseDataToFile(ActionEvent event) {
        File file = fileChooser.showOpenDialog(getWindow());
        if (file != null) {
            writeToFile(closeText.getText(), file);
        }
    }

    @FXML
    void saveOpenDataToFile(ActionEvent event) {
        File file = fileChooser.showOpenDialog(getWindow());
        if (file != null) {
            writeToFile(openText.getText(), file);
        }
    }

    @FXML
    void initialize() {
        loadSampleData();
    }

    private void loadSampleData() {
        keyField.setText(DEFAULT_KEY);
        openText.setText(DEFAULT_OPEN_TEXT);
    }

    private String encode(String text, String key) {
        StringBuilder stringBuilder = new StringBuilder();

        KeyIterator keyIterator = new KeyIterator(key);

        for (Character character : Lists.charactersOf(text)) {
            int characterDictionaryIndex = DICTIONARY.indexOf(character);
            int keyCharacterDictionaryIndex = DICTIONARY.indexOf(keyIterator.getCharacter());

            int newEncodedCharacterIndex = (characterDictionaryIndex + keyCharacterDictionaryIndex + 1) % DICTIONARY.size();

            char encodedCharacter = DICTIONARY.get(newEncodedCharacterIndex);
            stringBuilder.append(encodedCharacter);
        }

        return stringBuilder.toString();
    }

    private String decode(String text, String key) {
        StringBuilder stringBuilder = new StringBuilder();

        KeyIterator keyIterator = new KeyIterator(key);

        for (Character character : Lists.charactersOf(text)) {
            int characterDictionaryIndex = DICTIONARY.indexOf(character);
            int keyCharacterDictionaryIndex = DICTIONARY.indexOf(keyIterator.getCharacter());

            int newDecodedCharacterIndex = characterDictionaryIndex - keyCharacterDictionaryIndex - 1;
            newDecodedCharacterIndex = newDecodedCharacterIndex < 0 ?
                    newDecodedCharacterIndex + DICTIONARY.size() :
                    newDecodedCharacterIndex;

            stringBuilder.append(DICTIONARY.get(newDecodedCharacterIndex));
        }

        return stringBuilder.toString();
    }

    @SneakyThrows
    private String readFromFile(File file) {
        return Files.readString(Paths.get(file.toURI()));
    }

    @SneakyThrows
    private void writeToFile(String text, File file) {
        Files.writeString(Paths.get(file.toURI()), text);
    }

    private Window getWindow() {
        return keyField.getScene().getWindow();
    }

}
