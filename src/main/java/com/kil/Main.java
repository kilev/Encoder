package com.kil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;

public class Main extends Application {

    @SneakyThrows
    @Override
    public void start(Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainWindow.fxml"));
        Parent mainWindow = loader.load();
        stage.setScene(new Scene(mainWindow));
        stage.setResizable(false);
        stage.setTitle("Моделирование марковских процессов");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
