package ua.dean.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import ua.dean.Setevichok;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class MainApp extends Application {

    private static StringProperty text = new SimpleStringProperty("Diagnostic is started");
    private static StringBuilder sb = new StringBuilder("Diagnostic is started\n");

    public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {
        new Thread(() -> launch(args)).start();
        Setevichok setevichok = new Setevichok();
        sb.append("Getting list of gateways...");
        updateText();
        List<String> gateways =  setevichok.getGateways();
        sb.append("done\n");
        updateText();
        if (gateways.isEmpty()) {
            sb = new StringBuilder("Не доступен шлюз, обратитесь к админисратору");
            updateText();
            return;
        }
        sb.append("Getting list of UP interfaces...");
        updateText();
        List<String> interfaces = setevichok.getUpInterfaces();
        if (interfaces.isEmpty()) {
            sb = new StringBuilder("Нет активных интерфейсов, обратитесь к админисратору");
            updateText();
            return;
        }
        sb.append("done\nPing Gateways...");
        updateText();
        boolean gwStatus = false;
        for (String gw : gateways) {
            boolean status = setevichok.pingIsSuccess(gw);
            if (status == true) gwStatus = true;
        }
        if (gwStatus == false) {
            sb = new StringBuilder("Не доступен шлюз, обратитесь к админисратору");
            updateText();
            return;
        }
        sb.append("done\nPing External resources...");
        updateText();
        if (!setevichok.pingIsSuccess(setevichok.TEST_IP1)) {
            if (!setevichok.pingIsSuccess(setevichok.TEST_IP2)) {
                sb = new StringBuilder("Не работает интернет, отправьте запрос в провайдер");
                updateText();
                return;
            }
        }
        sb.append("done\nTry to lookup domain names...");
        if (setevichok.nsLookupIsSuccess(setevichok.TEST_DOMAIN1) || setevichok.nsLookupIsSuccess(setevichok.TEST_DOMAIN2)) {
                sb = new StringBuilder("Интернет работает. Перезагрузитесь или обратитесь к администратору");
                updateText();
                return;
        }
        sb.append("done\nPing DNS...");
        updateText();
        Set<String> dnsServers =  setevichok.getDnsServers();
        boolean dnsStatus = false;
        for (String dns : dnsServers) {
            boolean status = setevichok.pingIsSuccess(dns);
            if (status == true) dnsStatus = true;
        }
        if (dnsStatus == true) {
            sb = new StringBuilder("Не исправны или неверные DNS-сервера, обратитесь к администратору для исправления настроек");
            updateText();
            return;
        }
        sb = new StringBuilder("Не доступны DNS-сервера, обратитесь к администратору для исправления настроек");
        updateText();
    }

    private static void updateText() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            text.set(sb.toString());
        }));
        timeline.setCycleCount(1);
        timeline.play();

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
