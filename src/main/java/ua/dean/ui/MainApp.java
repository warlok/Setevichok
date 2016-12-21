package ua.dean.ui;/**
 * Created by Denys on 21/12/2016.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(initRootLayout());
        primaryStage.show();
    }

    public Scene initRootLayout() {
        Scene scene = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getClassLoader().getResource("ua/dean/ui/MainPanel.fxml"));
            Pane rootLayout = loader.load();
            scene = new Scene(rootLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }
}
