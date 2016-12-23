package ua.dean.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.UUID;

public class MainApp extends Application {

    public static StringProperty text = new SimpleStringProperty("111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");

    public static void main(String[] args) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            text.set(UUID.randomUUID().toString());
        }));
        timeline.setCycleCount(3);
        timeline.play();
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(new Image("images.jpg"));
        primaryStage.setScene(initRootLayout());
        primaryStage.show();
    }

    public Scene initRootLayout() {
        Scene scene = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getClassLoader().getResource("ua/dean/ui/MainPanel.fxml"));
            Pane rootLayout = loader.load();
            MainAppController controller = loader.getController();
            controller.setTextProperty(text);
            scene = new Scene(rootLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }
}
