package ua.dean.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainAppController {

    private StringProperty text;

    @FXML
    private Label label1;

    @FXML
    private void initialize() {

    }

    public void setTextProperty(StringProperty text) {
        label1.textProperty().bind(text);
        this.text = text;
    }
}
