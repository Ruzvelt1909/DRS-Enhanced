package com.mycompany.drsenhanced;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {
    public static void switchTo(Scene currentScene, String fxmlFile, String title) {
        try {
            Parent root = FXMLLoader.load(SceneSwitcher.class.getResource(fxmlFile));
            Stage stage = (Stage) currentScene.getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
