package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.gui.MainWindowController;
import view.gui.SelectWindowController;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        FXMLLoader mainLoader = new FXMLLoader(HelloApplication.class.getResource("MainWindow.fxml"));
      //  mainLoader.setRoot(new AnchorPane());
        Parent mainWindow = mainLoader.load();

        MainWindowController mainWindowController = mainLoader.getController();
        stage.setTitle("Hello!");
        stage.setScene(new Scene(mainWindow));
        stage.show();

        FXMLLoader secondaryLoader = new FXMLLoader(HelloApplication.class.getResource("SelectWindow.fxml"));
      //  secondaryLoader.setRoot(new AnchorPane());
        Parent secondaryWindow = secondaryLoader.load();

        SelectWindowController selectWindowController = secondaryLoader.getController();
        selectWindowController.setMainWindowController(mainWindowController);

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Select");
        secondaryStage.setScene(new Scene(secondaryWindow));
        secondaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}